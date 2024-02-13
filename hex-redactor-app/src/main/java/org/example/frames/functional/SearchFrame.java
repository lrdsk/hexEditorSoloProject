package org.example.frames.functional;

import org.example.frames.help.ShowFindIndexesFrame;
import org.example.models.ByteTableModel;
import org.example.utils.CustomFilePageReader;
import org.example.utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SearchFrame extends JFrame {
    private final JTextField searchTextField;
    private final JButton searchButton;
    private List<String[]> table;
    private final FileDisplayFrame fileDisplayFrame;
    private int readPages = 0;
    private Path path;

    public SearchFrame(FileDisplayFrame fileDisplayFrame, Path path) throws HeadlessException {
        this.path = path;
        this.searchTextField = new JTextField();
        this.searchButton = new JButton("Поиск");
        this.fileDisplayFrame = fileDisplayFrame;
        init();
    }

    private void init() {
        setTitle("Окно поиска.");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchTextField.getText();
                List<int[]> foundIndexes = new ArrayList<>();
                List<Integer> indexPages = new ArrayList<>();

                try (CustomFilePageReader customPageFileReader = new CustomFilePageReader(path, 256, readPages)) {
                    while (readPages < customPageFileReader.getPageCount()) {
                        table = customPageFileReader.readBytesToTableModel(100);
                        List<int[]> currFoundIndexes = SequenceHandler.findPatternInTable(table, input);
                        if(!currFoundIndexes.isEmpty()) {
                            foundIndexes.addAll(currFoundIndexes);

                            for(int i = 0; i < currFoundIndexes.size(); i++) {
                                indexPages.add(readPages);
                            }
                        }
                        readPages += customPageFileReader.getReadPages();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();

                System.out.println(foundIndexes.size());
                new ShowFindIndexesFrame(foundIndexes, indexPages, fileDisplayFrame).setVisible(true);

            }
        });

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(searchTextField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        pack();
    }

}
