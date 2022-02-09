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
import edu.ucr.cs242.project.util.StringUtil;

/**
 *
 * @author rehmke
 */
public class UnsplashApiRandom {

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
                    FileUtil.persistResponse(response.getId(), result);
                    FileUtil.persistImage(response.getId(), response.getUrls().getRegular()); // getFull()

                    EntityUtils.consume(entity1);
                }

                if (Config.DELAY_CODES.contains(statusCode)) {
                    if (Config.DEBUG) {
                        StringUtil.announce("! Delaying due to status code " + statusCode + "...");
                    }
                    try {
                        Thread.sleep(Config.REQUEST_DELAY_LIMIT);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                } else if (true) {
                    try {
                        Thread.sleep(Config.REQUEST_DELAY_POLITE);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                }

            } while (true || Config.DELAY_CODES.contains(statusCode));

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }

    }

}