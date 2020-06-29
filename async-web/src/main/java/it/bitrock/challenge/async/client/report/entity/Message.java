package it.bitrock.challenge.async.client.report.entity;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {

    private static final long serialVersionUID = 1207090780992647418L;

    private String messageValue;

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageValue, message.messageValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageValue);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageValue='" + messageValue + '\'' +
                '}';
    }
}
