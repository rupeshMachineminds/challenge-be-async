package it.bitrock.challenge.service.report.entity;

import it.bitrock.challenge.ReportWeight;

import javax.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class MessageRepositoryImpl implements MessageRepository {

    private static final long DELAY = 1000L * 10;

    @Override
    public Message fetchMessage(ReportWeight weight) {
        if (ReportWeight.HEAVY == weight) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return new Message(buildMessage(weight));
    }

    private String buildMessage(ReportWeight weight) {
        return MessageFormat.format("Message of type {0} generated at {1}", weight, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
