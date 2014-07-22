wsgen 主要用来生成合适的JAX-WS
    -cp 定义classpath
    -r  生成bean的wsdl文件的存放目录
    -s  生成发布web service的源代码文件存放目录
    -d  生成发布web service的编译过的文件存放目录
例子：wsgen -cp ./bin -r ./wsdl -s ./src -d ./bin -wsdl 某类

wsimport 主要根据服务端发布的wsdl文件生成客户端存根及框架，负责与web service服务器通信并将其封装成实例，客户端可以直接使用，就像使用本地实例一样
    -d 生成客户端执行类的class文件存放目录
    -s 生成客户端执行类的源文件存放目录
    -p 定义生成类的包名
例子： wsimport -d ./bin -s ./src -p com.test.webservice.client http://****:port/?wsdl