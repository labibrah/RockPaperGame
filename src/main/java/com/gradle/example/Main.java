package com.gradle.example;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class Main {
    public static final Map<String, Set<String>> movesItCanDefeat = new HashMap<>();
    public static int[][] outcomeArray;
    public static String[] moves;

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        //A cryptographically strong random key generated using SecureRandom
        RandomKeyGenerator randomKey = new RandomKeyGenerator();
        String originalKey = randomKey.getGeneratedString();

        Scanner scanner = new Scanner(System.in);
        int numberOfMoves = args.length;
        if (args.length == 0) {
            System.out.println("Moves cannot be 0");
            return;
        } else if (numberOfMoves % 2 == 0) {
            System.out.println("Number of moves must be odd");
            return;
        } else if (numberOfMoves < 3) {
            System.out.println("Number of moves must be a minimum of 3.");
            return;
        }

        //Checks for duplicate moves entered by user
        Map<String, Integer> map = new HashMap<>();

        for (String arg : args) {
            if (map.containsKey(arg)) {
                System.out.println("Moves cannot have duplicate names");
                return;
            } else {
                map.put(arg, 1);
            }
        }

        moves = args;

        outcomeArray = new int[numberOfMoves][numberOfMoves];

        initializeMovesToDefeat(numberOfMoves);

        // Game loop
        while (true) {
            // Makes a move and then creates HMAC key which is shown to the user
            String computerMove = getRandomMove(moves);
            HMAC hmacObject = new HMAC("HmacSHA256", computerMove, originalKey);
            String hmacKey = hmacObject.getHMACkey();
            System.out.println("HMAC: " + hmacKey);

            //Gives list of available moves as well as menu options
            System.out.println("Available moves:");
            int i = 1;
            for (String move : moves) {
                System.out.println(i + " - " + move);
                i++;
            }
            System.out.println("0 - exit");
            System.out.println("? - help");
            System.out.print("Enter your move: ");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            if (input.equals("?")) {
                TableGenerator table = new TableGenerator(args.length, moves);
                table.generateTable();
                continue;
            }
            int moveIndex = Integer.parseInt(input) - 1;
            String playerMove = moves[moveIndex];

            if (!movesItCanDefeat.containsKey(moves[moveIndex])) {
                System.out.println("Incorrect move. Try again.");
                continue;
            }
            System.out.println("Your move: " + playerMove);


            System.out.println("Computer move: " + computerMove);

            // Determine the winner
            String outcome = OutcomeDeterminer.getOutcome(playerMove, computerMove, movesItCanDefeat);

            System.out.println(outcome);

            System.out.println("HMAC key: " + originalKey);
            System.out.println("You can visit the link below to verify your results: ");
            System.out.println("https://www.freeformatter.com/hmac-generator.html");

            return;

        }
    }


    private static void initializeMovesToDefeat(int noOfMoves) {
        //Logic to initialize matrix of who can defeat whom.
        int p = noOfMoves >> 1;
        for (int i = 0; i < outcomeArray.length; i++) {
            for (int j = 0; j < outcomeArray[i].length; j++) {
                outcomeArray[i][j] = (int) Math.signum((i - j + p + noOfMoves) % noOfMoves - p);
            }
        }


        for (int i = 0; i < outcomeArray.length; i++) {
            Set<String> defeats = new HashSet<>();
            for (int j = 0; j < outcomeArray[i].length; j++) {
                if (outcomeArray[i][j] == 1) {
                    defeats.add(moves[j]);
                }
            }
            movesItCanDefeat.put(moves[i], defeats);

        }
//      Uncomment to see who can defeat whom
//        for(String s : movesItCanDefeat.keySet()){
//            System.out.print(s + " will defeat: ");
//            for (String st: movesItCanDefeat.get(s)) {
//                System.out.print(st + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    private static String getRandomMove(String[] moves) {
        Random random = new Random();
        return moves[random.nextInt(moves.length)];
    }
}

