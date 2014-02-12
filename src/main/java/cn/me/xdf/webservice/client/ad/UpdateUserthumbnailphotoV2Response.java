
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
 *         &lt;element name="UpdateUserthumbnailphotoV2Result" type="{http://tempuri.org/}StateInfo" minOccurs="0"/>
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
    "updateUserthumbnailphotoV2Result"
})
@XmlRootElement(name = "UpdateUserthumbnailphotoV2Response")
public class UpdateUserthumbnailphotoV2Response {

    @XmlElement(name = "UpdateUserthumbnailphotoV2Result")
    protected StateInfo updateUserthumbnailphotoV2Result;

    /**
     * Gets the value of the updateUserthumbnailphotoV2Result property.
     * 
     * @return
     *     possible object is
     *     {@link StateInfo }
     *     
     */
    public StateInfo getUpdateUserthumbnailphotoV2Result() {
        return updateUserthumbnailphotoV2Result;
    }

    /**
     * Sets the value of the updateUserthumbnailphotoV2Result property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateInfo }
     *     
     */
    public void setUpdateUserthumbnailphotoV2Result(StateInfo value) {
        this.updateUserthumbnailphotoV2Result = value;
    }

}
