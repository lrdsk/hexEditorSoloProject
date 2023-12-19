package frames.functional;

import utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class SequenceFrame extends JFrame {
    private final JLabel jLabelSeq;
    private final JButton buttonToDecimalSigned;
    private final JButton buttonToDecimalUnsigned;
    private final JButton buttonToDoubleSinglePrecision;
    private final JButton buttonToDoubleDoublePrecision;
    private final int lengthSeq;
    private final List<String[]> stringsArray;
    private final int[] indexes;
    public SequenceFrame(int[] indexes, int lengthSeq, List<String[]> stringsArray) throws HeadlessException{
        this.jLabelSeq = new JLabel();
        this.buttonToDecimalSigned = new JButton("10-ое со знаком");
        this.buttonToDecimalUnsigned = new JButton("10-ое без знака");
        this.buttonToDoubleSinglePrecision = new JButton("вещ-ое 1-ой точности");
        this.buttonToDoubleDoublePrecision = new JButton("вещ-ое 2-ой точности");
        this.indexes = indexes;
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
        String[] hexSequence = SequenceHandler.getSequenceByIndex(indexes, lengthSeq, stringsArray);
        jLabelSeq.setText(Arrays.toString(hexSequence));

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

    public String[] getSequenceByIndex(){
        String[] row = stringsArray.get(indexes[0]);
        if(indexes[1] + lengthSeq > row.length){
            int itSeq = 0;
            int curIndexRow = indexes[0];
            int curIndexColumn = indexes[1];
            int remainRows = stringsArray.size() - curIndexRow;
            String[] resultSeq = new String[lengthSeq];

            while(remainRows > 0 && itSeq < lengthSeq){
                resultSeq[itSeq] = row[curIndexColumn];
                itSeq++;
                curIndexColumn++;
                if(!(curIndexColumn < row.length)){
                    curIndexColumn = 0;
                    curIndexRow++;
                    remainRows = stringsArray.size() - curIndexRow;
                    if(curIndexRow < stringsArray.size()){
                        row = stringsArray.get(curIndexRow);
                    }
                }
            }
            return  resultSeq;
        }
        return Arrays.copyOfRange(row, indexes[1], indexes[1]+lengthSeq);
    }
}
