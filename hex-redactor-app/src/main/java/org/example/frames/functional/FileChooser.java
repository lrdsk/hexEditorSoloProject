package org.example.frames.functional;

import org.example.models.ByteTableModel;
import org.example.utils.CustomFileReader;
import org.example.utils.CustomFileWriter;

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
    public FileChooser() {
       this.buttonOpen = new JButton("Открыть");
       this.buttonSave = new JButton("Сохранить");
       init();
    }

    public void init(){
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
                if(path != null){
                    CustomFileReader customFileReader = new CustomFileReader(converteStringPathToFilePath(path));
                    FileDisplayFrame mainForm = null;
                    try {
                        mainForm = new FileDisplayFrame(customFileReader, getFileChooser());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    mainForm.setVisible(true);
                    setVisible(false);
                    fileDisplayFrame.dispose();
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(path != null){
                    CustomFileWriter customFileWriter = new CustomFileWriter(converteStringPathToFilePath(path));
                    if(byteTableModel.getDate() != null) {
                        customFileWriter.writeTableDataToFile(byteTableModel.getDate());
                        setVisible(false);
                    }
                }
            }
        });

        JPanel panelButtons = new JPanel(new GridLayout(2,1));
        panelButtons.add(buttonOpen);
        panelButtons.add(buttonSave);
        add(panelButtons);

    }

    public void setCurrentFileDisplay(FileDisplayFrame fileDisplayFrame){
        this.fileDisplayFrame = fileDisplayFrame;
    }
    private Path converteStringPathToFilePath(String path){
        return new File(path).toPath();
    }
    public void setByteTableModel(ByteTableModel byteTableModel){
        this.byteTableModel = byteTableModel;
    }
    private FileChooser getFileChooser(){
        return this;
    }
}
