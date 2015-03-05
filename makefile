JFLAGS	=	-g
JC	=	javac
.SUFFIXES:	.java	.class
.java.class:
	$(JC)	$(JFLAGS)	$*.java

CLASSES	=	\
	AssociationRM/C1ItemSet.java	\
	AssociationRM/CKItemSet.java	\
	AssociationRM/CRule.java	\
	AssociationRM/CRuleEntity.java	\
	AssociationRM/CTransaction.java	\
	AssociationRM/CTransactionEntity.java	\
	AssociationRM/CAssociationRuleMining.java

default:	classes

classes:	$(CLASSES:.java=.class)

clean:
		$(RM)	*.class
