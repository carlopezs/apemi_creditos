package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the vw_thm_consulta_rol database table.
 * 
 */
@Entity
@Table(name="vw_thm_consulta_rol")
@NamedQuery(name="VwThmConsultaRol.findAll", query="SELECT v FROM VwThmConsultaRol v")
public class VwThmConsultaRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=50)
	private String apellidos;

	@Column(name="horas_extra")
	private Integer horasExtra;

	@Column(name="id_thm_empleado")
	private Integer idThmEmpleado;
	@Id
	@Column(name="id_thm_rol_cabecera")
	private Integer idThmRolCabecera;

	@Column(name="periodo_rol", length=6)
	private String periodoRol;

	@Column(precision=7, scale=2)
	private BigDecimal total;

	public VwThmConsultaRol() {
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Integer getHorasExtra() {
		return this.horasExtra;
	}

	public void setHorasExtra(Integer horasExtra) {
		this.horasExtra = horasExtra;
	}

	public Integer getIdThmEmpleado() {
		return this.idThmEmpleado;
	}

	public void setIdThmEmpleado(Integer idThmEmpleado) {
		this.idThmEmpleado = idThmEmpleado;
	}

	public Integer getIdThmRolCabecera() {
		return this.idThmRolCabecera;
	}

	public void setIdThmRolCabecera(Integer idThmRolCabecera) {
		this.idThmRolCabecera = idThmRolCabecera;
	}

	public String getPeriodoRol() {
		return this.periodoRol;
	}

	public void setPeriodoRol(String periodoRol) {
		this.periodoRol = periodoRol;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}