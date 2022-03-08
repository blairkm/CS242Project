package edu.ucr.cs242.project.util;

import com.google.gson.Gson;
import edu.ucr.cs242.project.Config;
import edu.ucr.cs242.project.EncDec;
import edu.ucr.cs242.project.json.GeocodeResponse;
import edu.ucr.cs242.project.json.UnsplashResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 *
 * @author rehmke
 */
public class GeocodingUtil {

    // i.e. api.openweathermap.org/geo/1.0/direct?q=CITY_STATE_COUNTRY&appid=API_KEY
    
    public static String request(String _location) {
        String retVal = "";

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet("https://api.openweathermap.org/geo/1.0/direct?q=" + _location + "&appid=" + EncDec.dec("SrPyU7Kcy7LBWuOn9gR/urYu2LYXJxXj5S+/VGe3n5nX2NG9mMjrnDI3hj6umF9fdGc5xfBRpmZV7kTIxOgT5Yjz6OSTl0ZRm3PuphJMXw4="));

            int statusCode = -1;
            String result = "";

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {

                statusCode = response1.getCode();
                
                if (false || Config.DEBUG) {
                    System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                }

                HttpEntity entity1 = response1.getEntity();
                result = IOUtils.toString(entity1.getContent(), StandardCharsets.UTF_8);
                if (false || Config.DEBUG) {
                    System.out.println(result);
                }

                /*
                Gson gson = new Gson();
                GeocodeResponse response = gson.fromJson(result, GeocodeResponse.class);
                
                System.out.println("name: " + response.getName());
                System.out.println("lat: " + response.getLat());
                System.out.println("long: " + response.getLon());
                */
                
                String originalResult = result;
                String latitude = "";
                String longitude = "";
                int dotPosLatStart = -1;
                int dotPosLonStart = -1;
                int dotPosLatEnd = -1;
                int dotPosLonEnd = -1;
                
                dotPosLatStart = originalResult.indexOf("\"lat\":");
                result = result.substring(dotPosLatStart+6, result.length());                
                dotPosLatStart = result.indexOf(",");
                latitude = result.substring(0, dotPosLatStart);
                
                dotPosLonStart = originalResult.indexOf("\"lon\":");
                originalResult = originalResult.substring(dotPosLonStart+6, originalResult.length());
                dotPosLonStart = originalResult.indexOf(",");
                longitude = originalResult.substring(0, dotPosLonStart);
                
                retVal = latitude + "|" + longitude;

                EntityUtils.consume(entity1);
            }

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

        return retVal;
    }
    
    
    // example: 25.22579785|55.18304675047368
    
    public static String getLatitude(String _val) {
        String retVal = "";        
        try {
            int sepIndexOf = _val.indexOf("|");
            retVal = _val.substring(0, sepIndexOf);        
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return retVal;        
    }
    
    public static String getLongitude(String _val) {
        String retVal = "";        
        try {
            int sepIndexOf = _val.indexOf("|");
            retVal = _val.substring(sepIndexOf+1, _val.length());               
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }        
        return retVal;
    }

}