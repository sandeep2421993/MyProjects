package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class OrderIdController implements HttpHandler {

	public static int orderId=0; 
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
        URI uri= exchange.getRequestURI();
		
		orderId++;
		String response=""+orderId;
		//System.out.println(orderId);
		exchange.sendResponseHeaders(200, response.length());
		OutputStream stream =exchange.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
	}

}
