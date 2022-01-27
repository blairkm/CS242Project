package edu.ucr.cs242.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.google.gson.Gson;
import edu.ucr.cs242.project.json.UnsplashResponse;

/**
 *
 * @author rehmke
 */
public class UnsplashClient {

    protected static void perform() {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(Config.UNSPLASH_API_ENDPOINT);
            httpGet.setHeader("Accept-Version", "v1");
            httpGet.setHeader("Authorization", "Client-ID " + Config.UNSPLASH_ACCESS_KEY);

            int statusCode = -1;
            String result = "";
            do {

                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {

                    statusCode = response1.getCode();
                    if (Config.DEBUG) {
                        System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                    }

                    HttpEntity entity1 = response1.getEntity();
                    result = IOUtils.toString(entity1.getContent(), StandardCharsets.UTF_8);
                    if (Config.DEBUG) {
                        System.out.println(result);
                    }

                    // selectively parse, different thread?
                    Gson gson = new Gson();
                    UnsplashResponse response = gson.fromJson(result, UnsplashResponse.class);
                    
                    // perist payloads
                    FileUtil.persistMetadata(response.getId(), result);
                    FileUtil.persistImage(response.getId(), response.getUrls().getFull()); // or getRegular?
                    
                    /*
                        System.out.println("id: " + response.getId());
                        System.out.println("width: " + response.getWidth());
                        System.out.println("height: " + response.getHeight());
                        System.out.println("color: " + response.getColor());
                        System.out.println("description: " + response.getDescription());
                        System.out.println("url.regular: " + response.getUrls().getRegular());
                     */
                    
                    EntityUtils.consume(entity1);
                }

                if (Config.DELAY_CODES.contains(statusCode)) {
                    if (Config.DEBUG) {
                        announce("! Delaying due to status code " + statusCode + "...");
                    }
                    try {
                        Thread.sleep(Config.REQUEST_DELAY);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                } else if (Config.CONTINUOUS) {
                    try {
                        Thread.sleep(Config.REQUEST_DELAY);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                }

            } while (Config.CONTINUOUS || Config.DELAY_CODES.contains(statusCode));

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }

    }

    private static void announce(String _val) {
        System.out.println(_val);
    }

}