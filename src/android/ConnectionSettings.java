package edu.berkeley.eecs.emission.cordova.connectionsettings;

import android.content.Context;

import edu.berkeley.eecs.emission.R;

/*
 * Single class that returns all the connection level settings that need to be customized
 * when we connect to a different server.
 */

public class ConnectionSettings {
	public static String getConnectURL(Context ctxt) {
		return ctxt.getString(R.string.connect_url);
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
		return ctxt.getString(R.string.google_webapp_client_id);
	}
	
	public static String getMovesClientID(Context ctxt) {
		return ctxt.getString(R.string.moves_client_id);
	}
}
