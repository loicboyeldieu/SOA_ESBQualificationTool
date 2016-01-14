
package com.esbqualificationtool.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QualificationToolService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QualificationToolService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="byteSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="msProcessingTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualificationToolService", propOrder = {
    "byteSize",
    "msProcessingTime"
})
public class QualificationToolService {

    protected int byteSize;
    protected int msProcessingTime;

    /**
     * Gets the value of the byteSize property.
     * 
     */
    public int getByteSize() {
        return byteSize;
    }

    /**
     * Sets the value of the byteSize property.
     * 
     */
    public void setByteSize(int value) {
        this.byteSize = value;
    }

    /**
     * Gets the value of the msProcessingTime property.
     * 
     */
    public int getMsProcessingTime() {
        return msProcessingTime;
    }

    /**
     * Sets the value of the msProcessingTime property.
     * 
     */
    public void setMsProcessingTime(int value) {
        this.msProcessingTime = value;
    }

}
