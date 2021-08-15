package apemi.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cred_garante database table.
 * 
 */
@Entity
@Table(name="cred_garante")
@NamedQuery(name="CredGarante.findAll", query="SELECT c FROM CredGarante c")
public class CredGarante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_garante", unique=true, nullable=false)
	private Integer idGarante;

	@Column(nullable=false)
	private Boolean activo;

	//bi-directional many-to-one association to CredCabecera
	@OneToMany(mappedBy="credGarante")
	private List<CredCabecera> credCabeceras;

	//bi-directional many-to-one association to AsoPersona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private AsoPersona asoPersona;

	public CredGarante() {
	}

	public Integer getIdGarante() {
		return this.idGarante;
	}

	public void setIdGarante(Integer idGarante) {
		this.idGarante = idGarante;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<CredCabecera> getCredCabeceras() {
		return this.credCabeceras;
	}

	public void setCredCabeceras(List<CredCabecera> credCabeceras) {
		this.credCabeceras = credCabeceras;
	}

	public CredCabecera addCredCabecera(CredCabecera credCabecera) {
		getCredCabeceras().add(credCabecera);
		credCabecera.setCredGarante(this);

		return credCabecera;
	}

	public CredCabecera removeCredCabecera(CredCabecera credCabecera) {
		getCredCabeceras().remove(credCabecera);
		credCabecera.setCredGarante(null);

		return credCabecera;
	}

	public AsoPersona getAsoPersona() {
		return this.asoPersona;
	}

	public void setAsoPersona(AsoPersona asoPersona) {
		this.asoPersona = asoPersona;
	}

}