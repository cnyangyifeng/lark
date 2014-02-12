
package cn.me.xdf.webservice.client.ad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="GetGroupToUserListResult" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/>
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
    "getGroupToUserListResult"
})
@XmlRootElement(name = "GetGroupToUserListResponse")
public class GetGroupToUserListResponse {

    @XmlElement(name = "GetGroupToUserListResult")
    protected ArrayOfString getGroupToUserListResult;

    /**
     * Gets the value of the getGroupToUserListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getGetGroupToUserListResult() {
        return getGroupToUserListResult;
    }

    /**
     * Sets the value of the getGroupToUserListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setGetGroupToUserListResult(ArrayOfString value) {
        this.getGroupToUserListResult = value;
    }

}
