import java.util.*;

class token{
    String type;
    String value;
    public token(String Type, String Value){
        this.type = Type;
        this.value = Value;
    }

    @Override
    public String toString() {
        return "Token{type='" + type + "', value='" + value + "'}";
    }

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }
}

public class tokenizer {
    // code is just a string of text with the code
    private final String code;
    private int currentIndex;
    private char currentCharacter;

    // set containing keywords commonly used in java and python, limiting myself to only the keywords found in the two examples
    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
            "public", "class", "static", "void", "int", "def", "print"
    ));

    private static final Set<String> ifStmt = new HashSet<>(Arrays.asList(
            "if", "else", "elif"
    ));

    // next two lines are only a small subset of all the operators and delimiters in java and python, only using those that are relevant from the examples.
    // adding n number of new operators or separators will not change the program
    private static final Set<String> operators = new HashSet<>(Arrays.asList(
            "-", "/", "*", "+", "=", "<", ">"
    ));
    private static final Set<String> separators = new HashSet<>(Arrays.asList(
            "(", ")", "{", "}", ";", ",", ".", "[", "]", ":"
    ));

    LinkedList<token> tokens = new LinkedList<>();

    // constructor, takes in a String that contains the code you want to analyze
    public tokenizer(String Code) {
        // If code is null exit
        if (Code == null) {
            System.out.println("Code is null");
            System.exit(-1);
        }

        this.code = Code;
        this.currentIndex = 0;
        this.currentCharacter = Code.charAt(this.currentIndex);
    }

    public List<token> getTokens(){
        return tokens;
    }

    // code works by looking at index in array, and moving forward after handling each index appropriately
    private void advance() {
        // move to the next character
        currentIndex++;
        if (currentIndex < code.length()) {
            currentCharacter = code.charAt(currentIndex);
        } else {
            System.out.println("Input Successfully Tokenized");
            currentCharacter = '\0'; // End of input
        }
    }

    // if current index is white space skip it
    private void skipWhitespace() {
        // if character is white space go next
        while (Character.isWhitespace(currentCharacter)) {
            advance();
        }
    }

    private String scanKeyword() {
        StringBuilder result = new StringBuilder();

        while (Character.isLetterOrDigit(currentCharacter) || currentCharacter == '_') {
            result.append(currentCharacter);
            advance();
        }

        // instead of returning like other methods we need to differentiate if the string is a keyword or identifier, find it out below.
        String temp = result.toString();

        // if character is in keywords print it
        if (keywords.contains(temp)) {
            tokens.add(new token("KEYWORD", temp));
            return "KEYWORD: " + temp;
        }
        else if(ifStmt.contains(temp)){
            tokens.add(new token("LOGIC_STATEMENT", temp));
            return "LOGIC_STATEMENT" + temp;
        }
        // if it's not a keyword, then it must be an identifier, print it out
        else {
            tokens.add(new token("IDENTIFIER", temp));
            return "IDENTIFIER: " + temp;
        }
    }

    // create new string builder object because if you have for example 59 you need to build a string consisting of 5 and 9
    // return result as a string
    private String scanInteger() {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentCharacter)) {
            result.append(currentCharacter);
            advance();
        }
        tokens.add(new token("INT_LITERAL", result.toString()));
        return "INT_LITERAL: " + result;
    }

    // works the same way as scan integer just with operators
    private String scanOperator() {
        StringBuilder result = new StringBuilder();
        String temp = "";
        // this only works when operators are 1 character long
        // if operators were more than 1 character long you would need additional logic to build temp as a string builder,
        // and then handle output appropriately with switch
        while (operators.contains(String.valueOf(currentCharacter))) {
            result.append(currentCharacter);
            temp = String.valueOf(currentCharacter);
            advance();
        }
        if (temp.equals("=")){
            tokens.add(new token("ASSIGNMENT_OPERATOR", result.toString()));
            return "ASSIGNMENT_OPERATOR: " + result;
        }
        else if(temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/")){
            tokens.add(new token("ADDITION_OPERATOR", result.toString()));
            return "OPERATOR: " + result;
        }

        tokens.add(new token("OPERATOR", result.toString()));
        return "OPERATOR: " + result;
    }

    // just reads one separator is only one character at a time
    private String scanSeparator() {
        String result = String.valueOf(currentCharacter);
        advance(); // move to next value

        tokens.add(new token("SEPARATOR", result));
        return "SEPARATOR: " + result;
    }

    // find out if something is string literal
    private String scanStringLiteral() {
        StringBuilder result = new StringBuilder();
        char quote = currentCharacter;
        advance(); // Move past opening quote

        // if char is not end of string marked by " and char is not the end of the code add it
        while (currentCharacter != quote && currentCharacter != '\0') {
            result.append(currentCharacter);
            advance();
        }

        advance(); // Move past closing quote
        tokens.add(new token("STRING_LITERAL", result.toString()));
        return "STRING_LITERAL: \"" + result + "\"";
    }

    public void parseCode() {
        // while index is in range of code.length()
        while (currentIndex < code.length()) {
            // skip white space if applicable
            skipWhitespace();

            //  System.out.println(currentIndex + " / " + code.length());
            // figure out what the current character could be and print it out accordingly
            if (Character.isLetter(currentCharacter) || currentCharacter == '_') {
               scanKeyword();
            }
            else if (Character.isDigit(currentCharacter)) {
                scanInteger();
            }
            else if (operators.contains(String.valueOf(currentCharacter))) {
                scanOperator();
            }
            // if character is new line or a quote
            else if (currentCharacter == '"' || currentCharacter == '\'') {
                scanStringLiteral();
            }
            else if (separators.contains(String.valueOf(currentCharacter))) {
                scanSeparator();
            }
            else if (currentCharacter == '\0') {
                break;
            }
        }
    }
}
