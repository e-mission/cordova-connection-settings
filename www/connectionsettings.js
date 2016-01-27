/*global cordova, module*/

var exec = require("cordova/exec")

/*
 * Format of the returned value:
 * {
 *    "connectUrl": "...",
 *    "isSkipAuth": true/false,
 *    "googleWebAppClientID": "...",
 *    "ios": {
 *       "googleClientID": "...",
 *       "googleClientSecret": "...",
 *       "parseAppID": "...",
 *       "parseClientID": "...",
 *    }
 * }
 */

var ConnectionSettings = {
    getSettings: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "ConnectionSettings", "getSettings", []);
    }
}

module.exports = ConnectionSettings;
