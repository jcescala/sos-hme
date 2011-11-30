
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for buscarCDAByPacienteAndOrganizacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buscarCDAByPacienteAndOrganizacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paciente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="organizacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarCDAByPacienteAndOrganizacion", propOrder = {
    "paciente",
    "organizacion"
})
public class BuscarCDAByPacienteAndOrganizacion {

    protected String paciente;
    protected String organizacion;

    /**
     * Gets the value of the paciente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaciente() {
        return paciente;
    }

    /**
     * Sets the value of the paciente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaciente(String value) {
        this.paciente = value;
    }

    /**
     * Gets the value of the organizacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizacion() {
        return organizacion;
    }

    /**
     * Sets the value of the organizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizacion(String value) {
        this.organizacion = value;
    }

}
