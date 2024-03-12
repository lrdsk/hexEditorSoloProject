package org.example.frames.help;

import org.example.frames.functional.FileDisplayFrame;
import org.example.services.PatternFoundCallbackService;
import org.example.utils.CustomFilePageReader;
import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowFindIndexesFrame extends JFrame implements PatternFoundCallbackService {
    private final JButton buttonNext;
    private final JButton buttonExit;
    private final JLabel jLabel;
    private final FileDisplayFrame fileDisplayFrame;
    private int iteratorFoundIndexes = 0;
    private int clickCount;
    private final Path path;
    private int readPages = 0;
    private final ShowFindIndexesFrame showFindIndexesFrame = this;
    private final List<int[]> listIndexes = new ArrayList<>();
    private final List<Integer> indexPages = new ArrayList<>();
    private List<String[]> table;
    private final String text;
    private boolean taskComplete;

    public ShowFindIndexesFrame(FileDisplayFrame fileDisplayFrame, String text, Path path){
        setSize(600, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.jLabel = new JLabel();
        this.buttonNext = new JButton("Далее");
        this.buttonExit = new JButton("Выход");
        this.fileDisplayFrame = fileDisplayFrame;
        this.path = path;
        this.text = text;
        init();
    }
    public void init(){

        SequenceHandler sequenceHandler = new SequenceHandler();

        // Создаем ExecutorService с одним потоком
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try (CustomFilePageReader customPageFileReader = new CustomFilePageReader(path, 256, readPages)) {
                while (readPages < customPageFileReader.getPageCount()) {
                    table = customPageFileReader.readBytesToTableModel(100);
                    sequenceHandler.findPatternInTable(table, text, readPages, showFindIndexesFrame, this);

                    readPages += customPageFileReader.getReadPages();
                }
                sequenceHandler.shutdownExecutor();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


            onTaskComplete(true);
        });

// Не забудьте завершить ExecutorService после завершения работы
        executor.shutdown();

        StringBuilder text = new StringBuilder();

        if(listIndexes.isEmpty()) {
            text.append("Введенная вами последовательность для поиска не найдена.");
        }

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;

                if(iteratorFoundIndexes >= listIndexes.size() && taskComplete){
                       text
                               .delete(0, text.toString().length())
                               .append("Конец поиска");
                       updateJLabel(text.toString());
                }else{
                    if(iteratorFoundIndexes < listIndexes.size()) {
                        fileDisplayFrame.setNextPage(indexPages.get(iteratorFoundIndexes));
                        fileDisplayFrame.setIndexesCellInTable(listIndexes.get(iteratorFoundIndexes));

                        text.delete(0, text.toString().length());
                        text
                                .append("Введенная вами последовательность для поиска находится в ячейке: ")
                                .append(listIndexes.get(iteratorFoundIndexes)[0] + 1)
                                .append(" ")
                                .append(listIndexes.get(iteratorFoundIndexes)[1]);
                        jLabel.setText(text.toString());

                        iteratorFoundIndexes++;
                    }
                }

            }
        });

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jLabel.setText(text.toString());
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(buttonNext);
        panel.add(buttonExit);
        add(jLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.SOUTH);
    }

    @Override
    public void onPatternFound(int[] indexes, int readPages) {
        listIndexes.add(indexes);
        indexPages.add(readPages);
    }

    @Override
    public void onTaskComplete(boolean result) {
        this.taskComplete = result;
    }
    public void updateJLabel(String text) {
        jLabel.setText(text);
    }

}