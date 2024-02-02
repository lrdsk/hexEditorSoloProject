package org.example.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFileReader {
    private final Path path;
    private static final String HEXES = "0123456789ABCDEF";
    private int maxColumnCount;

    public CustomFileReader(Path path){
        this.path = path;
    }

    /*private int getMaxColumnFromFile(){
        int maxColumnCount = 0;
        try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            String line;
            while ((line = file.readLine()) != null){
                if(maxColumnCount < line.getBytes().length + 1){
                    maxColumnCount = line.getBytes().length + 1;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return maxColumnCount;
    }*/

    public List<String[]> readBytesFromFileToHex(){
        final List<String[]> hexRows = new ArrayList<>();
        if(Files.exists(path) && Files.isRegularFile(path)){
            int chunkSize = 256;
            maxColumnCount = chunkSize;
            try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
                byte[] buffer = new byte[chunkSize];
                int i = 0;
                while ((fileInputStream.read(buffer)) != -1) {
                    String[] str = getBytesHexFormat(buffer, i);
                    hexRows.add(str);
                    i++;
                    buffer = new byte[chunkSize];
                }
                return hexRows;
            } catch (IOException e) {
                e.printStackTrace();
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

        return hex.toString().split("\t");
    }

    public int getMaxColumnCount() {
        return maxColumnCount;
    }

    public Path getPath() {
        return path;
    }
}
