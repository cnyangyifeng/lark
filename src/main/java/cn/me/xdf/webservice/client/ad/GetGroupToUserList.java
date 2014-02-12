
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
 *         &lt;element name="GroupAccountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "groupAccountName",
    "systemTag",
    "sign"
})
@XmlRootElement(name = "GetGroupToUserList")
public class GetGroupToUserList {

    @XmlElement(name = "GroupAccountName")
    protected String groupAccountName;
    @XmlElement(name = "SystemTag")
    protected String systemTag;
    protected String sign;

    /**
     * Gets the value of the groupAccountName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupAccountName() {
        return groupAccountName;
    }

    /**
     * Sets the value of the groupAccountName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupAccountName(String value) {
        this.groupAccountName = value;
    }

    /**
     * Gets the value of the systemTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemTag() {
        return systemTag;
    }

    /**
     * Sets the value of the systemTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemTag(String value) {
        this.systemTag = value;
    }

    /**
     * Gets the value of the sign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSign() {
        return sign;
    }

    /**
     * Sets the value of the sign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSign(String value) {
        this.sign = value;
    }

}
