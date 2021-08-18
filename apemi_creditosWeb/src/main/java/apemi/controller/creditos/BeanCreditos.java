package apemi.controller.creditos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.CredGarante;
import apemi.model.core.entities.CredParametro;
import apemi.model.core.entities.SegUsuario;
import apemi.model.creditos.managers.ManagerCreditos;
import apemi.model.creditos.managers.ManagerParametros;
import apemi.model.garante.managers.ManagerGarante;
import appemi.model.creditos.dtos.DTOAmortizacion;

@Named
@SessionScoped
public class BeanCreditos implements Serializable {
	@EJB
	private ManagerCreditos managerCreditos;
	@EJB
	private ManagerParametros managerParametros;
	@EJB
	private ManagerAsociados managerAsociados;
	@EJB
	private ManagerGarante managerGarantes;

	private double monto;
	private double nroCuotas;
	private int idAsociado;
	private int idGarante;
	private double valorCuota;
	private List<SegUsuario> listaAsociados;
	private List<CredGarante> listaGarantes;
	private CredParametro paramCred;

	List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
	private static final long serialVersionUID = 1L;

	public BeanCreditos() {
		// TODO Auto-generated constructor stub
	}
	
	public String actionMenuCreditos() {
        listaGarantes = managerGarantes.findAllGarantes();
		listaAsociados = managerAsociados.findAllAsociados();
		List<CredParametro> parametros = managerParametros.findAllCredParametro();
		this.setParamCred(parametros.get(0));
		return "nuevoCredito";
	}

	public void actionListenerMostarTablaDeAmortizacion() {
		List<CredParametro> parametros = managerParametros.findAllCredParametro();

		listaAmortizacion = managerCreditos.generarAmortizacion(monto, nroCuotas,
				parametros.get(0).getInteres().doubleValue(), parametros.get(0).getSeguroDesgravamen().doubleValue());
		double tasaAnual = parametros.get(0).getInteres().doubleValue();
        tasaAnual = tasaAnual /100;	
    	double tasaPeriodica = (Math.pow(1.0+tasaAnual,(1.0/12.0)))-1.0;
    	double valoramortizado = monto*(tasaPeriodica/(1-Math.pow(1+tasaPeriodica,-nroCuotas)));
    	this.setValorCuota(Math.round((valoramortizado)* 100.0)/100.0);


	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(double nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public List<DTOAmortizacion> getListaAmortizacion() {
		return listaAmortizacion;
	}

	public void setListaAmortizacion(List<DTOAmortizacion> listaAmortizacion) {
		this.listaAmortizacion = listaAmortizacion;
	}

	public List<SegUsuario> getListaAsociados() {
		return listaAsociados;
	}

	public void setListaAsociados(List<SegUsuario> listaAsociados) {
		this.listaAsociados = listaAsociados;
	}

	public List<CredGarante> getListaGarantes() {
		return listaGarantes;
	}

	public void setListaGarantes(List<CredGarante> listaGarantes) {
		this.listaGarantes = listaGarantes;
	}

	public int getIdAsociado() {
		return idAsociado;
	}

	public void setIdAsociado(int idAsociado) {
		this.idAsociado = idAsociado;
	}

	public int getIdGarante() {
		return idGarante;
	}

	public void setIdGarante(int idGarante) {
		this.idGarante = idGarante;
	}

	public CredParametro getParamCred() {
		return paramCred;
	}

	public void setParamCred(CredParametro paramCred) {
		this.paramCred = paramCred;
	}

	public double getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(double valorCuota) {
		this.valorCuota = valorCuota;
	}

	
	
	
	

}
