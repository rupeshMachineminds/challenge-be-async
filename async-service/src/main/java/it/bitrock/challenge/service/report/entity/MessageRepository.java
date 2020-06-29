package it.bitrock.challenge.service.report.entity;

import it.bitrock.challenge.ReportWeight;

public interface MessageRepository {

    Message fetchMessage(ReportWeight weight);

}
