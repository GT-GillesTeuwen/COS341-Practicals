import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Exceptions.AmbiguousDeclarationException;
import Exceptions.EmptyTreeException;
import Exceptions.ProcedureNotDeclaredException;
import Exceptions.TreeCreationException;
import Exceptions.UnexpectedTokenException;
import Lexing.Lexer;
import Lexing.Symbol;
import Lexing.Token;
import Nodes.Node;
import OutputCreation.xmlWriter;
import Parsing.Parser;
import Visualisation.GUI;
import Visualisation.Tree;
import Visualisation.TreeVisualiser;

class Main {
    public static void main(String[] args) {
        // JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
        // j.setDialogTitle("Choose a text file to parse");
        // FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES",
        // "txt", "text");
        // j.setFileFilter(filter);
        // Open the save dialog
        // j.showOpenDialog(null);
        String textFileName = "Tests/test9.txt";// j.getSelectedFile().getAbsolutePath();
        Lexer lexer;
        ArrayList<Symbol> tokens = new ArrayList<>();
        try {
            lexer = new Lexer(textFileName);

            while (lexer.hasMore()) {
                lexer.moveAhead();
            }

            if (lexer.isSuccessful()) {
                tokens = lexer.getAllTokens();
                tokens.add(new Symbol("$", Token.DOLLAR));
            } else {
                System.out.println(lexer.errorMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString().replace("java.lang.Exception:", ""), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString().replace("java.lang.Exception:", ""));

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
        } catch (UnexpectedTokenException e) {
            JOptionPane.showMessageDialog(null, e.toString().replace("java.lang.Exception:", ""), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString().replace("java.lang.Exception:", ""));

        }
        Tree tree = null;
        try {
            tree = new Tree(n);
            xmlWriter.implement.writeXml(textFileName, tree.toTutorXML(), "tree");
            xmlWriter.implement.writeXml(textFileName, tree.getScopeTable().toHTML(), "table");
            GUI g = new GUI(tree);
            final ImageIcon icon = new ImageIcon("Images\\output-onlinepngtools.png");
            JOptionPane.showMessageDialog(null,
                    "Parsing complete, find the xml file at " + textFileName.replace(".txt", "_Parsed") + ".xml",
                    "Completed",
                    JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (AmbiguousDeclarationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        } catch (TreeCreationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        } catch (EmptyTreeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        } catch (ProcedureNotDeclaredException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Dialogue",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(e.toString());
        }

        // JFrame frame = new JFrame("SpringLayout");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JScrollPane scroll = new JScrollPane();
        // Container contentPane = frame.getContentPane();

        // SpringLayout layout = new SpringLayout();
        // JPanel mainPanel;
        // TreeVisualiser v = new TreeVisualiser(tree);
        // mainPanel = v;

        // mainPanel.setLayout(layout);
        // contentPane.setLayout(new BorderLayout());

        // mainPanel.setPreferredSize(new Dimension(1600, v.getDepth()));
        // scroll.setPreferredSize(new Dimension(1600, 500));
        // scroll.setViewportView(mainPanel);
        // contentPane.add(scroll);
        // // mainWindow.add(contentPane);
        // frame.setSize(500, 600);
        // frame.setVisible(true);

    }
}