package org.example.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SequenceSearcher implements AutoCloseable{
    private final Path path;
    private final int pageSize;
    private RandomAccessFile ram;

    public SequenceSearcher(Path path, int pageSize) throws FileNotFoundException {
        this.path = path;
        this.pageSize = pageSize;
        if (Files.exists(path) && Files.isRegularFile(path)) {
            this.ram = new RandomAccessFile(path.toAbsolutePath().toFile(), "r");
            //readPage();
        }
    }

    public List<Integer> findAllPatterns(byte[] pattern) throws IOException {
        List<Integer> indexes = new ArrayList<>();
        byte[] buffer = new byte[pattern.length];
        int index = 0;
        int x = 1;
        while (x != -1) {
            x = ram.read(buffer);
            //System.out.println("current x = " + x);

            if (equals(buffer, pattern)) {
                //System.out.println("current index = " + index);
                indexes.add(index);
            }
            index+=x;

        }
        return indexes;
    }

    private static boolean equals(byte[] arr1, byte[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void close() throws Exception {
        ram.close();
    }
}
