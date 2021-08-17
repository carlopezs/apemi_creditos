package appemi.model.creditos.dtos;

public class DTOAmortizacion {
	private int nroCuota;
	private double valorCuota;
	private double interes;
	private double capital;
	private double saldo;
	
	
	
	public DTOAmortizacion(int nroCuota, double valorCuota, double interes, double capital, double saldo) {
		super();
		this.nroCuota = nroCuota;
		this.valorCuota = valorCuota;
		this.interes = interes;
		this.capital = capital;
		this.saldo = saldo;
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
	
	
	
	
}
