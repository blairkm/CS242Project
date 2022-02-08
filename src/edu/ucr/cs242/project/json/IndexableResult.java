package edu.ucr.cs242.project.json;

/**
 *
 * @author rehmke
 */
public class IndexableResult {
     
    private String photoId = ""; // id
    private String created = ""; // created_at
    private String description = ""; // description
    private String altDescription = ""; // alt_description
    private String urlRaw = ""; // urls.raw
    private String userId = ""; // user.id
    private String username = ""; // user.username
    private String userBio = ""; // user.bio
    private String userFullname = ""; // user.name
    private String userLocation = ""; // user.location
    //private String photoTopics = "";
    //private String photoCategories = "";
    private String camera = ""; // exif.camera
    private String exposure = ""; // exif.exposure
    private String aperture = ""; // exif.aperture
    private String focal_length = ""; // exif.focal_length
    private String iso = ""; // exif.iso
    private String photoLocation = "";
    private String photoCity = ""; // location.city
    private String photoCountry = ""; // location.country
    private String photoLatitude = ""; // location.position.latitude
    private String photoLongitude = ""; // location.position.longitude
    private int photoViews = -1; // views
    private int photoDownloads = -1; // downloads

    /**
     * @return the photoId
     */
    public String getPhotoId() {
        return photoId;
    }

    /**
     * @param photoId the photoId to set
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    /**
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(String created) {
        this.created = created;
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
     * @return the altDescription
     */
    public String getAltDescription() {
        return altDescription;
    }

    /**
     * @param altDescription the altDescription to set
     */
    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    /**
     * @return the urlRaw
     */
    public String getUrlRaw() {
        return urlRaw;
    }

    /**
     * @param urlRaw the urlRaw to set
     */
    public void setUrlRaw(String urlRaw) {
        this.urlRaw = urlRaw;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the userBio
     */
    public String getUserBio() {
        return userBio;
    }

    /**
     * @param userBio the userBio to set
     */
    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    /**
     * @return the userFullname
     */
    public String getUserFullname() {
        return userFullname;
    }

    /**
     * @param userFullname the userFullname to set
     */
    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    /**
     * @return the userLocation
     */
    public String getUserLocation() {
        return userLocation;
    }

    /**
     * @param userLocation the userLocation to set
     */
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

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

    /**
     * @return the photoLocation
     */
    public String getPhotoLocation() {
        return photoLocation;
    }

    /**
     * @param photoLocation the photoLocation to set
     */
    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    /**
     * @return the photoCity
     */
    public String getPhotoCity() {
        return photoCity;
    }

    /**
     * @param photoCity the photoCity to set
     */
    public void setPhotoCity(String photoCity) {
        this.photoCity = photoCity;
    }

    /**
     * @return the photoCountry
     */
    public String getPhotoCountry() {
        return photoCountry;
    }

    /**
     * @param photoCountry the photoCountry to set
     */
    public void setPhotoCountry(String photoCountry) {
        this.photoCountry = photoCountry;
    }

    /**
     * @return the photoLatitude
     */
    public String getPhotoLatitude() {
        return photoLatitude;
    }

    /**
     * @param photoLatitude the photoLatitude to set
     */
    public void setPhotoLatitude(String photoLatitude) {
        this.photoLatitude = photoLatitude;
    }

    /**
     * @return the photoLongitude
     */
    public String getPhotoLongitude() {
        return photoLongitude;
    }

    /**
     * @param photoLongitude the photoLongitude to set
     */
    public void setPhotoLongitude(String photoLongitude) {
        this.photoLongitude = photoLongitude;
    }

    /**
     * @return the photoViews
     */
    public int getPhotoViews() {
        return photoViews;
    }

    /**
     * @param photoViews the photoViews to set
     */
    public void setPhotoViews(int photoViews) {
        this.photoViews = photoViews;
    }

    /**
     * @return the photoDownloads
     */
    public int getPhotoDownloads() {
        return photoDownloads;
    }

    /**
     * @param photoDownloads the photoDownloads to set
     */
    public void setPhotoDownloads(int photoDownloads) {
        this.photoDownloads = photoDownloads;
    }
    
}