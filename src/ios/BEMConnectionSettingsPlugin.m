#import "BEMConnectionSettingsPlugin.h"
#import "BEMConnectionSettings.h"

@implementation BEMConnectionSettingsPlugin

- (void)getSettings:(CDVInvokedUrlCommand*)command
{
    NSString* callbackId = [command callbackId];
    
    @try {
        ConnectionSettings* instance = [ConnectionSettings sharedInstance];
        NSDictionary *iosDict = @{@"googleClientID": [instance getGoogleiOSClientID],
                                  @"googleClientSecret": [instance getGoogleiOSClientSecret],
                                  @"parseAppID": [instance getParseAppID],
                                  @"parseClientID": [instance getParseClientID]};
        NSDictionary* retDict = @{@"connectURL": [[instance getConnectUrl] absoluteString],
                                         @"isSkipAuth": @([instance isSkipAuth]),
                                         @"googleWebAppClientID": [instance getGoogleWebAppClientID],
                                         @"ios": iosDict};
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_OK
                                   messageAsDictionary:retDict];
        [self.commandDelegate sendPluginResult:result callbackId:callbackId];
    }
    @catch (NSException *exception) {
        NSString* msg = [NSString stringWithFormat: @"While getting settings, error %@", exception];
        CDVPluginResult* result = [CDVPluginResult
                                   resultWithStatus:CDVCommandStatus_ERROR
                                   messageAsString:msg];
        [self.commandDelegate sendPluginResult:result callbackId:callbackId];
    }
}

@end
