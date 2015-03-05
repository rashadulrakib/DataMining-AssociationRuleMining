package AssociationRM;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * @author rakib
 * contains the main function and entry point of the program
 */
public class CAssociationRuleMining {

	public static void main(String[] args) {
		
		try{
			
			CAssociationRuleMining obj = new CAssociationRuleMining();
			
			BufferedReader br= new BufferedReader( new InputStreamReader(System.in));
			
			String inputFile= obj.getInputFile(br);
			double support = obj.getSupConf("support",br);
			double confidence = obj.getSupConf("confidence",br);
			
			br.close();
			
			CTransaction objLT = new CTransaction();
			CTransactionEntity objET = objLT.loadTransactionFile(inputFile);
			
			int columns =  objET.columnNames.size();
			int totalTransaction = objET.transactions.size();
			ArrayList<HashMap<HashSet<String>,Integer>> allFrequentItemSets = new ArrayList<HashMap<HashSet<String>,Integer>>();
			
			C1ItemSet obj1IS = new C1ItemSet(objET.transactions,support);
			HashMap<HashSet<String>,Integer> hmCandidate1ItemSet = obj1IS.generateCandidate1ItemSet();
			HashMap<HashSet<String>,Integer> hmFrequentK_1ItemSet = obj1IS.generateFrequent1ItemSet(hmCandidate1ItemSet);
			allFrequentItemSets.add(hmFrequentK_1ItemSet);
			
			CKItemSet objKIS = new CKItemSet(objET.transactions, support);
			for(int K=2;K<=columns;K++){
				HashMap<HashSet<String>,Integer> hmCandidateKItemSet = objKIS.generateCandidateKItemSet(hmFrequentK_1ItemSet, K);
				hmFrequentK_1ItemSet = objKIS.generateFrequentKItemSet(hmCandidateKItemSet);
				allFrequentItemSets.add(hmFrequentK_1ItemSet);
			}
			
			CRule objR = new CRule(allFrequentItemSets, objET.itemCodes, totalTransaction, support, confidence);
			for(int K=2;K<=columns;K++){
				objR.generateRules(K);
			}
			objR.printRules("Rules");
			
			System.out.println("The result is in the file Rules.");
			System.out.println("*** Algorithm Finished ***");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String getInputFile(BufferedReader br){
		String inputFile="";
		try{
			while(true){	
				System.out.println("What is the name of the file containing your data?");
				inputFile = br.readLine();
								
				if(inputFile.isEmpty()){
					System.out.println("File name cannot be empty.\n");
					continue;
				}else{
					File f = new File(inputFile);
					if(!f.exists()){
						System.out.println("The file does not exist.\n");
						continue;
					}else{
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return inputFile;
	}
	
	private double getSupConf(String msg, BufferedReader br){
		double value=0;
		try{
			while(true){	
				System.out.println("Please select the minimum "+msg+" rate(0.00-1.00):");
				String str = br.readLine();
								
				if(str.isEmpty()){
					System.out.println("Minimum "+msg+" rate cannot be empty.\n");
					continue;
				}else{
					boolean flag=false;
					try{
						value = Double.parseDouble(str.trim());
						flag=true;
					}catch(NumberFormatException exFormat){
					}
					if(!flag){
						System.out.println("Invalid "+msg+" rate.\n");
					}else{
						if(value<0 || value>1){
							System.out.println("The "+msg+" rate should be 0.00-1.00.\n");
						}else{
							break;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
}
