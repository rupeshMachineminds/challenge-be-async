package it.bitrock.challenge.service.report.control.fallback.mail;

import it.bitrock.challenge.service.report.control.fallback.Payload;

import java.io.Serializable;
import java.util.Arrays;

public class EmailPayload<T extends Serializable> implements Payload<T> {

    public EmailPayload(T payload, String[] receivers) {
        this.payload = payload;
        this.receivers = receivers;
    }

    private final T payload;
    private final String[] receivers;

    @Override
    public T getPayload() {
        return payload;
    }

    public String[] getReceivers() {
        return Arrays.copyOf(receivers, receivers.length);
    }
}
