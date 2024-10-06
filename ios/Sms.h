
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSmsSpec.h"

@interface Sms : NSObject <NativeSmsSpec>
#else
#import <React/RCTBridgeModule.h>

@interface Sms : NSObject <RCTBridgeModule>
#endif

@end
