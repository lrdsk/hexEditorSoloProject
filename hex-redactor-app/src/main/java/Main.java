import org.example.frames.functional.FileChooser;
import org.example.frames.functional.FileDisplayFrame;
import org.example.utils.CustomFilePageReader;
import org.example.utils.CustomFileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileChooser fileChooser = new FileChooser(0);
        try(CustomFilePageReader customFilePageReader= new CustomFilePageReader(new File("").toPath(), 256)) {
            FileDisplayFrame mainForm = new FileDisplayFrame(customFilePageReader, fileChooser);
            mainForm.setVisible(true);
        }
    }
}