import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Production {
    private static final int limit = 10000;
    private String lhs;
    private ArrayList<String> rhs;
    private int ntUses;

    public Production(String lhs, String[] allRhs) {
        this.lhs = lhs;
        this.rhs = new ArrayList<>();
        ntUses = 0;

        for (String string : allRhs) {
            if (string.equals("&")) {
                rhs.add("");
                rhs.add("");
                rhs.add("");
            } else {
                rhs.add(string);
            }

        }
    }

    public String getRandomRhs(int length) {
        if (length < limit || ntUses > 10) {
            int r = (int) (Math.random() * (rhs.size()));
            ntUses++;
            return rhs.get(r);
        }
        return getFirstTerminal();

    }

    private String getFirstTerminal() {
        for (int i = 0; i < rhs.size(); i++) {
            String regex = "<\\w*>";
            Pattern p = Pattern.compile(regex);
            String rhString = rhs.get(i);
            Matcher matcher = p.matcher(rhs.get(i));
            if (!matcher.find()) {
                ntUses = 0;
                return rhs.get(i);
            }
        }
        return rhs.get(0);
    }

}
