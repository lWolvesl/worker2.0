## 移动学工自动打卡
-   简介：河南师范大学移动学工打卡

    >   实现自动打卡
    >
    >   可以自定义打卡区间
    >
    >   可自定义邮件


## ## 更新日志
<font color="#d2691e">2022-03-05 打卡时间已更新为每日11：30开始</font>

<font color="gree">2022-02-21 支持到2022-02-21的移动学工打卡页面</font>

<font color="red">2022-01-28 现已支持部分学院打卡后自动截图！</font>


## 使用教程-源码编码启动

### 一、数据库

使用数据库文件将数据库结构导入至mysql中

将数据库文件中的内容填写完整，具体参考以下简介和(.sql)文件中的描述

注意修改数据库地址及账户密码（MySql）

![截屏2022-01-12 11.37.35](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2022-01-12%2011.37.35.png)

-   所有`数据库`为必填项
    -   个人cookiee栏，数据由浏览器获取，本例使用chrome浏览器
        -   首先打开微信中的移动学工，进入并复制链接拷贝至chrome
        -   ![1031638105610_.pic](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/1031638105610_.pic.jpg)
        -   ![截屏2021-11-28 21.23.27](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.23.27.png)
        -   按照页面登陆
        -   ![截屏2021-11-28 21.23.43](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.23.43.png)
        -   进入每日健康打卡
        -   ![截屏2021-11-28 21.25.05](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.25.05.png)
        -   顶栏的地址即为个人打卡页面地址（url）
        -   点击不安全
        -   ![截屏2021-11-28 21.26.01](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.26.01.png)
        -   再点击Cookie，如果显示只有一个，刷新页面即可
        -   ![截屏2021-12-02 19.49.38](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-12-02%2019.49.38.png)
        -   本软件使用的cookie为remeber_student开头的
        -   将**名称**和**内容**填入cookieValue即可
    -   邮箱
        -   发送打卡的邮箱需要开启 IMAP/SMTP 和 POP3/SMTP服务
        -   此处以163邮箱演示,进入上边栏的设置即可看到，注意：开启IMAP后的授权码只会出现一次
        -   ![截屏2021-11-28 21.30.13](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.30.13.png)
        -   配置文件中的mailServer为发送邮件的邮箱
        -   mailPassword为得到的IMAP授权码

### 二、Maven打包

![截屏2022-01-12 11.40.44](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2022-01-12%2011.40.44.png)

-   完整jar包为target下的 `worker2-0.0.1-SNAPSHOT.jar`

### 三、部署

-   将此jar包复制到需要运行到机器上，注意此程序需要长时运行

#### 1、前台运行，如windows

![截屏2021-11-28 21.56.03](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2021.56.03.png)

#### 2、后台运行，如linux、mac及云服务器

-   此处以云服务器为例子
-   首先将文件传入服务器或运行的位置
-   使用`nohup`进行后台运行

- `nohup java -jar worker2-0.0.1-SNAPSHOT.jar > ydxg.out &`

-   可以使用 cat查看输出文件
-   停止进程
    -   ![截屏2021-11-28 22.02.14](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2021-11-28%2022.02.14.png)
    -   使用`kill -9 24428`即可关闭此程序，24428为我的进程号，注意更改

#### 3、启动、停止

- 输入`localhost:8989/start?key = 12345679`启动服务

![截屏2022-01-12 11.43.40](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2022-01-12%2011.43.40.png)

- 输入`http://localhost:8989/stop`?key = 12345679停止服务
- ![截屏2022-01-12 11.44.41](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2022-01-12%2011.44.41.png)

- 服务启动成功后将在6点-11点自动完成数据库中所有人的打卡任务

### 截图功能

- 更新于 2022-01-28

注：本此更新截图功能介绍为linux系统

##### 1、首先于linux系统中加入中文字体防止截图乱码

```
# 创建中文字体目录
mkdir -p /usr/share/fonts/chinese/

# 将字体文件拷贝到中文字体目录中
cp songti.ttf /usr/share/fonts/chinese/

cd /usr/share/fonts/chinese/

# 为刚加入的字体设置缓存使之有效
fc-cache -fv

# 查看系统中的字体
fc-list
```
2、apt install chromium-browser
安装linux版chrome

使用cp命令将附带的chromedriver放入/usr/bin中

3、参考数据库文件填写完整

4、将数据库表中所需打卡用户的sc设置为1（即启用）

5、设置(src/main/java/com/li/worker2/service/Impl/sc.java)中的文件保存路径path

![截屏2022-01-28 12.15.19](https://typroa-wolves.oss-cn-hangzhou.aliyuncs.com/img-li/%E6%88%AA%E5%B1%8F2022-01-28%2012.15.19.png)

再启用服务时即可自动截图并打上学号姓名的水印