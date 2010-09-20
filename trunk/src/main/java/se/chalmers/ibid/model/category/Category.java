package se.chalmers.ibid.model.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table (name = "Categoria")
@BatchSize(size=10)
public class Category {
	
	private Long idCategoria;
	private String nombre;
	
	public Category(){}
	
    public Category(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="idCategoria")
    @SequenceGenerator( 
         name="IdCategoriaGenerator", 
         sequenceName="CategoriaSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="IdCategoriaGenerator")
	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
