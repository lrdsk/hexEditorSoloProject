package frames;

import utils.SearchManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFrame extends JFrame {
    private final JTextField searchTextField;
    private final JButton searchButton;
    private final List<String[]> table;

    public SearchFrame(List<String[]> table){
        this.searchTextField = new JTextField();
        this.searchButton = new JButton("Поиск");
        this.table = table;
        init();
    }

    public void init(){
        setTitle("Search frame");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        List<String[]> str = findPatternInTable(table, "102 100");
        for(String[] s : str){
            System.out.println(Arrays.toString(s));
        }
    }

    public List<String[]> findPatternInTable(List<String[]> table, String pattern){
        List<String[]> matches = new ArrayList<>();
        String[] strings = pattern.split(" ");
        byte[] bytesToSearch = new byte[strings.length];
        for(int i = 0; i < bytesToSearch.length; i++){
            bytesToSearch[i] = Byte.parseByte(strings[i]);
        }
        String[] hexToSearch = SearchManager.convertBytesToHex(bytesToSearch);

        for(String[] row : table){
            for(int i = 0; i <= row.length - hexToSearch.length; i++){
                boolean matchFound = true;
                for(int j = 0; j < hexToSearch.length; j++){
                    if(!(row[i+j].equals(hexToSearch[j]))){
                        matchFound = false;
                        break;
                    }
                }
                if(matchFound) {
                    matches.add(row);
                    break;
                }
            }
        }
        return matches;
    }
}
