package it.bitrock.challenge.service.report.boundary;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.bitrock.challenge.ReportWeight;
import it.bitrock.challenge.service.report.control.ReportGenerator;
import it.bitrock.challenge.service.report.control.fallback.mail.EmailPayload;
import it.bitrock.challenge.service.report.control.fallback.mail.EmailSenderImplMessageReport;
import it.bitrock.challenge.service.report.entity.MessageReport;

@Path("reports")
public class ReportsResource {
	
	private static final ConcurrentMap<String, MessageReport> contexts = new ConcurrentHashMap<>();

    @Inject
    private ReportGenerator reportGenerator;
    
    @Inject
    private EmailSenderImplMessageReport emailSender;

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getReport(@QueryParam ("weight") @DefaultValue("LIGHT") ReportWeight weight, @QueryParam ("requestId") String reqId) {
        final MessageReport report = reportGenerator.generate(weight);
        if(weight == ReportWeight.HEAVY) {
        	contexts.put(reqId, report);
        	EmailPayload<MessageReport> ep = new EmailPayload<MessageReport>(report, new String[] {});
        	emailSender.consume(ep);
        }
        
        return Response.ok(report).build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/consume")
    public Response consumeReport(@QueryParam ("requestId") String reqId) {
    	if(contexts.containsKey(reqId) && Objects.nonNull(contexts.get(reqId))) {
    		MessageReport mr = contexts.get(reqId);
    		contexts.remove(reqId);
    		return Response.ok(mr).build();
    	}
        return Response.ok("").build();
    }

}
