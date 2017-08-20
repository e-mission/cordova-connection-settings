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
    getSettings: function () {
    	return new Promise(function(resolve, reject) {	
    		exec(resolve, reject, "ConnectionSettings", "getSettings", []);
    	});
    }
}

module.exports = ConnectionSettings;
