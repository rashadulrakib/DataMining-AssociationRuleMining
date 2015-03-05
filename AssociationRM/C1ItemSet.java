package AssociationRM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rakib
 * This class contains methods to generate candidate-1 and frequent-1 item set 
 */
public class C1ItemSet {
	
	ArrayList<ArrayList<String>> transactions=null;
	int totalTransaction=0;
	double support=0;
	

	/**
	 * @param transactions
	 * @param support
	 * Constructor to create an object
	 */
	public C1ItemSet(ArrayList<ArrayList<String>> transactions,double support){
		this.transactions=transactions;
		this.totalTransaction = transactions.size();
		this.support = support;
	}
	
	
	/**
	 * @return candidate-1 itemset
	 */
	public HashMap<HashSet<String>,Integer> generateCandidate1ItemSet(){
		HashMap<HashSet<String>,Integer> candidate1ItemSet = new HashMap<HashSet<String>, Integer>();
		
		try{
			for(ArrayList<String> transaction: transactions){
				for(String item:transaction){
					HashSet<String> hs = new HashSet<String>();
					hs.add(item);
					if(!candidate1ItemSet.containsKey(hs)){
						candidate1ItemSet.put(hs,1);
					}else{
						candidate1ItemSet.put(hs, candidate1ItemSet.get(hs)+1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return candidate1ItemSet;
	}
	
	
	/**
	 * @param hmCandidate1ItemSet
	 * @return frequent-1 itemset
	 */
	public HashMap<HashSet<String>,Integer> generateFrequent1ItemSet (HashMap<HashSet<String>,Integer> hmCandidate1ItemSet){
		HashMap<HashSet<String>,Integer> hmFrequent1ItemSet = new HashMap<HashSet<String>, Integer>();
		try{
			Set<HashSet<String>> hsKeys = hmCandidate1ItemSet.keySet();
			for(HashSet<String> hsKey:hsKeys){
				int frequency = hmCandidate1ItemSet.get(hsKey);
				double itemSetSupport = (double)frequency/(double)totalTransaction;
				if(itemSetSupport>=support){
					hmFrequent1ItemSet.put(hsKey, frequency);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return hmFrequent1ItemSet;
	}
	
	
}
