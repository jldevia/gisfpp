package unpsjb.fipm.gisfpp.entidades.persona;

public enum TPersona {
	PF("Persona F�sica"), PJ("Persona Jur�dica");

	private String descripcion;

	private TPersona(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
