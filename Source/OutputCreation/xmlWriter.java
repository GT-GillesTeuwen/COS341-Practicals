package OutputCreation;

import java.io.FileWriter;
import java.io.IOException;

public class xmlWriter {
    public static class implement {
        public static void writeXml(String name, String xml, String suffix) {
            name = name.replace(".txt", suffix);
            String fileType = ".xml";
            if (suffix.equals("_table")) {
                fileType = ".html";
            }
            try {
                FileWriter writer = new FileWriter(name + fileType, false);
                writer.write(xml);
                writer.close();
            } catch (IOException e) {
                try {
                    FileWriter writer2 = new FileWriter(name + fileType, true);
                    writer2.write(xml);
                    writer2.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}
