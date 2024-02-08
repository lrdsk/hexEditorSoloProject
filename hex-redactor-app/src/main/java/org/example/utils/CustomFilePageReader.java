package org.example.utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

public class CustomFilePageReader implements AutoCloseable {
    private RandomAccessFile ram;
    private final int pageSize;
    private int currentPage = 0;
    private byte[] data;
    private final Path path;
    private int pagesToRead = 100;
    private int readPages;

    public CustomFilePageReader(Path file, int pageSize) throws IOException {
        this.path = file;
        this.pageSize = pageSize;
        this.data = new byte[pageSize];

        if(Files.exists(path) && Files.isRegularFile(path)) {
            this.ram = new RandomAccessFile(file.toAbsolutePath().toFile(), "r");
            readPage();
        }
    }
    public CustomFilePageReader(Path file, int pageSize, int currentPage) throws IOException {
        this.path = file;
        this.pageSize = pageSize;
        this.data = new byte[pageSize];
        this.currentPage = currentPage;

        if(Files.exists(file) && Files.isRegularFile(file)) {
            this.ram = new RandomAccessFile(file.toAbsolutePath().toFile(), "r");
            readPage();
        }
    }

    public void nextPage() throws IOException {
        if (currentPage != getPageCount()) {
            currentPage++;
            readPage();
        }
    }

    public void previousPage() throws IOException {
        if (currentPage == 0) {
            throw new IllegalStateException("File start reached");
        }
        currentPage--;
        readPage();
    }

    private void readPage() throws IOException {
        data = new byte[pageSize];
        ram.seek((long) currentPage * pageSize);
        ram.read(data);
    }

    private String[] formatBytes(byte[] bytes, int num) {
        String[] data = new String[bytes.length + 1];
        data[0] = String.valueOf(num + 1);
        for (int i = 0; i < bytes.length; i++) {
            data[i + 1] = HexFormat.of().toHexDigits(bytes[i]);
        }
        return data;
    }

    public List<String[]> readBytesToTableModel(int pageCount) throws IOException {
        final List<String[]> hexRows = new ArrayList<>();
        if(Files.exists(path) && Files.isRegularFile(path)){
            int pageIt = 0;
            int remainingPages = getPageCount() - currentPage;
            if(remainingPages < pageCount){
                pageCount = remainingPages;
            }
            while(pageIt < pageCount){
                byte[] bytes = getCurrentPage();

                hexRows.add(formatBytes(bytes, pageIt));
                if(currentPage != getPageCount()){
                    nextPage();
                }
                pageIt++;
            }
            readPages = pageCount;
        }
        return hexRows;
    }

    public int getReadPages() {
        return readPages;
    }
    public int getPageCount() throws IOException {
        long fileSize = ram.length();
        return (int) Math.ceil((double) fileSize / pageSize);
    }

    public byte[] getCurrentPage() {
        return data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void close() throws IOException {
        ram.close();
    }
}
