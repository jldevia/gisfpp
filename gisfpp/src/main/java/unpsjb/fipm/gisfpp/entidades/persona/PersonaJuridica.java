package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@DiscriminatorValue("PJ")
public class PersonaJuridica extends Persona {

	private static final long serialVersionUID = 1L;

	public PersonaJuridica() {
		super();
	}

	public PersonaJuridica(String razonSocial) {
		super(razonSocial);
	}

	@Override
	@NotBlank(message = "Debe indicar la \"Raz�n Social\" para la Organizaci�n.")
	@Length(max = 80, message = "La \"Raz�n Social\" de la Organizaci�n no debe ser mayor a 80 caracteres.")
	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}

	// Es Persona Jur�dica, no posee DNI
	@Override
	public String getDni() {
		return null;
	}

	// Es Persona Jur�dica, no posee CUIL
	@Override
	public String getCuil() {
		return null;
	}

	@Override
	public String getCuit() {
		return getValorIdentificador(TIdentificador.CUIT);
	}

	// Es Persona Jur�dica, no posee Matricula
	@Override
	public String getMatricula() {
		return null;
	}

	// Es Persona Jur�dica, no posee Legajo
	@Override
	public String getLegajo() {
		return null;
	}

}// Fin de la clase PersonaJuridica
