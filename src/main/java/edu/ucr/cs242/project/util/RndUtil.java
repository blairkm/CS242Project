package edu.ucr.cs242.project.util;

import edu.ucr.cs242.project.Config;
import java.util.Random;

/**
 *
 * @author rehmke
 */
public class RndUtil {
 
    public static int getRandomNumber() {
        int retVal = 0;        
        Random rand = new Random();
        retVal = rand.nextInt(Config.RANDOM_NUMBER_CEILING-Config.RANDOM_NUMBER_FLOOR) + Config.RANDOM_NUMBER_FLOOR;        
        return retVal;
    }
    
}