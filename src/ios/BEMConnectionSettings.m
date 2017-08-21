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
    CDVAppDelegate *ad = [[UIApplication sharedApplication] delegate];
    CDVViewController *vc = ad.viewController;
    NSString *configFilePath = [vc.commandDelegate pathForResource:@"json/connectionConfig.json"];
    
    // JSON code from here
    // https://stackoverflow.com/questions/17988178/read-json-file-from-documents-folder-ios-with-javascript
    
    BOOL fileExist = [[NSFileManager defaultManager] fileExistsAtPath:configFilePath];
    if (fileExist) {
        NSString *content = [NSString stringWithContentsOfFile:configFilePath encoding:NSUTF8StringEncoding error:NULL];
        NSData *data = [content dataUsingEncoding:NSUTF8StringEncoding];
        connSettingDict = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
    } else {
        // Use default values optimized for dev setting
        connSettingDict = @{@"connectUrl": @"http://localhost:8080",
                            @"ios": @{@"auth": @{@"method": @"dummy-dev"}}};
    }

    return [super init];
}

+ (ConnectionSettings*)sharedInstance
{
    if (sharedInstance == nil) {
        sharedInstance = [ConnectionSettings new];
    }
    return sharedInstance;
}

- (NSDictionary*)getSettings
{
    return connSettingDict;
}

- (NSURL*)getConnectUrl
{
    return [NSURL URLWithString:[connSettingDict objectForKey: @"connectUrl"]];
}

- (NSString*) authMethod
{
    return [[[connSettingDict objectForKey: @"ios"] objectForKey:@"auth"] objectForKey:@"method"];
}

- (NSString*)getClientID
{
    if (connSettingDict == NULL) {
        return NULL;
    }
    return [[[connSettingDict objectForKey: @"ios"] objectForKey:@"auth"] objectForKey:@"clientID"];
}

@end
