public class Main {
    public static void main(String[] args) {
        // get my local file paths
        //String sampleJavaPath = "src\\main\\inputs\\sampleJava.txt";
        //String samplePythonPath = "src\\main\\inputs\\samplePython.txt";

        String sampleJavaPath = "D:\\school\\Concepts of Programming Languages\\LexicalAnalyzer\\src\\main\\inputs\\sampleJava.txt";
        String samplePythonPath = "D:\\school\\Concepts of Programming Languages\\LexicalAnalyzer\\src\\main\\inputs\\samplePython.txt";

        // get the Strings of code
        String javaCode = new fileReader(sampleJavaPath).readFile();
        String pythonCode = new fileReader(samplePythonPath).readFile();

        // parse the Java code
        System.out.println("Parse Java:");
        lexicalAnalyzer LA = new lexicalAnalyzer(javaCode);
        LA.parseCode();

        // parse the Python code
        System.out.println("\nParse Python:");
        LA = new lexicalAnalyzer(pythonCode);
        LA.parseCode();
        System.exit(0);
    }
}

