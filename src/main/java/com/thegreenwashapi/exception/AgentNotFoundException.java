package com.thegreenwashapi.exception;

public class AgentNotFoundException extends RuntimeException{
    public AgentNotFoundException(String message) {
        super(message);
    }
}
