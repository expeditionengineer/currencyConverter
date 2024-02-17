package de.expeditionengineer.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class CurrecnyConverter 
{
    private String exchangeAPIKey;
    public CurrecnyConverter() {

        Properties prop = new Propoerties();
        FileInputStream fileINputStream = null;

        try {
            fileInputStream = new FileInputStream(".env");
            prop.load(fileInputStream); 
            exchangeAPIKey = prop.getProperty("API-Key");
        } catch (IOException) {
            System.println("API-Key couldnt be loaded from .env-file. Is a .env-file present in the current directory?");
        }
    }
    
    public loadExchangeData() {
        String apiURL = "http://127.0.0.1:8000";
        System.println("Doing a fetch request to the URL: " + apiURL);

        URL urlObj = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
    }

}
