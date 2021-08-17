package apemi.model.creditos.managers;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import appemi.model.creditos.dtos.DTOAmortizacion;

/**
 * Session Bean implementation class ManagerCreditos
 */
@Stateless
@LocalBean
public class ManagerCreditos {


    public ManagerCreditos() {
      
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
