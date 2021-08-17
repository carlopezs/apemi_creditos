package apemi.controller.asociados;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apemi.controller.JSFUtil;
import apemi.controller.seguridades.BeanSegLogin;
import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.SegModulo;

@Named
@SessionScoped
public class BeanAsoCiudades implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAsociados managerAsociados;

	
	@Inject
	private BeanSegLogin beanSegLogin;
	

	private AsoCiudad nuevaCiudad;
	private AsoCiudad edicionCiudad;
	private List<AsoCiudad> listaCiudades;

	public BeanAsoCiudades() {
	
	}

	public String actionMenuCiudades() {
		nuevaCiudad = new AsoCiudad();
		listaCiudades = managerAsociados.findAllCiudades();
		return "ciudades";
	}

	public void actionListenerInsertarNuevaCiudad() {
		try {
			managerAsociados.insertarCiudad(nuevaCiudad);
			listaCiudades = managerAsociados.findAllCiudades();
			nuevaCiudad = new AsoCiudad(); 
			JSFUtil.crearMensajeINFO("Ciudad insertada correctamente");
		} catch (Exception e) {

			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();			
		}
	}
	
	public void actionListenerActualizarEdicionCiudad() {
		try {

			managerAsociados.actualizarCiudad(beanSegLogin.getLoginDTO(), edicionCiudad);
			JSFUtil.crearMensajeINFO("Ciudad actualizada");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerCargarCiudad(AsoCiudad ciudad) {
		edicionCiudad=ciudad;
	}
	
	public void actionListenerEliminarCiudad(int idAsoCiudad) {
		try {
			managerAsociados.eliminarCiudad(idAsoCiudad);
			listaCiudades = managerAsociados.findAllCiudades();
			JSFUtil.crearMensajeINFO("Ciudad eliminada correctamente");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}

	public AsoCiudad getNuevaCiudad() {
		return nuevaCiudad;
	}

	public void setNuevaCiudad(AsoCiudad nuevaCiudad) {
		this.nuevaCiudad = nuevaCiudad;
	}

	public List<AsoCiudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<AsoCiudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	public AsoCiudad getEdicionCiudad() {
		return edicionCiudad;
	}

	public void setEdicionCiudad(AsoCiudad edicionCiudad) {
		this.edicionCiudad = edicionCiudad;
	}
	
	



}
