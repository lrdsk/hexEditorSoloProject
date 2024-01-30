package org.example.frames.help;

import org.example.models.ByteTableModel;
import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InsertSequenceIntoTableFrame extends JFrame{
    private final JLabel labelLengthOfCells;
    private final JTextField textFieldSequence;
    private final JButton buttonInsertWithoutReplacement;
    private final JButton buttonInsertWithReplacement;
    private final ByteTableModel byteTableModel;
    private final JTable jTable;
    private final int row;
    private final int column;


    public InsertSequenceIntoTableFrame(JTable jTable, ByteTableModel byteTableModel, int[] indexes){
        this.byteTableModel = byteTableModel;
        this.jTable = jTable;
        this.labelLengthOfCells = new JLabel("Введите последовательность байт: ");
        this.textFieldSequence = new JTextField();
        this.buttonInsertWithoutReplacement = new JButton("Без замены");
        this.buttonInsertWithReplacement = new JButton("C заменой");
        this.row = indexes[0];
        this.column = indexes[1];
        init();
    }

    public void init(){
        setTitle("Окно вставки");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonInsertWithoutReplacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sequenceText = textFieldSequence.getText();
                String[] cellsToInsert = sequenceText.split(" ");

                List<String[]> dataInserted = byteTableModel.insertCells(row, column, cellsToInsert, false);

                ByteTableModel byteTableModelInserted = new ByteTableModel(SequenceHandler.getMaxColumnCountInTable(dataInserted));
                byteTableModelInserted.addDate(dataInserted);

                jTable.setModel(byteTableModelInserted);
                dispose();
            }
        });

        buttonInsertWithReplacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sequenceText = textFieldSequence.getText();
                String[] cellsToInsert = sequenceText.split(" ");

                List<String[]> dataInserted = byteTableModel.insertCells(row, column, cellsToInsert, true);

                ByteTableModel byteTableModelInserted = new ByteTableModel(SequenceHandler.getMaxColumnCountInTable(dataInserted));
                byteTableModelInserted.addDate(dataInserted);

                jTable.setModel(byteTableModelInserted);
                dispose();
            }
        });

        JPanel panelInputData = new JPanel(new GridLayout(1,2));
        panelInputData.add(labelLengthOfCells);
        panelInputData.add(textFieldSequence);

        JPanel panelButtons = new JPanel(new GridLayout(1,2));
        panelButtons.add(buttonInsertWithoutReplacement);
        panelButtons.add(buttonInsertWithReplacement);

        add(panelInputData, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
        pack();
    }

}
