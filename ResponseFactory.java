package proxy;

public class ResponseFactory {
	/**
	 * Retourner la réponse adéquate
	 **/
	public static Response createResponse(Request request) {


		return new DummyResponse(request);
	}
}
