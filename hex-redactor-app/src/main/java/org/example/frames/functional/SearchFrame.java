package org.example.frames.functional;

import org.example.frames.help.ShowFindIndexesFrame;
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

public class SearchFrame extends JFrame{
    private final JTextField searchTextField;
    private final JButton searchButton;
    private final FileDisplayFrame fileDisplayFrame;
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
                dispose();

                new ShowFindIndexesFrame(
                        fileDisplayFrame, searchTextField.getText(), path
                ).setVisible(true);
            }
        });

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(searchTextField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        pack();
    }
}
