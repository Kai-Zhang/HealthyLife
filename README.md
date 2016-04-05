# HealthyLife
Your health companion in your Android phone.
##核心功能
通过分析用户手机使用情况与传感器信息，提供以下指数反映用户健康状况

- 噪音指数
- 睡眠指数
- 手机沉迷指数
- 运动指数
- 心情指数

##约定
1. 每次提交需通过编译
2. 如果与别人的代码出现冲突，应当交流清楚再合并

##一些接口
###1.应用使用情况
> 对API Level小于21与大于等于21采取了不同的策略。结果可能有所偏差。

`InfoManager.getAppUsageInfo(int day)`返回一个元素类型为`AppUsageModel`的`ArrayList`
传入的参数代表第几天，day=0表示今天，day=-1表示昨天，以此类推。

`AppUsageModel`的定义如下：

	public class AppUsageModel {
	    public String appName;//like "美团"
	    public String packageName;//like "com.sankuai.meituan"
	    public String appType;//like "购物"
	    public long foregroundTime;//milliseconds
	    public AppUsageModel(String appName,String packageName,String appType,long foregroundTime){
	        this.appName=appName;
	        this.packageName=packageName;
	        this.foregroundTime=foregroundTime;
	        this.appType=appType;
	    }
	}
###2.环境噪音
`InfoManager.getNoiseInfo(long startTime,long endTime)`返回一个元素类型为`NoiseModel`的`ArrayList`

`NoiseModel`的定义如下：

	public class NoiseModel {
	    public long time;
	    public double volume;
	    public NoiseModel(long time,double volume){
	        this.time=time;
	        this.volume=volume;
	    }
	}