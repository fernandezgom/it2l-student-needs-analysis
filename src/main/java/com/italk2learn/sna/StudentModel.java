package com.italk2learn.sna;


public class StudentModel {
	
	boolean includesAffect = false;
	
	int amountNextStep = 0;
	int amountProblemSolving = 0;
	int amountAffectBoosts = 0;
	int amountMathsVocab = 0;
	int amountTalkAloud = 0;
	int amountReflection = 0;
	int amountAffirmation = 0;
	
	int amountConfusion = 0;
	int amountFrustration = 0;
	int amountBoredom = 0;
	int amountFlow = 0;
	int amountSurprise = 0;
	
	int studentChallenge = 0;
	
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
	
	public void addAmountSurprise(){
		amountSurprise += 1;
	}
	
	public int getAmountSurprise(){
		return amountSurprise;
	}

}
