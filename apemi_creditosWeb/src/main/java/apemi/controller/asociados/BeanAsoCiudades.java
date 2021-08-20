package apemi.controller.asociados;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import apemi.controller.JSFUtil;
import apemi.controller.seguridades.BeanSegLogin;
import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.SegModulo;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	
	public String actionReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */ FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("asociados/ciudades.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://192.100.198.141:5432/calopezs", "calopezs","8171199261");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("reporte generado.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
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
