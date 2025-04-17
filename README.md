📘 Overview
This project, developed as part of CS 4308: Concepts of Programming Languages at Kennesaw State University, implements a recursive descent parser for validating Python if-elif-else structures. The parser checks for correct syntax including conditional logic, operators, delimiters, and print statements, mimicking features you'd find in modern IDEs for real-time syntax validation.

🎯 Assignment Purpose
The main objective is to:

Develop a recursive descent parser that evaluates Python if-elif-else statements.

Identify common syntax errors like missing colons, misplaced keywords, or invalid structure.

Build on the previous lexical analyzer by reusing and modifying it to tokenize Python code.

Provide hands-on experience with syntax analysis and recursive descent parsing, a key concept in compiler design.

⚙️ How It Works
File Reader: Reads a .py file and returns its contents as a raw string.

Tokenizer: Strips unnecessary whitespace and returns a list of tokens (type + value).

Example: Type: OPERATOR, Value: +

Parser:

Takes the list of tokens and walks through it recursively.

Follows grammar rules for if-elif-else blocks.

Logs any syntax errors and exits early if invalid.

If parsing completes successfully, the code is considered syntactically correct.

✅ Example of Valid Input
x = 10
if x < 0:
    print("x is negative")
elif x == 0:
    print("x is zero")
else:
    print("x is positive")

❌ Example of Invalid Inputs
x = 10
if x < 0:
    print("x is negative")
elif x > 10:
    print("x is zero")
else:
    print("x is positive")
elif y > 10:
    print("hello")  # <-- This is invalid

🚧 Limitations
Does not support:

Nested conditionals inside condition checks or block bodies

Advanced Python structures or third-party library code

Focused solely on basic if-elif-else grammar and syntax validation

👨‍💻 Authors
Jackson Nuckles
Gabriel Santiago
