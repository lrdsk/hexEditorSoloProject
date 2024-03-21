package org.example.frames.help;

import org.example.models.ByteTableModel;
import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectLengthOfCellsToDeleteFrame extends JFrame {
    private final ByteTableModel byteTableModel;
    private final JLabel labelLengthOfCells;
    private final JTextField textFieldLengthOfCells;
    private final JButton buttonDeleteWithZeros;
    private final JButton buttonDeleteWithDataShift;
    private final JTable jTable;
    private final int row;
    private final int column;

    public SelectLengthOfCellsToDeleteFrame(JTable jTable, ByteTableModel byteTableModel, int[] indexes) throws HeadlessException {
        this.byteTableModel = byteTableModel;
        this.jTable = jTable;
        this.labelLengthOfCells = new JLabel("Введите длину для удаления: ");
        this.textFieldLengthOfCells = new JTextField();
        this.buttonDeleteWithZeros = new JButton("C обнулением");
        this.buttonDeleteWithDataShift = new JButton("Cо сдвигом");
        this.row = indexes[0];
        this.column = indexes[1];
        init();
    }

    private void init(){
        setTitle("Окно выбора длины удаления");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonDeleteWithZeros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(textFieldLengthOfCells.getText());
                byteTableModel.clearCells(row, column, length);
                dispose();
            }
        });
        buttonDeleteWithDataShift.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(textFieldLengthOfCells.getText());
                try{
                List<String[]> dataCleared = byteTableModel.clearCellsWithShift(row,column,length);

                ByteTableModel byteTableModelInserted = new ByteTableModel(SequenceHandler.getMaxColumnCountInTable(dataCleared));
                byteTableModelInserted.addDate(dataCleared);

                jTable.setModel(byteTableModelInserted);
                }catch(IndexOutOfBoundsException ex){
                    System.out.println(ex.getMessage());
                }
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
}
