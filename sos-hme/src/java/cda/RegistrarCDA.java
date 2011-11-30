
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registrarCDA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registrarCDA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registrarCDA", propOrder = {
    "cda"
})
public class RegistrarCDA {

    protected String cda;

    /**
     * Gets the value of the cda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCda() {
        return cda;
    }

    /**
     * Sets the value of the cda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCda(String value) {
        this.cda = value;
    }

}
