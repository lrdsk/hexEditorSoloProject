package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SequenceHandler {

    public static String[] getSequenceByIndex(int[] indexes, int lengthSeq, List<String[]> stringsArray){
        String[] row = stringsArray.get(indexes[0]);
        if(indexes[1] + lengthSeq > row.length){
            int itSeq = 0;
            int curIndexRow = indexes[0];
            int curIndexColumn = indexes[1];
            int remainRows = stringsArray.size() - curIndexRow;
            String[] resultSeq = new String[lengthSeq];

            while(remainRows > 0 && itSeq < lengthSeq){
                resultSeq[itSeq] = row[curIndexColumn];
                itSeq++;
                curIndexColumn++;
                if(!(curIndexColumn < row.length)){
                    curIndexColumn = 0;
                    curIndexRow++;
                    remainRows = stringsArray.size() - curIndexRow;
                    if(curIndexRow < stringsArray.size()){
                        row = stringsArray.get(curIndexRow);
                    }
                }
            }
            return  resultSeq;
        }
        return Arrays.copyOfRange(row, indexes[1], indexes[1]+lengthSeq);
    }

    public static void clearCells(int[] indexes, int lengthSeq, List<String[]> stringsArray){
        String[] row = stringsArray.get(indexes[0]);

        int itSeq = 0;
        int curIndexRow = indexes[0];
        int curIndexColumn = indexes[1];
        int remainRows = stringsArray.size() - curIndexRow;

        while(remainRows > 0 && itSeq < lengthSeq){
            row[curIndexColumn] = String.valueOf(0);
            itSeq++;
            curIndexColumn++;
            if(curIndexColumn >= row.length) {
                curIndexColumn = 0;
                curIndexRow++;
                remainRows = stringsArray.size() - curIndexRow;
                if (curIndexRow < stringsArray.size()) {
                    row = stringsArray.get(curIndexRow);
                }
            }
        }
    }

    public static String[] mergeRows(String[] currentRow, String[] insertedRow, int index){
        int newArrayLength = currentRow.length + insertedRow.length;

        String[] mergedArray = new String[newArrayLength];

        System.arraycopy(currentRow, 0, mergedArray, 0, index);
        System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
        System.arraycopy(currentRow, index, mergedArray, index + insertedRow.length, currentRow.length - index);

        return mergedArray;
    }

    public static String[] replaceRows(String[] currentRow, String[] insertedRow, int index){
        int newArrayLength = 0;
        String[] mergedArray;

        if(currentRow.length - index >= insertedRow.length){
            newArrayLength = currentRow.length;
            mergedArray = new String[newArrayLength];
            System.arraycopy(currentRow, 0, mergedArray, 0, index);
            System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
            System.arraycopy(currentRow, index + insertedRow.length,
                    mergedArray, index + insertedRow.length, currentRow.length - index - insertedRow.length);
        }else{
            newArrayLength = currentRow.length - (currentRow.length - index) + insertedRow.length;
            mergedArray = new String[newArrayLength];
            System.arraycopy(currentRow, 0, mergedArray, 0, index);
            System.arraycopy(insertedRow, 0, mergedArray, index, insertedRow.length);
        }

        return mergedArray;
    }

    public static int getMaxColumnCountInTable(List<String[]> table){
        int max = 0;
        for(String[] row : table){
            if(row.length > max){
                max = row.length;
            }
        }
        return max;
    }

    public static List<String[]> fillTableWithZeros(List<String[]> table){
        int maxColumnSize = getMaxColumnCountInTable(table);
        StringBuilder resultRow = new StringBuilder();
        List<String[]> tableWithZeros = new ArrayList<>();
        for(String[] row : table){
            for(int i = 0; i < maxColumnSize; i++){
                if(i < row.length){
                    resultRow.append(row[i]).append("\t");
                }else{
                    resultRow.append(0).append("\t");
                }
            }
            tableWithZeros.add(resultRow.toString().split("\t"));
            resultRow.delete(0, resultRow.length());
        }
        return tableWithZeros;
    }

    public List<String> findPatternInTable(List<String[]> table, String pattern){
        String[] strings = pattern.split(" ");
        String[] stringsOverlap = new String[strings.length];
        List<String[]> matches = new ArrayList<>();
        int curIndexColumn = 0;
        int tableRowIterator = 0;
        int numberOfStrings = 0;

        while(curIndexColumn < table.get(tableRowIterator).length && numberOfStrings < strings.length){
            if(strings[numberOfStrings] == table.get(tableRowIterator)[curIndexColumn]){
            }
        }
        return new ArrayList<>();
    }
}
