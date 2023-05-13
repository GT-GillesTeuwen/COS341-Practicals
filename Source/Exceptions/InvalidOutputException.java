package Exceptions;

import CONSTANTS.ANSI_COLOURS;

public class InvalidOutputException extends Exception {
    public InvalidOutputException(String toOutput) {
        super(ANSI_COLOURS.ANSI_Red + "Cannot output " + toOutput + " since " + toOutput + " has no assigned value."
                + ANSI_COLOURS.ANSI_Reset);
    }
}
