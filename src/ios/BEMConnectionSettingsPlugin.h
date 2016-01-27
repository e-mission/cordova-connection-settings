#import <Cordova/CDV.h>

@interface BEMConnectionSettingsPlugin: CDVPlugin <UINavigationControllerDelegate>

- (void) getSettings:(CDVInvokedUrlCommand*)command;

@end
