package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utils.CustomFileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomFileWriterTest {

    private CustomFileWriterTest customFileWriterTest;

    @BeforeEach
    public void setup() {
        customFileWriterTest = new CustomFileWriterTest();
    }

    @Test
    public void writeTableDataToFile_ShouldWriteCorrectDataInHexFormat(@TempDir Path tempDir) throws IOException {
        List<String[]> hexTable = generateTestData();
        Path filePath = tempDir.resolve("testFile.txt");

        CustomFileWriter customFileWriter = new CustomFileWriter(filePath);
        customFileWriter.writeTableDataToFile(hexTable);

        // Проверяем, что файл был создан
        assertTrue(Files.exists(filePath));

        // Читаем данные из файла и проверяем, что записанные данные соответствуют ожидаемым
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(hexTable.size(), lines.size());

        for (int i = 0; i < hexTable.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < hexTable.get(i).length; j++){
                if(Byte.parseByte(hexTable.get(i)[j], 16) != 0){
                    sb.append((char)Byte.parseByte(hexTable.get(i)[j], 16));
                }else{
                    sb.append(0);
                }

            }
            String expectedLine = sb.toString();
            assertEquals(expectedLine, lines.get(i));
            sb.delete(0, expectedLine.length());
        }
    }

    private List<String[]> generateTestData() {
        List<String[]> hexTable = new ArrayList<>();

        hexTable.add(new String[]{"61", "0", "62"});
        hexTable.add(new String[]{"66", "61", "3F"});

        return hexTable;
    }
}
