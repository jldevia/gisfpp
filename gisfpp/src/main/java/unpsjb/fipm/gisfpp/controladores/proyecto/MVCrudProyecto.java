package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;

import unpsjb.fipm.gisfpp.entidades.proyecto.EstadoProyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.TipoProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.ServiciosProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudProyecto {

	private ServiciosProyecto servicio;
	private Proyecto seleccionado;
	private boolean creando = false;
	private boolean editando = false;
	private boolean ver = false;
	private String modo = null;
	private String titulo = " ";

	@Init
	@NotifyChange({ "modo", "creando", "editando", "ver", "titulo" })
	public void init() {
		servicio = (ServiciosProyecto) SpringUtil.getBean("servProyecto");
		final HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent()
				.getAttribute("modosProyecto");
		modo = (String) map.get("modo");
		if (modo.equals("nuevo")) {
			seleccionado = new Proyecto("", "", "", "", null, EstadoProyecto.GENERADO, null, null, "");
			creando = true;
			editando = false;
			ver = false;
			titulo = "Proyecto: Nuevo";
		} else if (modo.equals("edicion")) {
			seleccionado = acomodarProyecto((Proyecto) map.get("itemSeleccionado"));
			editando = true;
			creando = false;
			ver = false;
			titulo = "Proyecto: Edicion";
		} else if (modo.equals("ver")) {
			seleccionado = acomodarProyecto((Proyecto) map.get("itemSeleccionado"));
			ver = true;
			creando = false;
			editando = false;
			titulo = "Proyecto: Ver detalle";
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public List<TipoProyecto> getTipos() {
		return Arrays.asList(TipoProyecto.values());
	}

	public String getModo() {
		return modo;
	}

	public Proyecto getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Proyecto proyecto) {
		seleccionado = proyecto;
	}

	public Boolean getCreando() {
		return creando;
	}

	public Boolean getEditando() {
		return editando;
	}

	public Boolean getVer() {
		return ver;
	}

	@Command("nuevoProyecto")
	@NotifyChange({ "seleccionado", "creando" })
	public void nuevoProyecto() {
		seleccionado = new Proyecto("", "", "", "", null, EstadoProyecto.GENERADO, null, null, "");
		creando = true;
		editando = false;
		ver = false;
	}

	@Command("guardar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void guardar() throws Exception {
		if (creando) {
			try {
				int id = servicio.guardarProyecto(seleccionado);
				Clients.showNotification("Se creo un nuevo Proyecto con Id: " + id, Clients.NOTIFICATION_TYPE_INFO,
						null, "top_right", 3500);
				creando = false;
				editando = false;
				ver = true;
			} catch (ConstraintViolationException cve) {
				Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos", Messagebox.OK,
						Messagebox.ERROR);
			} catch (Exception e) {
				System.err.println("Exception original: " + e.getCause().toString());
				System.err.println("Causa: " + e.getMessage());
				throw e;
			}
		}
		if (editando) {
			try {
				servicio.editarProyecto(seleccionado);
				Clients.showNotification("Proyecto ( Id: " + seleccionado.getId() + ") guardado. ",
						Clients.NOTIFICATION_TYPE_INFO, null, "top_right", 3500);
				creando = false;
				editando = false;
				ver = true;
			} catch (ConstraintViolationException cve) {
				Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos", Messagebox.OK,
						Messagebox.ERROR);
			} catch (Exception e) {
				System.err.println("Exception original: " + e.getCause().toString());
				System.err.println("Causa: " + e.getMessage());
				throw e;
			}
		}
	}

	@Command("cancelar")
	@NotifyChange({ "creando", "editando", "ver" })
	public void cancelar() {
		if (modo == "edicion") {
			volver();
		} else {
			seleccionado = null;
			creando = false;
			editando = false;
			ver = true;
		}
	}

	@Command("volver")
	public void volver() {
		Panel panel = (Panel) Path.getComponent("/panelCentro/pnlCrudProyecto");
		Include include = (Include) Path.getComponent("/panelCentro");
		if (panel != null) {
			panel.onClose();
			include.setSrc(null);
			include.setSrc("vistas/proyecto/listarProyectos.zul");
		}
	}

	private Proyecto acomodarProyecto(Proyecto proyecto) {
		Proyecto proxy = new Proyecto("", "", "", "", null, null, null, null, "");
		proxy.setId(proyecto.getId());
		proxy.setCodigo(proyecto.getCodigo());
		proxy.setResolucion(proyecto.getResolucion());
		proxy.setTitulo(proyecto.getTitulo());
		proxy.setDescripcion(proyecto.getDescripcion());
		proxy.setTipo(proyecto.getTipo());
		proxy.setEstado(proyecto.getEstado());
		// debo volver las fechas tipo java.sql.Date que devuelve hibernate a
		// java.util.Date para poder utilizar en el .zul
		proxy.setFecha_inicio(new java.util.Date(proyecto.getFecha_inicio().getTime()));
		proxy.setFecha_fin(new java.util.Date(proyecto.getFecha_fin().getTime()));
		proxy.setDetalle(proxy.getDetalle());

		return proxy;
	}

}