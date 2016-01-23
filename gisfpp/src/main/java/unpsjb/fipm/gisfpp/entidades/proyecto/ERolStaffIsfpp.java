package unpsjb.fipm.gisfpp.entidades.proyecto;

public enum ERolStaffIsfpp {
	
	RESPONSABLE("Responsable de Isfpp"), TUTOR_ACADEMICO("Tutor Acad�mico"), TUTOR_EXTERNO("Tutor Externo");
	
	private String titulo;

	private ERolStaffIsfpp(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo(){
		return titulo;
	}

}
