package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum EEstadosIsfpp {

	GENERADA("Generada"), ACTIVA("Activa"), SUSPENDIDA("Suspendida"), CANCELADA("Cancelada"), CONCLUIDA("Concluida");

	private String titulo;

	private EEstadosIsfpp(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}
}
