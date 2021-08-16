package apemi.controller.seguridades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apemi.controller.JSFUtil;
import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.SegUsuario;
import apemi.model.seguridades.managers.ManagerSeguridades;



@Named
@SessionScoped
public class BeanSegUsuarios implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;
	@EJB
	private ManagerAsociados managerAsociados;
	
	private List<SegUsuario> listaUsuarios;
	private SegUsuario nuevoUsuario;
	private AsoPersona nuevaPersona;
	private SegUsuario edicionUsuario;
	private AsoPersona edicionPersona;
	private int idCiudad;
	private int idCiudadEdicion;
	private List<AsoCiudad> listaCiudades;
	
	@Inject
	private BeanSegLogin beanSegLogin;
	
	
	public BeanSegUsuarios() {
		
	}
	
	
	public String actionMenuUsuarios() {
		 
		listaCiudades = managerAsociados.findAllCiudades();
		listaUsuarios=managerSeguridades.findAllUsuarios();
		return "usuarios";
	}
	
	public void actionListenerActivarDesactivarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.activarDesactivarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionMenuNuevoUsuario() {
		nuevaPersona = new AsoPersona();
		nuevaPersona.setFechaNacimiento(new Date());
		nuevoUsuario=new SegUsuario();
		nuevoUsuario.setActivo(true);
		return "usuarios_nuevo";
	}
	
	public void actionListenerInsertarNuevoUsuario() {
		try {
			managerSeguridades.insertarUsuario(nuevoUsuario, nuevaPersona, idCiudad);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			nuevoUsuario=new SegUsuario();
			nuevoUsuario.setActivo(true);
			nuevaPersona = new AsoPersona();
			JSFUtil.crearMensajeINFO("Usuario insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionSeleccionarEdicionUsuario(SegUsuario usuario) {
		System.out.println(usuario.getClave());
		edicionUsuario=usuario;
		System.out.println("edicionUsuario: "+edicionUsuario.getClave());
		edicionPersona = usuario.getAsoPersona();
		return "usuarios_edicion";
	}
	
	public void actionListenerActualizarEdicionUsuario() {
		try {
			managerSeguridades.actualizarUsuario(beanSegLogin.getLoginDTO(),edicionPersona, idCiudadEdicion, edicionUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.eliminarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public SegUsuario getNuevoUsuario() {
		return nuevoUsuario;
	}

	public void setNuevoUsuario(SegUsuario nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public SegUsuario getEdicionUsuario() {
		return edicionUsuario;
	}

	public void setEdicionUsuario(SegUsuario edicionUsuario) {
		this.edicionUsuario = edicionUsuario;
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}

	public AsoPersona getNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(AsoPersona nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public List<AsoCiudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<AsoCiudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}


	public int getIdCiudad() {
		return idCiudad;
	}


	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}


	public AsoPersona getEdicionPersona() {
		return edicionPersona;
	}


	public void setEdicionPersona(AsoPersona edicionPersona) {
		this.edicionPersona = edicionPersona;
	}


	public int getIdCiudadEdicion() {
		return idCiudadEdicion;
	}


	public void setIdCiudadEdicion(int idCiudadEdicion) {
		this.idCiudadEdicion = idCiudadEdicion;
	}
	
	

}
