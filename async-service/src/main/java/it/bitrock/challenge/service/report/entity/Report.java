package it.bitrock.challenge.service.report.entity;

import java.io.Serializable;

public abstract class Report<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -5018044089254158445L;

    public Report(String id, T value) {
        this.id = id;
        this.value = value;
    }

    private final String id;
    private final T value;

    public String getId() {
        return id;
    }

    public T getValue() {
        return value;
    }
}
