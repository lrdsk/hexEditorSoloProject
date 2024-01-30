package org.example.frames.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowFindIndexesFrame extends JFrame {
    private final int[] foundIndexes;
    private final JButton buttonClose;
    private final JLabel jLabel;

    public ShowFindIndexesFrame(int[] foundIndexes){
        this.foundIndexes = foundIndexes;
        this.jLabel = new JLabel();
        this.buttonClose = new JButton("Ок");
        init();
    }
    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        StringBuilder text = new StringBuilder();
        if(foundIndexes[0] != -1 && foundIndexes[1] != -1) {
            text.append("Введенная вами последовательность для поиска находится в ячейке: ");
            for (int i : foundIndexes) {
                text.append(i).append(" ");
            }
        }else{
            text.append("Введенная вами последовательность для поиска не найдена.");
        }
        jLabel.setText(text.toString());
        add(jLabel, BorderLayout.NORTH);
        add(buttonClose, BorderLayout.SOUTH);
        pack();
    }
}
