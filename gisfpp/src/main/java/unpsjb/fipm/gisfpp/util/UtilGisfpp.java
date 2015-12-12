package unpsjb.fipm.gisfpp.util;

import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Jose Devia
 *
 */
public class UtilGisfpp {

	// Constantes utilizadas en el sistema
	public static final String MOD_NUEVO = "NUEVO";
	public static final String MOD_EDICION = "EDICION";
	public static final String MOD_VER = "VER";
	private static Logger logger;

	public static String getMensajeValidations(ConstraintViolationException cve) {
		StringBuffer acumulados = new StringBuffer("");
		int contador = 0;
		for (ConstraintViolation violation : cve.getConstraintViolations()) {
			contador = contador + 1;
			acumulados.append(contador + ") " + violation.getMessage() + "\n");
		}

		return new String(acumulados);
	}

	public static Logger getLogger() {
		if (logger == null) {
			logger = LoggerFactory.getLogger("log_gisfpp");
		}
		return logger;
	}

	/**
	 * Comprueba si el usuario actualmente conectado posee el rol en el Staff-Fi
	 * pasado como parámetro.
	 * 
	 * @param rol
	 * @return "True" si el usuario posee el rol en el Staff-Fi, "false" si no
	 *         lo posee.
	 */
	public static boolean rolStaffFi(String rol) {
		List<SimpleGrantedAuthority> autorizaciones = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		for (SimpleGrantedAuthority autorizacion : autorizaciones) {
			if ((autorizacion.getAuthority().contains("Staff-Fi")) && (autorizacion.getAuthority().contains(rol))) {
				return true;
			}
		}
		return false;
	}

}// fin de la clase
