package it.bitrock.challenge.service.report.control.fallback;

import it.bitrock.challenge.service.report.entity.MessageReport;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DummyMessageReportConsumerImpl implements FallbackConsumer<Payload<MessageReport>> {

	@Override
    public void consume(Payload<MessageReport> reportPayload) {
        System.out.println("received message report: " + reportPayload.getPayload());
    }
}
