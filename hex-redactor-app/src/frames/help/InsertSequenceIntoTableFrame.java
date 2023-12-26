package frames.help;

import models.ByteTableModel;
import utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InsertSequenceIntoTableFrame extends JFrame{
    private final JLabel labelLengthOfCells;
    private final JTextField textFieldSequence;
    private final JButton buttonInsert;
    private final ByteTableModel byteTableModel;
    private final JTable jTable;
    private final int row;
    private final int column;


    public InsertSequenceIntoTableFrame(JTable jTable, ByteTableModel byteTableModel, int[] indexes){
        this.byteTableModel = byteTableModel;
        this.jTable = jTable;
        this.labelLengthOfCells = new JLabel("Введите последовательность байт: ");
        this.textFieldSequence = new JTextField();
        this.buttonInsert = new JButton("Вставить");
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

        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.add(labelLengthOfCells);
        panel.add(textFieldSequence);

        buttonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sequenceText = textFieldSequence.getText();
                String[] cellsToInsert = sequenceText.split(" ");

                List<String[]> dateInserted = byteTableModel.insertCells(row, column, cellsToInsert);
                dateInserted = SequenceHandler.fillTableWithZeros(dateInserted);

                ByteTableModel byteTableModelInserted = new ByteTableModel(SequenceHandler.getMaxColumnCountInTable(dateInserted));
                byteTableModelInserted.addDate(dateInserted);

                jTable.setModel(byteTableModelInserted);
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
        add(buttonInsert, BorderLayout.SOUTH);
        pack();
    }

}
