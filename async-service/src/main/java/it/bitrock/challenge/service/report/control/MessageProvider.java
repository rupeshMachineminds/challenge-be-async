package it.bitrock.challenge.service.report.control;

import it.bitrock.challenge.ReportWeight;
import it.bitrock.challenge.service.report.entity.Message;
import it.bitrock.challenge.service.report.entity.MessageRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MessageProvider {

    @Inject
    private MessageRepository messageRepository;

    public Message getLightMessage() {
        return messageRepository.fetchMessage(ReportWeight.LIGHT);
    }

    public Message getHeavyMessage() {
        return messageRepository.fetchMessage(ReportWeight.HEAVY);
    }

}
