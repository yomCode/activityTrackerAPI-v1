package dev.decagon.Activity_Tracker.exceptions;

import lombok.Data;

@Data
public class NullUserException extends NullPointerException{

    private String debugMsg;

    public NullUserException(String s, String debugMsg) {
        super(s);
        this.debugMsg = debugMsg;
    }
}
