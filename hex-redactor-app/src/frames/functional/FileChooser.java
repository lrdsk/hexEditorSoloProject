package frames.functional;

import models.ByteTableModel;
import utils.CustomFileReader;
import utils.CustomFileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

public class FileChooser extends JFrame {
    private final JButton buttonOpen;
    private final JButton buttonSave;
    private String path;
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
                    FileDisplayFrame mainForm = new FileDisplayFrame(customFileReader, getFileChooser());
                    mainForm.setVisible(true);
                    setVisible(false);
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
