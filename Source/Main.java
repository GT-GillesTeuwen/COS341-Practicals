import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

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

class Main {
    public static void main(String[] args) {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
        j.setDialogTitle("Choose a text file to parse");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        j.setFileFilter(filter);
        // Open the save dialog
        j.showOpenDialog(null);
        String textFileName = j.getSelectedFile().getAbsolutePath();
        Lexer lexer;
        ArrayList<Symbol> tokens = new ArrayList<>();
        try {
            lexer = new Lexer(textFileName);

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
            JOptionPane.showMessageDialog(null, e.toString().replace("java.lang.Exception:", ""));
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString().replace("java.lang.Exception:", ""));
            System.out.println(e.toString().replace("java.lang.Exception:", ""));

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
            JOptionPane.showMessageDialog(null,
                    "Parsing complete, find the xml file at " + textFileName.replace(".txt", "_Parsed") + ".xml");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString().replace("java.lang.Exception:", ""));
            System.out.println(e.toString().replace("java.lang.Exception:", ""));

        }
    }
}