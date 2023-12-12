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
    private final CustomFileReader customFileReader;
    private List<String[]> stringsArray;
    private ByteTableModel bTableModel;
    private JTable byteTable;
    private JScrollPane byteTableScrollPane;
    private int previousRow = -1;
    private int previousColumn = -1;
    private JButton buttonShowDecimal;
    private JButton buttonShowLengthSeq;
    private MainFrame thisFrame;

    public MainFrame(CustomFileReader customFileReader){
        this.customFileReader = customFileReader;
        this.buttonShowDecimal = new JButton("Десятичное представление");
        this.buttonShowLengthSeq = new JButton("Последователност");
        this.stringsArray = customFileReader.readBytesFromFileToHex();
        this.bTableModel = new ByteTableModel(customFileReader.getMaxColumnCount());
        this.byteTable = new JTable(bTableModel);
        this.byteTableScrollPane = new JScrollPane(byteTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.thisFrame = this;
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
                new SelectLengthSeqFrame(getValueInCell(), thisFrame).setVisible(true);
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

        add(byteTableScrollPane, new GridBagConstraints(0,0,2,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        add(buttonShowDecimal, new GridBagConstraints(0,1,1,0,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonShowLengthSeq, new GridBagConstraints(1,1,1,0,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
    }
    public String getSeqInTable(int count){
        return "";
    }
    public String getValueInCell(){
        int selectedRow = byteTable.getSelectedRow();
        int selectedColumn = byteTable.getSelectedColumn();
        Object selectedValue = null;
        if(selectedRow != -1 && selectedColumn != -1) {
            selectedValue = byteTable.getValueAt(selectedRow, selectedColumn);
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