/*global cordova, module*/

var exec = require("cordova/exec")

/*
 * Format of the returned value:
 * {
 *    "connectUrl": "...",
 *    "isSkipAuth": true/false,
 *    "android": {
 *        "auth": {
 *            "method": "e.g. google-authutil",
 *            "clientID": "e.g. XXXXX"
 *        }
 *    },
 *    "ios": {
 *        "auth": {
 *            "method": "e.g. google-signin-lib",
 *            "clientID": "e.g. YYYYY"
 *        }
 *    }
 * }
 */

var ConnectionSettings = {
    // Return the current settings
    // This expects that the settings have been set earlier from a file
    // using `setSettings` in order to ensure that the lazy instantiation is 
    // correct (see https://github.com/e-mission/cordova-connection-settings/issues/9#issuecomment-324705272)
    // If settings were not set, this crashes 
    getSettings: function () {
    	return new Promise(function(resolve, reject) {	
    		exec(resolve, reject, "ConnectionSettings", "getSettings", []);
    	});
    },

    // Set the settings to be returned in `getSettings`
    // `getSettings` will crash if this is not done
    // This also refreshes the cached copy of the settings
    setSettings: function(newConfig) {
        return new Promise(function(resolve, reject) {
    		exec(resolve, reject, "ConnectionSettings", "setSettings", [newConfig]);
        });
    },

    // Platform-specific default settings to be used
    // if there is no file specifying settings
    getDefaultSettings: function() {
        return new Promise(function(resolve, reject) {
    		exec(resolve, reject, "ConnectionSettings", "getDefaultSettings", []);
        });
    }
}

module.exports = ConnectionSettings;
