package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectLengthSeqFrame extends JFrame {
    private JButton twoSizeButton;
    private JButton fourSizeButton;
    private JButton eightSizeButton;
    private final JLabel jLabel;
    private List<String[]> stringsArray;
    private int[] indexes;
    public SelectLengthSeqFrame(int[] indexes, List<String[]> stringsArray){
        this.indexes = indexes;
        this.twoSizeButton = new JButton("2");
        this.fourSizeButton = new JButton("4");
        this.eightSizeButton = new JButton("8");
        this.jLabel = new JLabel("Выберете длину для последовательности");
        this.stringsArray = stringsArray;
        init();
    }

    public void init(){
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        twoSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes,2, stringsArray).setVisible(true);
                dispose();
            }
        });
        fourSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes,4, stringsArray).setVisible(true);
                dispose();
            }
        });
        eightSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SequenceFrame(indexes,8, stringsArray).setVisible(true);
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
