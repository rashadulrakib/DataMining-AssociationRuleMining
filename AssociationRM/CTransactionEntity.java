package AssociationRM;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author rakib
 * This class contains the properties of transactions
 */
public class CTransactionEntity {
	
	ArrayList<ArrayList<String>> transactions;
	HashMap<String, String> itemCodes;
	ArrayList<String> columnNames;
	
	public CTransactionEntity(){
		transactions = new ArrayList<ArrayList<String>>();
		itemCodes = new HashMap<String, String>();
		columnNames = new ArrayList<String>();
	}	
}
