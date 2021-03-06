package unpsjb.fipm.gisfpp.dao.persona;

import java.util.List;

import unpsjb.fipm.gisfpp.dao.DaoGenerico;
import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.persona.TIdentificador;

public interface IDaoPersonaFisica extends DaoGenerico<PersonaFisica, Integer> {
	
	public List<PersonaFisica> getxNombre(String nombre) throws Exception;
	
	public List<PersonaFisica> getxIdentificador(TIdentificador tipo, String patron) throws Exception;
	
}
