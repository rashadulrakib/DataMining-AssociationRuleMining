package AssociationRM;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * @author rakib
 * This class contains methods to generate and print rules
 */
public class CRule {
	
	ArrayList<HashMap<HashSet<String>,Integer>> allFrequentItemSets=null;
	HashMap<String, String> itemCodes=null;
	int totalTransaction=0;
	double support=0;
	double confidence=0;
	ArrayList<CRuleEntity> rules =null;
	
	public CRule(ArrayList<HashMap<HashSet<String>,Integer>> allFrequentItemSets, HashMap<String, String> itemCodes, int totalTransaction, double support, double confidence){
		this.allFrequentItemSets = allFrequentItemSets;
		this.itemCodes = itemCodes;
		this.totalTransaction = totalTransaction;
		this.support = support;
		this.confidence = confidence;
		this.rules = new ArrayList<CRuleEntity>();
	}
	
	
	/**
	 * @param K
	 * Generate rules using Apriori knowledge
	 */
	public void generateRules(int K){
		try{
			Set<HashSet<String>> hsKeys = allFrequentItemSets.get(K-1).keySet();
			for(HashSet<String> hsKey:hsKeys){
				String [] items = (String [])hsKey.toArray(new String[hsKey.size()]);
				int setCount = getSetCount(items);
				ruleGenerationApriori(items, hsKey.size()-1, new ArrayList<String>(), setCount);
				//System.out.println();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void printRules(String outputFilePath){
		try{
			PrintWriter pr = new PrintWriter(outputFilePath);
			pr.println("Summary:\nTotal rows in the original set: "+ 
					totalTransaction +"\nTotal rules discovered: "+rules.size()+
					"\nThe selected measures: Support="+support+" Confidence="+confidence);
			pr.println("-------------------------------------------------------------------\n\nRules:\n");
			
			int count=0;
			for(CRuleEntity rule:rules){
				count++;
				pr.println("Rule#"+count+": (Support="+roundDouble(rule.ruleSupport)+", Confidence="+roundDouble(rule.ruleConfidence)+")");
				pr.println("{ "+decodeItems(rule.leftItems)+" }");
				pr.println("----> { "+decodeItems(rule.rightItems)+" }");
				if(count<rules.size()){
					pr.println();
				}
			}
			
			pr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param items
	 * @param end
	 * @param rightItems
	 * @param setCount
	 * Generate rules using Apriori knowledge
	 */
	void ruleGenerationApriori(String [] items, int end, ArrayList<String> rightItems, int setCount){
		try{
			for(int i=end;i>=0;i--){
				rightItems.add(items[i]);
				
				ArrayList<String> leftItems = getRestItems(items, rightItems);
				int sizeOfLeft = leftItems.size();
				if(sizeOfLeft>0){
					//System.out.println(rightItems);
					int leftCount = getSetCount((String [])leftItems.toArray(new String[leftItems.size()]));
					double ruleConfidence = (double)setCount/(double)leftCount;
					double ruleSupport = (double)setCount/(double)totalTransaction;
					if(ruleSupport>=support && ruleConfidence>=confidence){
						
						rules.add(new CRuleEntity((String[])leftItems.toArray(new String[leftItems.size()]),
								(String[])rightItems.toArray(new String[rightItems.size()]),
								ruleSupport,ruleConfidence));
						
						ruleGenerationApriori(items, i-1,rightItems, setCount);
					}
				}
				
				rightItems.remove(rightItems.size()-1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private ArrayList<String> getRestItems(String[] items, ArrayList<String> rightItems) {
		ArrayList<String> leftItems = new ArrayList<String>();
		
		try{
			for(String item: items){
				if(!rightItems.contains(item)){
					leftItems.add(item);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return leftItems;
	}
	
	private int getSetCount(String[] items) {
		int count=0;
		try{
			int itemSize = items.length;
			HashSet<String> hskey = new HashSet<String>();
			for(String item: items){
				hskey.add(item);
			}
			count = allFrequentItemSets.get(itemSize-1).get(hskey);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
	private String decodeItems(String items[]){
		String decodedValue="";
		try{
			for(String item: items){
				decodedValue=decodedValue+ " "+itemCodes.get(item)+",";
			}
			decodedValue=decodedValue.trim().replaceAll("[,]$", "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return decodedValue;
	}
	
	private double roundDouble(double number){
		try{
			DecimalFormat df = new DecimalFormat("#.##");      
			number = Double.valueOf(df.format(number)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return number;
	}
	
}
