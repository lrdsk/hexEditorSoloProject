package org.example.models;

import org.example.utils.SequenceHandler;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ByteTableModel extends AbstractTableModel {
    private final int countColumn;
    private final List<String[]> dataArrayList = new ArrayList<>();

    public ByteTableModel(int countColumn){
        this.countColumn = countColumn;
    }

    @Override
    public int getRowCount() {
        return dataArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return countColumn;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = dataArrayList.get(rowIndex);
        return row[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex == 0){
            return "Номер столбца/номер строки";
        }

        for(int i = 0; i < countColumn; i++){
            if(i == columnIndex){
                return String.valueOf(i);
            }
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0; // Позволяет редактирование кроме 1 столбца ячеек
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(columnIndex > 0) {
            String[] row = dataArrayList.get(rowIndex);
            row[columnIndex] = value.toString();
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void addDate(List<String[]> rows){
        dataArrayList.addAll(rows);
    }
    public List<String[]> getDate(){
        List<String[]> result = new ArrayList<>();
        for(String[] row : dataArrayList){
            String[] rowToSend = new String[row.length - 1];
            System.arraycopy(row, 1, rowToSend, 0, rowToSend.length);
            result.add(rowToSend);
        }
        return result;
    }

    public void clearCells(int rowIndex, int columnIndex, int lengthSeq){
        if(columnIndex > 0) {
                SequenceHandler.clearCellsWithZeros(rowIndex, columnIndex, lengthSeq, dataArrayList);
                fireTableDataChanged();
        }
    }

    public List<String[]> clearCellsWithShift(int rowIndex, int columnIndex, int lengthSeq){
        if(columnIndex == 0)
            columnIndex++;
        List<String[]> dataCleared = SequenceHandler.clearCellsWithShift(rowIndex, columnIndex, lengthSeq, dataArrayList);
        dataCleared = SequenceHandler.fillTableWithZeros(dataCleared);
        return dataCleared;
    }

    public List<String[]> insertCells(int rowIndex, int columnIndex, String[] cells, boolean replacement){
        List<String[]> dataArrayListInserted = dataArrayList;
        String[] currentRow = dataArrayList.get(rowIndex);
        String[] rowToInsert;

        if(!replacement) {
            rowToInsert = SequenceHandler.mergeRows(currentRow, cells, columnIndex);

        }else{
            rowToInsert = SequenceHandler.replaceRows(currentRow, cells, columnIndex);
        }

        dataArrayListInserted.remove(rowIndex);
        dataArrayListInserted.add(rowIndex, rowToInsert);
        dataArrayListInserted = SequenceHandler.fillTableWithZeros(dataArrayListInserted);

        return dataArrayListInserted;
    }
}
