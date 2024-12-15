package bankY;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "account_number", unique = true, nullable = false)
	private String accountNumber;
	
	@Column(name = "balance", nullable = false)
	private double balance;

	@Column(name = "holder_name", nullable = false)
	private String holderName;
	
	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
