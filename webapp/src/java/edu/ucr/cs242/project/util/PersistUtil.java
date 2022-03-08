package edu.ucr.cs242.project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rehmke
 */
public class PersistUtil {
    
    /*
    create database cs242;
    CREATE USER 'cs242_user'@'localhost' IDENTIFIED BY 'cs242_pw';
    GRANT ALL PRIVILEGES ON * . * TO 'cs242_user'@'localhost';
    select * from geo_data order by latitude asc;
    */    

    protected static void init() {

        String sql = "create table geo_data ( id varchar(15), name varchar(200), city varchar(100), country varchar(100), latitude varchar(50), longitude varchar(50) );";

        try {
            
            // H2 Database
            //Connection conn = DriverManager.getConnection("jdbc:h2:./cs242", "sa", "");
            
            // MySQL Database            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/cs242";
            Connection conn = DriverManager.getConnection(url, "cs242_user", "cs242_pw");
                    
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
            
        } catch (SQLException sqlex) {
            sqlex.printStackTrace(System.err);
        } catch (ClassNotFoundException cnfex) {
            cnfex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }

    private static void insert() {

        String sql = "insert into geo_data (id, name, city, country, latitude, longitude) values (?, ?, ?, ?, ?, ?);";

        try {
            
            // H2 Database
            //Connection conn = DriverManager.getConnection("jdbc:h2:./cs242", "sa", "");
            
            // MySQL Database            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/cs242";
            Connection conn = DriverManager.getConnection(url, "cs242_user", "cs242_pw");

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "ABC123");
            preparedStatement.setString(2, "nameX");
            preparedStatement.setString(3, "cityX");
            preparedStatement.setString(4, "countryX");
            preparedStatement.setString(5, "latitudeX");
            preparedStatement.setString(6, "longitudeX");

            preparedStatement.executeUpdate();

            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }

    private static void select() {

        String sql = "select id, name, city, country, latitude, longitude from geo_data where id = ?;";

        try {
            
            // H2 Database
            //Connection conn = DriverManager.getConnection("jdbc:h2:./cs242", "sa", "");
            
            // MySQL Database            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/cs242";
            Connection conn = DriverManager.getConnection(url, "cs242_user", "cs242_pw");

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "09SxeOdtlPE"); // ABC123

            ResultSet result = preparedStatement.executeQuery();

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

                System.out.println("id: " + id);
                System.out.println("name: " + name);
                System.out.println("city: " + city);
                System.out.println("country: " + country);
                System.out.println("latitude: " + latitude);
                System.out.println("longitude: " + longitude);

            }

            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }
    
    public static void main(String[] args) {

        init();
        //insert();
        //select();

    }    

}
