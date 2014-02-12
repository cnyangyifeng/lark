
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
 *         &lt;element name="AccountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OldPWD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NewPWD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "accountID",
    "oldPWD",
    "newPWD",
    "systemTag",
    "sign"
})
@XmlRootElement(name = "ChangePWD")
public class ChangePWD {

    @XmlElement(name = "AccountID")
    protected String accountID;
    @XmlElement(name = "OldPWD")
    protected String oldPWD;
    @XmlElement(name = "NewPWD")
    protected String newPWD;
    @XmlElement(name = "SystemTag")
    protected String systemTag;
    @XmlElement(name = "Sign")
    protected String sign;

    /**
     * Gets the value of the accountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * Sets the value of the accountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountID(String value) {
        this.accountID = value;
    }

    /**
     * Gets the value of the oldPWD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldPWD() {
        return oldPWD;
    }

    /**
     * Sets the value of the oldPWD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldPWD(String value) {
        this.oldPWD = value;
    }

    /**
     * Gets the value of the newPWD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPWD() {
        return newPWD;
    }

    /**
     * Sets the value of the newPWD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPWD(String value) {
        this.newPWD = value;
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
