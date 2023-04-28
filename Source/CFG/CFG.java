package CFG;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CFG
 */
public class CFG {

    Map<String, Production> rules;

    public CFG() {
        rules = new HashMap<>();
        try {
            Scanner file = new Scanner(new FileReader("cfg.txt"));
            while (file.hasNext()) {
                String line = file.nextLine();
                String[] split = line.split("::=");
                String[] split2 = split[1].split("\\|");
                Production p = new Production(split[0], split2);
                rules.put(split[0], p);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String create() {
        String cur = "<PROGR>";
        boolean found;
        do {
            found = false;
            String regex = "<\\w*>";
            Pattern p = Pattern.compile(regex);

            Matcher matcher = p.matcher(cur);
            if (matcher.find()) {
                found = true;
                int start = matcher.start();
                int end = matcher.end();
                String rule = cur.substring(start, end);

                String replace = rules.get(rule).getRandomRhs(cur.length());
                cur = cur.replaceFirst(rule, replace);
            }
        } while (found);

        return cur;

    }
}