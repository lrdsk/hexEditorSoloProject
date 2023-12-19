import frames.FileDisplayFrame;
import utils.CustomFileReader;
import java.io.File;
import java.nio.file.Path;
public class Main {
    public static void main(String[] args) {
        Path path = new File("/home/user/java_tasks/hex-redactor/homework/test.txt").toPath();
        CustomFileReader customFileReader = new CustomFileReader(path);
        FileDisplayFrame mainForm = new FileDisplayFrame(customFileReader);
        mainForm.setVisible(true);
    }
}