package models;

import utils.SequenceHandler;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
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

    public void clearCells(int row, int column, int lengthSeq){
        if(column > 0) {
            SequenceHandler.clearCells(new int[]{row,column}, lengthSeq, dataArrayList);
            fireTableDataChanged();
        }
    }

    public List<String[]> insertCells(int row, int column, String[] cells){
        List<String[]> dataArrayListInserted = dataArrayList;
        String[] currentRow = dataArrayList.get(row);
        String[] rowToInsert = SequenceHandler.mergeRows(currentRow, cells, column);
        dataArrayListInserted.remove(row);
        dataArrayListInserted.add(row, rowToInsert);

        return dataArrayListInserted;
    }
}
