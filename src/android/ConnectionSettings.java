package edu.berkeley.eecs.emission.cordova.connectionsettings;

import android.content.Context;
import android.text.TextUtils;

import org.apache.cordova.ConfigXmlParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import edu.berkeley.eecs.emission.cordova.unifiedlogger.Log;

/*
 * Single class that returns all the connection level settings that need to be customized
 * when we connect to a different server.
 */

public class ConnectionSettings {
    private JSONObject connectionSettings;

    private static ConnectionSettings sharedInstance;
    static String CONNECTION_SETTINGS_KEY = "connection_settings";
    private static String TAG = "ConnectionSettings";

    private ConnectionSettings() {
    }

    private static ConnectionSettings sharedInstance(Context ctxt) {
        if (sharedInstance == null) {
            sharedInstance = new ConnectionSettings();
            InputStream configFileStream = getConfigFileStream(ctxt);
            Log.i(ctxt, TAG, "configFilePath = "+configFileStream);
            sharedInstance.connectionSettings = getConfigFromFile(ctxt, configFileStream);
        }
        return sharedInstance;
    }

    /*
     * This has to return an InputStream because you cannot read assets using a path.
     * Instead, you have to open the stream using ctxt.getAssets().open(filepath)
     * https://stackoverflow.com/questions/4820816/how-to-get-uri-from-an-asset-file
     */

    private static InputStream getConfigFileStream(Context ctxt) {
            try {
            ConfigXmlParser parser = new ConfigXmlParser();
            parser.parse(ctxt);
            String launchUrl = parser.getLaunchUrl();
            URL connectionConfigURL = new URL(new URL(launchUrl), "json/connectionConfig.json");
            // URL connectionConfigURL = new URL(launchUrl);
            String fullPath = connectionConfigURL.getPath(); // e.g. /android_asset/www/json/connectionConfig.json
            System.out.println("path of the full URL = "+fullPath);

            String[] pathComponents = fullPath.split("/"); // "android_asset/www/json/connectionConfig.json"
            System.out.println("path of components = "+Arrays.toString(pathComponents)); // [, android_asset, www, json, connectionConfig.json]
            String[] assetPathComponents = Arrays.copyOfRange(pathComponents, 2, pathComponents.length); // [www, json, connectionConfig.json]
            System.out.println("path components within the asset dir = "+Arrays.toString(assetPathComponents)); // www/json/connectionConfig.json
            String assetPath = TextUtils.join("/", assetPathComponents);
            System.out.println("path within the asset dir = "+assetPath);

            System.out.println("children of . = "+Arrays.toString(ctxt.getAssets().list(".")));
            System.out.println("children of www = "+Arrays.toString(ctxt.getAssets().list("www")));
            System.out.println("children of www/json = "+Arrays.toString(ctxt.getAssets().list("www/json")));
            // Reading code based on
            // https://stackoverflow.com/questions/13814503/reading-a-json-file-in-android#13814551
            // But with a better way to read from file
            // you shouldn't have to read the file size first!
            return ctxt.getAssets().open(assetPath);
        } catch (MalformedURLException e) {
            Log.exception(ctxt, TAG, e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.exception(ctxt, TAG, e);
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getConfigFromFile(Context ctxt, InputStream configFileStream) {
        if (configFileStream == null) {
            Log.i(ctxt, TAG, "getConfigFromFile: configFilePath = "+configFileStream+" returning ");
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(configFileStream));
            StringBuilder finalString = new StringBuilder();
            String currLine = br.readLine();
            while (currLine != null) {
                finalString.append(currLine);
                currLine = br.readLine();
            }
            return new JSONObject(finalString.toString());
        } catch (FileNotFoundException e) {
            Log.exception(ctxt, TAG, e);
            sharedInstance.connectionSettings = null;
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.exception(ctxt, TAG, e);
            sharedInstance.connectionSettings = null;
            e.printStackTrace();
            return null;
            } catch (JSONException e) {
            Log.exception(ctxt, TAG, e);
                sharedInstance.connectionSettings = null;
            e.printStackTrace();
            return null;
            }
        }

    public static JSONObject getSettings(Context ctxt) {
        JSONObject connectionSettings = sharedInstance(ctxt).connectionSettings;
        Log.d(ctxt, TAG, "in getSettings, connectionSettings = "+connectionSettings);
        return connectionSettings;
    }

	public static String getConnectURL(Context ctxt) {
        JSONObject connectionSettings = sharedInstance(ctxt).connectionSettings;
        Log.d(ctxt, TAG, "in getConnectURL, connectionSettings = "+connectionSettings);
        if (connectionSettings == null) {
            return null;
        }
        try {
            String retVal = connectionSettings.getString("connectUrl");
            Log.d(ctxt, TAG, "in getConnectURL, returning "+retVal);
            return retVal;
        } catch(JSONException e) {
            Log.e(ctxt, TAG, "Got exception while retrieving connection settings");
            Log.exception(ctxt, TAG, e);
            return null;
        }
	}

    public static boolean isSkipAuth(Context ctxt) {
        String connectURL = getConnectURL(ctxt);
        if (connectURL.startsWith("http:")) {
            System.out.println("connectURL starts with http, skipping auth");
            return true;
        } else {
            return false;
        }
    }
	
	public static String getGoogleWebAppClientID(Context ctxt) {
        if (sharedInstance(ctxt).connectionSettings == null) {
            return null;
        }
        try {
            return sharedInstance(ctxt).connectionSettings.getJSONObject("android").getString("googleWebAppClientID");
        } catch(JSONException e) {
            Log.e(ctxt, TAG, "Got exception while retrieving connection settings");
            Log.exception(ctxt, TAG, e);
            return null;
	}
	}
}
