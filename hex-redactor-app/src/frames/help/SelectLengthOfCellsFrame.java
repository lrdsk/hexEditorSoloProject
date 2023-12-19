package frames.help;

import models.ByteTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLengthOfCellsFrame extends JFrame {
    private final ByteTableModel byteTableModel;
    private final JLabel labelLengthOfCells;
    private final JTextField textFieldLengthOfCells;
    private final JButton buttonSelect;
    private final int[] indexes;

    public SelectLengthOfCellsFrame(ByteTableModel byteTableModel, int[] indexes) throws HeadlessException {
        this.byteTableModel = byteTableModel;
        this.labelLengthOfCells = new JLabel("Введите длину для удаления: ");
        this.textFieldLengthOfCells = new JTextField();
        this.buttonSelect = new JButton("Выбрать");
        this.indexes = indexes;
        init();
    }

    private void init(){
        setTitle("Окно выбора длины удаления");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.add(labelLengthOfCells);
        panel.add(textFieldLengthOfCells);

        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(textFieldLengthOfCells.getText());
                byteTableModel.clearCells(indexes, length);
                dispose();
            }
        });

        add(panel, BorderLayout.CENTER);
        add(buttonSelect, BorderLayout.SOUTH);
        pack();
    }
}
