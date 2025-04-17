import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

// read file from input folder and send it back to main
public class fileReader {
    File file;

    // constructor pass in file location
    public fileReader(String FilePath){
        this.file = new File(FilePath);
    }

    // read the file
    public String readFile(){
        StringBuilder fileText = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine() )!= null){
                fileText.append(line).append("\n");
            }

            br.close();
        }catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }

        return fileText.toString();
    }
}