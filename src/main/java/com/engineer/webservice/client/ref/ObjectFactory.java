
package com.engineer.webservice.client.ref;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.engineer.webservice.client.ref package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloException_QNAME = new QName("http://www.morly.com/wstest", "HelloException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.engineer.webservice.client.ref
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HelloException }
     * 
     */
    public HelloException createHelloException() {
        return new HelloException();
    }

    /**
     * Create an instance of {@link PersonArray }
     * 
     */
    public PersonArray createPersonArray() {
        return new PersonArray();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.morly.com/wstest", name = "HelloException")
    public JAXBElement<HelloException> createHelloException(HelloException value) {
        return new JAXBElement<HelloException>(_HelloException_QNAME, HelloException.class, null, value);
    }

}
