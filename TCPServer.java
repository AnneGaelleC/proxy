package proxy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



public class TCPServer {

	private final static Logger logger = Logger.getLogger(TCPServer.class
			.toString());

	private String host;
	private int port;


	public TCPServer(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	/**
	 * Initie le server en écoute sur le port X
	 */
	public void serve() {
		ServerSocket serverSocket = null;

		logger.info("Initialisation du server sur le port: " + this.host
				+ ":" + this.port);		
		
		try {
			// Création de la connexion
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName(host));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erreur d'initialisation!", e);
			return;
		}
		logger.info("Conexion avec le server ouverte au port: " + this.host
				+ ":" + this.port);

		// en attente du client
		while (true) {
			logger.info("En attente de connexion...");
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			//ServerSocket socket;
			try {
				//socket = new ServerSocket();
		        //Thread t = new Thread(new Accepter_Connexion(socket));
		        //t.start();
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();

				// Parse de la requête
				String requestString = convertStreamToString(input);
				logger.info("Connexion reçue : contenue:\n" + requestString);
				Request request = new Request();
				request.parse(requestString);

				// récupere la réponse
				Response response = ResponseFactory.createResponse(request);
				String responseString = response.respond();
				logger.info("Réponse envoyé, contenue :\n" + responseString);
				output.write(responseString.getBytes());

				// ferme la connexion
				socket.close();

			} catch (Exception e) {
				logger.log(Level.SEVERE, "Erreur d'execution du server!", e);
				continue;
			}
		}
	}

	private String convertStreamToString(InputStream is) {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[2048];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is));
				int i = reader.read(buffer);
				writer.write(buffer, 0, i);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Problème de conversion", e);
				return "";
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		TCPServer server = new TCPServer("localhost", 8091);
		server.serve();
	}

}
