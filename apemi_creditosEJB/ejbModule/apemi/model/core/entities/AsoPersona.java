package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aso_persona database table.
 * 
 */
@Entity
@Table(name="aso_persona")
@NamedQuery(name="AsoPersona.findAll", query="SELECT a FROM AsoPersona a")
public class AsoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona", unique=true, nullable=false)
	private Integer idPersona;

	@Column(nullable=false, length=50)
	private String apellidos;

	@Column(nullable=false, length=10)
	private String cedula;

	@Column(nullable=false, length=50)
	private String correo;

	@Column(nullable=false, length=100)
	private String direccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento", nullable=false)
	private Date fechaNacimiento;

	@Column(nullable=false, length=10)
	private String genero;

	@Column(nullable=false, length=10)
	private String movil;

	@Column(nullable=false, length=50)
	private String nombres;

	@Column(name="telefono_", length=10)
	private String telefono;

	//bi-directional many-to-one association to AsoCiudad
	@ManyToOne
	@JoinColumn(name="id_ciudad", nullable=false)
	private AsoCiudad asoCiudad;

	//bi-directional many-to-one association to CredGarante
	@OneToMany(mappedBy="asoPersona")
	private List<CredGarante> credGarantes;

	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="asoPersona",cascade = CascadeType.ALL)
	private List<SegUsuario> segUsuarios;

	public AsoPersona() {
	}

	public Integer getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getMovil() {
		return this.movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public AsoCiudad getAsoCiudad() {
		return this.asoCiudad;
	}

	public void setAsoCiudad(AsoCiudad asoCiudad) {
		this.asoCiudad = asoCiudad;
	}

	public List<CredGarante> getCredGarantes() {
		return this.credGarantes;
	}

	public void setCredGarantes(List<CredGarante> credGarantes) {
		this.credGarantes = credGarantes;
	}

	public CredGarante addCredGarante(CredGarante credGarante) {
		getCredGarantes().add(credGarante);
		credGarante.setAsoPersona(this);

		return credGarante;
	}

	public CredGarante removeCredGarante(CredGarante credGarante) {
		getCredGarantes().remove(credGarante);
		credGarante.setAsoPersona(null);

		return credGarante;
	}

	public List<SegUsuario> getSegUsuarios() {
		return this.segUsuarios;
	}

	public void setSegUsuarios(List<SegUsuario> segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().add(segUsuario);
		segUsuario.setAsoPersona(this);

		return segUsuario;
	}

	public SegUsuario removeSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().remove(segUsuario);
		segUsuario.setAsoPersona(null);

		return segUsuario;
	}

}