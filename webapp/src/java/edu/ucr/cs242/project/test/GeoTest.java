package edu.ucr.cs242.project.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rehmke
 */
public class GeoTest {
 
    public static void main(String[] args) {
        performQuery("-0.27689667", "73.41794667", 100);
        
    }
    
    public static String performQuery(String _latitude, String _longitude, float _radius) {
        
        String retVal = "";
        
        String sql = "SELECT ((ACOS(SIN(? * PI() / 180) * SIN(Latitude * PI() / 180) + COS(? * PI() / 180) * COS(Latitude * PI() / 180) * COS((? - Longitude) * PI() / 180)) * 180 / PI()) * 60 * 1.1515) AS DISTANCE, id, name, city, country, latitude, longitude FROM geo_data HAVING DISTANCE<=? ORDER BY DISTANCE ASC";

        try {
            
            // H2 Database
            //Connection conn = DriverManager.getConnection("jdbc:h2:./cs242", "sa", "");
            
            // MySQL Database            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/cs242";
            Connection conn = DriverManager.getConnection(url, "cs242_user", "cs242_pw");

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, _latitude); // lat
            preparedStatement.setString(2, _latitude); // lat
            preparedStatement.setString(3, _longitude); // long
            preparedStatement.setFloat(4, _radius); // radius (in miles)

            ResultSet result = preparedStatement.executeQuery();
            
            int ctr = 0;

            while (result.next()) {

                String id = "";
                String name = "";
                String city = "";
                String country = "";
                String latitude = "";
                String longitude = "";

                id = "" + result.getString("id");
                name = "" + result.getString("name");
                city = "" + result.getString("city");
                country = "" + result.getString("country");
                latitude = "" + result.getString("latitude");
                longitude = "" + result.getString("longitude");

                /*
                System.out.println(++ctr);
                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("city: " + city);
                System.out.println("country: " + country);
                System.out.println("latitude: " + latitude);
                System.out.println("longitude: " + longitude);
                */
                
                retVal += ++ctr + ". Photo ID: " + id;
                retVal += "<br/>";
                retVal += "<img src='http://localhost:8080/searchPortal/photo/?id=" + id + "' width='250' height='250' />";
                retVal += "<br/>";
                retVal += "Location Name: " + name;
                retVal += "<br/>";
                retVal += "City: " + city;
                retVal += "<br/>";
                retVal += "Country: " + country;
                retVal += "<br/>";
                retVal += "Latitude: " + latitude;
                retVal += "<br/>";
                retVal += "Longitude: " + longitude;
                retVal += "<p/>";

            }

            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }   
        
        return retVal;
        
    }
    
}