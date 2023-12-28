package frames.functional;

import frames.help.ShowFindIndexesFrame;
import utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchFrame extends JFrame {
    private final JTextField searchTextField;
    private final JButton searchButton;
    private final List<String[]> table;
    private int[] findIndexes;

    public SearchFrame(List<String[]> table) throws HeadlessException{
        this.searchTextField = new JTextField();
        this.searchButton = new JButton("Поиск");
        this.table = table;
        init();
    }

    private void init(){
        setTitle("Окно поиска.");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchTextField.getText();
                int[] foundIndexes = SequenceHandler.findPatternInTable(table, input);
                dispose();
                new ShowFindIndexesFrame(foundIndexes).setVisible(true);
            }
        });

        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.add(searchTextField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        pack();
    }

}
