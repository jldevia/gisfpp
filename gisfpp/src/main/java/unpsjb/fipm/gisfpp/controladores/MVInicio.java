package unpsjb.fipm.gisfpp.controladores;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;

public class MVInicio {

	@Init
	public void init() {

	}

	@Command("verListaPersonas")
	public void verListaPersonas() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/persona/listarPersonas.zul");
	}

	@Command("verListaProyectos")
	public void verListaProyectos() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/proyecto/listarProyectos.zul");
	}

	@Command("verListaStaff")
	public void verListaStaff() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/staff/listaStaffFI.zul");
	}

	@Command("verListaOrganizaciones")
	public void verListaOrganizaciones() {
		Include include = (Include) Path.getComponent("/panelCentro");
		include.setSrc(null);
		include.setSrc("vistas/persona/listaOrganizaciones.zul");
	}

}// fin de la clase