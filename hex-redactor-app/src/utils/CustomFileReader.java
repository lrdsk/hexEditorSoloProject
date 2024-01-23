package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReader {
    private final Path path;
    private static final String HEXES = "0123456789ABCDEF";
    private int maxColumnCount;

    public CustomFileReader(Path path){
        this.path = path;
    }

    private int getMaxColumnFromFile(){
        int maxColumnCount = 0;
        if(Files.exists(path) && Files.isRegularFile(path)){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    if(maxColumnCount < line.length() + 1){
                        maxColumnCount = line.length() + 1;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return maxColumnCount;
    }

    public List<String[]> readBytesFromFileToHex(){
        final List<String[]> hexRows = new ArrayList<>();
        maxColumnCount = getMaxColumnFromFile();
        if(Files.exists(path) && Files.isRegularFile(path)){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
                String line;
                int i = 0;

                while ((line = bufferedReader.readLine()) != null){
                    byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                    hexRows.add(getBytesHexFormat(bytes, i));
                    i++;
                }

                return hexRows;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>();
    }

    private String[] getBytesHexFormat(byte[] byteRow, int i){
        final StringBuilder hex = new StringBuilder();

            for(int j = 0; j < byteRow.length; j++){
                byte currentByte = byteRow[j];
                if (j == 0) {
                    hex
                            .append(i + 1)
                            .append("\t");
                }
                if(currentByte != 48) {
                    hex
                            .append(HEXES.charAt((currentByte & 0xF0) >> 4))
                            .append(HEXES.charAt((currentByte & 0x0F)))
                            .append("\t");
                }else{
                    hex
                            .append(0)
                            .append("\t");
                }
            }
            while(hex.toString().split("\t").length < maxColumnCount){
                hex.append(0).append("\t");
            }

        return hex.toString().split("\t");
    }

    public int getMaxColumnCount() {
        return maxColumnCount;
    }

    public Path getPath() {
        return path;
    }
}
