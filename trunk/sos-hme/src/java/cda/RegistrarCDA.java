
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
 *         &lt;element name="cda" type="{http://cda/}cdaArr" minOccurs="0"/>
 *         &lt;element name="idPacienteOrg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idOrganizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "cda",
    "idPacienteOrg",
    "idOrganizacion"
})
public class RegistrarCDA {

    protected CdaArr cda;
    protected String idPacienteOrg;
    protected String idOrganizacion;

    /**
     * Gets the value of the cda property.
     * 
     * @return
     *     possible object is
     *     {@link CdaArr }
     *     
     */
    public CdaArr getCda() {
        return cda;
    }

    /**
     * Sets the value of the cda property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdaArr }
     *     
     */
    public void setCda(CdaArr value) {
        this.cda = value;
    }

    /**
     * Gets the value of the idPacienteOrg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPacienteOrg() {
        return idPacienteOrg;
    }

    /**
     * Sets the value of the idPacienteOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPacienteOrg(String value) {
        this.idPacienteOrg = value;
    }

    /**
     * Gets the value of the idOrganizacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrganizacion() {
        return idOrganizacion;
    }

    /**
     * Sets the value of the idOrganizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrganizacion(String value) {
        this.idOrganizacion = value;
    }

}
