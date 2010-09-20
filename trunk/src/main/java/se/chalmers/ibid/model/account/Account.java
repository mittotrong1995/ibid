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
@Table (name = "Cuenta")
public class Account {
	

	private Long idCuenta;
	private double saldo;
	private Long version;

	public Account(){}
	
    public Account(double saldo) {
		this.saldo = saldo;
	}

	@Column(name="idCuenta")
    @SequenceGenerator( 
         name="IdCuentaGenerator", 
         sequenceName="CuentaSeq")
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,
                    generator="IdCuentaGenerator")
	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

    @Column(name="saldo")
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	

}
