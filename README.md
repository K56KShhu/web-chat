# 咱部落

## 介绍
![login](https://github.com/zkyyo/web-chat/blob/master/image/login.png?raw=true)
![index](https://github.com/zkyyo/web-chat/blob/master/image/index.png?raw=true)
![chat](https://github.com/zkyyo/web-chat/blob/master/image/chat1.png?raw=true)
这是一个基于部落格的网站，我将其命名为“咱部落”。用户登陆后可以加入感兴趣的公开讨论区并自由发表言论，同时还可以在相应的讨论区中分享图片和文件。用户加入小组后，可以加入特定的授权讨论区，并使用与公开讨论区相同的功能。

## 功能实现

### 普通用户：
- 搜索讨论区并加入讨论区，窥屏别人聊天。新的聊天信息可以通过手动刷新来显示。
- 在讨论区中可以自由发表言论，也可以在发言中附带图片
- 在讨论区的图片分享区可以自由上传图片
- 在讨论区的文件分享区可以自由上传文件
- 用户可以修改自己的个人信息
### 网站管理员：
- 审批普通用户注册
- 新增、修改或删除讨论区	
- 新增、修改或删除讨论区里的图片或文件
###　举报机制：
当用户发表不当言论或上传不当文件时候，用户可以通过举报机制通知管理员，让管理员审核，必要时删除该用户的不当操作，并禁止该用户进行一切操作。
### 权限模块：
 咱部落权限分为两种，分别是管理权限和讨论区访问权限。管理权限有root（有且只有一个），admin和普通用户，普通用户可以参与相应讨论区，admin用户可以管理讨论区，用户等等，而root用户则可以任命或撤销admin。讨论区访问权限分为公开和授权，授权讨论区通过授权小组来管理用户，只有位于授权小组的用户才可以加入授权讨论区。（admin，root可以自由访问所有讨论区）
### 定时去除讨论区信息：
定时删除讨论区过期信息、图片和文件来维持服务器不会承担太多数据
### 数据分页：
数据过多的时候全部显示到页面上面会显得非常臃肿，这个时候需要采取分页的手段分段抽取后台的数据。
### 安全机制：
对数据库中的密码进行加密

## 使用方法
1. 导入sql. 有两种方式:
 - 其一是导入create.sql, 将导入 root,admin,user账号数据, 不包含其他任何聊天数据和文件数据.(DATABASE的创建和删除代码已被注释, 请检查本机中是否包含同名的数据库, 谨防被误删)
 - 其二是导入test目录下的dump.sql, 将导入测试文档中除文件以外的所有数据(可能会出现图片/文件丢失的情况)
2. 配置参数
 - web.xml中的上下文参数DATABASE需修改为本项目数据库, 
 - META-INF目录下context.xml中, Resource标签下的name和url都修改为本项目数据库, 设置本数据库账号及其密码
3. 配置Tomcat, 导入依赖包
4. 运行, 这里提供三种权限的账号和密码

权限 | 账号 | 密码 
----- | ----- | -----
root | root | root
admin | admin | admin
普通用户 | user | user

5. 其他的项目相关的配置都可以在web.xml中进行设置, 可以参考描述说明更改默认配置

## 开发环境
- `操作系统`：Ubuntu16.04
- `IDE`: IntelliJIDEA ULTIMATE 2016.3
- `Container`: Tomcat8.5.12
- `JDK`: java version "1.7.0_95"
OpenJDK Runtime Environment (IcedTea 2.6.4) (7u95-2.6.4-3)
OpenJDK 64-Bit Server VM (build 24.95-b01, mixed mode)
- `编码格式`: UTF-8
- `MySQL`: mysql  Ver 14.14 Distrib 5.7.18, for Linux (x86_64) using  EditLine wrapper

## 依赖
- commons-fileupload-1.3.2.jar
- commons-io-2.5.jar
- jsp-api.jar
- servlet-api.jar
- mysql-connector-java-5.1.41-bin.jar
- taglibs-standard-impl-1.2.5.jar
- taglibs-standard-spec-1.2.5.jar

## 许可
This software is licensed under MIT license. © 2017 zkyyo
