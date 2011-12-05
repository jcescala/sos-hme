
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for buscarCDAByPacienteAndRango complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buscarCDAByPacienteAndRango">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paciente" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="desde" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="hasta" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarCDAByPacienteAndRango", propOrder = {
    "paciente",
    "desde",
    "hasta",
    "offset"
})
public class BuscarCDAByPacienteAndRango {

    protected Object paciente;
    protected Object desde;
    protected Object hasta;
    protected Object offset;

    /**
     * Gets the value of the paciente property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPaciente() {
        return paciente;
    }

    /**
     * Sets the value of the paciente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPaciente(Object value) {
        this.paciente = value;
    }

    /**
     * Gets the value of the desde property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDesde() {
        return desde;
    }

    /**
     * Sets the value of the desde property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDesde(Object value) {
        this.desde = value;
    }

    /**
     * Gets the value of the hasta property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getHasta() {
        return hasta;
    }

    /**
     * Sets the value of the hasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setHasta(Object value) {
        this.hasta = value;
    }

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOffset(Object value) {
        this.offset = value;
    }

}