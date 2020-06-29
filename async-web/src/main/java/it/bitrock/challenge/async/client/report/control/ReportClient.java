package it.bitrock.challenge.async.client.report.control;

//import org.jboss.resteasy.client.jaxrs.ResteasyClient;
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
//import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import it.bitrock.challenge.async.client.report.entity.MessageReport;

@ApplicationScoped
public class ReportClient {

    private WebTarget target;
    
    @PostConstruct
    private void init() {
        Client client = ClientBuilder.newBuilder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        this.target = client.target("http://localhost:8787/challenge-async-service/api")
                .path("reports");
    }

    public MessageReport fetchReportLight(String requestId) {
        return fetchReport("LIGHT", requestId);
    }

    public MessageReport fetchReportHeavy(String requestId) {
        return fetchReport("HEAVY", requestId);
    }
    
    private MessageReport fetchReport(String weight, String requestId) {
    	MessageReport mr = null;
    	try {
    		mr = this.target.queryParam("weight", weight)
    				.queryParam("requestId", requestId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(MessageReport.class);
    	} catch(ProcessingException pe) {
    		throw pe;
    	}
    	return mr;
    }
}
