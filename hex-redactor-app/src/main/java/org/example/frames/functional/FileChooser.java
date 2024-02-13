package org.example.frames.functional;

import org.example.models.ByteTableModel;
import org.example.utils.CustomFilePageReader;
import org.example.utils.CustomPageFileWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileChooser extends JFrame {
    private final JButton buttonOpen;
    private final JButton buttonSave;
    private String path;
    private FileDisplayFrame fileDisplayFrame;
    private ByteTableModel byteTableModel;
    private int currentPage;

    public FileChooser(int currentPage) {
        this.currentPage = currentPage;
        this.buttonOpen = new JButton("Открыть");
        this.buttonSave = new JButton("Сохранить");
        init();
    }

    public void init() {
        setTitle("Файл");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FileChooser.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
                }
                if (path != null) {
                    try (CustomFilePageReader customFilePageReader =
                                 new CustomFilePageReader(converteStringPathToFilePath(path), 256, currentPage)) {
                        FileDisplayFrame mainForm = new FileDisplayFrame(customFilePageReader, getFileChooser());
                        mainForm.setVisible(true);
                        setVisible(false);
                        fileDisplayFrame.dispose();

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (path != null) {
                    try (CustomPageFileWriter customPageFileWriter =
                                 new CustomPageFileWriter(converteStringPathToFilePath(path), 256, currentPage)) {
                        if (byteTableModel.getDate() != null) {
                            customPageFileWriter.writeTableDataToFile(byteTableModel.getDate());
                            setVisible(false);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JPanel panelButtons = new JPanel(new GridLayout(2, 1));
        panelButtons.add(buttonOpen);
        panelButtons.add(buttonSave);
        add(panelButtons);

    }

    public void setCurrentFileDisplay(FileDisplayFrame fileDisplayFrame) {
        this.fileDisplayFrame = fileDisplayFrame;
    }

    private Path converteStringPathToFilePath(String path) {
        return new File(path).toPath();
    }

    public void setByteTableModel(ByteTableModel byteTableModel) {
        this.byteTableModel = byteTableModel;
    }

    private FileChooser getFileChooser() {
        return this;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
