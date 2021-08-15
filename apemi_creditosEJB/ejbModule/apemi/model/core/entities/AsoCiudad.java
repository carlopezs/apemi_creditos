package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the aso_ciudad database table.
 * 
 */
@Entity
@Table(name="aso_ciudad")
@NamedQuery(name="AsoCiudad.findAll", query="SELECT a FROM AsoCiudad a")
public class AsoCiudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ciudad", unique=true, nullable=false)
	private Integer idCiudad;

	@Column(nullable=false, length=100)
	private String nombre;

	//bi-directional many-to-one association to AsoPersona
	@OneToMany(mappedBy="asoCiudad",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<AsoPersona> asoPersonas;

	public AsoCiudad() {
	}

	public Integer getIdCiudad() {
		return this.idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AsoPersona> getAsoPersonas() {
		return this.asoPersonas;
	}

	public void setAsoPersonas(List<AsoPersona> asoPersonas) {
		this.asoPersonas = asoPersonas;
	}

	public AsoPersona addAsoPersona(AsoPersona asoPersona) {
		getAsoPersonas().add(asoPersona);
		asoPersona.setAsoCiudad(this);

		return asoPersona;
	}

	public AsoPersona removeAsoPersona(AsoPersona asoPersona) {
		getAsoPersonas().remove(asoPersona);
		asoPersona.setAsoCiudad(null);

		return asoPersona;
	}

}