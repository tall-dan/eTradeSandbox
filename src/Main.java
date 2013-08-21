import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.etrade.etws.account.Account;
import com.etrade.etws.account.AccountBalanceResponse;
import com.etrade.etws.account.AccountPosition;
import com.etrade.etws.account.AccountPositionsResponse;
import com.etrade.etws.market.QuoteData;
import com.etrade.etws.market.QuoteResponse;
import com.etrade.etws.order.PlaceEquityOrderResponse;

public class Main {

	// public final static Logger logger = Logger.getLogger(Main.class
	// .getName());
	/**
	 * @param args
	 */
	public static PrintWriter writer;

	public static void main(String[] args) {
		setupLog();
		Account a =getCashAccount();
		printPostitions(a);
		MarketManager.getQuotes();
		OrderManager.viewCurrentOrders(a);
		PlaceEquityOrderResponse resp=OrderManager.placeOrder(a);
		OrderManager.viewCurrentOrders(a);
		printPostitions(a);
		writer.close();

	}
	
	
	
	private static Account getCashAccount() {
		List<Account> alist = AccountManager.getAccounts();
		Account a = alist.get(6);
		AccountBalanceResponse br;
		br = AccountManager.getAccountBalance(a);
		writer.println("===================");
		writer.println("Account: " + a.getAccountId());
		writer.println("Total Securities mkt val: "
				+ br.getAccountBalance().getTotalSecuritiesMktValue());
		writer.println("Cash Available for withdrawal: "
				+ br.getAccountBalance().getCashAvailableForWithdrawal());
		writer.println("NetAccountValue: " + a.getNetAccountValue());
		return a;
	}
	
	
	private static void printPostitions(Account a){
		AccountPositionsResponse positions = AccountManager.getAcctPosition(a);
		Iterator<AccountPosition> pl = positions.getResponse().iterator();
		writer.println("Positions:");
		while (pl.hasNext()) {
			writer.println("===================");
			AccountPosition p = pl.next();

			writer.println("Description: " + p.getDescription());
			writer.println("Cost Basis: " + p.getCostBasis());// market val
																// + $10
																// commission
			writer.println("Market Value: " + p.getMarketValue());
			writer.println("Current Price: " + p.getCurrentPrice());
			p.getProductId().getTypeCode();// This should be EQ for everything
											// we want to do

		}
	}

	private static void setupLog() {
		try {
			writer = new PrintWriter("logs/output.log", "UTF-8");
			writer.println(new Date().toString());
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			handleError(e);
		}
	}

	/*
	 * private static void setupLog() { FileHandler fh; try {
	 * 
	 * // This block configure the logger with handler and formatter fh = new
	 * FileHandler("logs/output.log"); logger.addHandler(fh);
	 * //logger.setLevel(Level.ALL); SimpleFormatter formatter = new
	 * SimpleFormatter(); fh.setFormatter(formatter);
	 * 
	 * // the following statement is used to log any messages } catch
	 * (SecurityException | IOException e) { handleError(e); }
	 * 
	 * }
	 */

	public static void handleError(Exception e) {
		System.err.println(e.getMessage());
		e.printStackTrace();
		writer.close();
		System.exit(-1);
	}

}
