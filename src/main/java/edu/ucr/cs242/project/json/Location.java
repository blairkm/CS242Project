package edu.ucr.cs242.project.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author rehmke
 */
public class Location {

    @SerializedName("name")
    @Expose          
    private String name = "";    
    
    @SerializedName("city")
    @Expose          
    private String city = "";
    
    @SerializedName("country")
    @Expose          
    private String country = "";
    
    @SerializedName("position")
    @Expose      
    private Position position = new Position();  

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }    
    
    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
}