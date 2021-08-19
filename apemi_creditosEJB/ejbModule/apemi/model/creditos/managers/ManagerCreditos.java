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
import apemi.model.core.entities.CredDetalle;
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

	public List<CredCabecera> findAllCabeceras() {
		return mDAO.findAll(CredCabecera.class);
	}

	public List<CredDetalle> findAllDetalles() {
		return mDAO.findAll(CredDetalle.class);
	}

	public CredCabecera findCredCabeceraById(int idCabecera) throws Exception {
		CredCabecera credCabecera;
		credCabecera = (CredCabecera) mDAO.findById(CredCabecera.class, idCabecera);
		return credCabecera;

	}
	
	public void pagarCuota(CredDetalle credDetalle) throws Exception {
		CredDetalle detalleActualizar = (CredDetalle) mDAO.findById(CredDetalle.class, credDetalle.getIdCreditoDet());
		detalleActualizar.setPagado(credDetalle.getPagado());
		mDAO.actualizar(detalleActualizar);
	}
	
	public void pagarCredito(CredCabecera credCabecera) throws Exception {
		CredCabecera cabeceraActualizar = (CredCabecera) mDAO.findById(CredCabecera.class, credCabecera.getIdCreditoCab());
		cabeceraActualizar.setPagado(credCabecera.getPagado());
		mDAO.actualizar(cabeceraActualizar);
	}

	public Timestamp getTimestamp(java.util.Date date) {
		return date == null ? null : new java.sql.Timestamp(date.getTime());
	}

	public List<CredDetalle> findDetalleByCabId(int idCabecera) {

		return mDAO.findWhere(CredDetalle.class, "o.credCabecera.idCreditoCab=" + idCabecera, "o.idCreditoDet");
	}

	public void GenerarCredito(int idAsociado, int idGarante, CredParametro parametroCredito, double monto, int plazo,
			List<DTOAmortizacion> detalleCredito) throws Exception {

		// Creacion de la Cabecera
		double tasaAnual = parametroCredito.getInteres().doubleValue();
		tasaAnual = tasaAnual / 100;
		double tasaPeriodica = (Math.pow(1.0 + tasaAnual, (1.0 / 12.0))) - 1.0;
		double valorCuota = monto * (tasaPeriodica / (1 - Math.pow(1 + tasaPeriodica, -plazo)));

		SegUsuario asociado = (SegUsuario) mDAO.findById(SegUsuario.class, idAsociado);
		SegUsuario administrador = (SegUsuario) mDAO.findById(SegUsuario.class, 1);
		CredGarante garante = (CredGarante) mDAO.findById(CredGarante.class, idGarante);

		System.out.println("parametro asociado: " + idAsociado);
		System.out.println("parametro garante:  " + idGarante);

		CredCabecera cabeceraCredito = new CredCabecera();
		cabeceraCredito.setSegUsuario1(asociado); // asociado
		cabeceraCredito.setSegUsuario2(administrador); // admin
		cabeceraCredito.setCredGarante(garante); // garante
		cabeceraCredito.setFechaCreacion(getTimestamp(new Date()));
		cabeceraCredito.setInteres(parametroCredito.getInteres());
		cabeceraCredito.setDegravamenTotal(parametroCredito.getSeguroDesgravamen());
		cabeceraCredito.setMontoTotal(new BigDecimal(monto));
		cabeceraCredito.setValorCuota(new BigDecimal(valorCuota));
		cabeceraCredito.setPlazo(plazo);
		cabeceraCredito.setPagado(false);
		List<CredDetalle> listaDetalle = new ArrayList<CredDetalle>();
		cabeceraCredito.setCredDetalles(listaDetalle);
		mDAO.insertar(cabeceraCredito);
		// crear detalle de creditos
		for (DTOAmortizacion detalle : detalleCredito) {
			CredDetalle nuevodetalle = new CredDetalle();
			nuevodetalle.setCredCabecera(cabeceraCredito);
			nuevodetalle.setInteresCuota(new BigDecimal(detalle.getInteres()));
			nuevodetalle.setFechaCuota(getTimestamp(detalle.getFechaCuota()));
			nuevodetalle.setDegravamenCuota(new BigDecimal(detalle.getSeguroDesgravamen()));
			nuevodetalle.setSaldoCuota(new BigDecimal(detalle.getSaldo()));
			nuevodetalle.setCapitalCuota(new BigDecimal(detalle.getCapital()));
			nuevodetalle.setPagado(false);
			mDAO.insertar(nuevodetalle);
			// listaDetalle.add(nuevodetalle);

		}
		// cabeceraCredito.setCredDetalles(listaDetalle);
		System.out.println(listaDetalle.size());

	}

	public List<DTOAmortizacion> generarAmortizacion(double monto, double nroCuotas, double tasaAnual,
			double porcentajeSegDesgra) {
		double interes;
		double capitalCuota;
		double desgravamen;
		Date fechaCuota = new Date();
		double saldo = monto;
		tasaAnual = tasaAnual / 100;
		double tasaPeriodica = (Math.pow(1.0 + tasaAnual, (1.0 / 12.0))) - 1.0;
		double valorCuota = monto * (tasaPeriodica / (1 - Math.pow(1 + tasaPeriodica, -nroCuotas)));
		System.out.println("Esta es el valor de la cuota" + valorCuota);
		List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
		for (int i = 0; i <= nroCuotas; i++) {
			if (i == 0) {

				listaAmortizacion.add(new DTOAmortizacion(i, 0, 0, 0, saldo, 0, fechaCuota));
			} else {
				interes = saldo * tasaPeriodica;
				capitalCuota = valorCuota - interes;
				desgravamen = (porcentajeSegDesgra / 100) * saldo;
				saldo = saldo - capitalCuota;
				fechaCuota = aumentarMesFecha(fechaCuota);
				listaAmortizacion
						.add(new DTOAmortizacion(i, valorCuota, interes, capitalCuota, saldo, desgravamen, fechaCuota));
			}

		}
		return listaAmortizacion;
	}

	public Date aumentarMesFecha(Date fechaCuota) {
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
			mes = -1;
		}

		cal.set(año, mes + 1, dia);

		return cal.getTime();
	}
}
