//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.15 at 02:59:36 PM CET 
//


package com.esbqualificationtool.jaxbhandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flow" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="consumer" type="{}consumerType"/>
 *                   &lt;element name="startTimeInMs" type="{}startTimeInMsType"/>
 *                   &lt;element name="totalExecTimeInSec" type="{}totalExecTimeInSecType"/>
 *                   &lt;element name="frequencyInSec" type="{}frequencyInSecType"/>
 *                   &lt;element name="delayBetweenEachRequestInMs" type="{}delayBetweenEachRequestInMsType"/>
 *                   &lt;element name="request" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="producer" type="{}producerType"/>
 *                             &lt;element name="messageSize" type="{}messageSizeType"/>
 *                             &lt;element name="processingTimeInMs" type="{}processingTimeInMsType"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "flow"
})
@XmlRootElement(name = "scenario")
public class Scenario {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected List<Scenario.Flow> flow;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the flow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Scenario.Flow }
     * 
     * 
     */
    public List<Scenario.Flow> getFlow() {
        if (flow == null) {
            flow = new ArrayList<Scenario.Flow>();
        }
        return this.flow;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="consumer" type="{}consumerType"/>
     *         &lt;element name="startTimeInMs" type="{}startTimeInMsType"/>
     *         &lt;element name="totalExecTimeInSec" type="{}totalExecTimeInSecType"/>
     *         &lt;element name="frequencyInSec" type="{}frequencyInSecType"/>
     *         &lt;element name="delayBetweenEachRequestInMs" type="{}delayBetweenEachRequestInMsType"/>
     *         &lt;element name="request" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="producer" type="{}producerType"/>
     *                   &lt;element name="messageSize" type="{}messageSizeType"/>
     *                   &lt;element name="processingTimeInMs" type="{}processingTimeInMsType"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "consumer",
        "startTimeInMs",
        "totalExecTimeInSec",
        "frequencyInSec",
        "delayBetweenEachRequestInMs",
        "request"
    })
    public static class Flow {

        @XmlElement(required = true)
        protected ConsumerType consumer;
        protected int startTimeInMs;
        protected int totalExecTimeInSec;
        protected int frequencyInSec;
        protected int delayBetweenEachRequestInMs;
        @XmlElement(required = true)
        protected List<Scenario.Flow.Request> request;
        @XmlAttribute(required = true)
        protected BigInteger id;

        /**
         * Gets the value of the consumer property.
         * 
         * @return
         *     possible object is
         *     {@link ConsumerType }
         *     
         */
        public ConsumerType getConsumer() {
            return consumer;
        }

        /**
         * Sets the value of the consumer property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConsumerType }
         *     
         */
        public void setConsumer(ConsumerType value) {
            this.consumer = value;
        }

        /**
         * Gets the value of the startTimeInMs property.
         * 
         */
        public int getStartTimeInMs() {
            return startTimeInMs;
        }

        /**
         * Sets the value of the startTimeInMs property.
         * 
         */
        public void setStartTimeInMs(int value) {
            this.startTimeInMs = value;
        }

        /**
         * Gets the value of the totalExecTimeInSec property.
         * 
         */
        public int getTotalExecTimeInSec() {
            return totalExecTimeInSec;
        }

        /**
         * Sets the value of the totalExecTimeInSec property.
         * 
         */
        public void setTotalExecTimeInSec(int value) {
            this.totalExecTimeInSec = value;
        }

        /**
         * Gets the value of the frequencyInSec property.
         * 
         */
        public int getFrequencyInSec() {
            return frequencyInSec;
        }

        /**
         * Sets the value of the frequencyInSec property.
         * 
         */
        public void setFrequencyInSec(int value) {
            this.frequencyInSec = value;
        }

        /**
         * Gets the value of the delayBetweenEachRequestInMs property.
         * 
         */
        public int getDelayBetweenEachRequestInMs() {
            return delayBetweenEachRequestInMs;
        }

        /**
         * Sets the value of the delayBetweenEachRequestInMs property.
         * 
         */
        public void setDelayBetweenEachRequestInMs(int value) {
            this.delayBetweenEachRequestInMs = value;
        }

        /**
         * Gets the value of the request property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the request property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Scenario.Flow.Request }
         * 
         * 
         */
        public List<Scenario.Flow.Request> getRequest() {
            if (request == null) {
                request = new ArrayList<Scenario.Flow.Request>();
            }
            return this.request;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setId(BigInteger value) {
            this.id = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="producer" type="{}producerType"/>
         *         &lt;element name="messageSize" type="{}messageSizeType"/>
         *         &lt;element name="processingTimeInMs" type="{}processingTimeInMsType"/>
         *       &lt;/sequence>
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "producer",
            "messageSize",
            "processingTimeInMs"
        })
        public static class Request {

            @XmlElement(required = true)
            protected ProducerType producer;
            protected int messageSize;
            protected int processingTimeInMs;
            @XmlAttribute(required = true)
            protected BigInteger id;

            /**
             * Gets the value of the producer property.
             * 
             * @return
             *     possible object is
             *     {@link ProducerType }
             *     
             */
            public ProducerType getProducer() {
                return producer;
            }

            /**
             * Sets the value of the producer property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProducerType }
             *     
             */
            public void setProducer(ProducerType value) {
                this.producer = value;
            }

            /**
             * Gets the value of the messageSize property.
             * 
             */
            public int getMessageSize() {
                return messageSize;
            }

            /**
             * Sets the value of the messageSize property.
             * 
             */
            public void setMessageSize(int value) {
                this.messageSize = value;
            }

            /**
             * Gets the value of the processingTimeInMs property.
             * 
             */
            public int getProcessingTimeInMs() {
                return processingTimeInMs;
            }

            /**
             * Sets the value of the processingTimeInMs property.
             * 
             */
            public void setProcessingTimeInMs(int value) {
                this.processingTimeInMs = value;
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setId(BigInteger value) {
                this.id = value;
            }

        }

    }

}