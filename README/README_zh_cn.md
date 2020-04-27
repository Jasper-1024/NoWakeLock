# NoWakeLock

* 一个控制 Android 唤醒锁的应用,可以运行在 Android N 以后版本.

* **严重警告**: 这款应用依旧处于 Alpha 测试阶段.因为软件给您设备带来的损伤,本人概不负责.

## 关于

* 随着 Android 版本更新, doze 模式已经足够压制住应用后台耗电,但高级用户依旧希望可以自行控制唤醒.
* Android N 以后随着系统安全的升级 XSharedPreferences 基本被废, Amplify 的更新随之停滞.
* NoWakeLock 可以算是 [Amplify](https://github.com/mh0rst/Amplify) 的复刻,期望能在 Android N 以上版本带来同样的功能.

## 功能

* 已完成
  * 分应用 wakelock 唤醒锁计数.
  * wakelock 限制
  * 亮/暗色模式适配
  * wakelock 唤醒间隔限制
  * wakelock 正则过滤

* 计划
  * wakelock 唤醒锁计时
  * alarm 记录
  * alarm 限制

## 兼容性

* NoWakeLock 适用于 Android N 及以后版本.Android N 以前版本请使用 [Amplify](https://github.com/mh0rst/Amplify).
* NoWakeLock 仅在 Android Q EdXposed 框架下进行了测试.

## 编译

* ~~[master](https://github.com/Jasper-1024/NoWakeLock): 稳定版(还未就绪)~~

* [dev](https://github.com/Jasper-1024/NoWakeLock/tree/dev): 测试版,可能并不稳定.

* [init](https://github.com/Jasper-1024/NoWakeLock/tree/init): 预览版

## 安装

* [Github releases](https://github.com/Jasper-1024/NoWakeLock/releases), 包括 alpha/bate 版.
* [酷安](https://www.coolapk.com/apk/260112), 较为稳定的 alpha 版本.
* ~~[Play](none)~~,等待转为 bate 版后上线.

## 支持

* 仅支持在上述渠道下载的 NoWakeLock
* 请提交 [ISSUE](https://github.com/Jasper-1024/NoWakeLock/issues)

## 贡献者

* [Jasper Hale](https://github.com/Jasper-1024)

## 许可证

* NoWakeLock 以 GNU GPLv3 ([许可](https://github.com/Jasper-1024/NoWakeLock/blob/master/LICENSE)) 发布.

## 致谢

* NoWakelock 编写中参考了需多开源应用.这里表示致谢.

* [Amplify](https://github.com/mh0rst/Amplify).
* [XPrivacyLua](https://github.com/M66B/XPrivacyLua)
* [GravityBox](https://github.com/GravityBox/GravityBox)
