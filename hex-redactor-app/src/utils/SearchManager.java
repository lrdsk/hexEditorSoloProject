package utils;

import java.util.ArrayList;
import java.util.List;

public class SearchManager {
    private static final String HEXES = "0123456789ABCDEF";
    public static String[] convertBytesToHex(byte[] bytesToSearch){
        final StringBuilder hex = new StringBuilder(2 * bytesToSearch.length);
            for(byte b : bytesToSearch){
                hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F))).append("\t");
            }
            return hex.toString().split("\t");
    }
}
