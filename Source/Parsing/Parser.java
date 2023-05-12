package Parsing;

import java.util.ArrayList;

import Exceptions.UnexpectedTokenException;
import Lexing.Symbol;
import Lexing.Token;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class Parser {

    private ArrayList<Symbol> allTokens;
    private Symbol input;
    private int count;

    public Parser(ArrayList<Symbol> allTokens) throws UnexpectedTokenException {
        if (allTokens.size() == 0) {
            throw new UnexpectedTokenException("Parser Error: Nothing to parse");
        }
        this.allTokens = allTokens;
        input = allTokens.get(0);
        allTokens.remove(0);

        count = 0;
    }

    private void match(Token expectedToken) throws UnexpectedTokenException {
        if (input.getToken() == expectedToken) {
            count++;
            if (allTokens.size() != 0) {
                input = allTokens.get(0);
                allTokens.remove(0);
            }

        } else {
            throw new UnexpectedTokenException(
                    "ERROR UNEXPECTED TOKEN: expected:" + expectedToken + " got " + input + " at token " + count);

        }

    }

    public Node parseProgrP() throws UnexpectedTokenException {
        if (input.getToken() == Token.GET || input.getToken() == Token.VALUE || input.getToken() == Token.TEXT
                || input.getToken() == Token.DEC_NUMVAR
                || input.getToken() == Token.DEC_BOOLVAR || input.getToken() == Token.DEC_STRINGVAR
                || input.getToken() == Token.CALL
                || input.getToken() == Token.START_WHILE || input.getToken() == Token.START_IF
                || input.getToken() == Token.HALT) {
            Node n = parseProgr();
            match(Token.DOLLAR);
            System.out.println("Success");
            return n;
        } else {
            System.out.println("Error, with token " + count + " did not expect " + input + " at ProgP");
            return null;
        }

    }

    private Node parseProgr() throws UnexpectedTokenException {
        if (input.getToken() == Token.GET || input.getToken() == Token.VALUE || input.getToken() == Token.TEXT
                || input.getToken() == Token.DEC_NUMVAR
                || input.getToken() == Token.DEC_BOOLVAR || input.getToken() == Token.DEC_STRINGVAR
                || input.getToken() == Token.CALL
                || input.getToken() == Token.START_WHILE || input.getToken() == Token.START_IF
                || input.getToken() == Token.HALT) {
            Node c1 = parseAlgo();
            Node c2 = parseProcDefs();
            Node[] children = { c1, c2 };
            return new nNode("PROGR", children);
        } else {
            System.out.println("Error, with token " + count + " did not expect " + input + " at Prog");
            return null;
        }
    }

    private Node parseProcDefs() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case COMMA:
                tNode c1 = new tNode(input);
                match(Token.COMMA);
                Node c2 = parseProc();
                Node c3 = parseProcDefs();
                Node[] children = { c1, c2, c3 };
                return new nNode("PROCDEFS", children);
            case DOLLAR:
            case CLOSE_BRACE:
                tNode c1_1 = new tNode();
                Node[] children_1 = { c1_1 };
                return new nNode("PROCDEFS", children_1);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseProcDefs");
                return null;
        }
    }

    private Node parseProc() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case PROC:
                tNode c1 = new tNode(input);
                match(Token.PROC);
                Node c2 = parseDigits();
                tNode c3 = new tNode(input);
                match(Token.OPEN_BRACE);
                Node c4 = parseProgr();
                tNode c5 = new tNode(input);
                match(Token.CLOSE_BRACE);
                Node[] children = { c1, c2, c3, c4, c5 };
                return new nNode("PROC", children);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseProc");
                return null;
        }
    }

    private Node parseDigits() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case P_DIGIT:
            case ZERO:
                Node c1 = parseD();
                Node c2 = parseMore();
                Node[] children = { c1, c2 };
                return new nNode("DIGITS", children);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseDigits");
                return null;
        }
    }

    private Node parseMore() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case P_DIGIT:
            case ZERO:
                Node c1 = parseDigits();
                Node[] children = { c1 };
                return new nNode("MORE", children);
            case OPEN_BRACE:
            case DOLLAR:
            case CLOSE_BRACE:
            case COMMA:
            case STAR:
            case SEMICOLON:
            case ASSIGN:
            case CLOSE_BRACKET:
            case DEC_POINT:
            case IF_BODY:
            case WHILE_BODY:
                tNode c1_1 = new tNode();
                Node[] children_1 = { c1_1 };
                return new nNode("MORE", children_1);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at ParseMore");
                return null;
        }
    }

    private Node parseD() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case P_DIGIT:
                tNode c1 = new tNode(input);
                match(Token.P_DIGIT);
                Node[] children = { c1 };
                return new nNode("D", children);
            case ZERO:
                tNode c1_1 = new tNode(input);
                match(Token.ZERO);
                Node[] children_1 = { c1_1 };
                return new nNode("D", children_1);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseD");
                return null;
        }
    }

    private Node parseAlgo() throws UnexpectedTokenException {
        if (input.getToken() == Token.GET || input.getToken() == Token.VALUE || input.getToken() == Token.TEXT
                || input.getToken() == Token.DEC_NUMVAR
                || input.getToken() == Token.DEC_BOOLVAR || input.getToken() == Token.DEC_STRINGVAR
                || input.getToken() == Token.CALL
                || input.getToken() == Token.START_WHILE || input.getToken() == Token.START_IF
                || input.getToken() == Token.HALT) {
            Node c1 = parseInstr();
            Node c2 = parseComment();
            Node c3 = parseSeq();
            Node[] children = { c1, c2, c3 };
            return new nNode("ALGO", children);
        } else {
            System.out.println("Error, with token " + count + " did not expect " + input + " at Prog");
            return null;
        }
    }

    private Node parseSeq() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case SEMICOLON:
                tNode c1 = new tNode(input);
                match(Token.SEMICOLON);
                Node c2 = parseAlgo();
                Node[] children = { c1, c2 };
                return new nNode("SEQ", children);
            case DOLLAR:
            case CLOSE_BRACE:
            case COMMA:
                tNode c1_1 = new tNode();
                Node[] children_1 = { c1_1 };
                return new nNode("SEQ", children_1);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseSeq");
                return null;
        }
    }

    private Node parseComment() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case STAR:
                Node[] children = new Node[17];
                tNode c1 = new tNode(input);
                match(Token.STAR);
                for (int i = 0; i < 15; i++) {
                    children[i + 1] = parseC();
                }
                tNode c2 = new tNode(input);
                match(Token.STAR);
                children[0] = c1;
                children[16] = c2;
                return new nNode("COMMENT", children);
            case DOLLAR:
            case CLOSE_BRACE:
            case COMMA:
            case SEMICOLON:
                tNode c1_1 = new tNode();
                Node[] children_1 = { c1_1 };
                return new nNode("COMMENT", children_1);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseComment");
                return null;
        }
    }

    private Node parseC() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case ASCII:
                tNode c1 = new tNode(input);
                match(Token.ASCII);
                Node[] children = { c1 };
                return new nNode("C", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseC");
                return null;
        }
    }

    private Node parseInstr() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case GET:
                Node c1 = parseInput();
                Node[] children = { c1 };
                return new nNode("INSTR", children);
            case TEXT:
            case VALUE:
                c1 = parseOutput();
                Node[] children_1 = { c1 };
                return new nNode("INSTR", children_1);
            case DEC_NUMVAR:
            case DEC_BOOLVAR:
            case DEC_STRINGVAR:
                c1 = parseAssign();
                Node[] children_2 = { c1 };
                return new nNode("INSTR", children_2);
            case CALL:
                c1 = parseCall();
                Node[] children_3 = { c1 };
                return new nNode("INSTR", children_3);
            case START_WHILE:
                c1 = parseLoop();
                Node[] children_4 = { c1 };
                return new nNode("INSTR", children_4);
            case START_IF:
                c1 = parseBranch();
                Node[] children_5 = { c1 };
                return new nNode("INSTR", children_5);
            case HALT:
                tNode c1_1 = new tNode(input);
                match(Token.HALT);
                Node[] children_6 = { c1_1 };
                return new nNode("INSTR", children_6);
            default:
                System.out.println("Error unexpected token at parseInstr");
                return null;

        }
    }

    private Node parseBranch() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case START_IF:
                tNode c1 = new tNode(input);
                match(Token.START_IF);
                Node c2 = parseBoolExpr();
                tNode c3 = new tNode(input);
                match(Token.IF_BODY);
                Node c4 = parseAlgo();
                tNode c5 = new tNode(input);
                match(Token.CLOSE_BRACE);
                Node c6 = parseElse();
                Node[] children = { c1, c2, c3, c4, c5, c6 };
                return new nNode("BRANCH", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseBranch");
                return null;
        }
    }

    private Node parseElse() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case START_ELSE:
                tNode c1 = new tNode(input);
                match(Token.START_ELSE);
                Node c2 = parseAlgo();
                tNode c3 = new tNode(input);
                match(Token.CLOSE_BRACE);
                Node[] children = { c1, c2, c3 };
                return new nNode("ELSE", children);

            case DOLLAR:
            case CLOSE_BRACE:
            case COMMA:
            case STAR:
            case SEMICOLON:
                tNode c1_1 = new tNode();
                Node[] children_1 = { c1_1 };
                return new nNode("ELSE", children_1);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseElse");
                return null;
        }
    }

    private Node parseBoolExpr() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_BOOLVAR:
            case LOGIC_CONST:
            case START_AND:
            case START_OR:
            case START_NOT:
                Node c1 = parseLogic();
                Node[] children = { c1 };
                return new nNode("BOOLEXPR", children);
            case START_EQ:
            case START_LT:
            case START_GT:
                Node c1_1 = parseCmpr();
                Node[] children_1 = { c1_1 };
                return new nNode("BOOLEXPR", children_1);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseBoolExpr");
                return null;
        }
    }

    private Node parseCmpr() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case START_EQ:
                tNode c1 = new tNode(input);
                match(Token.START_EQ);
                Node c2 = parseNumExpr();
                tNode c3 = new tNode(input);
                match(Token.COMMA);
                Node c4 = parseNumExpr();
                tNode c5 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children = { c1, c2, c3, c4, c5 };
                return new nNode("CMPR", children);
            case START_LT:
                tNode c1_1 = new tNode(input);
                match(Token.START_LT);
                Node c2_1 = parseNumExpr();
                tNode c3_1 = new tNode(input);
                match(Token.COMMA);
                Node c4_1 = parseNumExpr();
                tNode c5_1 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_1 = { c1_1, c2_1, c3_1, c4_1, c5_1 };
                return new nNode("CMPR", children_1);
            case START_GT:
                tNode c1_2 = new tNode(input);
                match(Token.START_GT);
                Node c2_2 = parseNumExpr();
                tNode c3_2 = new tNode(input);
                match(Token.COMMA);
                Node c4_2 = parseNumExpr();
                tNode c5_2 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_2 = { c1_2, c2_2, c3_2, c4_2, c5_2 };
                return new nNode("CMPR", children_2);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseCmpr");
                return null;
        }
    }

    private Node parseLogic() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_BOOLVAR:
                Node c1 = parseBoolVar();
                Node[] children = { c1 };
                return new nNode("LOGIC", children);
            case LOGIC_CONST:
                tNode c1_1 = new tNode(input);
                match(Token.LOGIC_CONST);
                Node[] children_1 = { c1_1 };
                return new nNode("LOGIC", children_1);
            case START_AND:
                tNode c1_2 = new tNode(input);
                match(Token.START_AND);
                Node c2_2 = parseBoolExpr();
                tNode c3_2 = new tNode(input);
                match(Token.COMMA);
                Node c4_2 = parseBoolExpr();
                tNode c5_2 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_2 = { c1_2, c2_2, c3_2, c4_2, c5_2 };
                return new nNode("LOGIC", children_2);
            case START_OR:
                tNode c1_3 = new tNode(input);
                match(Token.START_OR);
                Node c2_3 = parseBoolExpr();
                tNode c3_3 = new tNode(input);
                match(Token.COMMA);
                Node c4_3 = parseBoolExpr();
                tNode c5_3 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_3 = { c1_3, c2_3, c3_3, c4_3, c5_3 };
                return new nNode("LOGIC", children_3);
            case START_NOT:
                tNode c1_4 = new tNode(input);
                match(Token.START_NOT);
                Node c2_4 = parseBoolExpr();
                tNode c3_4 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_4 = { c1_4, c2_4, c3_4 };
                return new nNode("LOGIC", children_4);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseLogic");
                return null;
        }
    }

    private Node parseBoolVar() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_BOOLVAR:
                tNode c1 = new tNode(input);
                match(Token.DEC_BOOLVAR);
                Node c2 = parseDigits();
                Node[] children = { c1, c2 };
                return new nNode("BOOLVAR", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseBoolVar");
                return null;
        }
    }

    private Node parseLoop() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case START_WHILE:
                tNode c1 = new tNode(input);
                match(Token.START_WHILE);
                Node c2 = parseBoolExpr();
                tNode c3 = new tNode(input);
                match(Token.WHILE_BODY);
                Node c4 = parseAlgo();
                tNode c5 = new tNode(input);
                match(Token.CLOSE_BRACE);
                Node[] children = { c1, c2, c3, c4, c5 };
                return new nNode("LOOP", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseLoop");
                return null;
        }
    }

    private Node parseCall() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case CALL:
                tNode c1 = new tNode(input);
                match(Token.CALL);
                Node c2 = parseDigits();
                Node[] children = { c1, c2 };
                return new nNode("CALL", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseCall");
                return null;
        }
    }

    private Node parseAssign() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_NUMVAR:
                Node c1 = parseNumVar();
                tNode c2 = new tNode(input);
                match(Token.ASSIGN);
                Node c3 = parseNumExpr();
                Node[] children = { c1, c2, c3 };
                return new nNode("ASSIGN", children);
            case DEC_BOOLVAR:
                Node c1_1 = parseBoolVar();
                tNode c2_1 = new tNode(input);
                match(Token.ASSIGN);
                Node c3_1 = parseBoolExpr();
                Node[] children_1 = { c1_1, c2_1, c3_1 };
                return new nNode("ASSIGN", children_1);
            case DEC_STRINGVAR:
                Node c1_2 = parseStringV();
                tNode c2_2 = new tNode(input);
                match(Token.ASSIGN);
                Node c3_2 = parseStri();
                Node[] children_2 = { c1_2, c2_2, c3_2 };
                return new nNode("ASSIGN", children_2);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseAssign");
                return null;
        }
    }

    private Node parseStri() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case QUOTE:
                Node[] children = new Node[17];
                tNode c1 = new tNode(input);
                match(Token.QUOTE);
                for (int i = 0; i < 15; i++) {
                    children[i + 1] = parseC();
                }
                tNode c2 = new tNode(input);
                match(Token.QUOTE);
                children[0] = c1;
                children[16] = c2;
                return new nNode("STRI", children);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseStri");
                return null;
        }
    }

    private Node parseStringV() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_STRINGVAR:
                tNode c1 = new tNode(input);
                match(Token.DEC_STRINGVAR);
                Node c2 = parseDigits();
                Node[] children = { c1, c2 };
                return new nNode("STRINGV", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseStringV");
                return null;
        }
    }

    private Node parseNumExpr() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case START_ADD:
                tNode c1 = new tNode(input);
                match(Token.START_ADD);
                Node c2 = parseNumExpr();
                tNode c3 = new tNode(input);
                match(Token.COMMA);
                Node c4 = parseNumExpr();
                tNode c5 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children = { c1, c2, c3, c4, c5 };
                return new nNode("NUMEXPR", children);
            case START_MUL:
                tNode c1_1 = new tNode(input);
                match(Token.START_MUL);
                Node c2_1 = parseNumExpr();
                tNode c3_1 = new tNode(input);
                match(Token.COMMA);
                Node c4_1 = parseNumExpr();
                tNode c5_1 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_1 = { c1_1, c2_1, c3_1, c4_1, c5_1 };
                return new nNode("NUMEXPR", children_1);
            case START_DIV:
                tNode c1_2 = new tNode(input);
                match(Token.START_DIV);
                Node c2_2 = parseNumExpr();
                tNode c3_2 = new tNode(input);
                match(Token.COMMA);
                Node c4_2 = parseNumExpr();
                tNode c5_2 = new tNode(input);
                match(Token.CLOSE_BRACKET);
                Node[] children_2 = { c1_2, c2_2, c3_2, c4_2, c5_2 };
                return new nNode("NUMEXPR", children_2);
            case DEC_NUMVAR:
                Node c1_3 = parseNumVar();
                Node[] children_3 = { c1_3 };
                return new nNode("NUMEXPR'", children_3);
            case DEC_ZERO:
            case MINUS:
            case P_DIGIT:

                Node c1_4 = parseDecNum();
                Node[] children_4 = { c1_4 };
                return new nNode("NUMEXPR'", children_4);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseNumExpr");
                return null;
        }
    }

    private Node parseNumExprP() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_NUMVAR:
                Node c1 = parseNumVar();
                Node[] children = { c1 };
                return new nNode("NUMEXPR'", children);
            case DEC_ZERO:
            case MINUS:
            case P_DIGIT:
                Node c1_1 = parseDecNum();
                Node[] children_1 = { c1_1 };
                return new nNode("NUMEXPR'", children_1);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseNumExprP");
                return null;
        }
    }

    private Node parseDecNum() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_ZERO:
                tNode c1 = new tNode(input);
                match(Token.DEC_ZERO);
                Node[] children = { c1 };
                return new nNode("DECNUM", children);
            case MINUS:
                Node c1_1 = parseNeg();
                Node[] children_1 = { c1_1 };
                return new nNode("DECNUM", children_1);

            case P_DIGIT:
                Node c1_2 = parsePos();
                Node[] children_2 = { c1_2 };
                return new nNode("DECNUM", children_2);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseDecNum");
                return null;
        }
    }

    private Node parseNeg() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case MINUS:
                tNode c1 = new tNode(input);
                match(Token.MINUS);
                Node c2 = parsePos();
                Node[] children = { c1, c2 };
                return new nNode("NEG", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseNeg");
                return null;
        }
    }

    private Node parsePos() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case P_DIGIT:
                Node c1 = parseInt();
                tNode c2 = new tNode(input);
                match(Token.DEC_POINT);
                Node c3 = parseD();
                Node c4 = parseD();
                Node[] children = { c1, c2, c3, c4 };
                return new nNode("POS", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parsePos");
                return null;
        }
    }

    private Node parseInt() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case P_DIGIT:
                tNode c1 = new tNode(input);
                match(Token.P_DIGIT);
                Node c2 = parseMore();
                Node[] children = { c1, c2 };
                return new nNode("INT", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseInt");
                return null;
        }
    }

    private Node parseNumVar() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case DEC_NUMVAR:
                tNode c1 = new tNode(input);
                match(Token.DEC_NUMVAR);
                Node c2 = parseDigits();
                Node[] children = { c1, c2 };
                return new nNode("NUMVAR", children);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseNumVar");
                return null;
        }
    }

    private Node parseOutput() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case VALUE:
                Node c1 = parseValue();
                Node[] children = { c1 };
                return new nNode("OUTPUT", children);
            case TEXT:
                Node c1_1 = parseText();
                Node[] children_1 = { c1_1 };
                return new nNode("OUTPUT", children_1);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseOutput");
                return null;
        }
    }

    private Node parseText() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case TEXT:
                tNode c1 = new tNode(input);
                match(Token.TEXT);
                Node c2 = parseStringV();
                Node[] children = { c1, c2 };
                return new nNode("TEXT", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseText");
                return null;
        }
    }

    private Node parseValue() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case VALUE:
                tNode c1 = new tNode(input);
                match(Token.VALUE);
                Node c2 = parseNumVar();
                Node[] children = { c1, c2 };
                return new nNode("VALUE", children);

            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseValue");
                return null;
        }
    }

    private Node parseInput() throws UnexpectedTokenException {
        switch (input.getToken()) {
            case GET:
                tNode c1 = new tNode(input);
                match(Token.GET);
                Node c2 = parseNumVar();
                Node[] children = { c1, c2 };
                return new nNode("INPUT", children);
            default:
                System.out.println("Error, with token " + count + " did not expect " + input + " at parseInput");
                return null;
        }
    }

}
