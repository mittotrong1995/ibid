package se.chalmers.ibid.model.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table (name = "Account")
public class Account {
	

	private Long accountId;
	private double money;
	private Long version;

	public Account(){}
	
    public Account(double money) {
		this.money = money;
	}

	@Column(name="accountId")
    @SequenceGenerator( 
         name="AccountIdGenerator", 
         sequenceName="AccountSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="AccountIdGenerator")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

    @Column(name="money")
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	

}
