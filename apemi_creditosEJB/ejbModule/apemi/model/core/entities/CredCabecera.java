package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cred_cabecera database table.
 * 
 */
@Entity
@Table(name="cred_cabecera")
@NamedQuery(name="CredCabecera.findAll", query="SELECT c FROM CredCabecera c")
public class CredCabecera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_credito_cab", unique=true, nullable=false)
	private Integer idCreditoCab;

	@Column(name="degravamen_total", nullable=false, precision=131089)
	private BigDecimal degravamenTotal;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(nullable=false, precision=131089)
	private BigDecimal interes;

	@Column(name="monto_total", nullable=false, precision=131089)
	private BigDecimal montoTotal;

	@Column(nullable=false)
	private Boolean pagado;

	@Column(nullable=false)
	private Integer plazo;

	@Column(name="valor_cuota", nullable=false, precision=131089)
	private BigDecimal valorCuota;

	//bi-directional many-to-one association to CredGarante
	@ManyToOne
	@JoinColumn(name="id_garante")
	private CredGarante credGarante;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario_asociado")
	private SegUsuario segUsuario1;

	//bi-directional many-to-one association to SegUsuario
	@ManyToOne
	@JoinColumn(name="id_seg_usuario")
	private SegUsuario segUsuario2;

	//bi-directional many-to-one association to CredDetalle
	@OneToMany(mappedBy="credCabecera")
	private List<CredDetalle> credDetalles;

	public CredCabecera() {
	}

	public Integer getIdCreditoCab() {
		return this.idCreditoCab;
	}

	public void setIdCreditoCab(Integer idCreditoCab) {
		this.idCreditoCab = idCreditoCab;
	}

	public BigDecimal getDegravamenTotal() {
		return this.degravamenTotal;
	}

	public void setDegravamenTotal(BigDecimal degravamenTotal) {
		this.degravamenTotal = degravamenTotal;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoTotal() {
		return this.montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Boolean getPagado() {
		return this.pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public Integer getPlazo() {
		return this.plazo;
	}

	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}

	public BigDecimal getValorCuota() {
		return this.valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public CredGarante getCredGarante() {
		return this.credGarante;
	}

	public void setCredGarante(CredGarante credGarante) {
		this.credGarante = credGarante;
	}

	public SegUsuario getSegUsuario1() {
		return this.segUsuario1;
	}

	public void setSegUsuario1(SegUsuario segUsuario1) {
		this.segUsuario1 = segUsuario1;
	}

	public SegUsuario getSegUsuario2() {
		return this.segUsuario2;
	}

	public void setSegUsuario2(SegUsuario segUsuario2) {
		this.segUsuario2 = segUsuario2;
	}

	public List<CredDetalle> getCredDetalles() {
		return this.credDetalles;
	}

	public void setCredDetalles(List<CredDetalle> credDetalles) {
		this.credDetalles = credDetalles;
	}

	public CredDetalle addCredDetalle(CredDetalle credDetalle) {
		getCredDetalles().add(credDetalle);
		credDetalle.setCredCabecera(this);

		return credDetalle;
	}

	public CredDetalle removeCredDetalle(CredDetalle credDetalle) {
		getCredDetalles().remove(credDetalle);
		credDetalle.setCredCabecera(null);

		return credDetalle;
	}

}