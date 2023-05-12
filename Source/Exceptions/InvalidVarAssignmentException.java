package Exceptions;

import CONSTANTS.ANSI_COLOURS;

public class InvalidVarAssignmentException extends Exception {
    public InvalidVarAssignmentException(String toAssign, String assigning) {
        super(ANSI_COLOURS.ANSI_Red + "Cannot assign " + assigning + " to variable " + toAssign + ". " + assigning
                + " has no value" + ANSI_COLOURS.ANSI_Reset);
    }
}
