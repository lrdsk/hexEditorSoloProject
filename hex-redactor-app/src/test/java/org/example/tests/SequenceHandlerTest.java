package org.example.tests;

import org.example.utils.SequenceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SequenceHandlerTest {

    private List<String[]> table;

    @BeforeEach
    void setup() {
        table = new ArrayList<>();
        table.add(new String[]{"1","A", "B", "C", "D", "E"});
        table.add(new String[]{"2","F", "G", "H", "I", "J"});
        table.add(new String[]{"3","K", "L", "M", "N", "O"});
    }
    @Test
    void getSequenceByIndex_ShouldReturnCorrectSequence() {
        int rowIndex = 1;
        int columnIndex = 3;
        int lengthSeq = 4;

        String[] expected = {"H", "I", "J", "3"};

        String[] result = SequenceHandler.getSequenceByIndex(rowIndex, columnIndex, lengthSeq, table);
        assertArrayEquals(expected, result);
    }
    @Test
    void clearCellsWithZeros_ShouldClearCellsInSequence() {
        int rowIndex = 1;
        int columnIndex = 3;
        int lengthSeq = 4;

        List<String[]> expected = new ArrayList<>(Arrays.asList(
                new String[]{"1","A", "B", "C", "D", "E"},
                new String[]{"2","F", "G", "0", "0", "0"},
                new String[]{"3","0", "L", "M", "N", "O"}
        ));

        SequenceHandler.clearCellsWithZeros(rowIndex, columnIndex, lengthSeq, table);

        for(int i = 0; i < expected.size(); i++){
            assertArrayEquals(expected.get(i), table.get(i));
        }

    }
    @Test
    void clearCellsWithShift_ShouldClearCellsAndShiftRow() {
        int rowIndex = 1;
        int columnIndex = 2;
        int lengthSeq = 2;

        List<String[]> expected = new ArrayList<>(Arrays.asList(
                new String[]{"1","A", "B", "C", "D", "E"},
                new String[]{"2","F", "I", "J"},
                new String[]{"3","K", "L", "M", "N", "O"}
        ));

        List<String[]> result = SequenceHandler.clearCellsWithShift(rowIndex, columnIndex, lengthSeq, table);

        for(int i = 0; i < expected.size(); i++){
            assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void mergeRows_ShouldMergeRowsAtIndex() {
        String[] currentRow = {"A", "B", "C", "D", "E"};
        String[] insertedRow = {"X", "Y", "Z"};
        int index = 2;

        String[] expected = {"A", "B", "X", "Y", "Z", "C", "D", "E"};

        String[] result = SequenceHandler.mergeRows(currentRow, insertedRow, index);

        assertArrayEquals(expected, result);
    }

    @Test
    void replaceRows_ShouldReplaceRowsAtIndex() {
        String[] currentRow = {"A", "B", "C", "D", "E"};
        String[] insertedRow = {"X", "Y", "Z"};
        int index = 2;

        String[] expected = {"A", "B", "X", "Y", "Z"};

        String[] result = SequenceHandler.replaceRows(currentRow, insertedRow, index);

        assertArrayEquals(expected, result);
    }

    @Test
    void getMaxColumnCountInTable_ShouldReturnMaximumColumnCount() {
        List<String[]> testTable = new ArrayList<>(Arrays.asList(
                new String[]{"A", "B"},
                new String[]{"C", "D", "E", "F"},
                new String[]{"G"}
        ));
        int expected = 4;

        int result = SequenceHandler.getMaxColumnCountInTable(testTable);

        assertEquals(expected, result);
    }

    @Test
    void fillTableWithZeros_ShouldFillTableWithZeros() {
        List<String[]> expected = new ArrayList<>(Arrays.asList(
                new String[]{"1","A", "B", "C", "D", "E"},
                new String[]{"2","F", "G", "H", "I", "J"},
                new String[]{"3","K", "L", "M", "N", "O"}
        ));

        List<String[]> result = SequenceHandler.fillTableWithZeros(table);

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void findPatternInTable_ShouldReturnIndexes() {
        String pattern = "G H I";
        int[] expected = {2, 3};
        int[] result = SequenceHandler.findPatternInTable(table, pattern);

        assertArrayEquals(expected, result);
    }

}

