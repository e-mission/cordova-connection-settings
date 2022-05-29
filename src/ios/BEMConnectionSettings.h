//
//  ConnectionSettings.h
//  E-Mission
//
//  Created by Kalyanaraman Shankari on 8/23/14.
//  Copyright (c) 2014 Kalyanaraman Shankari. All rights reserved.
//

#import <Foundation/Foundation.h>
#define CONNECTION_SETTINGS_KEY @"connection_settings"

@interface ConnectionSettings : NSObject
+(ConnectionSettings*) sharedInstance;
-(NSDictionary*)getSettings;
-(NSDictionary*)getDefaultConfig;
-(void)setSettings:(NSDictionary*)newConfig;
-(NSString*) getConnectString;
-(NSString*) authMethod;
-(NSString*) authValueForKey:(NSString*) key;
@end
