package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.CustomFileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
    public void testReadBytesFromFile() throws IOException {
        CustomFileReader customFileReader = new CustomFileReader(tempFile);
        List<String[]> strings = customFileReader.readBytesFromFileToHex();

        assertEquals(1, strings.size());
        String[] row = strings.get(0);

        assertEquals(content.length() + 1, row.length); // +1 for line number column
        assertEquals("1", row[0]); // line number
        assertEquals("48", row[1]); // 'H' in hex
        assertEquals("65", row[2]); // 'e' in hex
        assertEquals("6C", row[3]);

        /*CustomFileReader customFileReader1 = new CustomFileReader(Paths.get("/home/user/java_tasks/hex-redactor/homework/1.txt"));
        List<String[]> strings1 = customFileReader1.readBytesFromFileToHex();
        assertFalse(strings1.isEmpty());
    */
    }

    @AfterEach
    public void afterReadFile() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
