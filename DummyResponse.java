package proxy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
Classe utilisé pour trier les requêtes et faire les testes
 */
public class DummyResponse implements Response {

	private Request request;

	protected static final DateFormat HTTP_DATE_FORMAT = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss z");


	public DummyResponse(Request request) {
		this.request = request;
	}

	@Override
	public String respond() {
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 200 OK").append("\r\n");
		
		// entête
		sb.append("Date: ").append(HTTP_DATE_FORMAT.format(new Date()))
				.append("\r\n");
		sb.append("Server: Test Server - http://www.utfpr.edu.br/")
				.append("\r\n");
		sb.append("Connection: Close").append("\r\n");
		sb.append("Content-Type: text/html; charset=UTF-8").append("\r\n");
		sb.append("\r\n");
		
		// Corps de la requête
		sb.append("<html><head><title>Dummy Response</title></head><body><h1>HttpServer Response</h1>");
		sb.append("Method: ").append(request.getMethod()).append("<br/>");
		sb.append("URI: ").append(request.getUri()).append("<br/>");
		sb.append("Protocol: ").append(request.getProtocol()).append("<br/>");
		sb.append("</body></html>");
		return sb.toString();

	}

}
