package me.smartstore.exception;

import me.smartstore.util.Message;
public class InputRangeException extends RuntimeException{
    public InputRangeException() {
        super(Message.ERR_MSG_INVALID_INPUT_RANGE);
    }

    public InputRangeException(String message) {
        super(message);
    }
}
