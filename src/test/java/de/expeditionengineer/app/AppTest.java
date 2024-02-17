package de.expeditionengineer.app;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals; // Add this line

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public int numberOfRequests = 0;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void apiIsCalled()
    {
        MyHandler handler = new MyHandler();
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            
            server.createContext("/", handler);
            server.setExecutor(null); // creates a default executor
            server.start();
            } catch (Exception e) {
            System.out.println("Coudlnt start the HTTP-Server. Maybe port 8000 is already in use?");
        }
        CurrencyConverter converterObj = new CurrencyConverter();
        converterObj.loadExchangeData();
        assertEquals(1, handler.getCount());
    }
    static class MyHandler implements HttpHandler {
        private int count = 0; // counter variable
    
        @Override
        public void handle(HttpExchange t) throws IOException {
            count++; // increment the counter each time handle is called
            System.out.println("Handle method called " + count + " times");
    
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        public int getCount() {
            return count;
        }
    }
}

