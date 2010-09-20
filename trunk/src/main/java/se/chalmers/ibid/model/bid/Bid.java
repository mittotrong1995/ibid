package se.chalmers.ibid.model.bid;

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

import se.chalmers.ibid.model.account.Account;
import se.chalmers.ibid.model.product.Product;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table (name = "Bid")
public class Bid {
	

	private Long bidId;
	private Account account;
	private Product product;
	private Calendar date;
	private double money;

	
	public Bid(){}
	
    public Bid(Account account, Product product, double money) {
		this.account = account;
		this.product = product;
		this.money = money;
		this.date = Calendar.getInstance();
	}

	@Column(name="bidId")
    @SequenceGenerator( 
         name="BidIdGenerator", 
         sequenceName="BidSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="BidIdGenerator")
	public Long getBidId() {
		return bidId;
	}
	
	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="accountId")
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="productId")
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	@Column(name="money")
	public double getMoney() {
		return money;
	}
	
	public void setMoney(double money) {
		this.money = money;
	}	

}
