package frames.help;

import frames.functional.SequenceFrame;
import models.ByteTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectLengthSeqFrame extends JFrame {
    private final JButton twoSizeButton;
    private final JButton fourSizeButton;
    private final JButton eightSizeButton;
    private final JLabel jLabel;
    private final List<String[]> stringsArray;
    private final int[] indexes;
    private final ByteTableModel byteTableModel;
    public SelectLengthSeqFrame(ByteTableModel byteTableModel, int[] indexes) throws HeadlessException{
        this.indexes = indexes;
        this.twoSizeButton = new JButton("2");
        this.fourSizeButton = new JButton("4");
        this.eightSizeButton = new JButton("8");
        this.jLabel = new JLabel("Выберете длину для последовательности");
        this.byteTableModel = byteTableModel;
        this.stringsArray = byteTableModel.getDate();
        init();
    }

    private void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        twoSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes[0],indexes[1],2, stringsArray).setVisible(true);
                dispose();
            }
        });
        fourSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes[0],indexes[1],4, stringsArray).setVisible(true);
                dispose();
            }
        });
        eightSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes[0],indexes[1],8, stringsArray).setVisible(true);
                dispose();

            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(twoSizeButton);
        buttonPanel.add(fourSizeButton);
        buttonPanel.add(eightSizeButton);

        add(jLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

}
