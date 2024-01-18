import frames.functional.FileChooser;
import frames.functional.FileDisplayFrame;
import utils.CustomFileReader;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        FileChooser fileChooser = new FileChooser();
        CustomFileReader customFileReader = new CustomFileReader(new File("").toPath());
        FileDisplayFrame mainForm = new FileDisplayFrame(customFileReader, fileChooser);
        mainForm.setVisible(true);
    }
}