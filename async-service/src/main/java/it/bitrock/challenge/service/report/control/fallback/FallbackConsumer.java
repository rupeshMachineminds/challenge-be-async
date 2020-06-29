package it.bitrock.challenge.service.report.control.fallback;

public interface FallbackConsumer<T extends Payload<?>> {

    void consume(T payload);

}
