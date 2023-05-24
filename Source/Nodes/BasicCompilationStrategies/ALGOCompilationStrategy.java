package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ALGOCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String doneCode = "";
        for (Node child : node.getChildren()) {
            BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
            switch (child.getDisplayName()) {
                case "ASSIGN":
                    basicCompilationStrategy = new ASSIGNCompilationStrategy();
                    break;
                case "VALUE":
                case "TEXT":
                    basicCompilationStrategy = new OUTPUTCompilationStrategy();
                    break;
                case "INPUT":
                    basicCompilationStrategy = new INPUTCompilationStrategy();
                    break;
                case "LOOP":
                    basicCompilationStrategy = new LOOPCompilationStrategy();
                    break;
                case "BRANCH":
                    basicCompilationStrategy = new BRANCHCompilationStrategy();
                    break;
                case "h":
                    basicCompilationStrategy = new HCompilationStrategy();
                    break;
                case "CALL":
                    basicCompilationStrategy = new CALLCompilationStrategy();
                    break;

                default:
                    break;
            }
            if (child instanceof tNode) {
                doneCode += "\n" + basicCompilationStrategy.handle((tNode) child);
            }
            if (child instanceof nNode) {
                doneCode += "\n" + basicCompilationStrategy.handle((nNode) child);
            }
        }
        return doneCode;
    }

}
