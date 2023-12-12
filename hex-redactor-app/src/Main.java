import frames.MainFrame;
import frames.SequenceFrame;
import utils.CustomFileReader;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        CustomFileReader customFileReader = new CustomFileReader(Path.of("/home/user/java_tasks/hex-redactor/homework/test.txt"));
        MainFrame mainForm = new MainFrame(customFileReader);
        mainForm.setVisible(true);
    }
}