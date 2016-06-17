HealthyLife
====


本项目是一个基于安卓手机数据来预测用户情绪的工具，目前仓库中版本是用于收集数据的Demo。  

## 参与APP测试说明
<img width="200" src="https://cloud.githubusercontent.com/assets/10338754/16143982/d0fb26d0-34a2-11e6-85e3-215a99795ec9.png">
* 志愿者下载并安装APP：
 从[这里](https://github.com/Kai-Zhang/HealthyLife/releases/download/v1.1.2/app-release.apk)下载安装包安装在手机上。
 或者扫码下载。
* 安装之后首次开启时，在Android 5.1以上的版本需要用户通过权限要求。
* 应用每天在10：00，16：00，22：00时通过通知提醒用户反馈自己在这一段时间内的情绪状态，点击通知便可从六种情绪中选择最接近自己情绪状态的选项。请用户注意：
 * (a).情绪状态可多选，每次可选择一种或多种情绪，请尽可能包含从上次提交到目前提交所经历的所有情绪。
 * (b).志愿者请每天提交3次情绪状态，实验连续进行20天,共60次。
 * (c).部分手机会无法显示通知提醒，若有上述情况，请用户自行决定上传时间(每两次上传间隔时间至少5小时)。
* 当手机重启之后，需要重新运行一下应用来保证数据收集的正常进行。
* 应用其他时间运行在后台，不会产生打扰。请不要使用各种手机管理软件改变应用的运行环境，也不要再次从设置中调整软件的权限。
* 实验结束之后，根据APP的提示可领取纪念品一份。可选纪念品如下：
* (1) 金士顿16GU盘
* (2) 360随身WIFI
* (3) 飞利浦4孔位节能防火插座板
* (4) 南孚5号电池12粒装
* (5) 金士顿16G TF(Micro SD)高速存储卡
* 
* 联系人：张啸  QQ：935511561，注明：“参与APP测试”； 邮箱：tobexiao1@dislab.nju.edu.cn


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
