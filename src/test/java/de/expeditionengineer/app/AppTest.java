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
        String reportIfDataFetchWasSuccessful = converterObj.loadExchangeData(true);
        assertEquals(1, handler.getCount());
        assertTrue(reportIfDataFetchWasSuccessful.equals("success"));
    }
    static class MyHandler implements HttpHandler {
        private int count = 0; // counter variable
    
        @Override
        public void handle(HttpExchange t) throws IOException {
            count++; // increment the counter each time handle is called
            System.out.println("Handle method called " + count + " times");
    
            String response = "{\n" + //
                                "\t\"result\": \"success\",\n" + //
                                "\t\"documentation\": \"https://www.exchangerate-api.com/docs\",\n" + //
                                "\t\"terms_of_use\": \"https://www.exchangerate-api.com/terms\",\n" + //
                                "\t\"time_last_update_unix\": 1585267200,\n" + //
                                "\t\"time_last_update_utc\": \"Fri, 27 Mar 2020 00:00:00 +0000\",\n" + //
                                "\t\"time_next_update_unix\": 1585353700,\n" + //
                                "\t\"time_next_update_utc\": \"Sat, 28 Mar 2020 00:00:00 +0000\",\n" + //
                                "\t\"base_code\": \"USD\",\n" + //
                                "\t\"conversion_rates\": {\n" + //
                                "\t\t\"USD\": 1,\n" + //
                                "\t\t\"AUD\": 1.4817,\n" + //
                                "\t\t\"BGN\": 1.7741,\n" + //
                                "\t\t\"CAD\": 1.3168,\n" + //
                                "\t\t\"CHF\": 0.9774,\n" + //
                                "\t\t\"CNY\": 6.9454,\n" + //
                                "\t\t\"EGP\": 15.7361,\n" + //
                                "\t\t\"EUR\": 0.9013,\n" + //
                                "\t\t\"GBP\": 0.7679\n" + //
                                "\t}\n" + //
                                "}";
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

