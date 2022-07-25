# NoWakeLock

NoWakelock 可以让你轻松的控制 Android 设备的唤醒的频率和持续时间,进而节省电量消耗.

- 全面控制 Wakelock/Alarm/Service,没有功能限制.
- 应用级别的支持
  - 按照应用控制 Wakelock/Alarm/Service
  - 应用级别的 Wakelock/Alarm 正则表达式拦截支持.
  - 有限的多用户支持
- 支持 Android N 及以上版本.
- 完全的开源,不收集保存任何隐私数据.

**严重警告**:

- 这款应用依旧处于 Beta 测试阶段.因为此软件给您设备带来的损伤,本人概不负责.
- 2.0 版本与之前版本数据库不兼容,需要清除全部应用数据.

## 关于

随着 Android 版本更新, doze 模式已经足够压制住应用后台耗电,但高级用户依旧希望可以自行控制唤醒.

Android N 以后随着系统安全的升级 XSharedPreferences 基本被废, Amplify 的更新随之停滞.

NoWakeLock 可以算是 [Amplify](https://github.com/rsteckler/unbounce-android) 的复刻,期望能在 Android N 以上版本带来同样的功能.

## 2.0 计划

Alpha(完成):

- ~~Alarm 记录~~
- ~~Alarm 限制~~
- ~~Alarm 正则限制~~
- ~~Service 记录~~
- ~~Service 限制~~
- ~~Wakelock 唤醒时间统计.~~
- ~~Xposed 检测~~

Beta(现在):

- ~~长按复制信息~~
- 数据导入导出备份.
- 唤醒锁信息

Release:

- 禁用方案加载
- 更好的 Xposed 提示

## 兼容性

NoWakeLock 适用于 Android N ~ Android S.

NoWakeLock 仅在 EdXposed/LSPosed 框架下进行了测试.

## 编译

- ~~[master](https://github.com/Jasper-1024/NoWakeLock): 稳定版(还未就绪)~~

- [dev](https://github.com/Jasper-1024/NoWakeLock/tree/dev): 测试版,可能并不稳定.

- [init](https://github.com/Jasper-1024/NoWakeLock/tree/init): 预览版

## 安装

- [Github releases](https://github.com/Jasper-1024/NoWakeLock/releases), 包括 alpha/bate 版.
- [酷安](https://www.coolapk.com/apk/260112), 较为稳定的 beta 版本.
- [Play](https://play.google.com/store/apps/details?id=com.js.nowakelock),已上线 bate 版,需要加入测试计划.

## 支持

- 仅支持在上述渠道下载的 NoWakeLock
- 请提交 [ISSUE](https://github.com/Jasper-1024/NoWakeLock/issues)

## 贡献者

- [Jasper Hale](https://github.com/Jasper-1024)

## 许可证

- NoWakeLock 以 GNU GPLv3 ([许可](https://github.com/Jasper-1024/NoWakeLock/blob/master/LICENSE)) 发布.

## 致谢

- NoWakelock 编写中参考了需多开源应用.这里表示致谢.

- [Amplify](https://github.com/rsteckler/unbounce-android).
- [XPrivacyLua](https://github.com/M66B/XPrivacyLua)
- [GravityBox](https://github.com/GravityBox/GravityBox)
