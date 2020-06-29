package it.bitrock.challenge.async.client.report.boundary;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ProcessingException;

import it.bitrock.challenge.async.client.report.control.ReportClient;
import it.bitrock.challenge.async.client.report.entity.MessageReport;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    private static final long serialVersionUID = 9088197921672775501L;
    
    private enum ReportWeight {HEAVY, LIGHT}

    @Inject
    private ReportClient reportClient;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	ReportWeight weight = getWeightParameter(req);
        MessageReport report = null;
        String requestId = UUID.randomUUID().toString();
        try {
        	report = fetchReport(weight, requestId);
        	
        } catch(ProcessingException pe) {
        	if (pe.getCause() instanceof SocketTimeoutException) {
        		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        		report = new MessageReport();
        		report.setId(requestId);
    		}
        }
        printReport(report, resp);
    }

    private void printReport(MessageReport report, HttpServletResponse resp) throws IOException {
        if(Objects.isNull(report)) {
        	resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    	String reportAsJson = getReportAsJsonString(report);
        try(PrintWriter printWriter = resp.getWriter()) {
            printWriter.print(reportAsJson);
            printWriter.flush();
        }
    	
    }

    private String getReportAsJsonString(MessageReport report) {
        final JsonbConfig jsonbConfig = new JsonbConfig().withFormatting(true).withNullValues(true);

        try (final Jsonb jsonb =JsonbBuilder.create(jsonbConfig)) {
            return jsonb.toJson(report);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private MessageReport fetchReport(ReportWeight weight, String requestId) {
    	return  getReportSupplierForWeight(weight, requestId);
    }

    MessageReport getReportSupplierForWeight(ReportWeight weight, String requestId) {
        switch (weight) {
            case HEAVY:
            	return this.reportClient.fetchReportHeavy(requestId);
            case LIGHT:
                return this.reportClient.fetchReportLight(requestId);
            default:
                throw new IllegalArgumentException("weight " + weight + " is not valid");
        }
    }

    private ReportWeight getWeightParameter(HttpServletRequest req) {
        final String weightValue = Optional.ofNullable(req.getParameter("weight")).orElse("LIGHT");
        return ReportWeight.valueOf(weightValue);
    }
    
}
