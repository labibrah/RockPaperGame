package com.gradle.example;

import com.johncsinclair.consoletable.ConsoleTable;
import com.johncsinclair.consoletable.Styles;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class TableGenerator {

    int numberOfMoves;
    String[] moves;


    public TableGenerator(int numberOfMoves, String[] moves) {
        this.numberOfMoves = numberOfMoves;
        this.moves = moves;
    }

    public void generateTable() {
        ConsoleTable table = new ConsoleTable();
        String[] headers = new String[numberOfMoves + 1];
        headers[0] = "v PC\\User >";

        for (int i = 0; i < numberOfMoves; i++) {
            headers[i + 1] = moves[i];
        }
        table.setHeaders(headers);

        for (int i = 1; i < headers.length; i++) {
            ArrayList<String> resultsRow = new ArrayList<>();
            resultsRow.add(headers[i]);
            for (int k = 1; k < headers.length; k++) {
                String outcome = determineOutcome(headers[i], headers[k]);
                resultsRow.add(outcome);
            }
            rowDataBuilder(resultsRow);

            String[] row = rowDataBuilder(resultsRow);
            table.addRow(row);
        }


        System.out.print(table.withStyle(Styles.BASIC));

    }

    public String determineOutcome(String userMove, String computerMove) {
        Map<String, Set<String>> movesItCanDefeat = Main.movesItCanDefeat;

        return OutcomeDeterminer.getOutcome(userMove, computerMove, movesItCanDefeat);
    }

    public String[] rowDataBuilder(ArrayList<String> listOfOutcomes) {
        return listOfOutcomes.toArray(new String[listOfOutcomes.size()]);
    }

}
