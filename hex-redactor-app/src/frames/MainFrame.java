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
    private JButton buttonShow;

    public MainFrame(CustomFileReader customFileReader){
        this.customFileReader = customFileReader;
        this.buttonShow = new JButton("Показать");
        this.stringsArray = customFileReader.readBytesFromFileToHex();
        setTitle("Test frame");
        this.bTableModel = new ByteTableModel(customFileReader.getMaxColumnCount());
        this.byteTable = new JTable(bTableModel);
        this.byteTableScrollPane = new JScrollPane(byteTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        init();
    }

    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        buttonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = byteTable.getSelectedRow();
                int selectedColumn = byteTable.getSelectedColumn();
                Object selectedValue = null;
                if(selectedRow != -1 && selectedColumn != -1) {
                    selectedValue = byteTable.getValueAt(selectedRow, selectedColumn);
                }

                //new CurrentDecimalByteForm((String) selectedValue).create();
            }
        });

        byteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        byteTable.setDefaultRenderer(Object.class, new ColorfulTableCellRenderer());
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

        byteTableScrollPane.setPreferredSize(new Dimension(500,500));

        add(byteTableScrollPane, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        add(buttonShow, new GridBagConstraints(0,1,0,0,0,0,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));

        bTableModel.addDate(stringsArray);
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
