# NoWakeLock

This is English version(although a lot of google translation😂), for china
  user [中文版](https://github.com/Jasper-1024/NoWakeLock/blob/dev/README/README_zh_cn.md)

~~A major update is being planned, but it will take a lot of time.~~ it came now 2.0 beta.

NoWakelock allows you to easily control the frequency and duration of waking up your Android device to save power consumption.

- Take full control of Wakelock/Alarm/Service, no feature limitations.
- Application level support
  - Control Wakelock/Alarm/Service by application
  - Application-level Wakelock/Alarm regular expression interception support.
  - Limited multi-user support
- Support for Android N and above.
- Fully open source, no private data collected and stored.

**Serious warning**:

- This application is still in Beta testing stage. I will not be responsible for any damage
      caused by the software to your device.
- Version 2.0 is not compatible with the previous configuration, you need to clear all
      application data.

## About

With the update of Android version, doze mode is enough to suppress the power consumption of the
  background of the application, but some advanced users still hope to be able to control the
  wakelock alarm or service by themselves.

Amplify is good enough to meet the needs of most people, but after Android N, Amplify stops
  updating.

So NoWakeLock expects to achieve the same function as Amplify does on Android N and later.

## 2.x Features

- Alpha:
  - ~~record/block alarm~~
  - ~~record/block wakelock~~
  - ~~record/block service~~
  - ~~check module active status~~
  - ~~Data reset~~
  - ~~Regular expressions support~~

- Beta:
  - Data backup and recovery
  - ~~Long press to copy information~~

- Release:
  - Application statistics

## Compatibility

- NoWakeLock is available for Android N ~ Android S. For previous versions of Android N, please
  use [Amplify](https://github.com/rsteckler/unbounce-android).

- NoWakeLock was tested with the EdXposed / LSPosed.

## Compile

- ~~[master](https://github.com/Jasper-1024/NoWakeLock): stable version (not ready yet)~~

- [dev](https://github.com/Jasper-1024/NoWakeLock/tree/dev): Beta version, may not be stable.

- [feature](https://github.com/Jasper-1024/NoWakeLock/tree/feature): Alpha version

## Installation

- [Github releases](https://github.com/Jasper-1024/NoWakeLock/releases), including the alpha/beta
  version.
- [酷 安](https://www.coolapk.com/apk/260112), beta version but stable.
- [Play](https://play.google.com/store/apps/details?id=com.js.nowakelock), beta version.

## Support

- Only NoWakeLock downloaded from the above channels is supported
- Please submit [ISSUE](https://github.com/Jasper-1024/NoWakeLock/issues)

## Contributing

- [Jasper Hale](https://github.com/Jasper-1024)

## License

- NoWakeLock is released under GNU
  GPLv3 ([License](https://github.com/Jasper-1024/NoWakeLock/blob/master/LICENSE)).

## Thanks

- NoWakelock is written with reference to the need for more open source applications. Thanks here.

- [Amplify](https://github.com/rsteckler/unbounce-android).
- [XPrivacyLua](https://github.com/M66B/XPrivacyLua)
- [GravityBox](https://github.com/GravityBox/GravityBox)
