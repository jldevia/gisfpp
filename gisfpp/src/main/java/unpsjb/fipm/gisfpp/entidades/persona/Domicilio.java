package unpsjb.fipm.gisfpp.entidades.persona;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Domicilio {

	@Id
	@Column(name = "idDomicilio")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "altura", length = 4)
	private String altura;

	@Column(name = "calle", length = 50)
	private String calle;

	@Column(name = "num_piso", length = 3)
	private String piso;

	@Column(name = "num_dpto", length = 3)
	private String num_dpto;

	@Column(name = "codPostal", length = 5)
	private String cod_postal;

	@Column(name = "localidad", length = 50)
	private String localidad;

	@Column(name = "provincia", length = 50)
	private String provincia;

	public Domicilio() {
		super();
	}

	public Domicilio(String altura, String calle, String piso, String num_dpto, String localidad, String provincia) {
		super();
		this.altura = altura;
		this.calle = calle;
		this.piso = piso;
		this.num_dpto = num_dpto;
		this.localidad = localidad;
		this.provincia = provincia;
	}

	@NotBlank(message = "Debe indicar un n�mero de \"Altura\" para el Domicilio.")
	@Length(max = 4, message = "El n�mero de Altura no puede ser mayor a 9999")
	public String getAltura() {
		return altura;
	}

	public void setAltura(String numero) {
		this.altura = numero;
	}

	@NotBlank(message = "Debe indicar una \"Calle\" para el Domicilio.")
	@Length(max = 50, message = "La calle del Domicilio no debe ser mayor a 50 caracteres.")
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getNum_dpto() {
		return num_dpto;
	}

	public void setNum_dpto(String num_dpto) {
		this.num_dpto = num_dpto;
	}

	@NotBlank(message = "Debe indicar una \"Localidad\" para el Domicilio.")
	@Length(max = 50, message = "La \"Localidad\" del Domicilio no debe ser mayor a 50 caracteres.")
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@NotBlank(message = "Debe indicar una \"Provincia\" para el Domicilio.")
	@Length(max = 50, message = "La \"Provincia\" del Domicilio no debe ser mayor a 50 caracteres.")
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCod_postal() {
		return cod_postal;
	}

	public void setCod_postal(String cod_postal) {
		this.cod_postal = cod_postal;
	}

}// Fin clase Entity Domicilio
