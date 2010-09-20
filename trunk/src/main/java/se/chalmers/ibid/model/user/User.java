package se.chalmers.ibid.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import se.chalmers.ibid.model.account.Account;


@Entity
@Table (name = "User")
public class User {
	
	private Long userId;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private Account account;
	private Long version;
	
	public User(){}
	
    public User(String login, String password, String name,
			String surname, String email, Account account) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.account = account;
	}

	@Column(name="userId")
    @SequenceGenerator( 
         name="UserIdGenerator", 
         sequenceName="UserSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="UserIdGenerator")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="login")
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="surname")
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="accountId")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
