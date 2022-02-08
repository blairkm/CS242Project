package edu.ucr.cs242.project;

import com.google.gson.Gson;
import edu.ucr.cs242.project.indexer.LuceneIndexer;
import edu.ucr.cs242.project.json.IndexableResult;
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
public class UnsplashApiPhoto { 

    protected static void perform(String _photoId) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(Config.UNSPLASH_API_PHOTOS_ENDPOINT + "/" + _photoId);
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
                    FileUtil.persistImage(response.getId(), response.getUrls().getFull());                    

                    IndexableResult ir = new IndexableResult();
                    ir.setPhotoId(response.getId()); // id
                    ir.setCreated(response.getCreated_at()); // created_at
                    ir.setDescription(response.getDescription()); // description
                    ir.setAltDescription(response.getAlt_description()); // alt_description
                    ir.setUrlRaw(response.getUrls().getRaw()); // urls.raw
                    ir.setUserId(response.getUser().getId()); // user.id
                    ir.setUsername(response.getUser().getUsername()); // user.username
                    ir.setUserBio(response.getUser().getBio()); // user.bio
                    ir.setUserFullname(response.getUser().getName()); // user.name
                    ir.setUserLocation(response.getUser().getLocation()); // user.location
                    ir.setCamera(response.getExif().getCamera()); // exif.camera
                    ir.setExposure(response.getExif().getExposure()); // exif.exposure
                    ir.setAperture(response.getExif().getAperture()); // exif.aperture
                    ir.setFocal_length(response.getExif().getFocal_length()); // exif.focal_length
                    ir.setIso(response.getExif().getIso()); // exif.iso
                    ir.setPhotoLocation(response.getLocation().getName()); // location.name
                    ir.setPhotoCity(response.getLocation().getCity()); // location.city
                    ir.setPhotoCountry(response.getLocation().getCountry()); // location.country
                    ir.setPhotoLatitude(response.getLocation().getPosition().getLatitude()); // location.position.latitude
                    ir.setPhotoLongitude(response.getLocation().getPosition().getLongitude()); // location.position.longitude
                    ir.setPhotoViews(response.getViews()); // views
                    ir.setPhotoDownloads(response.getDownloads()); // downloads

                    FileUtil.persistIndexable(response.getId(), new Gson().toJson(ir));                    
                    
                    // Index simplified response object
                    LuceneIndexer.perform(Config.INDEXABLE_ARCHIVE_FILEPATH + System.getProperty("file.separator") + response.getId());

                    EntityUtils.consume(entity1);
                }

                if (Config.DELAY_CODES.contains(statusCode)) {
                    if (Config.DEBUG) {
                        announce("! Delaying due to status code " + statusCode + "...");
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

    private static void announce(String _val) {
        System.out.println(_val);
    }
        
}