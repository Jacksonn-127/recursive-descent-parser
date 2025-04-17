import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Parser {
    // tokens
    private final List<token> tokens;
    // index of token list
    private int index = 0;
    // this is how we'll check to make sure elif and else are following if statements
    boolean ifFlag = false;


    public Parser(List<token> inputTokens) {
        this.tokens = inputTokens;
    }

    public void parse() {
        while (index < tokens.size()) {
            statementHandler();
        }
        System.out.println("THE CODE SAMPLE IS ACCEPTED");
    }

    public void statementHandler() {
        //System.out.println("From statement handler: " + tokens.get(index).getValue() + " index: " + index);

        // keep checking tokens until you hit the end
        if (index > tokens.size()) {
            System.out.println("THE CODE SAMPLE IS ACCEPTED");
            System.exit(0);  // 0 for success is conventional
        }
        // if token is conditional logic type
        if (tokens.get(index).getType().equalsIgnoreCase("LOGIC_STATEMENT")) {
            ifHandler();
        }
        // else you have an identifier like x = 10 etc
        else if (tokens.get(index).getType().equalsIgnoreCase("identifier") ||
                tokens.get(index).getType().equalsIgnoreCase("KEYWORD")) {
            handleIdentifier();
        }
        // if its neither an identifier or conditional logic operator exit
        else {
            System.out.println("Syntax Error: Unexpected token " + tokens.get(index).getValue() + " " + index);
            System.exit(1);
        }

        // go next token
        index++;
    }

    public void ifHandler() {
        // if you have if do this
        if (tokens.get(index).getValue().equals("if")) {
            index++;
            handleIdentifier();
            index++;

            // if you successfully identify identifier and there is no : symbol assume EOF
            // first limitation of program here
            if (!tokens.get(index).getValue().equals(":")) {
                System.out.println("Syntax Error: Expected \":\", but found EOF");
                System.exit(0);
            }

            // if you find a valid if statement set the if flag to true
            ifFlag = true;

            // if you can go next index
            if (index + 1 >= tokens.size()) {
                if (!tokens.get(index).getType().equals("IDENTIFIER") && !tokens.get(index).getType().equals("KEYWORD")) {
                    System.out.println("Syntax Error: Expected \"IDENTIFIER OR KEYWORD\", but found EOF");
                    System.exit(0);
                }
            }
        }
        else if (tokens.get(index).getValue().equals("elif")) {
            index++;
            handleIdentifier();
            index++;

            if (!tokens.get(index).getValue().equals(":")) {
                System.out.println("Syntax Error: Expected \":\", but found EOF");
                System.exit(0);
            }

            if(!ifFlag){
                System.out.println("Syntax Error: Expected \"if\", but found elif");
                System.exit(0);
            }

            if (index + 1 >= tokens.size()) {
                if (!tokens.get(index).getType().equals("IDENTIFIER") && !tokens.get(index).getType().equals("KEYWORD")) {
                    System.out.println("Syntax Error: Expected \"IDENTIFIER OR KEYWORD\", but found EOF");
                    System.exit(0);
                }
            }
        }
        else if (tokens.get(index).getValue().equals("else")) {
            index++;

            // debugging for checking else statements, else marks the reset point for the "if" flag
            //System.out.println("reached else");

            if (!tokens.get(index).getValue().equals(":")) {
                System.out.println("Syntax Error: Expected \":\", but found " + tokens.get(index).getType());
                System.exit(0);
            }

            if(!ifFlag){
                System.out.println("Syntax Error: Expected \"if\", but found else");
                System.exit(0);
            }

            // after else you know you need an if again to set the flag back to true
            ifFlag = false;

            if (index + 1 >= tokens.size()) {
                if (!tokens.get(index).getType().equals("IDENTIFIER") && !tokens.get(index).getType().equals("KEYWORD")) {
                    System.out.println("Syntax Error: Expected \"IDENTIFIER OR KEYWORD\", but found EOF");
                    System.exit(0);
                }
            }
        }
    }

    public void handleIdentifier() {
        // Check if there are at least 2 more tokens after the identifier
        if (index + 2 >= tokens.size()) {
            System.out.println("Syntax Error: Unexpected end of input after identifier");
            System.exit(0);
        }
        // if you encounter print
        if (tokens.get(index).getType().equalsIgnoreCase("keyword")) {
            if (index + 3 < tokens.size()) {  // Make sure there are enough tokens
                if (!tokens.get(index + 1).getValue().equalsIgnoreCase("(")) {
                    System.out.println("Syntax Error: Expected \"(\", but found " + tokens.get(index + 1).getType());
                    System.exit(0);
                }
                if (!tokens.get(index + 2).getType().equalsIgnoreCase("STRING_LITERAL") && !tokens.get(index + 2).getType().equalsIgnoreCase("LITERAL")) {
                    System.out.println("Syntax Error: Expected \"STRING_LITERAL\", but found " + tokens.get(index + 2).getType());
                    System.exit(0);
                }
                if (!tokens.get(index + 3).getValue().equalsIgnoreCase(")")) {  // Check for closing parenthesis
                    System.out.println("Syntax Error: Expected \")\", but found " + tokens.get(index + 3).getType());
                    System.exit(0);
                }

                // If we get here, all is well
                index += 3;  // Shift index by 3 positions (the statementHandler will increment by 1 more)
                //System.out.println(tokens.get(index).getValue());


                } else {
                    System.out.println("Syntax Error: Expected \"SEPARATOR OR LITERAL\", but found EOF");
                    System.exit(0);
                }


            }

            // Check if the next token is an operator
            else if (!tokens.get(index + 1).getType().equalsIgnoreCase("assignment_operator") &&
                    !tokens.get(index + 1).getType().equalsIgnoreCase("OPERATOR") &&
                    !tokens.get(index + 1).getType().equalsIgnoreCase("ADDITION_OPERATOR")) {
                System.out.println("Syntax Error: Expected \"OPERATOR\", but found " +
                        tokens.get(index + 1).getType() + " " + tokens.get(index + 1).getValue());
                System.exit(0);
            }

            // Check if the token after the operator is a literal
            else if (!tokens.get(index + 2).getType().equalsIgnoreCase("INT_LITERAL") &&
                    !tokens.get(index + 2).getType().equalsIgnoreCase("STRING_LITERAL")) {
                System.out.println("Syntax Error: Expected \"LITERAL\", but found " +
                        tokens.get(index + 2).getType());
                System.exit(0);
            }

            // If we got here, everything is valid
            else {
                index = index + 2;
            }
        }
    }
