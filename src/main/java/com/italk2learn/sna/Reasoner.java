package com.italk2learn.sna;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.italk2learn.sna.exception.SNAException;
import com.italk2learn.sna.inter.Sequencer;

public class Reasoner {
	StudentModel student;
	
	private static final Logger logger = LoggerFactory.getLogger(Reasoner.class);
	
	public Reasoner (StudentModel thisStudent){
		student = thisStudent;
	}

	public void getNextTask(StudentNeedsAnalysis sna) {
		int studentChallenge = student.getStudentChallenge();
		String currenExercise = student.getCurrentExercise();
		String nextTask = "";
		
		sna.saveLog("SNA.counter.unstructured", ""+student.getUnstructuredTaskCounter());
		
		if (student.getUnstructuredTaskCounter() == 1){
			//sequence next unstructured task
			nextTask = calculateNextUnstructuredTask(currenExercise, studentChallenge);	
		}
		else {
			//switch to structured task
			String currentTask = currenExercise.substring(0, 7);
			nextTask = getNextStructuredTask(currentTask);
			student.setLastExploratoryExercise(currenExercise);
			student.setLastStudentChallenge(studentChallenge);
		}
		sna.setNextTask(nextTask, student.getRule());
	}
	
	private String getNextTaskForUnderChallgenge(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		String rule = "";
		
		if (currentTask.equals("task2.1")){
			rule = ("underChallenged_task2.1");
			nextTask = "task2.2";
		}
		else if (currentTask.equals("task2.2")){
			rule = ("underChallenged_task2.2");
			String leastUsedRep = getLeastUsedRep();
			nextTask = "task2.4.setA."+leastUsedRep;
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("underChallenged_task2.4_setA");
				nextTask = "task2.6.setA";
			}
			else {
				rule = ("underChallenged_task2.4_else");
				nextTask = "task2.6.setB";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("underChallenged_task2.6_setA");
				nextTask = "task2.7.setA";
			}
			else {
				rule = ("underChallenged_task2.6_else");
				nextTask = "task2.7.setB";
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("underChallenged_task2.7_setA");
				nextTask = "task2.7.setB";
			}
			else {
				rule = ("underChallenged_task2.7_else");
				String leastUsedRep = getLeastUsedRep();
				nextTask = "task2.4.setB."+leastUsedRep;
			}
		}
		student.setRule(rule);
		return nextTask;
	}
	
	private String getNextTaskForAppropriatelyChallgenge(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		String rule = "";
		
		if (currentTask.equals("task2.1")){
			rule = ("appChallenged_task2.1");
			nextTask = "task2.2";
		}
		else if (currentTask.equals("task2.2")){
			rule = ("appChallenged_task2.2");
			String leastUsedRep = getLeastUsedRep();
			nextTask = "task2.4.setA."+leastUsedRep;
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("appChallenged_task2.4_setA");
				String leastUsedRep = getLeastUsedRep();
				nextTask = "task2.4.setB."+leastUsedRep;
			}
			else {
				rule = ("appChallenged_task2.4_else");
				nextTask = "task2.6.setA";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("appChallenged_task2.6_setA");
				nextTask = "task2.6.setB";
			}
			else {
				rule = ("appChallenged_task2.6_else");
				nextTask = "task2.7.setA";
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				rule = ("appChallenged_task2.7_setA");
				nextTask = "task2.7.setB";
			}
			else {
				rule = ("appChallenged_task2.7_else");
				String leastUsedRep = getLeastUsedRep();
				nextTask = "task2.4.setB."+leastUsedRep;
			}
		}
		student.setRule(rule);
		return nextTask;
	}
	
	private String getNextTaskForOverChallenged(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		String mostUsedRep = getMostUsedRep();
		String rule = "";
			
		if (currentTask.equals("task2.1")){
			rule = ("overChallenged_task2.1");
			nextTask = "task2.1";
		}
		else if (currentTask.equals("task2.2")){
			rule = ("overChallenged_task2.1");
			nextTask = "task2.1";
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				rule = ("overChallenged_task2.4_setB");
				nextTask = getNextTaskWithMostUsedRep("task2.4.setA", mostUsedRep);
			}
			else if (fractionType.equals("setC")){
				rule = ("overChallenged_task2.4_setC");
				nextTask = getNextTaskWithMostUsedRep("task2.4.setB", mostUsedRep);
			}
			else {
				rule = ("overChallenged_task2.4_else");
				nextTask="task2.2";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				rule = ("overChallenged_task2.6_setB");
				nextTask = "task2.6.setA";
			}
			else if (fractionType.equals("setC")){
				rule = ("overChallenged_task2.6_setC");
				nextTask = "task2.6.setB";
			}
			else {
				rule = ("overChallenged_task2.6_else");
				nextTask = getNextTaskWithMostUsedRep("task2.4.setB", mostUsedRep);
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				rule = ("overChallenged_task2.7_setB");
				nextTask = "task2.7.setA";
			}
			else if (fractionType.equals("setC")){
				rule = ("overChallenged_task2.7_setC");
				nextTask = "task2.7.setB";
			}
			else {
				rule = ("overChallenged_task2.7_else");
				nextTask = "task2.6.setB";
			}
		}
		student.setRule(rule);
		return nextTask;
	}
	
	private String getNextTaskWithMostUsedRep(String taskDescription, String mostUsedRep){
		String result = taskDescription+".area";
		
		if (mostUsedRep.equals("liqu")){
			result = ".liqu";
		}
		else if (mostUsedRep.equals("numb")){
			result = ".numb";
		}
		else if (mostUsedRep.equals("sets")){
			result = ".sets";
		}
		return result;
	}
	
	
	private String getMostUsedRep(){
		String result = "area";
		int amountAreaUsed = student.getAmountArea();
		int amountSetUsed = student.getAmountSets();
		int amountNumbUsed = student.getAmountNumb();
		int amountLiguUsed = student.getAmountLiqu();
		
		if ((amountAreaUsed != 0) &&
				(amountAreaUsed >= amountSetUsed) &&
				(amountAreaUsed >= amountNumbUsed) &&
				(amountAreaUsed >= amountLiguUsed)){
			result = "area";
		}
		else if ((amountNumbUsed != 0) &&
				(amountNumbUsed >= amountAreaUsed) &&
				(amountNumbUsed >= amountSetUsed) &&
				(amountNumbUsed >= amountLiguUsed)){
			result = "numb";
		}
		else if ((amountLiguUsed != 0) &&
				(amountLiguUsed >= amountAreaUsed) &&
				(amountLiguUsed >= amountSetUsed) &&
				(amountLiguUsed >= amountNumbUsed)){
			result = "liqu";
		}
		else if ((amountSetUsed != 0) &&
				(amountSetUsed >= amountAreaUsed) &&
				(amountSetUsed >= amountNumbUsed) &&
				(amountSetUsed >= amountLiguUsed)){
			result = "sets";
		}
		return result;
	}
	
	private String getLeastUsedRep(){
		String result = "area";
		int amountAreaUsed = student.getAmountArea();
		int amountSetUsed = student.getAmountSets();
		int amountNumbUsed = student.getAmountNumb();
		int amountLiguUsed = student.getAmountLiqu();
		
		if ((amountAreaUsed == 0) ||
			((amountAreaUsed < amountNumbUsed) &&
			(amountAreaUsed < amountSetUsed) &&
			(amountAreaUsed < amountLiguUsed))){
			result = "area";
		}
		else if ((amountNumbUsed == 0) ||
				((amountNumbUsed < amountAreaUsed) &&
				(amountNumbUsed < amountSetUsed) &&
				(amountNumbUsed < amountLiguUsed))){
			result = "numb";
		}
		else if ((amountLiguUsed == 0) ||
				((amountLiguUsed < amountAreaUsed) &&
				(amountLiguUsed < amountSetUsed) &&
				(amountLiguUsed < amountNumbUsed))){
			result = "liqu";
		}
		else if ((amountSetUsed == 0) ||
				((amountSetUsed < amountAreaUsed) &&
				(amountSetUsed < amountNumbUsed) &&
				(amountSetUsed < amountLiguUsed))){
			result = "sets";
		}
		return result;
	}
	
	private String calculateNextUnstructuredTask(String currenExercise, int studentChallenge){
		String nextTask = "";
		if (studentChallenge == StudentChallenge.overChallenged){
			nextTask = getNextTaskForOverChallenged(currenExercise);
		}
		else if (studentChallenge == StudentChallenge.underChallenged){
			nextTask = getNextTaskForUnderChallgenge(currenExercise); 
		}
		else {
			nextTask = getNextTaskForAppropriatelyChallgenge(currenExercise);
		}
		return nextTask;
	}
	
	private String getNextStructuredTask(String currentTask){
		String nextTask = "";
		boolean inEngland = student.getInEngland();
		String rule = "";
		
		if (currentTask.equals("task2.1")){
			if (inEngland){
				rule = "nextStructuredTask_task2.1_english";
				nextTask = "MA_GBR_0800CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.1_german";
				nextTask = "task2graph_9-12";
			}
			
		}
		else if (currentTask.equals("task2.2")){
			if (inEngland){
				rule = "nextStructuredTask_task2.2_english";
				nextTask = "MA_GBR_1125CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.2_german";
				nextTask = "task2graph_9-12";
			}
		}
		else if (currentTask.equals("task2.3")){
			if (inEngland){
				rule = "nextStructuredTask_task2.3_english";
				nextTask = "MA_GBR_0850CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.3_german";
				nextTask = "task2graph_9-12";
			}
		}
		else if (currentTask.equals("task2.4")){
			if (inEngland){
				rule = "nextStructuredTask_task2.4_english";
				nextTask = "MA_GBR_0950CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.4_german";
				nextTask = "task8graph_1-5";
			}
		}
		else if (currentTask.equals("task2.5")){
			if (inEngland){
				rule = "nextStructuredTask_task2.5_english";
				nextTask = "MA_GBR_1150CAx0300";
			}
			else{
				rule = "nextStructuredTask_task2.5_german";
				nextTask = "task8graph_1-5";
			}
		}
		else if (currentTask.equals("task2.6")){
			if (inEngland){
				rule = "nextStructuredTask_task2.6_english";
				nextTask = "MA_GBR_1150CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.6_german";
				nextTask = "task3graph_1-2";
			}	
		}
		else if (currentTask.equals("task2.7")){
			if (inEngland){
				rule = "nextStructuredTask_task2.7_english";
				nextTask = "MA_GBR_1150CAx0100";
			}
			else{
				rule = "nextStructuredTask_task2.7_german";
				nextTask = "task1graph_3-7";
			}
		}
		student.setRule(rule);
		return nextTask;
		
	}
	
	public void getNextStructuredTask(StudentNeedsAnalysis sna, int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial) throws SNAException {
		logger.info("getNextStructuredTask()---values--> whizzStudID="+whizzStudID+" whizzPrevContID="+whizzPrevContID+ " prevScore="+prevScore+ " timestamp"+timestamp.toString()+" WhizzSuggestion="+WhizzSuggestion +" trial="+Trial);
		Sequencer sq= new SNASequencer(); 
		String nextTask = "";
		int counter=1;
		
		if (student.getInEngland()){
			counter=3;
		}
		
		sna.saveLog("SNA.counter.structured", ""+student.getStructuredTaskCounter());
		
		if ((student.getStructuredTaskCounter() >=1) && (student.getStructuredTaskCounter()<= counter)){
			String contID = student.getCurrentExercise();
			//sequence next structured task
			if (sna.isWhizzExercise()) {
				nextTask= sq.next(whizzStudID, contID, prevScore, timestamp, WhizzSuggestion, Trial, StructuredActivityType.WHIZZ);
			}
			else { 
				nextTask= sq.next(whizzStudID, contID, prevScore, timestamp, WhizzSuggestion, Trial, StructuredActivityType.FRACTIONS_TUTOR);
			}
			student.setRule(sq.getRule());
		}
		else {
			//switch to next unstructured task
			int studentChallenge = student.getLastStudentChallenge();
			if (studentChallenge == StudentChallenge.flow) studentChallenge = StudentChallenge.underChallenged;
			nextTask=calculateNextUnstructuredTask(student.getLastExploratoryExercise(), studentChallenge);
		}
		sna.setNextTask(nextTask, student.getRule());
	}

	

	

}