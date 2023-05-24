package Nodes.BasicCompilationStrategies;

import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class CALLCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "GOSUB ";
        try {
            int id = Node.s.getProc(node.getData(), node.getId()).getId();

            String name = Node.s.getNameFromID(id);
            code += name + "";
        } catch (ProcedureNotDeclaredException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }

}
