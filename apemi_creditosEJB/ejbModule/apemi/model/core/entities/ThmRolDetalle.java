package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the thm_rol_detalle database table.
 * 
 */
@Entity
@Table(name="thm_rol_detalle")
@NamedQuery(name="ThmRolDetalle.findAll", query="SELECT t FROM ThmRolDetalle t")
public class ThmRolDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_thm_rol_detalle", unique=true, nullable=false)
	private Integer idThmRolDetalle;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(name="id_thm_rol_cabecera", nullable=false)
	private Integer idThmRolCabecera;

	@Column(nullable=false)
	private Integer orden;

	@Column(name="tipo_detalle", nullable=false, length=2)
	private String tipoDetalle;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal valor;

	//bi-directional many-to-one association to ThmRolCabecera
	@ManyToOne
	@JoinColumn(name="thm_id_thm_rol_cabecera")
	private ThmRolCabecera thmRolCabecera;

	public ThmRolDetalle() {
	}

	public Integer getIdThmRolDetalle() {
		return this.idThmRolDetalle;
	}

	public void setIdThmRolDetalle(Integer idThmRolDetalle) {
		this.idThmRolDetalle = idThmRolDetalle;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdThmRolCabecera() {
		return this.idThmRolCabecera;
	}

	public void setIdThmRolCabecera(Integer idThmRolCabecera) {
		this.idThmRolCabecera = idThmRolCabecera;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getTipoDetalle() {
		return this.tipoDetalle;
	}

	public void setTipoDetalle(String tipoDetalle) {
		this.tipoDetalle = tipoDetalle;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ThmRolCabecera getThmRolCabecera() {
		return this.thmRolCabecera;
	}

	public void setThmRolCabecera(ThmRolCabecera thmRolCabecera) {
		this.thmRolCabecera = thmRolCabecera;
	}

}