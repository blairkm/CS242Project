package edu.ucr.cs242.project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.ucr.cs242.project.json.UnsplashListResponse;
import edu.ucr.cs242.project.json.UnsplashResponse;
import edu.ucr.cs242.project.util.StringUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
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
public class UnsplashApiUser { 

    protected static void perform(String _username) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(Config.UNSPLASH_API_USER_ENDPOINT + "/" + _username + "/photos");
            httpGet.setHeader("Accept-Version", "v1");
            httpGet.setHeader("Authorization", "Client-ID " + Config.getUnsplashAccessKey());

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
                    Type collectionType = new TypeToken<List<UnsplashResponse>>() {}.getType();
                    List<UnsplashListResponse> responses = (List<UnsplashListResponse>) new Gson().fromJson(result, collectionType);

                    // perist payloads
                    UnsplashResponse response = null;
                    Iterator it = responses.iterator();
                    while (it.hasNext()) {
                        response = (UnsplashResponse) it.next();

                        // id, urls.regular, user.username  
                        FileUtil.persistMetadata(response.getId() + " " + response.getUrls().getFull() + " " + response.getUser().getUsername() + System.getProperty("line.separator"));
                        FileUtil.persistImage(response.getId(), response.getUrls().getRegular()); // getFull()

                    }

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

            } while (false || Config.DELAY_CODES.contains(statusCode));

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }

    }
        
}