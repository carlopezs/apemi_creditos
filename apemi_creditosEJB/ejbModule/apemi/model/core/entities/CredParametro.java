package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the cred_parametros database table.
 * 
 */
@Entity
@Table(name="cred_parametros")
@NamedQuery(name="CredParametro.findAll", query="SELECT c FROM CredParametro c")
public class CredParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parametro_credito", unique=true, nullable=false)
	private Integer idParametroCredito;

	@Column(nullable=false, precision=131089)
	private BigDecimal interes;

	@Column(name="monto_minimo", nullable=false, precision=131089)
	private BigDecimal montoMinimo;

	@Column(name="plazo_max_monto_min", nullable=false)
	private Long plazoMaxMontoMin;

	@Column(name="seguro_desgravamen", nullable=false, precision=131089)
	private BigDecimal seguroDesgravamen;

	public CredParametro() {
	}

	public Integer getIdParametroCredito() {
		return this.idParametroCredito;
	}

	public void setIdParametroCredito(Integer idParametroCredito) {
		this.idParametroCredito = idParametroCredito;
	}

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoMinimo() {
		return this.montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Long getPlazoMaxMontoMin() {
		return this.plazoMaxMontoMin;
	}

	public void setPlazoMaxMontoMin(Long plazoMaxMontoMin) {
		this.plazoMaxMontoMin = plazoMaxMontoMin;
	}

	public BigDecimal getSeguroDesgravamen() {
		return this.seguroDesgravamen;
	}

	public void setSeguroDesgravamen(BigDecimal seguroDesgravamen) {
		this.seguroDesgravamen = seguroDesgravamen;
	}

}