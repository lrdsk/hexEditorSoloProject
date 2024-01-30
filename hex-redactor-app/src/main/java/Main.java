import org.example.frames.functional.FileChooser;
import org.example.frames.functional.FileDisplayFrame;
import org.example.utils.CustomFileReader;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileChooser fileChooser = new FileChooser();
        CustomFileReader customFileReader = new CustomFileReader(new File("").toPath());
        FileDisplayFrame mainForm = new FileDisplayFrame(customFileReader, fileChooser);
        mainForm.setVisible(true);
    }
}