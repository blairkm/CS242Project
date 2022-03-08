package edu.ucr.cs242.project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import edu.ucr.cs242.project.Config;
import edu.ucr.cs242.project.FileUtil;
import edu.ucr.cs242.project.json.IndexableResult;

/**
 *
 * @author rehmke
 */
public class ImportGeoData {

    public static void main(String[] args) {
        perform();
    }

    private static void perform() {

        // @note: one-time schema creation (DDL for geo_data)
        // PersistUtil.init();

        String currentFilepath = "";
        String currentContent = "";        
        
        Gson gson = new Gson();
        
        String rootFilepath = StringUtils.replace(Config.INDEXABLE_ARCHIVE_FILEPATH, "PartB", "");
        
        List files = FileUtil.getFiles(rootFilepath, false);
        Iterator it = files.iterator();
        while (it.hasNext()) {
            currentFilepath = ""+it.next();
            currentContent = FileUtil.readFileToString(currentFilepath);            
            IndexableResult json = gson.fromJson(currentContent, IndexableResult.class);            
            insert(json.getPhotoId(), json.getPhotoLocation(), json.getPhotoCity(), json.getPhotoCountry(), json.getPhotoLatitude(), json.getPhotoLongitude());            
        }
        
    }
    
    private static void insert(String _photoId, String _photoLocation, String _photoCity, String _photoCountry, String _photoLatitude, String _photoLongitude) {

        String sql = "insert into geo_data (id, name, city, country, latitude, longitude) values (?, ?, ?, ?, ?, ?);";

        try {
            
            // H2 Database
            //Connection conn = DriverManager.getConnection("jdbc:h2:./cs242", "sa", "");
            
            // MySQL Database            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/cs242";
            Connection conn = DriverManager.getConnection(url, "cs242_user", "cs242_pw");

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, _photoId);
            preparedStatement.setString(2, _photoLocation);
            preparedStatement.setString(3, _photoCity);
            preparedStatement.setString(4, _photoCountry);
            preparedStatement.setString(5, _photoLatitude);
            preparedStatement.setString(6, _photoLongitude);

            preparedStatement.executeUpdate();

            conn.close();
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }    
    
}