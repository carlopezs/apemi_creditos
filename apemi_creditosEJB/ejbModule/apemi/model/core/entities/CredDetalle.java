package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the cred_detalle database table.
 * 
 */
@Entity
@Table(name="cred_detalle")
@NamedQuery(name="CredDetalle.findAll", query="SELECT c FROM CredDetalle c")
public class CredDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_credito_det", unique=true, nullable=false)
	private Integer idCreditoDet;

	@Column(name="capital_cuota", nullable=false, precision=131089)
	private BigDecimal capitalCuota;

	@Column(name="degravamen_cuota", nullable=false, precision=131089)
	private BigDecimal degravamenCuota;

	@Column(name="fecha_cuota", nullable=false)
	private Timestamp fechaCuota;

	@Column(name="interes_cuota", nullable=false, precision=131089)
	private BigDecimal interesCuota;

	@Column(nullable=false)
	private Boolean pagado;

	@Column(name="saldo_cuota", nullable=false, precision=131089)
	private BigDecimal saldoCuota;

	//bi-directional many-to-one association to CredCabecera
	@ManyToOne
	@JoinColumn(name="id_credito_cab")
	private CredCabecera credCabecera;

	public CredDetalle() {
	}

	public Integer getIdCreditoDet() {
		return this.idCreditoDet;
	}

	public void setIdCreditoDet(Integer idCreditoDet) {
		this.idCreditoDet = idCreditoDet;
	}

	public BigDecimal getCapitalCuota() {
		return this.capitalCuota;
	}

	public void setCapitalCuota(BigDecimal capitalCuota) {
		this.capitalCuota = capitalCuota;
	}

	public BigDecimal getDegravamenCuota() {
		return this.degravamenCuota;
	}

	public void setDegravamenCuota(BigDecimal degravamenCuota) {
		this.degravamenCuota = degravamenCuota;
	}

	public Timestamp getFechaCuota() {
		return this.fechaCuota;
	}

	public void setFechaCuota(Timestamp fechaCuota) {
		this.fechaCuota = fechaCuota;
	}

	public BigDecimal getInteresCuota() {
		return this.interesCuota;
	}

	public void setInteresCuota(BigDecimal interesCuota) {
		this.interesCuota = interesCuota;
	}

	public Boolean getPagado() {
		return this.pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public BigDecimal getSaldoCuota() {
		return this.saldoCuota;
	}

	public void setSaldoCuota(BigDecimal saldoCuota) {
		this.saldoCuota = saldoCuota;
	}

	public CredCabecera getCredCabecera() {
		return this.credCabecera;
	}

	public void setCredCabecera(CredCabecera credCabecera) {
		this.credCabecera = credCabecera;
	}

}