package Exceptions;

import CONSTANTS.ANSI_COLOURS;

public class InvalidNumvarAssignmentException extends Exception {
    public InvalidNumvarAssignmentException(String toAssign,String assigning) {
        super(ANSI_COLOURS.ANSI_Red+"Cannot assign "+assigning+ " to NUMVAR "+toAssign+". "+assigning+" has no value"+ANSI_COLOURS.ANSI_Reset);
    }
}
