---------------------------------------------------------------------------------------------
This is an example of AR algorithm implementation in Java.
---------------------------------------------------------------------------------------------

- What is this program for?

 This program is an implementation of the association rule mining algorithm "Apriori" written in Java.

 
- Overview of the program code:

The assignment contains the "AssociationRM" folder having the complete solution and can be opened in Eclipse. It contains following files with functions.

Note (BONUS): 
============
This program also incorporates BONUS logic of pruning the rules while generating rules. The method "ruleGenerationApriori" in CRule.java incorporates the logic for pruning rules while generating rules. The logic of the BONUS is: Stop proceeding to generate rules using the left subset of an item set, when the rule using its left super set has confidence<min_confidence.

For example: I={i1,i2,i3,i4}. If the confidence of the rule {i1,i2,i3}=>{i4} < min_confidence, then we do not need to proceed to generate rule {i1,i2}=>{i3,i4}, {i1,i3}=>{i2,i4}, {i2,i3}=>{i1,i4} since their confidence scores are also < min_confidence.

There are 7 files with mentioned main functions (Other than constructors)

File Name					Function Names
===============================================================================================
1. C1ItemSet.java					
					public HashMap<HashSet<String>,Integer> generateCandidate1ItemSet()
					public HashMap<HashSet<String>,Integer> generateFrequent1ItemSet (HashMap<HashSet<String>,Integer> hmCandidate1ItemSet)
				
2. CKItemSet.java
					public HashMap<HashSet<String>,Integer> generateCandidateKItemSet(HashMap<HashSet<String>,Integer> hmFrequentK_1ItemSet, int K)
					public HashMap<HashSet<String>,Integer> generateFrequentKItemSet(HashMap<HashSet<String>,Integer> hmCandidateKItemSet)
					private int getCandidateFrequency(ArrayList<String> itemset)
					private boolean isJoinable(HashSet<String> hsi, HashSet<String> hsj)
					private HashSet<String> getJoined(HashSet<String> hmi, HashSet<String> hmj)
					private void candidateGenerationApriori(ArrayList<HashSet<String>> hsKeys, String[] arr, int items, int start, String[] subSet)
					private boolean subsetExists(String[] subSet, ArrayList<HashSet<String>> hsKeys)

3. CRule.java
					public void generateRules(int K)
					public void printRules(String outputFilePath)
					void ruleGenerationApriori(String [] items, int end, ArrayList<String> rightItems, int setCount)
					private ArrayList<String> getRestItems(String[] items, ArrayList<String> rightItems)
					private int getSetCount(String[] items)
					private String decodeItems(String items[])
					private double roundDouble(double number)
						
4. CRuleEntity.java
					Structure class for Rules: String [] leftItems, String [] rightItems, double ruleSupport, double ruleConfidence

5. CTransaction.java
					public CTransactionEntity loadTransactionFile(String transactionFilePath)
					
		
6. CTransactionEntity.java
					Structure class for transaction DB: ArrayList<ArrayList<String>> transactions, HashMap<String, String> itemCodes, ArrayList<String> columnNames.

7. CAssociationRuleMining.java
					public static void main(String[] args)
					private String getInputFile(BufferedReader br)
					private double getSupConf(String msg, BufferedReader br)

					
- The following is the  program structure:
				
				Input the Filename, Confidence and Support:
					CTransaction class will load the data into memory.
				Generate candidate 1-itemsets and Frequent 1-ItemSets:
					C1ItemSet class will generate candidate 1-itemsets and Frequent 1-ItemSets.
				Generate candidate K-itemsets and Frequent K-ItemSets:
					CKItemSet class will generate candidate K-itemsets using Apriori and generate Frequent K-ItemSets using support.
				Generate and Save rules:
					CRule class will generate rules using Apriori and confidence; then save the rules to file.

main()	----> Input filename, support, confidence
		---->check fileexists, support and confidence values
CTransaction ---->loadTransactionFile 
             
C1ItemSet ----> generateCandidate1ItemSet
		  ----> generateFrequent1ItemSet
		  
CKItemSet ----> generateCandidateKItemSet
		  ----> generateFrequentKItemSet 
		  
CRule ----> generateRules
	  ----> printRules

-How to run the program:
	a) run using make file:
	Place the input file (e.g., data1) in the root assignment directory (assign3)
	Copy the package folder "AssociationRM" under "assign3". Now all source files are under the package folder "AssociationRM"
	From the "assign3" folder compile the source files by "make"
	From the "assign3" folder run the executable program by "java AssociationRM.CAssociationRuleMining" 

rakib@bluenose:~/assign3$ make
javac   -g      AssociationRM/C1ItemSet.java
javac   -g      AssociationRM/CKItemSet.java
javac   -g      AssociationRM/CRule.java
javac   -g      AssociationRM/CTransaction.java
javac   -g      AssociationRM/CAssociationRuleMining.java
rakib@bluenose:~/assign3$ java AssociationRM.CAssociationRuleMining
What is the name of the file containing your data?
data2
Please select the minimum support rate(0.00-1.00):
0.5
Please select the minimum confidence rate(0.00-1.00):
0.6
The result is in the file Rules.
*** Algorithm Finished ***

	
	b) run without make file:
	Place the input file in the root assignment directory (assign3)
	Copy the package folder "AssociationRM" under "assign3". Now all source files are under the package folder "AssociationRM"
	From the "assign3" folder compile the source files by "javac AssociationRM/*.java" 
	From the "assign3" folder run the executable program by "java AssociationRM.CAssociationRuleMining" 
	
Demo example: 
rakib@bluenose:~/assign3$ ls
AssociationRM  data1  data2  data3
rakib@bluenose:~/assign3$ javac AssociationRM/*.java
rakib@bluenose:~/assign3$ java AssociationRM.CAssociationRuleMining
What is the name of the file containing your data?
data1
Please select the minimum support rate(0.00-1.00):
0.2
Please select the minimum confidence rate(0.00-1.00):
0.3
The result is in the file Rules.
*** Algorithm Finished ***
rakib@bluenose:~/assign3$ ls
AssociationRM  data1  data2  data3  Rules
 