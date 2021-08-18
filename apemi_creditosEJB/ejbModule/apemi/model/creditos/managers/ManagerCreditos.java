package apemi.model.creditos.managers;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.CredCabecera;
import apemi.model.core.entities.CredGarante;
import apemi.model.core.entities.CredParametro;
import apemi.model.core.entities.SegUsuario;
import apemi.model.core.managers.ManagerDAO;
import apemi.model.garante.managers.ManagerGarante;
import appemi.model.creditos.dtos.DTOAmortizacion;

/**
 * Session Bean implementation class ManagerCreditos
 */
@Stateless
@LocalBean
public class ManagerCreditos {

	@EJB
	private ManagerDAO mDAO;
	
	
    public ManagerCreditos() {
      
    }
    
    
    public void GenerarCredito(int idAsociado , int idGarante ,CredParametro parametroCredito,
    		double monto , int plazo) throws Exception{
    	
    	// Creacion de la Cabecera
    	double tasaAnual = parametroCredito.getInteres().doubleValue();
        tasaAnual = tasaAnual /100;	
    	double tasaPeriodica = (Math.pow(1.0+tasaAnual,(1.0/12.0)))-1.0;
    	double valorCuota = monto*(tasaPeriodica/(1-Math.pow(1+tasaPeriodica,-plazo)));
    	
    	SegUsuario asociado = (SegUsuario) mDAO.findById(SegUsuario.class, idAsociado);
    	SegUsuario administrador = (SegUsuario) mDAO.findById(SegUsuario.class, 1);
    	CredGarante garante = (CredGarante) mDAO.findById(CredGarante.class, idGarante);
    	CredCabecera cabeceraCredito = new CredCabecera();
    	cabeceraCredito.setSegUsuario1(administrador); //admin
    	cabeceraCredito.setSegUsuario2(asociado); // asociado
    	cabeceraCredito.setCredGarante(garante); // garante
    	cabeceraCredito.setFechaCreacion((Timestamp) new Date());
    	cabeceraCredito.setInteres(parametroCredito.getInteres());
    	cabeceraCredito.setDegravamenTotal(parametroCredito.getSeguroDesgravamen());
    	cabeceraCredito.setMontoTotal(new BigDecimal(monto));
    	cabeceraCredito.setValorCuota(new BigDecimal(valorCuota));
    	cabeceraCredito.setPlazo(plazo);
    	cabeceraCredito.setPagado(false);
    	
    	
    }
    
    public List<DTOAmortizacion> generarAmortizacion(double monto ,double nroCuotas,double tasaAnual){
    	double interes;
    	double capitalCuota;
    	Date fechaCuota = new Date() ;
    	double saldo = monto;
    	tasaAnual = tasaAnual /100;	
    	double tasaPeriodica = (Math.pow(1.0+tasaAnual,(1.0/12.0)))-1.0;
    	double valorCuota = monto*(tasaPeriodica/(1-Math.pow(1+tasaPeriodica,-nroCuotas)));
    	System.out.println("Esta es el valor de la cuota"+valorCuota);
    	List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
    	for (int i = 0; i <= nroCuotas; i++) {
    		if (i == 0) {
    
    			listaAmortizacion.add(new DTOAmortizacion(i, 0, 0, 0, saldo,fechaCuota));
			}else {
				interes = saldo * tasaPeriodica;
				capitalCuota = valorCuota - interes;
				saldo = saldo - capitalCuota;
				fechaCuota = aumentarMesFecha(fechaCuota);
				listaAmortizacion.add(new DTOAmortizacion(i, valorCuota, interes, capitalCuota, saldo, fechaCuota));
			}
			
		}
    	return listaAmortizacion;
    }

    public Date aumentarMesFecha(Date fechaCuota ) {
    	int año;
    	int mes;
    	int dia;
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(fechaCuota);
    	
    
    	dia = cal.get(Calendar.DATE);
    	mes = cal.get(Calendar.MONTH);
    	año = cal.get(Calendar.YEAR);
    	
    	int mesPrueba = mes + 1;
    	if (mesPrueba == 12) {
			año++;
			mes=-1;
		}
    	
    	cal.set(año , mes + 1,dia);
    	
    	return cal.getTime();
    }
}
