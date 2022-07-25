# 常见问题

什么是 Wakelock/Alarm/Service,怎样的设置才能使得效果最大化?

- 参见官方说明:
  - [wakelock](https://developer.android.com/training/scheduling/wakelock)
  - [alarms](https://developer.android.com/training/scheduling/alarms)  
  - [services](https://developer.android.com/guide/components/services)
- 很遗憾没有完美的参考,每个设备之间差异巨大.
  - [Amplify](https://forum.xda-developers.com/t/mod-xposed-amplify-battery-extender-control-alarms-services-and-wakelocks.2853874/) 提供了一个Wakelock/Alarm/Service 信息表 -> [参考列表](https://docs.google.com/spreadsheets/d/19S0tKF-kfozACgnGIX0gYFBe1K8_R3NBloP4Q1FgpIU/edit#gid=2072742181)

误操作导致无法设备开机

- 有两种情况
  - 原因是 Nowakelock 的 bug,此时需要卸载 Xposed 框架,进入系统清除 Nowakelock 数据.
    - 进入 RE -> 文件管理 -> `/data/adb/modules` 下选择 Xposed 对应模块,删除文件夹.
  - 误操作,限制了重要的系统唤醒锁,此时不需要卸载 Xposed 框架
    - 进入 RE -> 文件管理 -> `/data/misc/xxx-xxx-xxx/prefs/com.js.nowakelock` 文件夹删除
      - `xxx-xxx-xxx` 是一段很长的随机字符串,每个机器可能都不同.
      - 进入系统后还原误操作,如果不清楚只能清除 Nowakelock 数据

## 其他

是否收集隐私数据

- 除了未来可能的云端禁用方案加载外,所有数据均在本地,不会上传到任何地方.
- NoWakelock 不会收集存储任何隐私数据.

需要新功能或发现 bug

- 请在 [这里](https://github.com/Jasper-1024/NoWakeLock/issues) 提交,我会尽可能完成.

我想帮助更新翻译

- 欢迎提交 PR.
