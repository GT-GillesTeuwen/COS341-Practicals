import java.io.FileWriter;
import java.io.IOException;

public class xmlWriter {
    static class implement {
        public static void writeXml(String name, String xml) {
            name=name.replace(".txt", "_Parsed");
            try {
                FileWriter writer = new FileWriter(name + ".xml", false);
                writer.write(xml);
                writer.close();
            } catch (IOException e) {
                try {
                    FileWriter writer2 = new FileWriter(name + ".xml", true);
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
