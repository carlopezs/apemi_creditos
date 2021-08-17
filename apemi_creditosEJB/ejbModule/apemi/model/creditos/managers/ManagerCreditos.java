package apemi.model.creditos.managers;

import java.util.ArrayList;
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
    
    public List<DTOAmortizacion> generarAmortizacion(double monto ,int nroCuotas,int tasaAnual){
    	double interes;
    	double capitalCuota;
    	double saldo = monto;
    	double plazoAños = nroCuotas / 12;	
    	tasaAnual = tasaAnual /100;
    	double tasaPeriodica = (Math.pow(1+plazoAños,(1/12)))-1;
    	double valorCuota = monto*(tasaPeriodica/1-Math.pow((1+tasaPeriodica),-nroCuotas));
    	List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
    	for (int i = 0; i <= nroCuotas; i++) {
    		if (i == 0) {
    			listaAmortizacion.add(new DTOAmortizacion(i, valorCuota, 0, 0, saldo));
			}else {
				interes = saldo * tasaPeriodica;
				capitalCuota = valorCuota - interes;
				saldo = saldo - capitalCuota;
				listaAmortizacion.add(new DTOAmortizacion(i, valorCuota, interes, capitalCuota, saldo));
			}
			
		}
    	return listaAmortizacion;
    }

}
