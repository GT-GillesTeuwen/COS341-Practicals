import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

class Main {
    public static void main(String[] args) {
        String textFileName = "test9";
        Lexer lexer;
        ArrayList<Symbol> tokens = new ArrayList<>();
        try {
            lexer = new Lexer(textFileName + ".txt");

            while (lexer.hasMore()) {
                lexer.moveAhead();
            }

            if (lexer.isSuccessful()) {
                System.out.println("Ok! :D");
                tokens = lexer.getAllTokens();
                tokens.add(new Symbol("$", Token.DOLLAR));
            } else {
                System.out.println(lexer.errorMessage());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int q = 0;
        for (Symbol token : tokens) {
            System.out.println(token + "\t" + q++);
        }

        Parser p;
        Node n = null;
        try {
            p = new Parser(tokens);
            n = p.parseProgrP();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }

        // System.out.println("DONE");

        // Tree t=new Tree(n);
        // System.out.println(t.maxDepth());

        // JFrame f= new JFrame("Panel Example");
        // f.setSize(1500, 1080);
        // TreeVisualiser v=new TreeVisualiser(n);
        // f.add(v);
        // f.setVisible(true);
        try {
            Tree tree = new Tree(n);
            xmlWriter.implement.writeXml(textFileName, tree.toSpecXML());
            JFrame frame = new JFrame("SpringLayout");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JScrollPane scroll = new JScrollPane();
            Container contentPane = frame.getContentPane();

            SpringLayout layout = new SpringLayout();
            JPanel mainPanel;
            TreeVisualiser v = new TreeVisualiser(tree);
            mainPanel = v;

            mainPanel.setLayout(layout);
            contentPane.setLayout(new BorderLayout());

            mainPanel.setPreferredSize(new Dimension(1600, v.getDepth()));
            scroll.setPreferredSize(new Dimension(1600, 500));
            scroll.setViewportView(mainPanel);
            contentPane.add(scroll);
            // mainWindow.add(contentPane);
            frame.setSize(500, 600);
            frame.setVisible(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
    }
}