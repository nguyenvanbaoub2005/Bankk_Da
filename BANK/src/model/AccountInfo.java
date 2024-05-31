package model;

import java.sql.Date;
import java.util.ArrayList;

class AccountInfo {
	    private double balance;
	    private ArrayList<Transaction> transactions;

	    
	
		public AccountInfo(double balance, ArrayList<Transaction> transactions) {
			super();
			this.balance = balance;
			this.transactions = transactions;
		}

		public double getBalance() {
	        return balance;
	    }

	    public ArrayList<Transaction> getTransactions() {
	        return transactions;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }

	    public void addTransaction(Transaction transaction) {
	        transactions.add(transaction);
	    }
	}

