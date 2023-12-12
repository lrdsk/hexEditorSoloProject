package models;

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
        for(int i = 0; i < countColumn; i++){
            if(i == columnIndex){
                return String.valueOf(i);
            }
        }
        return "";
    }

    public void addDate(List<String[]> rows){
        dataArrayList.addAll(rows);
    }
}
