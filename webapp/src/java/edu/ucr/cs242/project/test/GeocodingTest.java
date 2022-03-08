package edu.ucr.cs242.project.test;

import edu.ucr.cs242.project.util.GeocodingUtil;

/**
 *
 * @author rehmke
 */
public class GeocodingTest {
 
    public static void main(String[] args) {
        //System.out.println(GeocodingUtil.request("Maldives"));
        System.out.println(GeocodingUtil.getLatitude("25.22579785|55.18304675047368"));
        System.out.println(GeocodingUtil.getLongitude("25.22579785|55.18304675047368"));
    }
    
}