package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowDecimalByteFrame extends JFrame {
    private final JLabel jLabel;
    private String hexCell;
    private JButton buttonToDecimalSigned;
    private JButton buttonToDecimalUnsigned;

    public ShowDecimalByteFrame(String hexCell) {
        this.hexCell = hexCell;
        this.jLabel = new JLabel(hexCell);
        this.buttonToDecimalSigned = new JButton("В десятичное со знаком");
        this.buttonToDecimalUnsigned = new JButton("В десятичное без знака");
        init();
    }
    public void init(){
        setTitle("Представление байта в десятичной системе");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        buttonToDecimalSigned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int decimalNumber = Integer.parseInt(hexCell, 16);
                if ((decimalNumber & 0x8000) != 0) {
                    decimalNumber -= 0x10000;
                }
                jLabel.setText(String.valueOf(decimalNumber));
            }
        });

        buttonToDecimalUnsigned.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.setText(String.valueOf(Integer.parseUnsignedInt(hexCell,16)));
            }
        });

        add(jLabel, new GridBagConstraints(0,0,2,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.CENTER,
                new Insets(1,1,1,1),0,0));
        add(buttonToDecimalSigned, new GridBagConstraints(0,1,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.BOTH,
                new Insets(1,1,1,1),0,0));
        add(buttonToDecimalUnsigned, new GridBagConstraints(1,1,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.CENTER,
                new Insets(1,1,1,1),0,0));

        pack();
    }
}
