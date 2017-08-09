#import <Cordova/CDV.h>

@interface BEMConnectionSettingsPlugin: CDVPlugin <UINavigationControllerDelegate>

- (void) getSettings:(CDVInvokedUrlCommand*)command;
- (void) setSettings:(CDVInvokedUrlCommand*)command;

@end
