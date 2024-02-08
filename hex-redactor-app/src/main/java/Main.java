import org.example.frames.functional.FileChooser;
import org.example.frames.functional.FileDisplayFrame;
import org.example.utils.CustomFilePageReader;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileChooser fileChooser = new FileChooser(0);
        try(CustomFilePageReader customFilePageReader= new CustomFilePageReader(new File("").toPath(), 256)) {
            FileDisplayFrame mainForm = new FileDisplayFrame(customFilePageReader, fileChooser);
            mainForm.setVisible(true);
        }
    }
}