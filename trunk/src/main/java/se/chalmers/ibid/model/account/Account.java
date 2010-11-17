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
	private double availableMoney;
	private double blockedMoney;
	private Long version;

	public Account(){}
	
    public Account(double money) {
		this.availableMoney = money;
		this.blockedMoney = 0;
	}

	@Column(name="accountId")
    @SequenceGenerator( 
         name="AccountIdGenerator", 
         sequenceName="AccountSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="AccountIdGenerator")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

    @Column(name="availableMoney")
	public double getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(double money) {
		this.availableMoney = money;
	}
	
    @Column(name="blockedMoney")
	public double getBlockedMoney() {
		return blockedMoney;
	}

	public void setBlockedMoney(double money) {
		this.blockedMoney = money;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	

}
