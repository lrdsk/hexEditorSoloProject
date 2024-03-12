package org.example.tests;

import org.example.utils.CustomFilePageReader;
import org.example.utils.CustomPageFileWriter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomFilePageReaderTest {
    @TempDir
    static Path tempDir;
    private Path file;

    private CustomFilePageReader customFilePageReader;
    private final int pageSize = 256; // Пример размера страницы

    @BeforeEach
    void setup() throws IOException {
        file = Files.createTempFile("test", ".txt");
    }

    @AfterEach
    void cleanup() throws IOException {

        Files.deleteIfExists(file);
    }

    @Test
    void readBytesToTableModel() throws IOException {

        Files.writeString(file, "Hello");
        try(CustomFilePageReader customFilePageReader = new CustomFilePageReader(file, pageSize, 0)){
            List<String[]> hexRows = customFilePageReader.readBytesToTableModel(1);

            assertEquals(1, hexRows.size());
            List<String> list = new ArrayList<>();
            for(int i = 0; i < 6; i++){
                list.add(hexRows.get(0)[i]);
            }
            assertArrayEquals(new String[]{"1", "48", "65", "6c", "6c", "6f"}, list.toArray());
        }

    }

    @Test
    void getPageCount() throws IOException {
        try(CustomFilePageReader customFilePageReader = new CustomFilePageReader(file, pageSize, 0)) {
            Files.write(file, "Hello World!".getBytes());
            int pageCount = customFilePageReader.getPageCount();
            assertEquals(1, pageCount);
        }
    }

    @Test
    void getCurrentPage() throws IOException {
        try(CustomFilePageReader customFilePageReader = new CustomFilePageReader(file, pageSize, 0)) {
            byte[] currentPage = customFilePageReader.getCurrentPage();
            assertNotNull(currentPage);
            assertEquals(pageSize, currentPage.length);
        }
    }

    @Test
    void getPath() {
        try(CustomFilePageReader customFilePageReader = new CustomFilePageReader(file, pageSize, 0)) {
            Path path = customFilePageReader.getPath();
            assertNotNull(path);
            assertTrue(Files.exists(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
