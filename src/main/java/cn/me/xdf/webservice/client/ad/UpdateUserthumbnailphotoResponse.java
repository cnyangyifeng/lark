
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
 *         &lt;element name="UpdateUserthumbnailphotoResult" type="{http://tempuri.org/}StateInfo" minOccurs="0"/>
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
    "updateUserthumbnailphotoResult"
})
@XmlRootElement(name = "UpdateUserthumbnailphotoResponse")
public class UpdateUserthumbnailphotoResponse {

    @XmlElement(name = "UpdateUserthumbnailphotoResult")
    protected StateInfo updateUserthumbnailphotoResult;

    /**
     * Gets the value of the updateUserthumbnailphotoResult property.
     * 
     * @return
     *     possible object is
     *     {@link StateInfo }
     *     
     */
    public StateInfo getUpdateUserthumbnailphotoResult() {
        return updateUserthumbnailphotoResult;
    }

    /**
     * Sets the value of the updateUserthumbnailphotoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateInfo }
     *     
     */
    public void setUpdateUserthumbnailphotoResult(StateInfo value) {
        this.updateUserthumbnailphotoResult = value;
    }

}
