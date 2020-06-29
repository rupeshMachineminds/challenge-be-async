package it.bitrock.challenge.service.report.entity;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 2757490859980219993L;

    public Message(String messageValue) {
        this.messageValue = messageValue;
    }

    private final String messageValue;

    public String getMessageValue() {
        return messageValue;
    }
}
