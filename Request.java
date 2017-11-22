package proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Realise le parsage de la requête
 */
public class Request {

	private String method;
	private String uri;
	private String protocol;

	public Request() {
	}


	public void parse(String input) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(input));
		String line = null;
		int lineNumber = 0;
		while ((line = br.readLine()) != null) {
			System.out.println(lineNumber + " " + line);
			if (lineNumber == 0) {
				String[] values = line.split(" ");
				if (values.length == 3) { 
					this.method = values[0];
					this.uri = values[1];
					this.protocol = values[2];
				} // cas d'erreur
			} else {
				
			}
			lineNumber++;

		}
	}


	public String getMethod() {
		return method;
	}

	/**
	 * Recuperer l'URI

	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Recuperer le protocol
	 */
	public String getProtocol() {
		return protocol;
	}

}
