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
@Table (name = "Usuario")
public class User {
	
	private Long idUsuario;
	private String login;
	private String password;
	private String nombre;
	private String apellidos;
	private String email;
	private Account cuenta;
	private Long version;
	
	public User(){}
	
    public User(String login, String password, String nombre,
			String apellidos, String email, Account cuenta) {
		this.login = login;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.cuenta = cuenta;
	}

	@Column(name="idUsuario")
    @SequenceGenerator( 
         name="IdUsuarioGenerator", 
         sequenceName="UsuarioSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="IdUsuarioGenerator")
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
	
	@Column(name="nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name="apellidos")
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="idCuenta")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
	public Account getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(Account cuenta) {
		this.cuenta = cuenta;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
