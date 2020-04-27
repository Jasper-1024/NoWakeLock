# 常见问题

* ~~只能记录 wakelock 无法限制 wakelock~~(1.0.4alpha 以后版本已解决)
  * 参照 [GravityBox-q](https://github.com/GravityBox/GravityBox/tree/q) IPC 使用 XSharedPreferences ,受限于 SELinux 必须将 SELinux 设置为宽松,才可以正常读取.
  * 后面计划将使用 XSharedPreferences 部分也替换为 ContentProvider 实现,但是实现相同效果较为困难.
  * 设置 SElinux(需要root权限),且重启会失效.
    * `adb shell` 进入终端,或者本机使用终端进入亦可.
    * `su` 获取 root 权限.
    * `getenforce` 可以获取 SElinux 状态,我们需要将状态设置为 permissive 模式.
    * `setenforce 0` 设置为 permissive 模式.
    * `setenforce 1` 设置为 enforce 模式.

* 记录信息一直为 0 .原因暂时未知,本地亦未复现.

* 新功能需求请提交 issue ,否则暂时会按照我自行制定的计划表进行.
