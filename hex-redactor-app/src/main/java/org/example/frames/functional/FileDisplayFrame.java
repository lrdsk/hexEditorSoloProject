package org.example.frames.functional;

import org.example.frames.help.CutSequenceFromTableFrame;
import org.example.frames.help.InsertSequenceIntoTableFrame;
import org.example.frames.help.SelectLengthOfCellsToDeleteFrame;
import org.example.frames.help.SelectLengthSeqFrame;
import org.example.models.ByteTableModel;
import org.example.utils.CustomFileReader;
import org.example.utils.CustomFileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.List;

public class FileDisplayFrame extends JFrame {
    private final List<String[]> stringsArray;
    private final ByteTableModel byteTableModel;
    private final JTable byteTable;
    private final JScrollPane byteTableScrollPane;
    private final JButton buttonShowDecimal;
    private final JButton buttonShowLengthSeq;
    private final JButton buttonDeleteCell;
    private final JButton buttonInsertCell;
    private final JButton buttonCutCell;
    private final JButton buttonSaveChanges;
    private final JButton buttonFindSeq;
    private final JButton buttonFileMenu;
    private int previousRow = -1;
    private int previousColumn = -1;
    private final Path path;
    private final FileChooser fileChooser;


    public FileDisplayFrame(CustomFileReader customFileReader, FileChooser fileChooser) throws HeadlessException{
        this.buttonShowDecimal = new JButton("Десятичное представление");
        this.buttonShowLengthSeq = new JButton("Последователность");
        this.buttonDeleteCell = new JButton("Удалить");
        this.buttonInsertCell = new JButton("Вставить");
        this.buttonCutCell = new JButton("Вырезать");
        this.buttonSaveChanges = new JButton("Сохранить");
        this.buttonFindSeq = new JButton("Найти");
        this.buttonFileMenu = new JButton("Файл");
        this.fileChooser = fileChooser;
        this.stringsArray = customFileReader.readBytesFromFileToHex();
        this.path = customFileReader.getPath();
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
                int[] indexes = getIndexesCellInTable();
                indexes[1] -= 1;
                new SelectLengthSeqFrame((ByteTableModel) byteTable.getModel(), indexes).setVisible(true);
            }
        });
        buttonDeleteCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indexes = getIndexesCellInTable();
                new SelectLengthOfCellsToDeleteFrame(byteTable, (ByteTableModel) byteTable.getModel(), indexes).setVisible(true);
            }
        });
        buttonCutCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] indexes = getIndexesCellInTable();
                indexes[1] -= 1;
                new CutSequenceFromTableFrame(byteTable,(ByteTableModel) byteTable.getModel(), indexes).setVisible(true);
            }
        });
        buttonInsertCell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertSequenceIntoTableFrame(byteTable, byteTableModel, getIndexesCellInTable()).setVisible(true);
            }
        });
        buttonFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setByteTableModel(byteTableModel);
                fileChooser.setVisible(true);
                fileChooser.setCurrentFileDisplay(FileDisplayFrame.this);
            }
        });
        buttonSaveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomFileWriter customFileWriter = new CustomFileWriter(path);
                customFileWriter.writeTableDataToFile(byteTableModel.getDate());
            }
        });
        buttonFindSeq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchFrame(byteTableModel.getDate()).setVisible(true);
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
        buttonsEditPanel.setLayout(new GridLayout(5,1));
        buttonsEditPanel.add(buttonDeleteCell);
        buttonsEditPanel.add(buttonInsertCell);
        buttonsEditPanel.add(buttonCutCell);
        buttonsEditPanel.add(buttonFindSeq);
        buttonsEditPanel.add(buttonFileMenu);

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