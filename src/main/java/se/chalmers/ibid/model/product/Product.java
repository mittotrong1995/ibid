package se.chalmers.ibid.model.product;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

import se.chalmers.ibid.model.category.Category;

@Entity
@Table (name = "Product")
@BatchSize(size=10)
public class Product {
	

	private Long productId;
	private String name;
	private double startprice;
	private Calendar date;
	private Category category;
	private Long version;
	
	public Product(){}
	
    public Product(String name, double startprice, Calendar date, Category category) {
		this.name = name;
		this.date = date;
		this.category = category;
	}

	@Column(name="productId")
    @SequenceGenerator( 
         name="ProductIdGenerator", 
         sequenceName="ProductSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="ProductIdGenerator")
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    @Column(name="startprice")
	public double getStartprice() {
		return startprice;
	}

	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="idCategory")
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
