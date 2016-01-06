package unpsjb.fipm.gisfpp.servicios.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.servicios.IServicioGenerico;

public interface IServicioPF extends IServicioGenerico<PersonaFisica, Integer> {
	
	/**
	 * Devuelve el listado de personas cuyo nombre contienen el patron de busqueda 
	 * pasado como parametro
	 * @param patronNombre (String): patron de busqueda
	 * @return Listado de Personas Fisicas (List<PersonaFisica>)
	 * @throws Exception
	 */
	public List<PersonaFisica> getxNombre(String patronNombre) throws Exception;
	
	/**
	 * 
	 * @param campo (String): Tipo de identificador por el que se desea realizar la busqueda
	 * @param patronValor (String): Valor del identificador a buscar
	 * @return (List<PersonaFisica>) Listado de personas resultado de la busqueda.
	 * @throws Exception
	 */
	public List<PersonaFisica> getxIdentificador(String campo, String patronValor) throws Exception;
}