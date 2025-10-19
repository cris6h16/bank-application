package com.devsu.hackerearth.backend.account.model.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends Base {

	private Date date;
	private String type;
	private double amount;
	private double balance;

	@ManyToOne
	@JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_transaction_account"))
	private Account account;
}
