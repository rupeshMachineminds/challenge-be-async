package it.bitrock.challenge.async.client.report.boundary;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

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

import it.bitrock.challenge.async.client.report.control.ConsumeReportClient;
import it.bitrock.challenge.async.client.report.entity.MessageReport;

@WebServlet("/consume")
public class ConsumeReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ConsumeReportClient consumeClient;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// Initialize for server-sent events
    	resp.setContentType("text/event-stream");
    	resp.setCharacterEncoding("UTF-8");

        MessageReport report = null;
        try {
        	report = getReport(Optional.ofNullable(req.getParameter("requesId")).orElse(""));
        	printReport(report, resp);
        } catch(ProcessingException pe) { }
    }

    private void printReport(MessageReport report, HttpServletResponse resp) throws IOException {
    	String reportAsJson = getReportAsJsonString(report);
    	if(Objects.nonNull(reportAsJson)) {
    		try(PrintWriter printWriter = resp.getWriter()) {
                printWriter.write("data: " + reportAsJson + "\n\n");
                printWriter.flush();
            }
    	}
    }

    private String getReportAsJsonString(MessageReport report) {
    	if(Objects.nonNull(report)) {
    		final JsonbConfig jsonbConfig = new JsonbConfig().withFormatting(false).withNullValues(true);
            try (final Jsonb jsonb = JsonbBuilder.create(jsonbConfig)) {
                return jsonb.toJson(report);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    	} else {
    		return null;
    	}
        
    }

    MessageReport getReport(String requestId) {
    	return this.consumeClient.fetchReport(requestId);
    }
    
}
