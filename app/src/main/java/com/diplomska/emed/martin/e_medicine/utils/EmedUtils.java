package com.diplomska.emed.martin.e_medicine.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Martin on 9/16/2015.
 */
public class EmedUtils {

    public static String readJsonFeed(String urlLink) {
        try {
            URL url = new URL(urlLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // read the response
            System.out.println("Response Code: " + conn.getResponseCode());
            InputStream in = new BufferedInputStream(conn.getInputStream());
            return IOUtils.toString(in, "UTF-8");

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
