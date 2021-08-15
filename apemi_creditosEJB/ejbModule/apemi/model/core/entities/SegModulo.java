package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_modulo database table.
 * 
 */
@Entity
@Table(name="seg_modulo")
@NamedQuery(name="SegModulo.findAll", query="SELECT s FROM SegModulo s")
public class SegModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_modulo", unique=true, nullable=false)
	private Integer idSegModulo;

	@Column(name="nombre_modulo", nullable=false, length=50)
	private String nombreModulo;

	@Column(name="ruta_acceso", nullable=false, length=100)
	private String rutaAcceso;

	//bi-directional many-to-one association to SegAsignacion
	@OneToMany(mappedBy="segModulo")
	private List<SegAsignacion> segAsignacions;

	public SegModulo() {
	}

	public Integer getIdSegModulo() {
		return this.idSegModulo;
	}

	public void setIdSegModulo(Integer idSegModulo) {
		this.idSegModulo = idSegModulo;
	}

	public String getNombreModulo() {
		return this.nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public String getRutaAcceso() {
		return this.rutaAcceso;
	}

	public void setRutaAcceso(String rutaAcceso) {
		this.rutaAcceso = rutaAcceso;
	}

	public List<SegAsignacion> getSegAsignacions() {
		return this.segAsignacions;
	}

	public void setSegAsignacions(List<SegAsignacion> segAsignacions) {
		this.segAsignacions = segAsignacions;
	}

	public SegAsignacion addSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().add(segAsignacion);
		segAsignacion.setSegModulo(this);

		return segAsignacion;
	}

	public SegAsignacion removeSegAsignacion(SegAsignacion segAsignacion) {
		getSegAsignacions().remove(segAsignacion);
		segAsignacion.setSegModulo(null);

		return segAsignacion;
	}

}