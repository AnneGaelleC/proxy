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
	 * Inicia o servidor e fica escutando no endereço e porta especificada no
	 * construtor
	 */
	public void serve() {
		ServerSocket serverSocket = null;

		logger.info("Iniciando servidor no endereço: " + this.host
				+ ":" + this.port);		
		
		try {
			// Cria a conexão servidora
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName(host));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erro ao iniciar servidor!", e);
			return;
		}
		logger.info("Conexão com o servidor aberta no endereço: " + this.host
				+ ":" + this.port);

		// Fica esperando pela conexão cliente
		while (true) {
			logger.info("Aguardando conexões...");
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

				// Realiza o parse da requisição recebida
				String requestString = convertStreamToString(input);
				logger.info("Conexão recebida. Conteúdo:\n" + requestString);
				Request request = new Request();
				request.parse(requestString);

				// recupera a resposta de acordo com a requisicao
				Response response = ResponseFactory.createResponse(request);
				String responseString = response.respond();
				logger.info("Resposta enviada. Conteúdo:\n" + responseString);
				output.write(responseString.getBytes());

				// Fecha a conexão
				socket.close();

			} catch (Exception e) {
				logger.log(Level.SEVERE, "Erro ao executar servidor!", e);
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
				logger.log(Level.SEVERE, "Erro ao converter stream para string", e);
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
