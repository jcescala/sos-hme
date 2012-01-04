
package cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for buscarCDAByRango complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buscarCDAByRango">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desde" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="hasta" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
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
@XmlType(name = "buscarCDAByRango", propOrder = {
    "desde",
    "hasta",
    "offset",
    "idOrganizacion"
})
public class BuscarCDAByRango {

    protected Object desde;
    protected Object hasta;
    protected Object offset;
    protected String idOrganizacion;

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
