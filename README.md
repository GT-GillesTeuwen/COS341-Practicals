# Student Programming Language Parser with Scoping

## Description

This program is a parser for the student programming language, which takes in code written in the student programming language and outputs a syntax tree visually and as an xml file. The program runs as an exe and provides a hybrid console and gui output. The lexed tokens are displayed on the console for error tracing while the gui provides error messages and a visual syntax tree.

The program now also performs scoping. Procedure definitions, procedure calls and variables are assingned a scope. Calling procedures that don't exist will result in an error. Ambiguous procedure names will also result in errors. Uncalled procedures result in warnings.

As of practical 4 the program will give error messages and fail to compile when unassigned variables are used as if they have values. This field is visible as the "has-value" column in the symbol table.

## Requirements

Java version 19 was used to write this parser. Java's backwards compatabiliy may be used but this should be done cautiously.

## Launching

The program is an exe file and can be launched on a Windows system by clicking on it.

If the exe does not launch the project can be run using
javac *.java
java Main

## Using the Application

1. Launch the program
2. A file-chooser will appear. Select a text file containing student programming language code.
3. Once selected, the lexer will attempt to tokenise the input string. Then the parser will attempt to parse the sequence of tokens.
4. Output:
   1. If errors or warnings have occured JMessageDialogues will inform the user of the errors or warnings. The console can then be used to trace the specfic token which caused the error. If an error occured while lexing then the error may only appear in the console.
   2. If no errors occured then a visualiser will appear (see below). Also a dialogue will appear informing the user of the location of the xml file and html table. For convenience, the parsed xml file is created in the same directory as the original text file. The html table is also created in the same directory.

## Using the Visualiser

There are two tabs in the visualiser

1. The AST tab.
   1. Drag nodes to move them
   2. Click on nodes to look inside them and see their ID, data, children etc
   3. Drag empty space to pan across the whole tree
   4. Scroll to adjust the zoom.
   5. Right click on empty space to get the context menu and adjust font size
2. The Table tab
   1. Scroll through the table to see scope IDs and relevant information
   2. Click a row then right click to get the context menu. This allows you to go to that node in the AST

## Credits

This program was created by Gilles Teuwen for COS341 at the University of Pretoria.
