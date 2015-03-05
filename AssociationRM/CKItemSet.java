package AssociationRM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * @author rakib
 * This class contains methods to generate candidate-k and frequent-k item set
 */

public class CKItemSet {
	
	boolean validCandidate;
	
	ArrayList<ArrayList<String>> transactions=null;
	int totalTransaction=0;
	double support=0;
	
	public CKItemSet(ArrayList<ArrayList<String>> transactions,double support){
		this.transactions=transactions;
		this.totalTransaction = transactions.size();
		this.support = support;
	}
	
	/**
	 * @param hmFrequentK_1ItemSet
	 * @param K
	 * @return candidate-k itemset using Apriori knowledge
	 */
	public HashMap<HashSet<String>,Integer> generateCandidateKItemSet(HashMap<HashSet<String>,Integer> hmFrequentK_1ItemSet, int K){
		HashMap<HashSet<String>,Integer> hm = new HashMap<HashSet<String>, Integer>();
		try{
			ArrayList<HashSet<String>> hsKeys = new ArrayList<HashSet<String>>(hmFrequentK_1ItemSet.keySet());
			
			for(int i=0;i<hsKeys.size();i++){
				for(int j=i+1;j<hsKeys.size();j++){
					HashSet<String> hsi = hsKeys.get(i);
					HashSet<String> hsj = hsKeys.get(j);
									
					boolean joinable = isJoinable(hsi, hsj);
					if(!joinable){
						continue;
					}
					
					HashSet<String> joinedSet  = getJoined(hsi, hsj);
					if(joinedSet.size()!=K){
						continue;
					}
					
					validCandidate=true;
					candidateGenerationApriori(hsKeys, (String [])joinedSet.toArray(new String[joinedSet.size()]),K-1, 0, new String[K-1]);
					if(!validCandidate){
						continue;
					}
				
					int frequency = getCandidateFrequency(new ArrayList<String>(Arrays.asList((String [])joinedSet.toArray(new String[joinedSet.size()]))));
					hm.put(joinedSet, frequency);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return hm;
	}
	
	/**
	 * @param hmCandidateKItemSet
	 * @return frequent-k itemset
	 */
	public HashMap<HashSet<String>,Integer> generateFrequentKItemSet(HashMap<HashSet<String>,Integer> hmCandidateKItemSet){
		HashMap<HashSet<String>,Integer> hmFrequentKItemSet = new HashMap<HashSet<String>, Integer>();
		try{
			Set<HashSet<String>> hsKeys = hmCandidateKItemSet.keySet();
			for(HashSet<String> hsKey:hsKeys){
				int frequency = hmCandidateKItemSet.get(hsKey);
				double itemSetSupport = (double)frequency/(double)totalTransaction;
				if(itemSetSupport>=support){
					hmFrequentKItemSet.put(hsKey, frequency);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return hmFrequentKItemSet;
	}
	
	
	/**
	 * @param itemset
	 * @return candidate itemset frequency by scanning transaction DB
	 */
	private int getCandidateFrequency(ArrayList<String> itemset){
		
		int freq=0;
		try{
			for(ArrayList<String> transaction: transactions){
				if(transaction.containsAll(itemset)){
					freq++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return freq;
	}
	
	
	/**
	 * @param hsi
	 * @param hsj
	 * @return true if two sets are joinable and vice versa
	 */
	private boolean isJoinable(HashSet<String> hsi, HashSet<String> hsj){
		boolean joinable=true; 
		try{
			HashMap<String, String> hmi = new HashMap<String, String>();
			HashMap<String, String> hmj = new HashMap<String, String>();
			
			for(String itemi: hsi){
				String [] arr = itemi.split("=");
				hmi.put(arr[0], arr[1]);
			}
			
			for(String itemj: hsj){
				String [] arr = itemj.split("=");
				hmj.put(arr[0], arr[1]);
			}
			
			for(String key: hmi.keySet()){
				if(hmj.containsKey(key)){
					if(!hmi.get(key).equals(hmj.get(key))){ //hmi={"2=0"}, hmj={"2=1"},
						joinable=false;
						break;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return joinable;
	}
	
	/**
	 * @param hmi
	 * @param hmj
	 * @return the joined set
	 */
	private HashSet<String> getJoined(HashSet<String> hmi, HashSet<String> hmj){
		HashSet<String> joinedSet = new HashSet<String>();
		try{
			joinedSet.addAll(hmi);
			joinedSet.addAll(hmj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return joinedSet;
	}
	
	/**
	 * @param hsKeys
	 * @param arr
	 * @param items
	 * @param start
	 * @param subSet
	 * this method adopts the idea of recursive call from http://stackoverflow.com/questions/127704/algorithm-to-return-all-combinations-of-k-elements-from-n
	 */
	private void candidateGenerationApriori(ArrayList<HashSet<String>> hsKeys, String[] arr, int items, int start, String[] subSet){
        try{
        	if (items == 0){
                boolean isExists = subsetExists(subSet,hsKeys);
                if(!isExists){
                	validCandidate = false;
                }
                
                return;
            }
    		for (int i = start; i <= arr.length-items; i++){
            	subSet[subSet.length - items] = arr[i];
            	candidateGenerationApriori(hsKeys, arr, items-1, i+1, subSet);
            }    	
        }catch(Exception e){
			e.printStackTrace();
		}
    }
	
	/**
	 * @param subSet
	 * @param hsKeys
	 * @return true if an itemset exists in the list of candidate k-itemsets and vice versa
	 */
	private boolean subsetExists(String[] subSet, ArrayList<HashSet<String>> hsKeys) {
		try{
			HashSet<String> hs = new HashSet<String>();
			for(String item: subSet){
				hs.add(item);
			}
			
			for(HashSet<String> hsKey: hsKeys){
				if(hsKey.size()==hs.size() && hsKey.containsAll(hs) && hs.containsAll(hsKey)){
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	
}
