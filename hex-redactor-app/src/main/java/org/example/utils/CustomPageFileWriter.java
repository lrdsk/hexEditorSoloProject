package org.example.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomPageFileWriter implements AutoCloseable {
    private RandomAccessFile ram;
    private final int pageSize;
    private int currentPage = 0;
    private final byte[] data;
    private final Path path;

    public CustomPageFileWriter(Path file, int pageSize, int currentPage) throws IOException {
        this.path = file;
        this.pageSize = pageSize;
        this.data = new byte[pageSize];
        this.currentPage = currentPage;

        if(Files.exists(path) && Files.isRegularFile(path)) {
            this.ram = new RandomAccessFile(file.toAbsolutePath().toFile(), "rw");
        }
    }
    private void writeData(byte[] data) throws IOException {
        ram.seek((long) currentPage * pageSize);
        ram.write(data);
        setPage(currentPage + 1);
    }

    private void setPage(int pageNumber) {
        this.currentPage = pageNumber;
    }

    public void writeTableDataToFile(List<String[]> hexTable) throws IOException {
        List<byte[]> bytes = convertToBytes(hexTable);

        for(byte[] b : bytes){
            writeData(b);
        }
    }

    private static List<byte[]> convertToBytes(List<String[]> hexList) {
        List<byte[]> byteList = new ArrayList<>();

        for (String[] hexArray : hexList) {
            List<Byte> byteArray = new ArrayList<>();
            for (int i = 0; i < hexArray.length; i++) {
                if(hexArray[i] != null && !Objects.equals(hexArray[i], "00") && !Objects.equals(hexArray[i], "0")) {
                    int intValue = Integer.parseInt(hexArray[i], 16);
                    byteArray.add((byte) intValue);
                }
            }
                
            byte[] bytes = new byte[byteArray.size()];
            for(int i = 0; i < bytes.length; i++){
                bytes[i] = byteArray.get(i);
            }
            byteList.add(bytes);
        }

        return byteList;
    }
    private static List<String[]> convertToString(List<byte[]> byteList){
        List<String[]> charList = new ArrayList<>();
        for(byte[] byteArray : byteList){
            String[] row = new String[byteArray.length];
            for(int i = 0; i < byteArray.length; i++){
                if(!(byteArray[i] == 0)){
                    row[i] = String.valueOf((char) byteArray[i]);
                }else {
                    row[i] = "";
                }
            }
            charList.add(row);
        }

        return charList;
    }


    @Override
    public void close() throws IOException {
        ram.close();
    }

    public Path getPath() {
        return path;
    }
}
