package frames;

import utils.SearchManager;
import utils.SequenceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFrame extends JFrame {
    private final JTextField searchTextField;
    private final JButton searchButton;
    private final List<String[]> table;

    public SearchFrame(List<String[]> table) throws HeadlessException{
        this.searchTextField = new JTextField();
        this.searchButton = new JButton("Поиск");
        this.table = table;
        init();
    }

    private void init(){
        setTitle("Search frame");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchTextField.getText();
                SequenceHandler.findPatternInTable(table, input);
            }
        });

        JPanel panel = new JPanel(new GridLayout(1,2));
        panel.add(searchTextField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
    }

}
