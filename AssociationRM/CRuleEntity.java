package AssociationRM;


/**
 * @author rakib
 * This class contains the properties of a rule
 */
public class CRuleEntity {
	String [] leftItems=null;
	String [] rightItems=null;
	double ruleSupport=0;
	double ruleConfidence=0;
	int items=0;
	public CRuleEntity(String [] leftItems, String [] rightItems, double ruleSupport, double ruleConfidence){
		this.leftItems= leftItems;
		this.rightItems = rightItems;
		this.ruleSupport = ruleSupport;
		this.ruleConfidence = ruleConfidence;
		this.items = leftItems.length+ rightItems.length;
	}
}
