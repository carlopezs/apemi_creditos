package apemi.controller.garantes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import apemi.controller.JSFUtil;
import apemi.controller.seguridades.BeanSegLogin;
import apemi.model.core.entities.AsoCiudad;
import apemi.model.core.entities.AsoPersona;
import apemi.model.core.entities.CredGarante;
import apemi.model.core.entities.SegUsuario;
import apemi.model.garante.managers.ManagerGarante;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@SessionScoped
public class BeanGarante implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerGarante managerGarante;
	
	private List<CredGarante> listaGarante;
	private List<AsoCiudad> listaCiudades;
	
	private CredGarante nuevoGarante;
	private AsoPersona nuevaPersona;
	
	private CredGarante edicionGarante;
	private AsoPersona edicionPersona;
	
	private int idCiudad;
	private int idCiudadEdicion;
	
	@Inject
	private BeanSegLogin beanSegLogin;
	
	public BeanGarante() {
		
	}
	
	public String actionMenuGarantes() {
		listaGarante = managerGarante.findAllGarantes();
		listaCiudades = managerGarante.findAllCiudades();
		return "garantes";
	}
	
	public void actionListenerActivarDesactivarGarante(int idGarante) {
		try {
			managerGarante.activarDesactivarGarante(idGarante);
			listaGarante = managerGarante.findAllGarantes();
			JSFUtil.crearMensajeINFO("Garante activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionMenuNuevoGarante() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1935, 1, 1);
	    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		nuevaPersona = new AsoPersona();
		nuevaPersona.setFechaNacimiento(date);
		nuevoGarante = new CredGarante();
		nuevoGarante.setActivo(true);
		return "garante_nuevo";
	}
	
	public String actionReporte() {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("creditos/reportegarante.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporteGarantes.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://192.100.198.141:5432/calopezs", "calopezs",
					"8171199261");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			JSFUtil.crearMensajeINFO("Reporte generado correctamente");
			System.out.println("reporte generado exitosamente.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	
	public void actionListenerInsertarNuevoGarante() {

		try {

			//nuevoUsuario.setClave(null);
			managerGarante.insertarGarante(nuevoGarante, nuevaPersona, idCiudad);
			listaGarante = managerGarante.findAllGarantes();
			nuevoGarante = new CredGarante();
			nuevoGarante.setActivo(true);
			nuevaPersona = new AsoPersona();

			JSFUtil.crearMensajeINFO("Garante creado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionSeleccionarEdicionGarante(CredGarante garante) {
		
		edicionGarante = garante;
		edicionPersona = garante.getAsoPersona();
		return "garante_edicion";
	}
	
	public void actionListenerActualizarEdicionGarante() {
		try {
			managerGarante.actualizarGarante(beanSegLogin.getLoginDTO(), edicionPersona, idCiudadEdicion,
					edicionGarante);
			listaGarante = managerGarante.findAllGarantes();
			JSFUtil.crearMensajeINFO("Garante actualizado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public List<CredGarante> getListaGarante() {
		return listaGarante;
	}

	public void setListaGarante(List<CredGarante> listaGarante) {
		this.listaGarante = listaGarante;
	}

	public CredGarante getNuevoGarante() {
		return nuevoGarante;
	}

	public void setNuevoGarante(CredGarante nuevoGarante) {
		this.nuevoGarante = nuevoGarante;
	}

	public AsoPersona getNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(AsoPersona nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public CredGarante getEdicionGarante() {
		return edicionGarante;
	}

	public void setEdicionGarante(CredGarante edicionGarante) {
		this.edicionGarante = edicionGarante;
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
