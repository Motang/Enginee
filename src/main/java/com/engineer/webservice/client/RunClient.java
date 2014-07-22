package com.engineer.webservice.client;

import com.engineer.webservice.client.ref.Example;
import com.engineer.webservice.client.ref.Example_Service;

public class RunClient {
	/**  
     * @param args  
     */  
    public static void main(String[] args) {   
        //初始化服务框架类   
        Example_Service service = new Example_Service();   
        //或者本地服务借口的实例   
        Example server = (Example) service.getExamplePort();   
        try {   
            //调用web service的toSayHello方法   
            System.out.println("输入toSayHello的返回值——" + server.toSayHello("阿土"));            
//             Person person = new Person();   
//             person.setName("阿土");   
//             person.setAge(25);   
//             //调用web service的sayHello方法   
//             server.sayHello(person, "机器人");   
//                
//             person = new Person();   
//             person.setName("aten");   
//             person.setAge(30);   
//             //调用web service的sayHello方法   
//             PersonArray list = server.sayHello(person, "机器人");   
//            //输出返回值   
//             System.out.println("/n以下输入sayHello的返回值——");   
//            for (Person p : list.getItem()) {   
//                System.out.println(p.getName() + ":" + p.getAge());   
//            }              
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}
