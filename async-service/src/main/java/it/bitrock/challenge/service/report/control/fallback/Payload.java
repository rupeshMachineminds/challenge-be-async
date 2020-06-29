package it.bitrock.challenge.service.report.control.fallback;

import java.io.Serializable;

public interface Payload<T extends Serializable> extends Serializable {

    T getPayload();

}
