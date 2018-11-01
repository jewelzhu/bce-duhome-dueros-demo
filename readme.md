这是一个集成了dueros和duhome的demo项目，实现了对接dueros和duhome以实现使用语音控制设备的功能。你可以运行本demo来走通整个流程，进而将本demo中的代码按需集成到你自己的系统中。

# 核心功能
1) 一个oauth server，基于spring security oauth2框架，使用mysql做持久层。这个oauth server负责向dueros进行用户资源授权，之后dueros遵循oauth协议向后面的skill bot发送请求访问资源。

2) 一个dueros skill bot, 可以接受发现设备/打开灯/关闭灯的dueros反控指令，同时根据dueros请求体中所带的access_token对用户身份、所访问资源进行鉴权。如果需要支持更丰富的指令，在此基础上进一步增加各种命令模型即可。

3) 一个对接duhome的app server，向duhome下发指令从而控制设备

# demo场景描述
本demo的使用者是一个假想的智能家居服务商，他有两个用户，一个是bob，一个是lily，bob有一个智能设备，叫做小夜灯，而lily则没有智能设备。

bob对着dueros说发现设备，dueros则为他发现了小夜灯，然后bob可以说打开/关闭小夜灯来操控小夜灯的开关。

而lily对着dueros说发现设备，dueros则回答我没有发现智能设备。

# 运行demo的前提条件
1) 一个已申购了https证书的自有域名 （dueros要求）

2) 在dueros.baidu.com上创建了一个智能家居技能

3) 已获得duhome的试用权限（请联系bibo01@baidu.com)，并在duhome上创建了一个设备，设备模型包含属性power, power值为on对应灯打开，power值为off对应灯关闭，设备已经连上了duhome且duhome可以通过更新power的值来控制设备的开灯关灯。

4) 公网java8运行环境及mysql server

## Step by Step Guide:

### 1. 生成springboot要求格式的证书密钥文件
    openssl pkcs12 -export -clcerts -in my.domain.name.cer -inkey my.domain.name.key -out my_key_file
回车后会要求你输入密码，这个密码一会儿要填写到配置里的server.ssl.key-store-password中，然后将生成的my_key_file文件放到resources目录下
    
### 2. 创建数据库表
向mysql数据库中导入src/resources/schema.sql

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

### 4. 编译并启动服务

    cd bce-duhome-dueros-demo
    # 编译可执行jar包
    bash gradlew build
    # 启动服务
    java -jar -Dserver.port=443 duhome-dueros-demo-0.1.0.jar
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