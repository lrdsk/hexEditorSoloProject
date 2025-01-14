package org.example.utils;

import org.example.frames.help.ShowFindIndexesFrame;
import org.example.services.PatternFoundCallbackService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SequenceHandler {
    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private int currentSizeListIndexes = 0;

    public static String[] getSequenceByIndex(int rowIndex, int columnIndex, int lengthSeq, List<String[]> table) {
        String[] row = table.get(rowIndex);
        if (columnIndex + lengthSeq > row.length) {
            int itSeq = 0;
            int curIndexRow = rowIndex;
            int curIndexColumn = columnIndex;
            String[] resultSeq = new String[lengthSeq];

            while (curIndexRow < table.size() && itSeq < lengthSeq) {
                resultSeq[itSeq] = row[curIndexColumn];
                itSeq++;
                curIndexColumn++;
                if (!(curIndexColumn < row.length)) {
                    curIndexColumn = 0;
                    curIndexRow++;
                    if (curIndexRow < table.size()) {
                        row = table.get(curIndexRow);
                    }
                }
            }
            return resultSeq;
        }
        return Arrays.copyOfRange(row, columnIndex, columnIndex + lengthSeq);
    }

    public static void clearCellsWithZeros(int rowIndex, int columnIndex, int lengthSeq, List<String[]> table) {
        String[] row = table.get(rowIndex);

        int itSeq = 0;
        int curIndexRow = rowIndex;
        int curIndexColumn = columnIndex;
        int remainRows = table.size() - curIndexRow;

        while (remainRows > 0 && itSeq < lengthSeq) {
            if (curIndexColumn != 0) {
                row[curIndexColumn] = String.valueOf(0);
                itSeq++;
            }
            curIndexColumn++;
            if (curIndexColumn >= row.length) {
                curIndexColumn = 0;
                curIndexRow++;
                remainRows = table.size() - curIndexRow;
                if (curIndexRow < table.size()) {
                    row = table.get(curIndexRow);
                }
            }
        }
    }

    public static List<String[]> clearCellsWithShift(int rowIndex, int columnIndex, int lengthSeq, List<String[]> table) {
        String[] row = table.get(rowIndex);
        String[] mergedArray;
        int newArrayLength = 0;

        if (row.length - columnIndex >= lengthSeq) {
            newArrayLength = row.length - lengthSeq;
        }
        mergedArray = new String[newArrayLength];
        System.arraycopy(row, 0, mergedArray, 0, columnIndex);
        System.arraycopy(row, columnIndex + lengthSeq,
                mergedArray, columnIndex, row.length - columnIndex - lengthSeq);

        table.remove(rowIndex);
        table.add(rowIndex, mergedArray);
        return table;
    }

    public static String[] mergeRows(String[] currentRow, String[] insertedRow, int index) {
        int newArrayLength = currentRow.length + insertedRow.length;
        String[] mergedArray = new String[newArrayLength];

        System.arraycopy(currentRow, 0, mergedArray, 0, index);
        System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
        System.arraycopy(currentRow, index, mergedArray, index + insertedRow.length, currentRow.length - index);

        return mergedArray;
    }

    public static String[] replaceRows(String[] currentRow, String[] insertedRow, int index) {
        int newArrayLength = 0;
        String[] mergedArray;

        if (currentRow.length - index >= insertedRow.length) {
            newArrayLength = currentRow.length;
            mergedArray = new String[newArrayLength];
            System.arraycopy(currentRow, 0, mergedArray, 0, index);
            System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
            System.arraycopy(currentRow, index + insertedRow.length,
                    mergedArray, index + insertedRow.length, currentRow.length - index - insertedRow.length);
        } else {
            newArrayLength = currentRow.length - (currentRow.length - index) + insertedRow.length;
            mergedArray = new String[newArrayLength];
            System.arraycopy(currentRow, 0, mergedArray, 0, index);
            System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
        }

        return mergedArray;
    }

    public static int getMaxColumnCountInTable(List<String[]> table) {
        int max = 0;
        for (String[] row : table) {
            if (row.length > max) {
                max = row.length;
            }
        }
        return max;
    }

    public static List<String[]> fillTableWithZeros(List<String[]> table) {
        int maxColumnSize = getMaxColumnCountInTable(table);
        StringBuilder resultRow = new StringBuilder();
        List<String[]> tableWithZeros = new ArrayList<>();
        for (String[] row : table) {
            for (int i = 0; i < maxColumnSize; i++) {
                if (i < row.length) {
                    resultRow.append(row[i]).append("\t");
                } else {
                    resultRow.append(0).append("\t");
                }
            }
            tableWithZeros.add(resultRow.toString().split("\t"));
            resultRow.delete(0, resultRow.length());
        }
        return tableWithZeros;
    }

    public void findPatternInTable(List<String[]> table, String pattern, int indexPage, PatternFoundCallbackService callback, ShowFindIndexesFrame showFindIndexesFrame) {
        Callable<List<int[]>> findPatternTask = () -> {

            String[] strings = pattern.split(" ");
            List<int[]> listIndexes = new ArrayList<>();
            int[] indexes = new int[2];
            int curIndexColumn = 0;
            int curIndexRow = 0;
            String[] row = table.get(curIndexRow);

            while (curIndexRow < table.size()) {
                int numberOfOverlap = 0;
                if (Objects.equals(row[curIndexColumn], strings[0])) {
                    indexes[0] = curIndexRow;
                    indexes[1] = curIndexColumn;
                    numberOfOverlap++;
                    int curIndexColumnInOverlap = curIndexColumn;
                    for (int i = 1; i < strings.length; i++) {
                        curIndexColumnInOverlap++;
                        if (curIndexColumnInOverlap >= row.length) {
                            curIndexColumnInOverlap = 0;
                            curIndexRow++;
                            if (curIndexRow < table.size()) {
                                row = table.get(curIndexRow);
                            }
                        }
                        if (Objects.equals(row[curIndexColumnInOverlap], strings[i])) {
                            numberOfOverlap++;
                        }
                    }
                }

                curIndexColumn++;
                if (curIndexColumn >= row.length) {
                    curIndexColumn = 0;
                    curIndexRow++;
                    if (curIndexRow < table.size()) {
                        row = table.get(curIndexRow);
                    }
                }
                if (numberOfOverlap == strings.length) {
                    listIndexes.add(indexes);
                    currentSizeListIndexes++;
                    callback.onPatternFound(indexes, indexPage);

                    if(currentSizeListIndexes == 1){
                        StringBuilder text = new StringBuilder();
                        text
                                .append("Введенная вами последовательность для поиска находится в ячейке: ")
                                .append(listIndexes.get(0)[0] + 1)
                                .append(" ")
                                .append(listIndexes.get(0)[1]);

                        showFindIndexesFrame.updateJLabel(text.toString());
                    }
                    indexes = new int[2];
                }
            }
            return listIndexes;
        };
        executor.submit(findPatternTask);
    }
    public void shutdownExecutor(){
        executor.shutdown();
    }
}
