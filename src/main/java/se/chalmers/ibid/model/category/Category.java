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
@Table (name = "Category")
@BatchSize(size=10)
public class Category {
	
	private Long categoryId;
	private String name;
	
	public Category(){}
	
    public Category(String name) {
		this.name = name;
	}

	@Column(name="categoryId")
    @SequenceGenerator( 
         name="CategoryIdGenerator", 
         sequenceName="CategorySeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="CategoryIdGenerator")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
