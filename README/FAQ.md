# FAQ

What is Wakelock/Alarm/Service and how can I set it to maximize the effect?

- Official Documents
  - [wakelock](https://developer.android.com/training/scheduling/wakelock)
  - [alarms](https://developer.android.com/training/scheduling/alarms)  
  - [services](https://developer.android.com/guide/components/services)

- [Amplify](https://forum.xda-developers.com/t/mod-xposed-amplify-battery-extender-control-alarms-services-and-wakelocks.2853874/) offer some Wakelock/Alarm/Service information -> [Amplify](https://docs.google.com/spreadsheets/d/19S0tKF-kfozACgnGIX0gYFBe1K8_R3NBloP4Q1FgpIU/edit#gid=2072742181)

Wrong operation causes the device to fail to boot

- There are two cases
  - The reason is the bug of Nowakelock, you need to uninstall Xposed framework and enter the system to clear Nowakelock data.
    - Go to RE -> File Management -> `/data/adb/modules` and select Xposed module and delete the folder.
- If you have restricted the important system wake-up lock by mistake, you don't need to uninstall Xposed framework at this time.
  - Go to RE -> File Management -> `/data/misc/xxx-xxx-xxx/prefs/com.js.nowakelock` folder and delete it
    - `xxx-xxx-xxx` is a long random string, which may be different for each machine.
  - If you are not sure, you can only clear the Nowakelock data after entering the system to restore the error.

## other

Whether private data is collected

- All data is local and not uploaded anywhere, except for a possible future cloud disabling solution load.
- NoWakelock does not collect or store any private data.

Need new features or find bugs

- Please submit them at [here](https://github.com/Jasper-1024/NoWakeLock/issues) and I will do my best to complete them.

I would like to help update the translation

- Feel free to submit a PR.
