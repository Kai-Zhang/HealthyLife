HealthyLife
====

<img src="https://cloud.githubusercontent.com/assets/10338754/16071720/7fe01470-330f-11e6-92b2-74eb170c6208.png">

本项目是一个基于安卓手机数据来预测用户情绪的工具，目前仓库中版本是用于收集数据的Demo。  
这一项目用于我的本科毕业设计。

## 使用说明
* 安装之后首次开启时，在Android 5.1以上的版本需要用户通过权限要求。
* 应用每天在早晚10点时通过通知提醒用户反馈自己在这一段时间内的情绪状态，点击通知便可从六种情绪中选择最接近自己情绪状态的选项。
* 由于某些原因应用可能在打开主界面的时候弹出多次通知，这时只需提交一次情绪，忽略剩余通知即可。简单来说即每天仅需要提交一早一晚两次情绪。
* 当手机重启之后，需要重新运行一下应用来保证数据收集的正常进行。
* 应用其他时间运行在后台，不会产生打扰。请不要使用各种手机管理软件改变应用的运行环境，也不要再次从设置中调整软件的权限。
* 收集阶段持续若干天，结束后，在主界面点击选项选择准备数据库，收集数据的数据库会被复制到sd存储卡根目录下。然后可以提取出发送给我。
* 从[这里](https://github.com/Kai-Zhang/HealthyLife/releases/download/v1.1.1/app-release.apk)下载安装包安装在手机上。
* 

## 声明
* 本应用目前收集：
    * 环境声音数据，但并不保存原始数据，只收集声音的噪声值等特征
    * 加速度传感器信息
    * 光照传感器信息
    * GPS的原始信息，包括经度纬度海拔高度等信息
    * 周边WiFi的SSID,BSSID,rssi信息
    * 陀螺仪数据(角速度)
    * 电子罗盘数据(磁场)
    * 通讯录，不保留原始数据，仅记录联系人姓名与号码的hashcode，[查看源码](https://github.com/Kai-Zhang/HealthyLife/blob/ekman/app/src/main/java/org/graduation/collector/ContactCollector.java)
    * 通话记录，不保留原始数据，仅记录通话号码的hashcode，[查看源码](https://github.com/Kai-Zhang/HealthyLife/blob/ekman/app/src/main/java/org/graduation/collector/ContactCollector.java)
    * 短信，不保留原始数据，仅记录对方号码的hashcode，[查看源码](https://github.com/Kai-Zhang/HealthyLife/blob/ekman/app/src/main/java/org/graduation/collector/ContactCollector.java)
* 应用不会记录敏感信息数据，数据仅用于跟随其后的情绪模型训练的研究，不用作他用。研究结束后即进行销毁。
* 应用运行损耗一定电量，虽然不大，但也仍需要注意。

## 致谢
感谢您对我支持，感谢每一个协助我的人。
