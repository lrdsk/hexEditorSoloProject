package frames.functional;

import frames.SearchFrame;
import frames.help.InsertSequenceIntoTableFrame;
import frames.help.SelectLengthOfCellsToDeleteFrame;
import frames.help.SelectLengthSeqFrame;
import models.ByteTableModel;
import utils.CustomFileReader;
import utils.CustomFileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class FileDisplayFrame extends JFrame {
    private final List<String[]> stringsArray;
    private final ByteTableModel byteTableModel;
    private final JTable byteTable;
    private final JScrollPane byteTableScrollPane;
    private final JButton buttonShowDecimal;
    private final JButton buttonShowLengthSeq;
    private final JButton buttonDeleteCell;
    private final JButton buttonPasteCell;
    private final JButton buttonCutCell;
    private final JButton buttonApplyChanges;
    private int previousRow = -1;
    private int previousColumn = -1;


    public FileDisplayFrame(CustomFileReader customFileReader) throws HeadlessException{
        this.buttonShowDecimal = new JButton("Десятичное представление");
        this.buttonShowLengthSeq = new JButton("Последователность");
        this.buttonDeleteCell = new JButton("Удаление");
        this.buttonPasteCell = new JButton("Вставка");
        this.buttonCutCell = new JButton("Вырезать");
        this.buttonApplyChanges = new JButton("Apply");
        this.stringsArray = customFileReader.readBytesFromFileToHex();
        this.byteTableModel = new ByteTableModel(customFileReader.getMaxColumnCount());
        this.byteTable = new JTable(byteTableModel);
        this.byteTableScrollPane = new JScrollPane(byteTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        init();
    }

    private void init(){
        setTitle("Main frame");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        byteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        byteTable.setDefaultRenderer(Object.class, new ColorfulTableCellRenderer());
        byteTable.getTableHeader().setReorderingAllowed(false);
        byteTableScrollPane.setPreferredSize(new Dimension(500,500));
        byteTableModel.addDate(stringsArray);

        buttonShowDecimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowDecimalByteFrame(getValueInCell()).setVisible(true);
            }
        });
        buttonShowLengthSeq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectLengthSeqFrame(stringsArray, getIndexesCellInTable()).setVisible(true);
                new SearchFrame(stringsArray).setVisible(true);
            }
        });
        buttonDeleteCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indexes = getIndexesCellInTable();
                new SelectLengthOfCellsToDeleteFrame((ByteTableModel) byteTable.getModel(), indexes).setVisible(true);
                System.out.println("row: " + indexes[0] + " column: " + indexes[1] + " value: " + byteTableModel.getValueAt(indexes[0], indexes[1]));
            }
        });
        buttonPasteCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertSequenceIntoTableFrame(byteTable, byteTableModel, getIndexesCellInTable()).setVisible(true);
            }
        });
        buttonApplyChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomFileWriter customFileWriter = new CustomFileWriter(
                        new File("/home/user/java_tasks/hex-redactor/homework/test.txt").toPath()
                );
                customFileWriter.writeTableDataToFile(byteTableModel.getDate());
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
        buttonsEditPanel.setLayout(new GridLayout(4,1));
        buttonsEditPanel.add(buttonDeleteCell);
        buttonsEditPanel.add(buttonPasteCell);
        buttonsEditPanel.add(buttonCutCell);
        buttonsEditPanel.add(buttonApplyChanges);

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