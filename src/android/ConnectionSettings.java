package edu.berkeley.eecs.emission.cordova.connectionsettings;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import edu.berkeley.eecs.emission.cordova.unifiedlogger.Log;
import edu.berkeley.eecs.emission.cordova.usercache.UserCacheFactory;

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
            try {
                sharedInstance.connectionSettings =
                        UserCacheFactory.getUserCache(ctxt).getLocalStorage(CONNECTION_SETTINGS_KEY, false);
            } catch (JSONException e) {
                sharedInstance.connectionSettings = null;
            }
        }
        return sharedInstance;
    }

	public static String getConnectURL(Context ctxt) {
        if (sharedInstance(ctxt).connectionSettings == null) {
            return null;
        }
        try {
            return sharedInstance(ctxt).connectionSettings.getString("connectUrl");
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
