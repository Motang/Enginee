package com.engineer.webservice;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="Example", targetNamespace="http://www.morly.com/wstest", serviceName="Example")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class Example {

	private ArrayList<Person> persons = new ArrayList<Person>();;   
    /**  
     *   
     * 返回一个字符串  
     * @param userName  
     * @return  
     */  
    @WebMethod(operationName="toSayHello",action="toSayHello",exclude=false)   
    @WebResult(name="returnWord")//自定义该方法返回值在WSDL中相关的描述   
    public String toSayHello(@WebParam(name="userName")String userName) {   
        return "Hello:" + userName;   
    }   
  
    /**  
     * web services 方法的返回值与参数的类型不能为接口  
     * @param person  
     * @return  
     * @throws HelloException  
     */  
    @WebMethod(operationName="sayHello", action="sayHello")   
    @WebResult(partName="personList")   
    public Person[] sayHello(
    		@WebParam(partName="person", mode=Mode.IN)Person person,    
    		@WebParam(partName="userName", mode=Mode.IN)String userName) throws HelloException {   
        if (person == null || person.getName() == null) {   
            throw new HelloException("说hello出错，对像为空。。");   
        }   
        System.out.println(person.getName() + " 对 " + userName + " 说：Hello，我今年" + person.getAge() + "岁");   
        persons.add(person);   
        return persons.toArray(new Person[0]);   
    }   
}
