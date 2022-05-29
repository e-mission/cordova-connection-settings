//
//  ConnectionSettings.m
//  E-Mission
//
//  Created by Kalyanaraman Shankari on 8/23/14.
//  Copyright (c) 2014 Kalyanaraman Shankari. All rights reserved.
//

#import "BEMConnectionSettings.h"
#import "BEMBuiltinUserCache.h"
#import <Cordova/CDV.h>

@interface ConnectionSettings() {
    NSDictionary *connSettingDict;
}
@end

@implementation ConnectionSettings
static ConnectionSettings *sharedInstance;

-(id)init{
    connSettingDict = [self getConfigFromDB];
    if (connSettingDict == NULL) {
        connSettingDict = [self getConfigFromFile];
    }
    /*
    if (connSettingDict == NULL) {
        connSettingDict = [self getDefaultConfig];
    }
     */
    return [super init];
}

+ (ConnectionSettings*)sharedInstance
{
    if (sharedInstance == nil) {
        sharedInstance = [ConnectionSettings new];
    }
    return sharedInstance;
}

- (NSDictionary*) getConfigFromDB
{
    return [[BuiltinUserCache database] getLocalStorage:CONNECTION_SETTINGS_KEY withMetadata:NO];
}

- (NSDictionary*) getConfigFromFile
{
    CDVAppDelegate *ad = [[UIApplication sharedApplication] delegate];
    CDVViewController *vc = ad.viewController;
    NSString *configFilePath = [vc.commandDelegate pathForResource:@"json/connectionConfig.json"];
    
    // JSON code from here
    // https://stackoverflow.com/questions/17988178/read-json-file-from-documents-folder-ios-with-javascript
    
    BOOL fileExist = [[NSFileManager defaultManager] fileExistsAtPath:configFilePath];
    if (fileExist) {
        NSString *content = [NSString stringWithContentsOfFile:configFilePath encoding:NSUTF8StringEncoding error:NULL];
        NSData *data = [content dataUsingEncoding:NSUTF8StringEncoding];
        return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
    } else {
        return NULL;
    }
}

- (NSDictionary*)getSettings
{
    return connSettingDict;
}

- (void) setSettings:(NSDictionary*) newSettings {
    [[BuiltinUserCache database] putLocalStorage:CONNECTION_SETTINGS_KEY jsonValue:newSettings];
    connSettingDict = newSettings;
}

- (NSDictionary*)getDefaultConfig
{
    // Use default values optimized for dev setting
    return @{@"connectUrl": @"http://localhost:8080",
                        @"ios": @{@"auth": @{@"method": @"dummy-dev"}}};
}

// BEGIN: Native code access methods
// These throw if the connection settings are not set, to ensure that there are no invocations
// from the plugin initialize methods before the javascript has the opportunity to set the config
// Note that we cannot throw from the constructor instead because then we cannot create the shared instance
// and even the javascript cannot set the config
- (NSString*)getConnectString
{
    if (connSettingDict == NULL) {
        NSException* notFoundEx = [[NSException alloc] initWithName:@"SettingsNotFound"
                                                             reason:@"Connection settings not saved to database!"
                                                           userInfo:NULL];
        @throw notFoundEx;
    }

    return [connSettingDict objectForKey: @"connectUrl"];
}

- (NSDictionary*) nativeAuth
{
    if (connSettingDict == NULL) {
        NSException* notFoundEx = [[NSException alloc] initWithName:@"SettingsNotFound"
                                                             reason:@"Connection settings not saved to database!"
                                                           userInfo:NULL];
        @throw notFoundEx;
    }
    return [[connSettingDict objectForKey: @"ios"] objectForKey:@"auth"];
}

- (NSString*) authMethod
{
    return [[self nativeAuth] objectForKey:@"method"];
    }

- (NSString*) authValueForKey:(NSString*) key
{
    return [[self nativeAuth] objectForKey:key];
}
// END: Native code access methods

@end
