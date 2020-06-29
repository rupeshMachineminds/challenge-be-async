package it.bitrock.challenge.service.report.control;

import it.bitrock.challenge.ReportWeight;
import it.bitrock.challenge.service.report.entity.Message;
import it.bitrock.challenge.service.report.entity.MessageReport;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReportGenerator {

    @Inject
    private MessageProvider messageProvider;

    public MessageReport generate(ReportWeight weight) {
        Message message = getMessage(weight);
        return new MessageReport(message);
    }

    private Message getMessage(ReportWeight weight) {
        switch (weight) {
            case HEAVY:
                return messageProvider.getHeavyMessage();
            case LIGHT:
                return messageProvider.getLightMessage();
            default:
                throw new IllegalArgumentException("Unknown message weight requested " + weight);
        }
    }
}
