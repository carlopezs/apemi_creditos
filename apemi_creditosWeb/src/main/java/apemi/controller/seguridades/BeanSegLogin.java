package apemi.controller.seguridades;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import apemi.controller.JSFUtil;
import apemi.model.core.entities.CredCabecera;
import apemi.model.core.entities.SegModulo;
import apemi.model.creditos.managers.ManagerCreditos;
import apemi.model.seguridades.dtos.LoginDTO;
import apemi.model.seguridades.managers.ManagerSeguridades;



@Named
@SessionScoped
public class BeanSegLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idSegUsuario;
	private String clave;
	private LoginDTO loginDTO;
	private String direccionIP;
	private List<CredCabecera> listadoCabecerasUsua;
	
	@EJB
	private ManagerSeguridades mSeguridades;
	
	@EJB
	private ManagerCreditos mCreditos;
	
	public BeanSegLogin() {
		
	}
	
	@PostConstruct
	public void inicializar() {
		HttpServletRequest req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String agente=req.getHeader("user-agent");
		String ipAddress = req.getHeader("X-FORWARDED-FOR");
		if(ipAddress==null) {
			ipAddress=req.getRemoteAddr();
		}
		direccionIP=ipAddress;
	}
	
	public String actionLogin() {
		System.out.println(idSegUsuario);
		System.out.println(clave);
		try {
			loginDTO=mSeguridades.login(idSegUsuario, clave, direccionIP);
			if (loginDTO.getAsoPersona().getCedula().equals("1002003000")) {
				return "menu?faces-redirect=true";
			}
			else {
				listadoCabecerasUsua = mCreditos.findCabeceraByAsociado(idSegUsuario);
				return "credAsociados?faces-redirect=true";
			}
			//loginDTO.setDireccionIP(direccionIP);
		
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	public String actionMenu(String ruta) {
		return ruta+"?faces-redirect=true";
	}
	
	public String actionCerrarSesion(){
		mSeguridades.cerrarSesion(loginDTO.getIdSegUsuario());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}
	
	public void actionVerificarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String requestPath=ec.getRequestPathInfo();
		
		//primero validamos si loginDTO aun no se ha inicializado es porque
		//el usuario aun no ha pasado por la pantalla de login:
		if(loginDTO==null || loginDTO.getIdSegUsuario()==0)
		{
			try {
				mSeguridades.accesoNoPermitido(0, requestPath);
				ec.redirect(ec.getRequestContextPath()+"/faces/login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//Extraemos la parte inicial de la ruta a la que se esta accediendo:
		String rutaUsuario=requestPath.substring(1);
		rutaUsuario=rutaUsuario.substring(0, rutaUsuario.indexOf("/"));
		//validacion de la ruta de acceso:
		boolean verificado=false;
		for(SegModulo modulo:loginDTO.getListaModulos()) {
			//extraemos el inicio de la ruta (nombre de la carpeta):
			String rutaModulo=modulo.getRutaAcceso();
			rutaModulo=rutaModulo.substring(0,rutaModulo.indexOf("/"));
			//verificamos con la ruta a la que se est?? accediendo:
			if(rutaUsuario.equals(rutaModulo)){
				verificado=true;
				break;
			}
		}
		try {
			if(verificado==false) {
				mSeguridades.accesoNoPermitido(loginDTO.getIdSegUsuario(), requestPath);
				ec.redirect(ec.getRequestContextPath()+"/faces/login.xhtml");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionListenerInicialiarDemo() {
		System.out.println("Se inicializa");
		try {
			mSeguridades.inicializarDemo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public int getIdSegUsuario() {
		return idSegUsuario;
	}

	public void setIdSegUsuario(int idSegUsuario) {
		this.idSegUsuario = idSegUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LoginDTO getLoginDTO() {
		return loginDTO;
	}

	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}

	public List<CredCabecera> getListadoCabecerasUsua() {
		return listadoCabecerasUsua;
	}

	public void setListadoCabecerasUsua(List<CredCabecera> listadoCabecerasUsua) {
		this.listadoCabecerasUsua = listadoCabecerasUsua;
	}

	
	
}
