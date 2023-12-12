package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLengthSeqFrame extends JFrame {
    private JButton twoSizeButton;
    private JButton fourSizeButton;
    private JButton eightSizeButton;
    private MainFrame mainFrame;
    private final JLabel jLabel;
    public SelectLengthSeqFrame(String hexCell, MainFrame mainFrame){
        this.twoSizeButton = new JButton("2");
        this.fourSizeButton = new JButton("4");
        this.eightSizeButton = new JButton("8");
        this.mainFrame = mainFrame;
        this.jLabel = new JLabel("Выберете длину для последовательности");
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
                System.out.println(mainFrame.getSeqInTable(2));
            }
        });
        fourSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSeqInTable(4);
            }
        });
        eightSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getSeqInTable(8);
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
