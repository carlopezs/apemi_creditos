package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_usuario database table.
 * 
 */
@Entity
@Table(name="seg_usuario")
@NamedQuery(name="SegUsuario.findAll", query="SELECT s FROM SegUsuario s")
public class SegUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_usuario", unique=true, nullable=false)
	private Integer idSegUsuario;

	@Column(nullable=false)
	private Boolean activo;

	@Column(nullable=false, length=50)
	private String clave;

	//bi-directional many-to-one association to CredCabecera
	@OneToMany(mappedBy="segUsuario1",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CredCabecera> credCabeceras1;

	//bi-directional many-to-one association to CredCabecera
	@OneToMany(mappedBy="segUsuario2",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CredCabecera> credCabeceras2;

	//bi-directional many-to-one association to SegAsignacion
	@OneToMany(mappedBy="segUsuario")
	private List<SegAsignacion> segAsignacions;

	//bi-directional many-to-one association to AsoPersona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private AsoPersona asoPersona;

	//bi-directional many-to-one association to ThmEmpleado
	@OneToMany(mappedBy="segUsuario",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<ThmEmpleado> thmEmpleados;

	public SegUsuario() {
	}

	public Integer getIdSegUsuario() {
		return this.idSegUsuario;
	}

	public void setIdSegUsuario(Integer idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<CredCabecera> getCredCabeceras1() {
		return this.credCabeceras1;
	}

	public void setCredCabeceras1(List<CredCabecera> credCabeceras1) {
		this.credCabeceras1 = credCabeceras1;
	}

	public CredCabecera addCredCabeceras1(CredCabecera credCabeceras1) {
		getCredCabeceras1().add(credCabeceras1);
		credCabeceras1.setSegUsuario1(this);

		return credCabeceras1;
	}

	public CredCabecera removeCredCabeceras1(CredCabecera credCabeceras1) {
		getCredCabeceras1().remove(credCabeceras1);
		credCabeceras1.setSegUsuario1(null);

		return credCabeceras1;
	}

	public List<CredCabecera> getCredCabeceras2() {
		return this.credCabeceras2;
	}

	public void setCredCabeceras2(List<CredCabecera> credCabeceras2) {
		this.credCabeceras2 = credCabeceras2;
	}

	public CredCabecera addCredCabeceras2(CredCabecera credCabeceras2) {
		getCredCabeceras2().add(credCabeceras2);
		credCabeceras2.setSegUsuario2(this);

		return credCabeceras2;
	}

	public CredCabecera removeCredCabeceras2(CredCabecera credCabeceras2) {
		getCredCabeceras2().remove(credCabeceras2);
		credCabeceras2.setSegUsuario2(null);

		return credCabeceras2;
	}

	public List<SegAsignacion> getSegAsignacions() {
		return this.segAsignacions;
	}

	public void setSegAsignacions(List<SegAsignacion> segAsignacions) {
		this.segAsignacions = segAsignacions;
	}

	public SegAsignacion addSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().add(segAsignacion);
		segAsignacion.setSegUsuario(this);

		return segAsignacion;
	}

	public SegAsignacion removeSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().remove(segAsignacion);
		segAsignacion.setSegUsuario(null);

		return segAsignacion;
	}

	public AsoPersona getAsoPersona() {
		return this.asoPersona;
	}

	public void setAsoPersona(AsoPersona asoPersona) {
		this.asoPersona = asoPersona;
	}

	public List<ThmEmpleado> getThmEmpleados() {
		return this.thmEmpleados;
	}

	public void setThmEmpleados(List<ThmEmpleado> thmEmpleados) {
		this.thmEmpleados = thmEmpleados;
	}

	public ThmEmpleado addThmEmpleado(ThmEmpleado thmEmpleado) {
		getThmEmpleados().add(thmEmpleado);
		thmEmpleado.setSegUsuario(this);

		return thmEmpleado;
	}

	public ThmEmpleado removeThmEmpleado(ThmEmpleado thmEmpleado) {
		getThmEmpleados().remove(thmEmpleado);
		thmEmpleado.setSegUsuario(null);

		return thmEmpleado;
	}

}