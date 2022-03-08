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
import com.google.gson.reflect.TypeToken;
import edu.ucr.cs242.project.json.UnsplashListResponse;
import edu.ucr.cs242.project.json.UnsplashResponse;
import edu.ucr.cs242.project.util.DateUtil;
import edu.ucr.cs242.project.util.RndUtil;
import edu.ucr.cs242.project.util.StringUtil;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.hc.core5.http.ProtocolException;

/**
 *
 * @author rehmke
 */
public class UnsplashApiCrawler {
    
    private static int REQUEST_COUNT = 0;
    
   protected static void perform() {        
        do {            
            getByContext("latest");        
            getByContext("popular");        
            getByContext("oldest");                        
        } while (Config.CONTINUOUS);                
    }

    protected static void getByContext(String _context) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            
            // download list of photos by context...
            
            HttpGet httpGet = new HttpGet(Config.UNSPLASH_API_LATEST_ENDPOINT + "?" + "page=" + /*"3"*/ RndUtil.getRandomNumber() + "&per_page=" + Config.MAX_PHOTO_RESPONSE_COUNT + "&" + "order_by=" + _context);
            httpGet.setHeader("Accept-Version", "v1");
            httpGet.setHeader("Authorization", "Client-ID " + Config.getUnsplashAccessKey());
            
            //Set usernames = new HashSet();

            int statusCode = -1;
            String result = "";
            do {                                                
                
                if (REQUEST_COUNT < Config.PERIODIC_REQUEST_LIMIT) {

                    try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                        
                        //++REQUEST_COUNT;

                        statusCode = response1.getCode();
                        if (Config.DEBUG) {
                            System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                            try {
                                System.out.println("- Reported Limit: " + response1.getHeader("X-Ratelimit-Limit").getValue());
                                System.out.println("- Reported Remaining: " + response1.getHeader("X-Ratelimit-Remaining").getValue());
                                REQUEST_COUNT = Integer.parseInt(""+response1.getHeader("X-Ratelimit-Remaining").getValue());
                            } catch (ProtocolException pex) {
                                pex.printStackTrace(System.err);
                            }
                        }

                        if (statusCode == 200) {
                            HttpEntity entity1 = response1.getEntity();
                            result = IOUtils.toString(entity1.getContent(), StandardCharsets.UTF_8);
                            if (Config.DEBUG) {
                                System.out.println(result);
                            }

                            // selectively parse, different thread?
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<List<UnsplashResponse>>() {}.getType();
                            List<UnsplashListResponse> responses = (List<UnsplashListResponse>) new Gson().fromJson(result, collectionType);

                            FileUtil.persistResponse(DateUtil.getTimestamp() + "_" + _context, result);

                            // perist payloads
                            UnsplashResponse response = null;
                            Iterator it = responses.iterator();
                            while (it.hasNext() && REQUEST_COUNT < Config.PERIODIC_REQUEST_LIMIT) {
                                response = (UnsplashResponse) it.next();

                                if (!FileUtil.isAlreadyReceived(response.getId())) {
                                    REQUEST_COUNT = UnsplashApiPhoto.perform(response.getId());    
                                } else {
                                    System.out.println("- Skipping item: " + response.getId() + "...");
                                }

                                // @note: it seems that another request for the same photo's metadata may not count against API usage...?
                                //++REQUEST_COUNT; 

                                //usernames.add(response.getUser().getUsername());

                            }

                            EntityUtils.consume(entity1);                            
                        }
                        
                    }                         
                    
                }          
                                               
                if (REQUEST_COUNT >= Config.PERIODIC_REQUEST_LIMIT || Config.DELAY_CODES.contains(statusCode)) {
                    if (Config.DEBUG) {
                        if (Config.DELAY_CODES.contains(statusCode)) {
                            StringUtil.announce("! Delaying at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + " due to status code: " + statusCode + "...");    
                        } else if (REQUEST_COUNT >= Config.PERIODIC_REQUEST_LIMIT) {
                            StringUtil.announce("! Delaying at " + DateUtil.getFormattedDate(new Date(), "h:mm:ss a") + " due to API limit: " + REQUEST_COUNT + "/" + Config.PERIODIC_REQUEST_LIMIT + "...");    
                        }                        
                    }
                    try {
                        Thread.sleep(Config.REQUEST_DELAY_LIMIT);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                    
                    REQUEST_COUNT = 0; // reset
                    
                } else {
                    
                    try {
                        Thread.sleep(Config.REQUEST_DELAY_POLITE);
                    } catch (InterruptedException iex) {
                        iex.printStackTrace(System.err);
                    }
                }

            } while (false);
            
            // download photos by user...
            /*
            String username = "";
            Iterator it = usernames.iterator();
            while (it.hasNext()) {
                username = ""+it.next();
                UnsplashApiUser.perform(username);
            } 
            */

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }

    }

}