package com.italk2learn.sna;


public class StudentModel {
	
	String currentExercise = "task2.2";
	String lastExploratoryExercise = "";
	int lastStudentChallenge = 0;
	
	String lastFeedbackProvided = "";
	
	boolean includesAffect = false;
	
	boolean inEngland = true;
	
	int amountNextStep = 0;
	int amountProblemSolving = 0;
	int amountAffectBoosts = 0;
	int amountMathsVocab = 0;
	int amountTalkAloud = 0;
	int amountReflection = 0;
	int amountAffirmation = 0;
	int amountTaskNotFinished = 0;
	
	int amountConfusion = 0;
	int amountFrustration = 0;
	int amountBoredom = 0;
	int amountFlow = 0;
	int amountSurprise = 0;
	
	int amountArea = 0;
	int amountNumb = 0;
	int amountSets = 0;
	int amountLiqu = 0;
	
	int studentChallenge = 0;
	
	int unstructuredTaskCounter = 0;
	int structuredTaskCounter = 0;
	
	String rule = "";
	
	public void setRule(String value){
		rule = value;
	}
	
	public String getRule(){
		return rule;
	}
	
	public String getStudentChallengeAsString(){
		String result = "";
		if (studentChallenge == StudentChallenge.overChallenged){
			result = "overChallenged";
		}
		else if (studentChallenge == StudentChallenge.underChallenged){
			result = "underChallenged";
		}
		else if (studentChallenge == StudentChallenge.flow){
			result = "flow";
		}
		return result;
	}
	
	public void setLastFeedbackProvided(String value){
		lastFeedbackProvided = value;
	}
	
	public String getLastFeedbackProvided(){
		return lastFeedbackProvided;
	}
	
	public void setLastExploratoryExercise(String value){
		lastExploratoryExercise = value;
	}
	
	public String getLastExploratoryExercise(){
		return lastExploratoryExercise;
	}
	
	public void setLastStudentChallenge(int value){
		lastStudentChallenge = value;
	}
	
	public int getLastStudentChallenge(){
		return lastStudentChallenge;
	}
	
	public void setInEngland(boolean value){
		inEngland = value;
	}
	
	public boolean getInEngland(){
		return inEngland;
	}
	
	public void setCurrentExercise(String value){
		currentExercise = value;
	}
	
	public String getCurrentExercise(){
		return currentExercise;
	}
	
	public void setUnstructuredTaskCounter(int value){
		unstructuredTaskCounter = value;
	}
	
	public int getUnstructuredTaskCounter(){
		return unstructuredTaskCounter;
	}
	
	public void setStructuredTaskCounter(int value){
		structuredTaskCounter = value;
	}
	
	public int getStructuredTaskCounter(){
		return structuredTaskCounter;
	}
	
	public void setStudentChallenge(int value){
		studentChallenge = value;
	}
	
	public int getStudentChallenge(){
		return studentChallenge;
	}
	
	public void setIncludesAffect(boolean value){
		includesAffect = value;
	}
	
	public boolean getIncludesAffect(){
		return includesAffect;
	}
	
	/*
	 * perceived task difficulty classifier PTD
	 * 1=overchallenged
	 * 2=flow
	 * 3=underchallenged
	 */
	int PTD = 0;
	
	public void setPTD(int value){
		PTD = value;
	}
	
	public int getPTD(){
		return PTD;
	}
	
	public void resetAffectValues(){
		amountNextStep = 0;
		amountProblemSolving = 0;
		amountAffectBoosts = 0;
		amountMathsVocab = 0;
		amountTalkAloud = 0;
		amountReflection = 0;
		amountAffirmation = 0;
		
		amountConfusion = 0;
		amountFrustration = 0;
		amountBoredom = 0;
		amountFlow = 0;
		
		PTD = 0;
	}
	
	public void resetFeedbackValues(){
		amountNextStep = 0;
		amountProblemSolving = 0;
		amountAffectBoosts = 0;
		amountMathsVocab = 0;
		amountTalkAloud = 0;
		amountReflection = 0;
		amountAffirmation = 0;
	}
	
	
	public void addAmountConfusion(){
		amountConfusion += 1;
	}
	
	public int getAmountConfusion(){
		return amountConfusion;
	}
	
	public void addAmountFrustration(){
		amountFrustration += 1;
	}
	
	public int getAmountFrustration(){
		return amountFrustration;
	}
	
	public void addAmountBoredom(){
		amountBoredom += 1;
	}
	
	public int getAmountBoredom(){
		return amountBoredom;
	}
	
	public void addAmountFlow(){
		amountBoredom += 1;
	}
	
	public int getAmountFlow(){
		return amountBoredom;
	}
	
	
	public void addAmountNextStep(){
		amountNextStep += 1;
	}
	
	public int getAmountNextStep(){
		return amountNextStep;
	}
	
	public void addAmountProblemSolving(){
		amountProblemSolving += 1;
	}
	
	public int getAmountProblemSolving(){
		return amountProblemSolving;
	}
	
	public void addAmountAffectBoosts(){
		amountAffectBoosts += 1;
	}
	
	public int getAmountAffectBoosts(){
		return amountAffectBoosts;
	}
	
	public void addAmountMathsVocab(){
		amountMathsVocab += 1;
	}
	
	public int getAmountMathsVocab(){
		return amountMathsVocab;
	}
	
	public void addAmountTalkAloud(){
		amountTalkAloud += 1;
	}
	
	public int getAmountTalkAloud(){
		return amountTalkAloud;
	}
	
	public void addAmountReflection(){
		amountReflection += 1;
	}
	
	public int getAmountReflection(){
		return amountReflection;
	}
	
	public void addAmountAffirmation(){
		amountAffirmation += 1;
	}
	
	public int getAmountAffirmation(){
		return amountAffirmation;
	}
	
	
	public void addAmountTaskNotFinished(){
		amountTaskNotFinished +=1;
	}
	
	public int getAmountTaskNotFinished(){
		return amountTaskNotFinished;
	}
	
	public void addAmountSurprise(){
		amountSurprise += 1;
	}
	
	public int getAmountSurprise(){
		return amountSurprise;
	}

	public void addAmountArea(){
		amountArea += 1;
	}
	
	public int getAmountArea(){
		return amountArea;
	}
	
	public void addAmountNumb(){
		amountNumb +=1;
	}
	
	public int getAmountNumb(){
		return amountNumb;
	}
	
	public void addAmountSets(){
		amountSets +=1;
	}
	
	public int getAmountSets(){
		return amountSets;
	}
	
	public void addAmountLiqu(){
		amountLiqu +=1;
	}
	
	public int getAmountLiqu(){
		return amountLiqu;
	}
	
}