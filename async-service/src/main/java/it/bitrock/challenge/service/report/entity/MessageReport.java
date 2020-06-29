package it.bitrock.challenge.service.report.entity;

import java.util.UUID;

public class MessageReport extends Report<Message> {

    private static final long serialVersionUID = -4848913967201342078L;

    public MessageReport(Message value) {
        super(UUID.randomUUID().toString(), value);
    }
}
