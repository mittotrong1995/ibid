package se.chalmers.ibid.model.product;

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("eventoDao")
public class ProductDaoHibernate extends GenericDaoHibernate<Product, Long> implements ProductDao{
	
	@SuppressWarnings("unchecked")
	public List<Product> buscarEventosPorNombre(String nombre) throws InstanceNotFoundException{
		String stringConsulta;
		stringConsulta = "SELECT e FROM Evento e WHERE e.nombre LIKE :nombre";
		Query consulta = getSession().createQuery(stringConsulta);
		consulta.setParameter("nombre", nombre);
		List<Product> eventos;
		try {
			eventos = (List<Product>) consulta.list();
			if (eventos.isEmpty()) throw new InstanceNotFoundException(nombre, Product.class.getName());
		} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
		return eventos;
	}
	
	//Cuando tipo es verdadero, genera una consulta para recuperar los eventos. En caso contrario, recupera la cuenta.
	private Query prepararBusquedaUsuario(List<String> palabrasClave, Long idCategoria, boolean tipo){
    	
		int numPalabrasClave = palabrasClave.size();
		String stringConsulta;
    	if(tipo) stringConsulta = "SELECT e FROM Evento e WHERE e.fecha >= :fechaActual";
    	else stringConsulta = "SELECT COUNT(e) FROM Evento e WHERE e.fecha >= :fechaActual";
    	
    	switch(numPalabrasClave){
    		case 0: break;
    		default: 
    				for (int i=0; i<numPalabrasClave; i++) 
    						stringConsulta = stringConsulta + " AND UPPER(e.nombre) LIKE :palabra" + i;
    				break;
    	}
    	
    	if (idCategoria!=null) stringConsulta = stringConsulta  + " AND e.categoria.idCategoria = :idCategoria";
    	stringConsulta = stringConsulta + " ORDER BY e.fecha asc";
    	
		Query consulta = getSession().createQuery(stringConsulta);
		
		consulta.setCalendar("fechaActual", Calendar.getInstance());
		
		for (int i=0; i<numPalabrasClave; i++){	
			consulta = consulta.setParameter("palabra" + i, "%" + palabrasClave.get(i).toUpperCase() + "%");
		}
		
		if (idCategoria!=null) consulta = consulta.setParameter("idCategoria", idCategoria);
		
		return consulta;
	}
	
	private Query prepararBusquedaAdministrador(List<String> palabrasClave, Long idCategoria, boolean tipo){
    	
		int numPalabrasClave = palabrasClave.size();
		String stringConsulta;
    	if(tipo) stringConsulta = "SELECT e FROM Evento e " +
    							  "WHERE (e NOT IN (SELECT DISTINCT t.evento FROM TipoApuesta t) " + 
    							  "OR e IN (SELECT DISTINCT t.evento FROM TipoApuesta t " +
    							                    "WHERE t NOT IN (SELECT DISTINCT t FROM TipoApuesta t WHERE t.ganadorasEstablecidas=TRUE)))";
    	else stringConsulta = "SELECT COUNT(e) FROM Evento e " +
		  					  "WHERE (e NOT IN (SELECT DISTINCT t.evento FROM TipoApuesta t) " + 
		                      "OR e IN (SELECT DISTINCT t.evento FROM TipoApuesta t " +
		                               "WHERE t NOT IN (SELECT DISTINCT t FROM TipoApuesta t WHERE t.ganadorasEstablecidas=TRUE)))";
    	
    	switch(numPalabrasClave){
    		case 0: break;
    		default: 
    				for (int i=0; i<numPalabrasClave; i++) 
    						stringConsulta = stringConsulta + " AND UPPER(e.nombre) LIKE :palabra" + i;
    				break;
    	}
    	
    	if (idCategoria!=null) stringConsulta = stringConsulta  + " AND e.categoria.idCategoria = :idCategoria";
    	stringConsulta = stringConsulta + " ORDER BY e.fecha asc";
    	
		Query consulta = getSession().createQuery(stringConsulta);
		
		for (int i=0; i<numPalabrasClave; i++){	
			consulta = consulta.setParameter("palabra" + i, "%" + palabrasClave.get(i).toUpperCase() + "%");
		}
		
		if (idCategoria!=null) consulta = consulta.setParameter("idCategoria", idCategoria);
		
		return consulta;
	}
	
	//Este metodo puede recibir idCategoria a null, pero la lista la tiene que recibir, aunque sea vacia
	public int getNumeroEventosPorEspecificaciones(List<String> palabrasClave, Long idCategoria, boolean esAdmin){		
	    try {
	    	Query consulta;
	    	if (!esAdmin) consulta = prepararBusquedaUsuario(palabrasClave, idCategoria, false);
	    	else consulta = prepararBusquedaAdministrador(palabrasClave, idCategoria, false);
	    	long numero = (Long) consulta.uniqueResult();
    		return (int) numero;
    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> buscarEventosPorEspecificaciones(List<String> palabrasClave, Long idCategoria, int startIndex, int count, boolean esAdmin) {
	    try {
	    	Query consulta;
	    	if (!esAdmin) consulta = prepararBusquedaUsuario(palabrasClave, idCategoria, true);
	    	else consulta = prepararBusquedaAdministrador(palabrasClave, idCategoria, true);   		
    		return consulta.setFirstResult(startIndex).
    						setMaxResults(count).
    						list();

    	} catch (HibernateException e) {
            throw convertHibernateAccessException(e);
        }
	}	
	
}
