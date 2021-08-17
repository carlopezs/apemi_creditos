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

@Named
@SessionScoped
public class BeanAsoCiudades implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAsociados managerAsociados;

	
	@Inject
	private BeanSegLogin beanSegLogin;
	

	private AsoCiudad nuevaCiudad;
	private List<AsoCiudad> listaCiudades;

	public BeanAsoCiudades() {
		// TODO Auto-generated constructor stub
	}

	public String actionMenuCiudades() {
		listaCiudades = managerAsociados.findAllCiudades();
		return "ciudades";
	}

	public void actionListenerInsertarNuevaCiudad() {
		try {
			managerAsociados.insertarCiudad(nuevaCiudad);
			JSFUtil.crearMensajeINFO("Ciudad insertada correctamente");
		} catch (Exception e) {

			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();			
		}
	}
	
	public void actionListenerActualizarEdicionUsuario() {
		try {
			managerAsociados.actualizarCiudad(beanSegLogin.getLoginDTO(), nuevaCiudad);
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

}
