import java.util.Iterator;
import java.util.List;

import com.etrade.etws.account.Account;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List<Account> alist = new Login().getAccounts();
		Iterator<Account> al = alist.iterator();
		while (al.hasNext()) {
			Account a = al.next();
			System.out.println("===================");
			System.out.println("Account: " + a.getAccountId());
			System.out.println("===================");
		}

	}

	public static void handleError(Exception e) {
		System.err.println(e.getMessage());
		e.printStackTrace();
		System.exit(-1);
	}

}
