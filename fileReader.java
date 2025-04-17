import java.io.*;
import java.util.Arrays;

public class fileReader {
    String filePath;

    public fileReader(String FilePath){
        this.filePath = FilePath;
    }

    public String readFile() throws IOException {
        String fileContent = "";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                fileContent = fileContent.concat(line).concat("\n");
            }
        }catch (IOException e){
            System.out.println("Error reading file: " + Arrays.toString(e.getStackTrace()));
        }


        return fileContent;
    }
}
