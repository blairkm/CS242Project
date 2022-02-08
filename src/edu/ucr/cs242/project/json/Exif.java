package edu.ucr.cs242.project.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author rehmke
 */
public class Exif {
 
    @SerializedName("camera")
    @Expose    
    private String camera = "";
    
    @SerializedName("exposure")
    @Expose    
    private String exposure = "";
    
    @SerializedName("aperture")
    @Expose    
    private String aperture = "";
    
    @SerializedName("focal_length")
    @Expose    
    private String focal_length = "";
    
    @SerializedName("iso")
    @Expose    
    private String iso = "";

    /**
     * @return the camera
     */
    public String getCamera() {
        return camera;
    }

    /**
     * @param camera the camera to set
     */
    public void setCamera(String camera) {
        this.camera = camera;
    }

    /**
     * @return the exposure
     */
    public String getExposure() {
        return exposure;
    }

    /**
     * @param exposure the exposure to set
     */
    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    /**
     * @return the aperture
     */
    public String getAperture() {
        return aperture;
    }

    /**
     * @param aperture the aperture to set
     */
    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    /**
     * @return the focal_length
     */
    public String getFocal_length() {
        return focal_length;
    }

    /**
     * @param focal_length the focal_length to set
     */
    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    /**
     * @return the iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso the iso to set
     */
    public void setIso(String iso) {
        this.iso = iso;
    }
    
}