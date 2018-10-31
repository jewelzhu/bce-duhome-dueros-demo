这是一个集成了dueros和duhome的demo项目，实现了对接dueros和duhome以实现使用语音控制设备的功能，demo中仅以灯为示例，可以自行扩展到其他设备。

本项目的核心功能包括 
----
1) 一个oauth server，基于spring security oauth2框架。对接dueros需要用户提供一个oauth server向其进行用户资源授权，之后dueros遵循oauth协议向资源端（即后面的skill bot)发送请求访问资源。
2）一个dueros skill bot, 可以接受发现设备/打开灯/关闭灯的dueros反控指令，同时根据dueros请求体中所带的access_token对用户身份、所访问资源进行鉴权。
3) 一个对接duhome的app server，将dueros skill bot接受到的指令通过duhome下发给设备端

要运行此demo的前提条件
----
1）一个已申购了https证书的自有域名 （dueros强制要求）
2）在dueros.baidu.com上创建了一个智能家居技能
3）已获得duhome的试用权限（请联系bibo01@baidu.com)，并在duhome上创建并连接了至少一个设备
4）公网java8运行环境（例如一台绑定了公网IP的虚拟机）
5）一个可被java8运行环境访问的mysql server

这些条件都满足以后，可根据下面的guide来运行本demo，进而将本demo中的代码集成到你自己的系统中。

Step by Step Guide:
 to be continued