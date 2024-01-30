package org.example.tests;

import org.example.utils.CustomFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CustomFileReaderTest {
    private Path tempFile;
    private String content;

    @BeforeEach
    public void createFile() throws IOException {
        content = "Hello, World!";
        tempFile = Files.createTempFile("test", ".txt");
        Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));

    }

    @Test
    public void readBytesFromFileToHex_ShouldReturnCorrectList() throws IOException {
        CustomFileReader customFileReader = new CustomFileReader(tempFile);
        List<String[]> strings = customFileReader.readBytesFromFileToHex();

        assertEquals(1, strings.size());
        String[] row = strings.get(0);

        assertEquals(content.length() + 1, row.length); // +1 for line number column
        assertEquals("1", row[0]); // line number
        assertEquals("48", row[1]); // 'H' in hex
        assertEquals("65", row[2]); // 'e' in hex
        assertEquals("6C", row[3]);

    }

    @AfterEach
    public void afterReadFile() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
