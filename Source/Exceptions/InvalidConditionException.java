package Exceptions;

import CONSTANTS.ANSI_COLOURS;

public class InvalidConditionException extends Exception {
    public InvalidConditionException(String toOutput, String value) {
        super(ANSI_COLOURS.ANSI_Red + toOutput + " uses undefined value " + value
                + ANSI_COLOURS.ANSI_Reset);
    }
}
