package unpsjb.fipm.gisfpp.entidades.proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "sub_proyecto")
public class SubProyecto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "proyectoId", nullable = false, foreignKey=@ForeignKey(name="fk_proyecto_subproyecto"))
	private Proyecto perteneceA;

	@Column(length = 80, name = "titulo", nullable = false)
	private String titulo;

	@Column(length = 500)
	private String descripcion;
	
	@Lob
	private String detalle;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "perteneceA")
	private List<Isfpp> instanciasIsfpp;

	public SubProyecto() {
		super();
	}

	public SubProyecto(Proyecto proyecto, String titulo, String descripcion) {
		super();
		this.perteneceA = proyecto;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.instanciasIsfpp = new ArrayList<>();
	}

	public Integer getId() {
		return Id;
	}

	protected void setId(Integer id) {
		Id = id;
	}

	public Proyecto getPerteneceA() {
		return perteneceA;
	}

	public void setPerteneceA(Proyecto proyecto) {
		this.perteneceA = proyecto;
	}

	@NotBlank(message = "Debe especificarle un \"t�tulo\" al Sub-Proyecto.")
	@Length(max = 80, message = "El \"t�tulo\" del Sub-Proyecto no debe ser mayor a 80 caracteres.")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Length(message = "Las \"descripcion\" del Sub-Proyecto no debe superar los 500 caracteres.")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Isfpp> getInstanciasIsfpp() {
		return instanciasIsfpp;
	}

	public void setInstanciasIsfpp(List<Isfpp> instanciasIsfpp) {
		this.instanciasIsfpp = instanciasIsfpp;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	
}// fin de la entidad SubProyecto
