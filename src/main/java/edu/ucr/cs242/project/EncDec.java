package edu.ucr.cs242.project;

import org.jasypt.util.text.AES256TextEncryptor;

/**
 *
 * @author rehmke
 */
public class EncDec {       

    protected static String enc(String _val) {
        String retVal = "";
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(ENC_DEC_CREDENTIAL);
        retVal = textEncryptor.encrypt(_val);
        return retVal;
    }

    protected static String dec(String _val) {
        String retVal = "";
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(ENC_DEC_CREDENTIAL);
        retVal = textEncryptor.decrypt(_val);
        return retVal;
    }
    
    /*
    public static void main(String[] args) {
        String test = "UNSPLASH_ACCESS_KEY_VALUE";
        System.out.println(enc(test));
        System.out.println(dec(enc(test)));
    }
    */
    
    private static final String ENC_DEC_CREDENTIAL = "Sdl;fCj347520$sfjdkl#";

}