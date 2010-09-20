package se.chalmers.ibid.model.product;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import se.chalmers.ibid.model.category.Category;
import se.chalmers.ibid.model.exception.DuplicateInstanceException;

@Entity
@Table (name = "Evento")
@BatchSize(size=10)
public class Product {
	

	private Long idEvento;
	private String nombre;
	private Calendar fecha;
	private Category categoria;
	private Set<TipoApuesta> tiposApuesta;
	private Long version;
	
	public Product(){}
	
    public Product(String nombre, Calendar fecha, Category categoria) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.categoria = categoria;
		this.tiposApuesta = new HashSet<TipoApuesta>();
	}

	@Column(name="idEvento")
    @SequenceGenerator( 
         name="IdEventoGenerator", 
         sequenceName="EventoSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="IdEventoGenerator")
	public Long getIdEvento() {
		return idEvento;
	}
	
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	
	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="fecha")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFecha() {
		return fecha;
	}
	
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="idCategoria")
	public Category getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}
	
	@OneToMany(mappedBy="evento")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Set<TipoApuesta> getTiposApuesta() {
		return tiposApuesta;
	}
	
	public void setTiposApuesta(Set<TipoApuesta> tiposApuesta) {
		this.tiposApuesta = tiposApuesta;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public TipoApuesta addTipoApuesta(TipoApuesta tipoApuesta) throws DuplicateInstanceException{
		Set<TipoApuesta> lista = getTiposApuesta();
		
		Iterator<TipoApuesta> iterador = lista.iterator();
		while(iterador.hasNext()){
			if (iterador.next().getPregunta().equalsIgnoreCase(tipoApuesta.getPregunta())){
				throw new DuplicateInstanceException(tipoApuesta.getPregunta(),TipoApuesta.class.getName());
			}
		}
		
		lista.add(tipoApuesta);
		setTiposApuesta(lista);
		tipoApuesta.setEvento(this);
		return tipoApuesta;
	}
	
	

}
