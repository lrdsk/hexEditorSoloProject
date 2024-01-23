package utils;

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

    private int getMaxColumnFromFile(){
        int maxColumnCount = 0;
        if(Files.exists(path) && Files.isRegularFile(path)){
            System.out.println("file exists");
            try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
                String line;
                while ((line = file.readLine()) != null){
                    if(maxColumnCount < line.length() + 1){
                        maxColumnCount = line.length() + 1;
                    }
                }
                System.out.println("found maxColumnCount");
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
            long currtime = System.nanoTime();
            System.out.println("file exists");
            try(RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
                String line;
                int i = 0;
                System.out.println("start reading");
                while ((line = file.readLine()) != null){
                    byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                    hexRows.add(getBytesHexFormat(bytes, i));
                    i++;
                   /* byte[] row = new byte[maxColumnCount];

                    byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
                    System.arraycopy(bytes, 0, row, 0, bytes.length);
                    hexRows.add(getBytesHexFormat(row, i));*/
                    i++;
                }
                System.out.println("file is read");
                long endTime = System.nanoTime();
                long duration = (endTime - currtime) ;
                System.out.println(duration);
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
        /*while(hex.toString().split("\t").length < maxColumnCount){
            hex.append(0).append("\t");
        }*/


        return hex.toString().split("\t");
    }

    public int getMaxColumnCount() {
        return maxColumnCount;
    }

    public Path getPath() {
        return path;
    }
}
