package edu.ucr.cs242.project.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author rehmke
 */
public class Urls {
 
    @SerializedName("raw")
    @Expose                
    private String raw = "";
    
    @SerializedName("full")
    @Expose                 
    private String full = "";
    
    @SerializedName("regular")
    @Expose                 
    private String regular = "";
    
    @SerializedName("small")
    @Expose                 
    private String small = "";
    
    @SerializedName("thumb")
    @Expose                 
    private String thumb = "";

    /**
     * @return the raw
     */
    public String getRaw() {
        return raw;
    }

    /**
     * @param raw the raw to set
     */
    public void setRaw(String raw) {
        this.raw = raw;
    }

    /**
     * @return the full
     */
    public String getFull() {
        return full;
    }

    /**
     * @param full the full to set
     */
    public void setFull(String full) {
        this.full = full;
    }

    /**
     * @return the regular
     */
    public String getRegular() {
        return regular;
    }

    /**
     * @param regular the regular to set
     */
    public void setRegular(String regular) {
        this.regular = regular;
    }

    /**
     * @return the small
     */
    public String getSmall() {
        return small;
    }

    /**
     * @param small the small to set
     */
    public void setSmall(String small) {
        this.small = small;
    }

    /**
     * @return the thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * @param thumb the thumb to set
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
}