//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.15 at 02:59:36 PM CET 
//


package com.esbqualificationtool.jaxbhandler;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consumerType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="consumerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="consumer1"/>
 *     &lt;enumeration value="consumer2"/>
 *     &lt;enumeration value="consumer3"/>
 *     &lt;enumeration value="consumer4"/>
 *     &lt;enumeration value="consumer5"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "consumerType")
@XmlEnum
public enum ConsumerType {

    @XmlEnumValue("consumer1")
    CONSUMER_1("consumer1"),
    @XmlEnumValue("consumer2")
    CONSUMER_2("consumer2"),
    @XmlEnumValue("consumer3")
    CONSUMER_3("consumer3"),
    @XmlEnumValue("consumer4")
    CONSUMER_4("consumer4"),
    @XmlEnumValue("consumer5")
    CONSUMER_5("consumer5");
    private final String value;

    ConsumerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConsumerType fromValue(String v) {
        for (ConsumerType c: ConsumerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
