# NoWakeLock

* 一个控制 Android 唤醒锁的应用,可以运行在 Android N 以后版本.

* **严重警告**: 这款应用依旧处于 Alpha 测试阶段.因为软件给您设备带来的损伤,本人概不负责.

## 关于

* 随着 Android 版本更新, doze 模式已经足够压制住应用后台耗电,但高级用户依旧希望可以自行控制唤醒.
* Android N 以后随着系统安全的升级 XSharedPreferences 基本被废, Amplify 的更新随之停滞.
* NoWakeLock 可以算是 [Amplify](https://github.com/mh0rst/Amplify) 的复刻,期望能在 Android N 以上版本带来同样的功能.

## 功能

* 已完成
  * wakelock 唤醒锁计数.
  * wakelock 限制
  * 亮/暗色模式适配

* 开发中
  * wakelock 唤醒锁计时
  * alarm 记录
  * alarm 限制
  * 分应用展示

## 安装

* NoWakeLock 目前只在 Github releases 和 酷安 发布,其他市场均非本人发布.
* BUG 反馈,新功能讨论,请提交对应 issue .

## 编译

* ~~[master](https://github.com/Jasper-1024/NoWakeLock): 稳定版(还未就绪)~~

* [dev](https://github.com/Jasper-1024/NoWakeLock/tree/dev): 测试版,可能并不稳定.

* [init](https://github.com/Jasper-1024/NoWakeLock/tree/init): 预览版

## BUG

* 只能记录 wakelock 无法限制 wakelock
  * 参照 [GravityBox-q](https://github.com/GravityBox/GravityBox/tree/q) IPC 使用 XSharedPreferences ,受限于 SELinux 必须将 SELinux 设置为宽松,才可以正常读取.
  * 后面计划将使用 XSharedPreferences 部分也替换为 ContentProvider 实现,但是实现相同效果较为困难.

* Setting 界面的 ToolBar menu 无法隐藏.
  * 已经做了隐藏设置,但是 UI 无法生效,可能和 PreferenceFragmentCompat 实现有关,待研究.

## 贡献者

* [Jasper Hale](https://github.com/Jasper-1024)

## 许可证

* NoWakeLock 以 GNU GPLv3 ([许可](https://github.com/Jasper-1024/NoWakeLock/blob/master/LICENSE)) 发布.
