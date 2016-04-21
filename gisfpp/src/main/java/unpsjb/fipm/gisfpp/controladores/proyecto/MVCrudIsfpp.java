package unpsjb.fipm.gisfpp.controladores.proyecto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.Button;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import unpsjb.fipm.gisfpp.entidades.persona.PersonaFisica;
import unpsjb.fipm.gisfpp.entidades.proyecto.EEstadosIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.Isfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.MiembroStaffIsfpp;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.servicios.proyecto.IServiciosIsfpp;
import unpsjb.fipm.gisfpp.util.GisfppWorkflowException;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class MVCrudIsfpp {

	private Logger log;
	private Isfpp item;
	private IServiciosIsfpp servicio;
	private SubProyecto perteneceA;
	private String modo;
	private boolean creando;
	private boolean editando;
	private boolean ver;
	private HashMap<String, Object> args;

	@Init
	@NotifyChange({ "modo", "item", "creando", "editando", "ver" })
	public void init() throws Exception {
		log = UtilGisfpp.getLogger();
		servicio = (IServiciosIsfpp) SpringUtil.getBean("servIsfpp");
		args = (HashMap<String, Object>) Executions.getCurrent().getAttribute("argsCrudIsfpp");
		perteneceA = (SubProyecto) args.get("perteneceA");
		modo = (String) args.get("modo");
		switch (modo) {
		case UtilGisfpp.MOD_NUEVO: {
			item = new Isfpp(perteneceA, "", "", new Date(), new Date(), "",null, null);
			creando = true;
			editando = (ver = false);
			break;
		}
		case UtilGisfpp.MOD_EDICION: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (ver = false);
			editando = true;
			break;
		}
		case UtilGisfpp.MOD_VER: {
			item = servicio.getInstancia((Integer) args.get("idItem"));
			item.setPerteneceA(perteneceA);
			creando = (editando = false);
			ver = true;
		}
		}

	}

	public Isfpp getItem() {
		return item;
	}

	public String getModo() {
		return modo;
	}

	public boolean isCreando() {
		return creando;
	}

	public boolean isEditando() {
		return editando;
	}

	public boolean isVer() {
		return ver;
	}

	@Command("guardar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void guardar() throws Exception {
		try {
			if (creando) {
				servicio.persistir(item);
				Clients.showNotification("Nueva ISFPP guardada", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
						3500);
			}
			if (editando) {
				servicio.editar(item);
				Clients.showNotification("ISFPP actualizada.", Clients.NOTIFICATION_TYPE_INFO, null,
						"top_right", 3500);
			}
			creando = editando = false;
			ver=true;
		} catch (ConstraintViolationException cve) {
			Messagebox.show(UtilGisfpp.getMensajeValidations(cve), "Error: Validaci�n de datos.", Messagebox.OK,
					Messagebox.ERROR);
		} catch (DataIntegrityViolationException | org.hibernate.exception.ConstraintViolationException dive) {
			Messagebox.show(dive.getMessage(), "Error: Violacion Restricciones de Integridad BD.", Messagebox.OK,
					Messagebox.ERROR);
		}catch (GisfppWorkflowException wfe) {
			try {
				servicio.eliminar(item);
				creando = editando = false;
				ver=true;
				throw wfe;
			} catch (Exception e) {
				log.error(this.getClass().getName(), wfe);
				throw wfe;
			}
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Command("nuevaIsfpp")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void nuevaIsfpp() {
		item = new Isfpp(perteneceA, "", "", new Date(), new Date(), "",null, null);
		creando = true;
		editando = (ver = false);
	}

	@Command("editarIsfpp")
	@NotifyChange({ "creando", "editando", "ver" })
	public void editar() {
		editando = true;
		creando = (ver = false);
	}

	@Command("cancelar")
	@NotifyChange({ "item", "creando", "editando", "ver" })
	public void cancelar() {
		creando = (editando = false);
		ver = true;
	}

	@Command("salir")
	public void salir() {
		Map<String, Object> map = new HashMap<>();
		if(modo.equals(UtilGisfpp.MOD_NUEVO) || modo.equals(UtilGisfpp.MOD_EDICION)){
			map.put("actualizar", true);
		}else{
			map.put("actualizar", false);
		}
		BindUtils.postGlobalCommand(null, null, "cerrandoTab", map);
		Tab tab = (Tab) args.get("tab");
		tab.close();
	}
	
	@Command("verDlgStaff")
	public void verDlgStaff(@BindingParam("modo") String arg1, @BindingParam("itemStaff") MiembroStaffIsfpp arg2){
		Map<String, Object> args = new HashMap<>();
		args.put("modo", arg1);
		args.put("item", arg2);
		args.put("de", item);
		Window dlg = (Window) Executions.createComponents("vistas/proyecto/dlgStaffIsfpp.zul", null, args);
		dlg.doModal();
	}
	
	@GlobalCommand("retornoDlgStaffIsfpp"	)
	@NotifyChange("item")
	public void retornoDlgStaffIsfpp(@BindingParam("modo")String arg1, @BindingParam("newItem") MiembroStaffIsfpp arg2){
		if(arg1.equals(UtilGisfpp.MOD_NUEVO)){
			item.addMiembroStaff(arg2);
		}
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	@Command("quitarMiembroSatff")
	@NotifyChange("item")
	public void quitarMiembroStaff(@BindingParam("itemStaff") MiembroStaffIsfpp arg1){
		item.removerMiembroStaff(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	@Command("verDlgLkpPracticante")
	public void verDlgLkpPracticante(){
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgLookupPersona.zul", null, null);
		dlg.doModal();
	}
	
	@GlobalCommand("obtenerLkpPersona")
	@NotifyChange("item")
	public void retornoLkpPracticante(@BindingParam("seleccion")PersonaFisica arg1){
		item.addPracticante(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	@Command("quitarPracticante")
	@NotifyChange("item")
	public void quitarPracticante(@BindingParam("practicante") PersonaFisica arg1){
		item.removerPracticante(arg1);
		Clients.showNotification("Guarde cambios para confirmar la operacion.", Clients.NOTIFICATION_TYPE_WARNING, null, 
				"top_right", 4000);
	}
	
	//Dialogo para ver los datos de contacto de una persona
	@Command("dlgVerDatosContacto")
	public void verDlgVerDatosContacto(@BindingParam("itemPersona") PersonaFisica arg1) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("itemPersona", arg1);
		Window dlg = (Window) Executions.createComponents("vistas/persona/dlgVerDatosContacto.zul", null, map);
		dlg.doModal();
	}
	
	@Command("activarIsfpp")
	@NotifyChange("item")
	public void activarIsfpp() throws Exception{
		servicio.activarIsfpp(item.getId());
		Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
				3500);
		servicio.refrescarInstancia(item);
	}
	
	@Command("suspenderIsfpp")
	@NotifyChange("item")
	public void suspenderIsfpp() throws Exception{
		Messagebox.show("Desea realmente \"Suspender\" esta Isfpp ? Si la suspende todo Workflow activo asociada a la misma"
				+ " tambi�n ser� suspendido.", "Gisfpp: Suspendiendo Isfpp", new Button [] {Button.YES, Button.NO}
				, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
					
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.suspenderIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
									3500);	
						}
					}
				});
		servicio.refrescarInstancia(item);
	}
	
	@Command("cancelarIsfpp")
	@NotifyChange("item")
	public void cancelarIsfpp() throws Exception{
		Messagebox.show("Desea realmente \"Cancelar\" esta Isfpp ? Si la cancela, no podr� volver a activarla y todo Workflow activo asociada a la misma"
				+ " ser� eliminado.", "Gisfpp: Cancelando Isfpp", new Button [] {Button.YES, Button.NO}
				, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
					
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.cancelarIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
									3500);	
						}
					}
				});
		servicio.refrescarInstancia(item);
	}
	
	@Command("concluirIsfpp")
	@NotifyChange("item")
	public void concluirIsfpp() throws Exception{
		Messagebox.show("Desea realmente dar por \"Concluida\" esta Isfpp?", "Gisfpp: Isfpp concluida",
				new Button [] {Button.YES, Button.NO}, Messagebox.QUESTION, new EventListener<Messagebox.ClickEvent>() {
					
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (event.getName().equals(Messagebox.ON_YES)) {
							servicio.concluirIsfpp(item.getId());
							Clients.showNotification("Estado de Isfpp actualizado.", Clients.NOTIFICATION_TYPE_INFO, null, "top_right",
									3500);	
						}
					}
				});
		servicio.refrescarInstancia(item);
	}

}// fin de la clase
