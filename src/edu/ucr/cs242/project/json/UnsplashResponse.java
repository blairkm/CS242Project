package edu.ucr.cs242.project.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author rehmke
 */
public class UnsplashResponse {
 
    @SerializedName("id")
    @Expose    
    private String id = "";
    
    @SerializedName("created_at")
    @Expose        
    private String created_at = "";
    
    @SerializedName("updated_at")
    @Expose        
    private String updated_at = "";    
    
    @SerializedName("promoted_at")
    @Expose        
    private String promoted_at = "";  
    
    @SerializedName("width")
    @Expose            
    private int width = -1;
    
    @SerializedName("height")
    @Expose            
    private int height = -1;
    
    @SerializedName("color")
    @Expose            
    private String color = "";    
    
    @SerializedName("blur_hash")
    @Expose            
    private String blur_hash = "";        
    
    @SerializedName("description")
    @Expose            
    private String description = "";        
    
    @SerializedName("alt_description")
    @Expose            
    private String alt_description = "";    
    
    private Urls urls = null;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at the updated_at to set
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * @return the promoted_at
     */
    public String getPromoted_at() {
        return promoted_at;
    }

    /**
     * @param promoted_at the promoted_at to set
     */
    public void setPromoted_at(String promoted_at) {
        this.promoted_at = promoted_at;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the blur_hash
     */
    public String getBlur_hash() {
        return blur_hash;
    }

    /**
     * @param blur_hash the blur_hash to set
     */
    public void setBlur_hash(String blur_hash) {
        this.blur_hash = blur_hash;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the alt_description
     */
    public String getAlt_description() {
        return alt_description;
    }

    /**
     * @param alt_description the alt_description to set
     */
    public void setAlt_description(String alt_description) {
        this.alt_description = alt_description;
    }

    /**
     * @return the urls
     */
    public Urls getUrls() {
        return urls;
    }

    /**
     * @param urls the urls to set
     */
    public void setUrls(Urls urls) {
        this.urls = urls;
    }
    
}