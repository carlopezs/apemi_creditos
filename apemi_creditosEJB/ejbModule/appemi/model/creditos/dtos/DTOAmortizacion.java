package appemi.model.creditos.dtos;

import java.util.Date;

public class DTOAmortizacion {
	private int nroCuota;
	private double valorCuota;
	private double interes;
	private double capital;
	private double saldo;
	private Date fechaCuota;
	
	
	
	
	public DTOAmortizacion(int nroCuota, double valorCuota, double interes, double capital, double saldo,
			Date fechaCuota) {
		super();
		this.nroCuota = nroCuota;
		this.valorCuota = valorCuota;
		this.interes = interes;
		this.capital = capital;
		this.saldo = saldo;
		this.fechaCuota = fechaCuota;
	}
	public int getNroCuota() {
		return nroCuota;
	}
	public void setNroCuota(int nroCuota) {
		this.nroCuota = nroCuota;
	}
	public double getValorCuota() {
		return valorCuota;
	}
	public void setValorCuota(double valorCuota) {
		this.valorCuota = valorCuota;
	}
	public double getInteres() {
		return interes;
	}
	public void setInteres(double interes) {
		this.interes = interes;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public Date getFechaCuota() {
		return fechaCuota;
	}


	public void setFechaCuota(Date fechaCuota) {
		this.fechaCuota = fechaCuota;
	}
	
	
	
	
}
