package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.AssertTrue;

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
	@NotBlank(message = "Debe indicar la \"Raz�n Social\" de la Organizaci�n.")
	@Length(max = 80, message = "La \"Raz�n Social\" de la Organizaci�n no debe ser mayor a 80 caracteres.")
	public String getNombre() {
		return nombre;
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

	@AssertTrue(message = "El N� de Identificaci�n permitido para una Organizacion es el CUIT solamente.")
	private boolean isIdentificacionesValidas() {
		if (getIdentificadores() == null) {
			return true;
		} else if (getIdentificadores().isEmpty()) {
			return true;
		} else if (getIdentificadores().size() == 1
				&& (getIdentificadores().get(0).getTipo().equals(TIdentificador.CUIT))) {
			return true;
		} else {
			return false;
		}
	}

}// Fin de la clase PersonaJuridica
