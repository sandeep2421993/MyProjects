package cs.cs414.a5.g.pizzaorderingsystemserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cs.cs414.a5.g.util.DataUtil;

public class LogsController implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
       
		URI uri = exchange.getRequestURI();
		String response = null;
		
		response=DataUtil.getLoggedin().toString();
		exchange.sendResponseHeaders(200, response.length());
		OutputStream stream =exchange.getResponseBody();
		stream.write(response.getBytes());
		stream.close();
    
		

		
	}

}
