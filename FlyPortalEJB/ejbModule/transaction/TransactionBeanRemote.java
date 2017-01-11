package transaction;

import java.util.LinkedList;

import javax.ejb.Remote;

@Remote
public interface TransactionBeanRemote {

	LinkedList<Transaction> getTransactions();

}
