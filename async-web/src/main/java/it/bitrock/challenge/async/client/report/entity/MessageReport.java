package it.bitrock.challenge.async.client.report.entity;

import java.io.Serializable;
import java.util.Objects;

public class MessageReport implements Serializable {

    private static final long serialVersionUID = 2257348771300077442L;

    private String id;
    private Message value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message getValue() {
        return value;
    }

    public void setValue(Message value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageReport that = (MessageReport) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "MessageReport{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
