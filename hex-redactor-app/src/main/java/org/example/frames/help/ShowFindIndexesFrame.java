package org.example.frames.help;

import org.example.frames.functional.FileDisplayFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ShowFindIndexesFrame extends JFrame {
    private final List<int[]> foundIndexes;
    private final JButton buttonClose;
    private final JLabel jLabel;
    private final List<Integer> indexPages;
    private final FileDisplayFrame fileDisplayFrame;
    private int iteratorFoundIndexes = 0;
    private int clickCount;

    public ShowFindIndexesFrame(List<int[]> foundIndexes, List<Integer> indexPages, FileDisplayFrame fileDisplayFrame){
        this.jLabel = new JLabel();
        this.buttonClose = new JButton("Далее");
        this.fileDisplayFrame = fileDisplayFrame;
        this.indexPages = indexPages;
        this.foundIndexes = foundIndexes;
        init();
    }
    public void init(){
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        StringBuilder text = new StringBuilder();
        if(!foundIndexes.isEmpty()) {
                text
                        .append("Введенная вами последовательность для поиска находится в ячейке: ")
                        .append(foundIndexes.get(iteratorFoundIndexes)[0] + 1)
                        .append(" ")
                        .append(foundIndexes.get(iteratorFoundIndexes)[1]);
        }else{
            text.append("Введенная вами последовательность для поиска не найдена.");
        }

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                if(iteratorFoundIndexes < foundIndexes.size()){
                    fileDisplayFrame.setNextPage(indexPages.get(iteratorFoundIndexes));
                    fileDisplayFrame.setIndexesCellInTable(foundIndexes.get(iteratorFoundIndexes));

                    text.delete(0, text.toString().length());
                    text
                            .append("Введенная вами последовательность для поиска находится в ячейке: ")
                            .append(foundIndexes.get(iteratorFoundIndexes)[0] + 1)
                            .append(" ")
                            .append(foundIndexes.get(iteratorFoundIndexes)[1]);
                    jLabel.setText(text.toString());

                    iteratorFoundIndexes++;
                }

                if(clickCount > foundIndexes.size()){
                    dispose();
                }
            }
        });

        jLabel.setText(text.toString());
        add(jLabel, BorderLayout.NORTH);
        add(buttonClose, BorderLayout.SOUTH);
        pack();
    }
}
