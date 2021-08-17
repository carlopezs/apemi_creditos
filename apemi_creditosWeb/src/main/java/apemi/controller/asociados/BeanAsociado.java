package apemi.controller.asociados;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apemi.controller.JSFUtil;
import apemi.controller.seguridades.BeanSegLogin;
import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.SegUsuario;

@Named
@SessionScoped
public class BeanAsociado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerAsociados managerAsociados;
	

	private List<SegUsuario> listaAsociados;
	private SegUsuario nuevoUsuario;
	private AsoPersona nuevaPersona;
	private SegUsuario edicionUsuario;
	private AsoPersona edicionPersona;
	private int idCiudad;
	private int idCiudadEdicion;
	private List<AsoCiudad> listaCiudades;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanAsociado() {
		// TODO Auto-generated constructor stub
	}
	
	public String actionMenuAsociados() {

		listaCiudades = managerAsociados.findAllCiudades();
		listaAsociados = managerAsociados.findAllAsociados();
		return "asociados";
	}

	public void actionListenerActivarDesactivarAsociado(int idSegUsuario) {
		try {
			managerAsociados.activarDesactivarAsociado(idSegUsuario);
			listaAsociados = managerAsociados.findAllAsociados();
			JSFUtil.crearMensajeINFO("Asociado activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public String actionMenuNuevoAsociado() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1935, 1, 1);
	    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		nuevaPersona = new AsoPersona();
		nuevaPersona.setFechaNacimiento(date);
		nuevoUsuario = new SegUsuario();
		nuevoUsuario.setActivo(true);
		return "asociado_nuevo";
	}

	public void actionListenerInsertarNuevoAsociado() {

		try {

			//nuevoUsuario.setClave(null);
			managerAsociados.insertarAsociado(nuevoUsuario, nuevaPersona, idCiudad);
			listaAsociados = managerAsociados.findAllAsociados();
			nuevoUsuario = new SegUsuario();
			nuevoUsuario.setActivo(true);
			nuevaPersona = new AsoPersona();

			JSFUtil.crearMensajeINFO("Asociado creado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	

	public String actionSeleccionarEdicionAsociado(SegUsuario usuario) {
		System.out.println(usuario.getClave());
		edicionUsuario = usuario;
		edicionPersona = usuario.getAsoPersona();
		return "asociado_edicion";
	}

	public void actionListenerActualizarEdicionAsociado() {
		try {
			managerAsociados.actualizarAsociado(beanSegLogin.getLoginDTO(), edicionPersona, idCiudadEdicion,
					edicionUsuario);
			listaAsociados = managerAsociados.findAllAsociados();
			JSFUtil.crearMensajeINFO("Asociado actualizado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SegUsuario> getListaAsociados() {
		return listaAsociados;
	}

	public void setListaAsociados(List<SegUsuario> listaAsociados) {
		this.listaAsociados = listaAsociados;
	}

	public SegUsuario getNuevoUsuario() {
		return nuevoUsuario;
	}

	public void setNuevoUsuario(SegUsuario nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public AsoPersona getNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(AsoPersona nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public SegUsuario getEdicionUsuario() {
		return edicionUsuario;
	}

	public void setEdicionUsuario(SegUsuario edicionUsuario) {
		this.edicionUsuario = edicionUsuario;
	}

	public AsoPersona getEdicionPersona() {
		return edicionPersona;
	}

	public void setEdicionPersona(AsoPersona edicionPersona) {
		this.edicionPersona = edicionPersona;
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public int getIdCiudadEdicion() {
		return idCiudadEdicion;
	}

	public void setIdCiudadEdicion(int idCiudadEdicion) {
		this.idCiudadEdicion = idCiudadEdicion;
	}

	public List<AsoCiudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<AsoCiudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}
	
	
	

}
