# Student Programming Language Parser

## Description

This program is a parser for the student programming language, which takes in code written in the student programming language and outputs a syntax tree visually and as an xml file. The program runs as an exe and provides a hybrid console and gui output. The lexed tokens are displayed on the console for error tracing while the gui provides error messages and a visual syntax tree

## Requirements

Java version 19 was used to write this parser. Java's backwards compatabiliy may be used but this should be done cautiously.

## Launching

The program is an exe file and can be launched on a Windows system by clicking on it.

## Using application

1. Launch the program
2. A file-chooser will appear. Select a text file containing student programming language code.
3. Once selected, the lexer will attempt to tokenise the input string. Then the parser will attempt to parse the sequence of tokens.
4. Output:
   1. If errors have occured JMessageDialogues will inform the user of the errors. The console can then be used to trace the specfic token which caused the error. If an error occured while lexing then the error may only appear in the console
   2. If no errors occured then a visual AST will appear, note that the nodes are draggable. Also a dialogue will appear informing the user of the location of the xml file. For convenience, the parsed xml file is created in the same directory as the original text file


## Credits

This program was created by Gilles Teuwen for COS341 at the University of Pretoria.
