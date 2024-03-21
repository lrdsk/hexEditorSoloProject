package org.example.frames.functional;

import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SequenceFrame extends JFrame {
    private final JLabel jLabelSeq;
    private final JButton buttonToDecimalSigned;
    private final JButton buttonToDecimalUnsigned;
    private final JButton buttonToDoubleSinglePrecision;
    private final JButton buttonToDoubleDoublePrecision;
    private final int lengthSeq;
    private final List<String[]> stringsArray;
    private int rowIndex;
    private int columnIndex;
    public SequenceFrame(int rowIndex, int columnIndex, int lengthSeq, List<String[]> stringsArray) throws HeadlessException{
        this.jLabelSeq = new JLabel();
        this.buttonToDecimalSigned = new JButton("10-ое со знаком");
        this.buttonToDecimalUnsigned = new JButton("10-ое без знака");
        this.buttonToDoubleSinglePrecision = new JButton("вещ-ое 1-ой точности");
        this.buttonToDoubleDoublePrecision = new JButton("вещ-ое 2-ой точности");
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.lengthSeq = lengthSeq;
        this.stringsArray = stringsArray;
        init();
    }

    private void init(){
        setTitle("Последовательность");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        if(rowIndex < 0 || columnIndex < 0){
            rowIndex = 0;
            columnIndex = 0;
        }
        String[] hexSequence = SequenceHandler.getSequenceByIndex(rowIndex, columnIndex, lengthSeq, stringsArray);
        StringBuilder stringSequence = new StringBuilder();
        for(String s : hexSequence){
            if(s != null){
                stringSequence.append(s).append(" ");
            }
        }

        jLabelSeq.setText(stringSequence.toString());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(buttonToDecimalSigned);
        buttonPanel.add(buttonToDecimalUnsigned);
        buttonPanel.add(buttonToDoubleSinglePrecision);
        buttonPanel.add(buttonToDoubleDoublePrecision);

        add(jLabelSeq, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        buttonToDecimalSigned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultCell = new StringBuilder();
                for(String hexCell : hexSequence) {
                    int decimalNumber = 0;
                    if(hexCell != null){
                        decimalNumber = Integer.parseInt(hexCell, 16);
                    }
                    if ((decimalNumber & 0x8000) != 0) {
                        decimalNumber -= 0x10000;
                    }
                    resultCell.append(decimalNumber).append(" ");
                }
                jLabelSeq.setText(resultCell.toString());
            }
        });

        buttonToDecimalUnsigned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultCell = new StringBuilder();
                for(String hexCell : hexSequence){
                    int decimalNumber = 0;
                    if(hexCell != null) {
                        decimalNumber = Integer.parseUnsignedInt(hexCell, 16);
                    }
                    resultCell.append(decimalNumber).append(" ");
                }
                jLabelSeq.setText(resultCell.toString());
            }
        });
        buttonToDoubleSinglePrecision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultCell = new StringBuilder();
                for(String hexCell : hexSequence){
                    int intValue = 0;
                    float floatValue = 0;
                    if(hexCell != null){
                        intValue = Integer.parseInt(hexCell, 16);
                        floatValue = Float.intBitsToFloat(intValue);
                    }
                    resultCell.append(floatValue).append(" ");
                }
                jLabelSeq.setText(resultCell.toString());
            }
        });

        buttonToDoubleDoublePrecision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultCell = new StringBuilder();
                for(String hexCell : hexSequence){
                    int intValue = 0;
                    double doubleValue = 0;
                    if(hexCell != null){
                        intValue = Integer.parseInt(hexCell, 16);
                        doubleValue = Float.intBitsToFloat(intValue);
                    }

                    resultCell.append(doubleValue).append(" ");
                }
                jLabelSeq.setText(resultCell.toString());
            }
        });

        pack();
    }
}
