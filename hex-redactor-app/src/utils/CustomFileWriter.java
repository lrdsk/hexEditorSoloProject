package utils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomFileWriter {
    private final Path path;

    public CustomFileWriter(Path path) {
        this.path = path;
    }

    public void writeTableDataToFile(List<String[]> hexTable){
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(path.toFile().toPath()), StandardCharsets.UTF_8))
        ){
            List<byte[]> byteList = convertToBytes(hexTable);
            List<String[]> dataStringList = convertToString(byteList);

            for(String[] line : dataStringList){
                for(String s : line){
                    bufferedWriter.write(s);
                }
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<byte[]> convertToBytes(List<String[]> hexList) {
        List<byte[]> byteList = new ArrayList<>();

        for (String[] hexArray : hexList) {
            byte[] byteArray = new byte[hexArray.length];
            for (int i = 0; i < hexArray.length; i++) {
                if(hexArray[i] != null) {
                    byteArray[i] = Byte.parseByte(hexArray[i], 16);
                }else{
                    byteArray[i] = 0;
                }
            }

            byteList.add(byteArray);
        }

        return byteList;
    }

    private static List<String[]> convertToString(List<byte[]> byteList){
        List<String[]> charList = new ArrayList<>();
        for(byte[] byteArray : byteList){
            String[] row = new String[byteArray.length];
            for(int i = 0; i < byteArray.length; i++){
                if(!(byteArray[i] == 0)){
                    row[i] = String.valueOf((char) byteArray[i]);
                }else {
                    row[i] = "0";
                }
            }
            charList.add(row);
        }

        return charList;
    }
}
