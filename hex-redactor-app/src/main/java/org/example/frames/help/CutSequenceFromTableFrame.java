package org.example.frames.help;
import org.example.models.ByteTableModel;
import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CutSequenceFromTableFrame extends JFrame{
    private final ByteTableModel byteTableModel;
    private final JLabel labelLengthOfCells;
    private final JTextField textFieldLengthOfCells;
    private final JButton buttonDeleteWithZeros;
    private final JButton buttonDeleteWithDataShift;
    private final JTable jTable;
    private final int row;
    private final int column;

    public CutSequenceFromTableFrame(JTable jTable, ByteTableModel byteTableModel, int[] indexes) throws HeadlessException {
        this.byteTableModel = byteTableModel;
        this.jTable = jTable;
        this.labelLengthOfCells = new JLabel("Введите длину для вырезки: ");
        this.textFieldLengthOfCells = new JTextField();
        this.buttonDeleteWithZeros = new JButton("C обнулением");
        this.buttonDeleteWithDataShift = new JButton("Cо сдвигом");
        this.row = indexes[0];
        this.column = indexes[1];
        init();
    }

    public void init(){
        setTitle("Окно выбора вырезки.");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonDeleteWithZeros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(textFieldLengthOfCells.getText());

                addStringToCutInClipboard(row, column, length, byteTableModel.getDate());
                byteTableModel.clearCells(row, column, length);
                dispose();
            }
        });
        buttonDeleteWithDataShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(textFieldLengthOfCells.getText());

                addStringToCutInClipboard(row, column, length, byteTableModel.getDate());
                List<String[]> dataCleared = byteTableModel.clearCellsWithShift(row,column,length);

                ByteTableModel byteTableModelInserted = new ByteTableModel(SequenceHandler.getMaxColumnCountInTable(dataCleared));
                byteTableModelInserted.addDate(dataCleared);

                jTable.setModel(byteTableModelInserted);
                dispose();
            }
        });

        JPanel panelInputData = new JPanel(new GridLayout(1,2));
        panelInputData.add(labelLengthOfCells);
        panelInputData.add(textFieldLengthOfCells);

        JPanel panelButtons = new JPanel(new GridLayout(1,2));
        panelButtons.add(buttonDeleteWithZeros);
        panelButtons.add(buttonDeleteWithDataShift);

        add(panelInputData, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
        pack();
    }

    private void addStringToCutInClipboard(int row, int column, int length, List<String[]> table){
        String[] sequenceToCut = SequenceHandler.getSequenceByIndex(row, column, length, byteTableModel.getDate());
        StringBuilder stringToCut = new StringBuilder();
        for(String s : sequenceToCut){
            stringToCut.append(s).append(" ");
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(stringToCut.toString());
        clipboard.setContents(stringSelection, null);
    }
}
