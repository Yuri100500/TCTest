package com.epam.scripps.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Iurii_Galias on 6/18/2015.
 */
public class AnvatoServices
{
    public static void checkingMetadataOfflineService(String programTitle, String episodeName, String programDescription)
    {
        String response = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet anvatoOfflineRequest = new HttpGet("http://tkx2-prod.anvato.net/rest/v2/now/cooking?anvack=anvato_scripps_app_web_prod_0837996dbe373629133857ae9eb72e740424d80a");
        try
        {
            HttpResponse execute = client.execute(anvatoOfflineRequest);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s;
            while ((s = buffer.readLine()) != null)
            {
                response += s;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Iterator<String> res = new JSONObject(response).getJSONObject("schedule").getJSONObject("current_event").getJSONObject("custom_metadata").keys();
        JSONObject customMetaData = new JSONObject(response).getJSONObject("schedule").getJSONObject("current_event").getJSONObject("custom_metadata");

        while (res.hasNext())
        {
            String key = res.next();
            customMetaData.getJSONObject(key);
        }
    }
}