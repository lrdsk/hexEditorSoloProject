package org.example.tests;

import org.example.utils.CustomFilePageReader;
import org.example.utils.CustomPageFileWriter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomPageFileWriterTest {
    @TempDir
    static Path tempDir;
    private Path file;
    private CustomPageFileWriter customPageFileWriter;
    private final int pageSize = 256;

    @BeforeEach
    void setup() throws IOException {
        file = Files.createTempFile("test", ".txt");
    }


    @Test
    void writeTableDataToFile() throws IOException {
        try(CustomPageFileWriter customPageFileWriter = new CustomPageFileWriter(file, pageSize, 0)){
            List<String[]> hexTable = new ArrayList<>();
            hexTable.add(new String[]{"48","65","6C","6C","6F","57","6f","72","6C","64","21"}); // "Hello", "World", "!"

            customPageFileWriter.writeTableDataToFile(hexTable);

            // Проверка содержимого файла
            List<String> fileLines = Files.readAllLines(customPageFileWriter.getPath());
            assertEquals("HelloWorld!", fileLines.get(0));
        }

    }
    @AfterEach
    public void afterReadFile() throws IOException {
        Files.deleteIfExists(file);
    }

}
