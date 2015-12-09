package unpsjb.fipm.gisfpp.dao.proyecto;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.fipm.gisfpp.entidades.proyecto.Proyecto;
import unpsjb.fipm.gisfpp.entidades.proyecto.SubProyecto;
import unpsjb.fipm.gisfpp.util.UtilGisfpp;

public class DaoSubProyecto extends HibernateDaoSupport implements IDaoSubProyecto {

	private Logger log = UtilGisfpp.getLogger();

	@Override
	@Transactional(readOnly = false)
	public Integer crear(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().save(instancia);
			return instancia.getId();
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void actualizar(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().update(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void eliminar(SubProyecto instancia) throws DataAccessException {
		try {
			getHibernateTemplate().delete(instancia);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SubProyecto> recuperarTodo() throws DataAccessException {
		String query = "form SubProyecto as sp inner join fetch sp.proyecto";
		try {
			return (List<SubProyecto>) getHibernateTemplate().find(query, null);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public SubProyecto recuperarxId(Integer id) throws DataAccessException {
		SubProyecto result;
		try {
			result = getHibernateTemplate().get(SubProyecto.class, id);
			Hibernate.initialize(result.getProyecto());
			return result;
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<SubProyecto> listadoSubProyectos(Proyecto proyecto) throws DataAccessException, HibernateException {
		String query = "from SubProyecto as sp where sp.proyecto = ?";
		try {
			return (List<SubProyecto>) getHibernateTemplate().find(query, proyecto);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			throw e;
		}
	}

}// fin de la clase