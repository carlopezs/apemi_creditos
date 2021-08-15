package apemi.model.thumano.dtos;

public class DTOThmCargo {
	private int idThmCargo;
	private String nombreCargo;
	private double remuneracionMensual;
	public DTOThmCargo(int idThmCargo, String nombreCargo, double remuneracionMensual) {
		super();
		this.idThmCargo = idThmCargo;
		this.nombreCargo = nombreCargo;
		this.remuneracionMensual = remuneracionMensual;
	}
	public int getIdThmCargo() {
		return idThmCargo;
	}
	public void setIdThmCargo(int idThmCargo) {
		this.idThmCargo = idThmCargo;
	}
	public String getNombreCargo() {
		return nombreCargo;
	}
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}
	public double getRemuneracionMensual() {
		return remuneracionMensual;
	}
	public void setRemuneracionMensual(double remuneracionMensual) {
		this.remuneracionMensual = remuneracionMensual;
	}
	
	
}
