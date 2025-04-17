import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

        // my file path :)
        String inputFile = "D:\\school\\Concepts of Programming Languages\\recursiveDescentParser\\src\\main\\input\\input.txt"; // Corrected variable name

        // get the code from the file reader
        fileReader fr = new fileReader(inputFile); //Corrected class name.
        String code = fr.readFile();
        //System.out.println(code);

        // get token value pairs
        tokenizer tk = new tokenizer(code);
        tk.parseCode();
        List<token> tokens = tk.getTokens();

     /*  for(int i = 0; i < tokens.size(); i++){
            System.out.println("Type: " + tokens.get(i).getType() + " Value: "+ tokens.get(i).getValue());
        } */

        Parser parser = new Parser(tokens);
        parser.parse();
    }
}
