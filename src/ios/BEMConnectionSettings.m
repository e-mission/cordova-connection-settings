//
//  ConnectionSettings.m
//  E-Mission
//
//  Created by Kalyanaraman Shankari on 8/23/14.
//  Copyright (c) 2014 Kalyanaraman Shankari. All rights reserved.
//

#import "BEMConnectionSettings.h"
#import "BEMBuiltinUserCache.h"

@interface ConnectionSettings() {
    NSDictionary *connSettingDict;
}
@end

@implementation ConnectionSettings
static ConnectionSettings *sharedInstance;

-(id)init{
    connSettingDict = [[BuiltinUserCache database] getLocalStorage:CONNECTION_SETTINGS_KEY withMetadata:NO];
    return [super init];
}

+ (ConnectionSettings*)sharedInstance
{
    if (sharedInstance == nil) {
        sharedInstance = [ConnectionSettings new];
    }
    return sharedInstance;
}

- (NSURL*)getConnectUrl
{
    return [NSURL URLWithString:[connSettingDict objectForKey: @"connectUrl"]];
}

- (BOOL)isSkipAuth
{
    if([[self getConnectUrl].scheme isEqualToString:@"http"]) {
        return true;
    } else {
        return false;
    }
}

- (NSString*)getGoogleiOSClientID
{
    return [[connSettingDict objectForKey: @"ios"] objectForKey:@"googleClientID"];
}

@end
