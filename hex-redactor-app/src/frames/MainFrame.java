package frames;

import models.ByteTableModel;
import utils.CustomFileReader;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainFrame extends JFrame {
    private final List<String[]> stringsArray;
    private final ByteTableModel bTableModel;
    private final JTable byteTable;
    private final JScrollPane byteTableScrollPane;
    private final JButton buttonShowDecimal;
    private final JButton buttonShowLengthSeq;
    private final JButton buttonDeleteCell;
    private final JButton buttonPasteCell;
    private final JButton buttonCutCell;
    private int previousRow = -1;
    private int previousColumn = -1;


    public MainFrame(CustomFileReader customFileReader){
        this.buttonShowDecimal = new JButton("Десятичное представление");
        this.buttonShowLengthSeq = new JButton("Последователность");
        this.buttonDeleteCell = new JButton("Удаление");
        this.buttonPasteCell = new JButton("Вставка");
        this.buttonCutCell = new JButton("Вырезать");
        this.stringsArray = customFileReader.readBytesFromFileToHex();
        this.bTableModel = new ByteTableModel(customFileReader.getMaxColumnCount());
        this.byteTable = new JTable(bTableModel);
        this.byteTableScrollPane = new JScrollPane(byteTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        init();
    }

    public void init(){
        setTitle("Test frame");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        byteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        byteTable.setDefaultRenderer(Object.class, new ColorfulTableCellRenderer());
        byteTable.getTableHeader().setReorderingAllowed(false);
        byteTableScrollPane.setPreferredSize(new Dimension(500,500));
        bTableModel.addDate(stringsArray);

        buttonShowDecimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CurrentDecimalByteFrame(getValueInCell()).setVisible(true);
            }
        });
        buttonShowLengthSeq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectLengthSeqFrame(getIndexesCellInTable(), stringsArray).setVisible(true);
                new SearchFrame(stringsArray).setVisible(true);
            }
        });
        buttonDeleteCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indexes = getIndexesCellInTable();
                int row = byteTable.getSelectedRow();
                int column = byteTable.getSelectedColumn();
                bTableModel.setValueAt(0, row, column);
                System.out.println("row: " + row + " column: " + column + " value: " + bTableModel.getValueAt(row, column));
            }
        });
        byteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row =  byteTable.rowAtPoint(e.getPoint());
                int column = byteTable.columnAtPoint(e.getPoint());

                if (row != -1 && column != -1) {
                    previousRow = row;
                    previousColumn = column;
                    byteTable.repaint();
                }
            }
        });

        JPanel buttonsEditPanel = new JPanel();
        buttonsEditPanel.setLayout(new GridLayout(3,1));
        buttonsEditPanel.add(buttonDeleteCell);
        buttonsEditPanel.add(buttonPasteCell);
        buttonsEditPanel.add(buttonCutCell);

        add(byteTableScrollPane, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        add(buttonsEditPanel, new GridBagConstraints(1,0,1,1,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        add(buttonShowDecimal, new GridBagConstraints(0,1,1,0,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonShowLengthSeq, new GridBagConstraints(1,1,1,0,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
    }

    public int[] getIndexesCellInTable(){
        int selectedRow = byteTable.getSelectedRow();
        int selectedColumn = byteTable.getSelectedColumn();
        return new int[]{selectedRow,selectedColumn};
    }
    public String getValueInCell(){
        int[] indexes = getIndexesCellInTable();
        Object selectedValue = null;
        if(indexes[0] != -1 && indexes[1] != -1) {
            selectedValue = byteTable.getValueAt(indexes[0], indexes[1]);
        }
        return (String) selectedValue;
    }

    private class ColorfulTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row == previousRow && column == previousColumn) {
                // Изменение цвета выбранной ячейки
                c.setBackground(Color.BLUE);
            } else {
                // Возврат ячейки в исходное состояние
                c.setBackground(table.getBackground());
            }

            return c;
        }
    }
}