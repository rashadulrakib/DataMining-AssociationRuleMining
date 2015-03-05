package AssociationRM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * @author rakib
 * This class contains method to load transaction file into memory
 */
public class CTransaction {
	
	/**
	 * @param transactionFilePath
	 * @return an entity for all transactions
	 */
	public CTransactionEntity loadTransactionFile(String transactionFilePath) {
		CTransactionEntity objET = new CTransactionEntity();
		try{
			ArrayList<ArrayList<String>> transactions= new ArrayList<ArrayList<String>>();
			HashMap<String, String> itemCodes = new HashMap<String, String>(); //0=0-> outlook=sunny
			
			BufferedReader br = new BufferedReader(new FileReader(transactionFilePath));
			
			String line="";
			
			line = br.readLine();
			String [] columnNames = line.split("\\s+");
			
			HashMap<String,HashMap<String,Integer>> hmColumnValueCodes = new HashMap<String, HashMap<String,Integer>>(); 
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if(line.isEmpty()){
					continue;
				}
				ArrayList<String> encodedValues = new ArrayList<String>();
				
				String [] items = line.split("\\s+");
				for(int i=0;i<items.length;i++){
					String columnName = columnNames[i];
					String value = items[i];
					
					if(!hmColumnValueCodes.containsKey(columnName)){
						HashMap<String,Integer> hmValueCodes = new HashMap<String, Integer>();
						hmValueCodes.put(value, 0);
						hmColumnValueCodes.put(columnName, hmValueCodes);
					}else{
						HashMap<String,Integer> hmValueCodes = hmColumnValueCodes.get(columnName);
						if(!hmValueCodes.containsKey(value)){
							int nextValueIndex = hmValueCodes.size();
							hmValueCodes.put(value,nextValueIndex);
							hmColumnValueCodes.put(columnName,hmValueCodes);
						}
					}
					
					String encodedValue =  i+"="+hmColumnValueCodes.get(columnName).get(value);
					//System.out.print(encodedValue+" ");
					
					itemCodes.put(encodedValue,columnName+"="+value);
					encodedValues.add(encodedValue);
				}
				
				transactions.add(encodedValues);
				//System.out.println();
			}
			
			br.close();
			
			objET.transactions = transactions;
			objET.itemCodes = itemCodes;
			objET.columnNames = new ArrayList<String>(Arrays.asList(columnNames));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return objET;
	}
}
