这是一个集成了dueros和duhome的demo项目，实现了对接dueros和duhome以实现使用语音控制设备的功能。你可以运行本demo来走通整个流程，也可以把本demo当成一个sdk集成到自己的项目中。

# 核心功能
1) 一个oauth server，基于spring security oauth2框架，使用mysql做持久层。这个oauth server负责向dueros进行用户资源授权，之后dueros遵循oauth协议向后面的skill bot发送请求访问资源。

2) 一个dueros skill bot, 可以接受发现设备/打开灯/关闭灯的dueros反控指令，同时根据dueros请求体中所带的access_token对用户身份、所访问资源进行鉴权。如果需要支持更丰富的指令，在此基础上进一步增加各种命令模型即可。

3) 一个对接duhome的app server，向duhome下发指令从而控制设备

# 运行demo

## demo场景描述
本demo的使用者是一个假想的智能家居服务商，他有两个用户，一个是bob，一个是lily，bob有一个智能设备，叫做小夜灯，而lily则没有智能设备。

bob对着dueros说发现设备，dueros则为他发现了小夜灯，然后bob可以说打开/关闭小夜灯来操控小夜灯的开关。

而lily对着dueros说发现设备，dueros则回答我没有发现智能设备。

## 运行demo的前提条件
1) 一个已申购了https证书的自有域名 （dueros要求）

2) 在dueros.baidu.com上创建了一个智能家居技能

3) 已获得duhome的试用权限（请联系bibo01@baidu.com)，并在duhome上创建了一个设备，设备模型包含属性power, power值为on对应灯打开，power值为off对应灯关闭，设备已经连上了duhome且duhome可以通过更新power的值来控制设备的开灯关灯。

4) 公网java8运行环境及mysql server

## Step by Step Run The Demo

### 1. 生成springboot要求格式的证书密钥文件
    openssl pkcs12 -export -clcerts -in my.domain.name.cer -inkey my.domain.name.key -out my_key_file
回车后会要求你输入密码，这个密码一会儿要填写到配置里的server.ssl.key-store-password中，然后将生成的my_key_file文件放到resources目录下
    
### 2. 创建数据库表
向mysql数据库中导入oauth-config/src/main/resources/schema.sql

    mysql -h YourMysqlHost -P YourMysqlPort -u YourUserName -p YourPassword YourDbname < schema.sql

### 3. 填写src/main/resources/application.properties

    # 填写你的数据库信息
    spring.datasource.url=jdbc:mysql://dbhost:port/dbname
    spring.datasource.username=dbuser
    spring.datasource.password=dbpass
    spring.datasource.driverclassname=com.mysql.jdbc.Driver
    
    server.ssl.key-store-type=PKCS12
    # 填写证书密钥文件
    server.ssl.key-store=classpath:your_key_file
    # 填写证书密钥文件的密码
    server.ssl.key-store-password:the password used when encrpting your key
    
    # 填写百度云ak
    bce.ak=your bce ak  
    # 填写百度云sk 
    bce.sk=your bce sk   
    # 填写你要测试的设备在duhome上的唯一标示puid
    my.test.puid=your duhome puid
    
    # run基本的demo
    demo.simpliest:true

### 4. 编译并启动服务

    cd bce-duhome-dueros-demo
    # 编译可执行jar包
    bash gradlew build
    # 来到可执行jar包所在目录
    cd subprojects/dueros-duhome-demo/build/libs
    # 启动服务
    nohup java -jar -Dserver.port=443 -Dlogging.path=logs duhome-dueros-demo-0.1.0.jar > /dev/null 2>&1 &
现在，一个https webservice服务已启动

### 5. 将你的域名解析到运行了该demo的服务器地址

这样就可以通过"https://域名"访问到这个demo服务了
        
### 6. 在dueros.baidu.com上填写服务地址

现在在dueros的技能控制台上配置服务，假定你的域名为your.domain.name，则

你的授权地址为https://my.domain.name/oauth/authorize，

Token地址为https://my.domain.name/oauth/token，请求方式为POST，

设备云Webservice为https://my.domain.name/api/bot，

你还要为dueros设置一对Client_Id和ClientSecret，假设你填写的是Client_Id为dueros_client, Client_secret为dueros_client_secret，现在你需要将这对client_id/client_secret以及页面上的以https://xiaodu.baidu.com/saiya/auth/开头的回调地址插入mysql中：

    INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)  
        VALUES ('dueros_client', null, 'dueros_client_secret', 'read,write', 'authorization_code,refresh_token', '<从页面上获得的回调地址>', 'ROLE_USER', 1800, 86400, null, false);  

点击保存。

### 7. 可以测试用语音控制设备了

如果你没有dueros真机，那么可以使用dueros网页版的模拟测试。

首先你可以以bob的身份对dueros进行授权，在"配置服务"页面点击"授权"，跳转到demo的登录页面，输入账户名bob密码123，对read/write权限申请点击approve，然后浏览器将会自动跳转回dueros的授权成功页面。

现在来到模拟测试页面，试试说"发现设备"，那么dueros会为你发现小夜灯，然后再说"关闭小夜灯"或者"打开小夜灯"则你会看到你的设备灯被关闭或打开。

然后你清除下浏览器缓存，以lily的身份授权再试试吧，lily的密码也是123。

# 把本项目用作集成dueros/duhome的sdk

项目里包含4个submodule，分别是dueros-bot, duhome-sdk, oauth-config和dueros-duhome-demo。前三个模块是sdk，你可以按需全部使用或只使用其中的部分，第四个模块是使用了前三个模块的示例demo。

### dueros-bot

支持智能家居设备的dueros响应操作，例如开灯/关灯等。里面包含了一个默认的BotController会帮你分发处理来自dueros的请求。

要使用dueros-bot, 你需要按需实现com.baidubce.iot.dueros.bot.executor下的bean，例如你的设备支持TurnOn/TurnOff操作，那么你就需要提供实现了TurnOnExecutor/TurnOffExecutor的两个bean。

同时你需要提供一个实现了UserApplianceManager的bean用来管理哪些用户对应着哪些设备。具体可参考dueros-duhome-demo里的实现。

### duhome-sdk

一个duhome的http client sdk，你只需要提供百度云aksk即设备的puid就可以通过duhome-sdk向设备发送指令。

### oauth-config

一个基于spring security oauth2框架实现的oauth server，提供了对dueros非标oauth请求的兼容适配，使用mysql存储oauth token等信息，如果你的项目是基于spring security的，可以较为方便的启用。

使用oauth-config模块，你需要在mysql中创建定义在oauth-config/src/main/resources/schema.sql中的相关表，然后在application.properties里配置dueros.bot.url.pattern参数, 程序将为这里定义的url开启oauth鉴权和dueros非标oauth适配，如果你使用的就是dueros-bot那么使用默认值/api/bot即可。

### dueros-duhome-demo

顾名思义就是利用了上述三个模块实现的一个完整的可执行webservice，目前里面有两个版本，一个通过demo.simpliest配置开启，使用的是本文中描述的最简单的demo，只支持一个灯的开关；另一个是ledvance智能灯长青，通过demo.ledvance开启，需要依赖redis，支持灯的调亮调色调温灯功能。