package com.italk2learn.sna;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import MFSeq.FTSequencer;
import MFSeq.WhizzSequencer;

import com.italk2learn.sna.exception.SNAException;

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
		sna.setNextTask(nextTask);
	}
	
	private String getNextTaskForUnderChallgenge(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		
		if (currentTask.equals("task2.1")){
			nextTask = "task2.2";
		}
		else if (currentTask.equals("task2.2")){
			String leastUsedRep = getLeastUsedRep();
			nextTask = "task2.4.setA."+leastUsedRep;
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.6.setA";
			}
			else {
				nextTask = "task2.6.setB";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.7.setA";
			}
			else {
				nextTask = "task2.7.setB";
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.7.setB";
			}
			else {
				nextTask = "task2.4.setB";
			}
		}
		return nextTask;
	}
	
	private String getNextTaskForAppropriatelyChallgenge(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		
		if (currentTask.equals("task2.1")){
			nextTask = "task2.2";
		}
		else if (currentTask.equals("task2.2")){
			String leastUsedRep = getLeastUsedRep();
			nextTask = "task2.4.setA."+leastUsedRep;
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.4.setB";
			}
			else {
				nextTask = "task2.6.setA";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.6.setB";
			}
			else {
				nextTask = "task2.7.setA";
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8,12);
			if (fractionType.equals("setA")){
				nextTask = "task2.7.setB";
			}
			else {
				nextTask = "task2.4.setB";
			}
		}
		return nextTask;
	}
	
	private String getNextTaskForOverChallenged(String currenExercise){
		String nextTask = "";
		String currentTask = currenExercise.substring(0, 7);
		String mostUsedRep = getMostUsedRep();
			
		if (currentTask.equals("task2.1")){
			nextTask = "task2.1";
		}
		else if (currentTask.equals("task2.2")){
			nextTask = "task2.1";
		}
		else if (currentTask.equals("task2.4")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				nextTask = getNextTaskWithMostUsedRep("task2.4.setA", mostUsedRep);
			}
			else if (fractionType.equals("setC")){
				nextTask = getNextTaskWithMostUsedRep("task2.4.setB", mostUsedRep);
			}
			else {
				nextTask="task2.2";
			}
		}
		else if (currentTask.equals("task2.6")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				nextTask = "task2.6.setA";
			}
			else if (fractionType.equals("setC")){
				nextTask = "task2.6.setB";
			}
			else {
				nextTask = getNextTaskWithMostUsedRep("task2.4.setB", mostUsedRep);
			}
		}
		else if (currentTask.equals("task2.7")){
			String fractionType = currenExercise.substring(8, 12);
			if (fractionType.equals("setB")){
				nextTask = "task2.7.setA";
			}
			else if (fractionType.equals("setC")){
				nextTask = "task2.7.setB";
			}
			else {
				nextTask = "task2.6.setB";
			}
		}
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
		
		if (currentTask.equals("task2.1")){
			if (inEngland) nextTask = "MA_GBR_0800CAx0100";
			else nextTask = "Task2_graph_9-12";
			
		}
		else if (currentTask.equals("task2.2")){
			if (inEngland) nextTask = "MA_GBR_1125CAx0100";
			else nextTask = "Task2_graph_9-12";
		}
		else if (currentTask.equals("task2.3")){
			if (inEngland) nextTask = "MA_GBR_0850CAx0100";
			else nextTask = "Task2_graph_9-12";
		}
		else if (currentTask.equals("task2.4")){
			if (inEngland) nextTask = "MA_GBR_0950CAx0100";
			else nextTask = "Task8_graph_1-5";
		}
		else if (currentTask.equals("task2.5")){
			if (inEngland) nextTask = "MA_GBR_1150CAx0300";
			else nextTask = "Task8_graph_1-5";
		}
		else if (currentTask.equals("task2.6")){
			if (inEngland) nextTask = "MA_GBR_1150CAx0100";
			else nextTask = "Task3_graph_1-2";
		}
		else if (currentTask.equals("task2.7")){
			if (inEngland) nextTask = "MA_GBR_1150CAx0100";
			else nextTask = "Task1_graph_3-7";
		}
		
		return nextTask;
		
	}
	
	public void getNextStructuredTask(StudentNeedsAnalysis sna, int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial) throws SNAException {
		logger.info("getNextStructuredTask()---values--> whizzStudID="+whizzStudID+" whizzPrevContID="+whizzPrevContID+ " prevScore="+prevScore+ " timestamp"+timestamp.toString()+" WhizzSuggestion="+WhizzSuggestion +" trial="+Trial);
		String message = null;
		
		String nextTask = "";
		int counter=1;
		
		if (student.getInEngland()){
			counter=3;
		}
		
		if ((student.getStructuredTaskCounter() >=1) && (student.getStructuredTaskCounter()<= counter)){
			
			//sequence next structured task
			
			if (sna.isWhizzExercise()) {
				nextTask= WhizzSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
				if ((nextTask == null) || nextTask.equals("") ||  nextTask.equals("-1")){
					logger.info("VPS for Whizz failed, getting fixed sequence");
					String currentTask = student.getCurrentExercise();
					nextTask=calculateNextWhizztask(currentTask);
					message="VPS for Whizz failed, getting fixed sequence ---values--> whizzStudID="+whizzStudID+" whizzPrevContID="+whizzPrevContID+ " prevScore="+prevScore+ " timestamp"+timestamp.toString()+" WhizzSuggestion="+WhizzSuggestion +" trial="+Trial;
				}
			}
			else { 
				nextTask= FTSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
				if ((nextTask == null) || nextTask.equals("") ||  nextTask.equals("-1")){
					logger.info("VPS for CTAT failed, getting fixed sequence");
					String currentTask = student.getCurrentExercise();
					nextTask=calculateNextFTtask(currentTask);
					message="VPS for CTAT failed, getting fixed sequence ---values--> whizzStudID="+whizzStudID+" whizzPrevContID="+whizzPrevContID+ " prevScore="+prevScore+ " timestamp"+timestamp.toString()+" WhizzSuggestion="+WhizzSuggestion +" trial="+Trial;
				}
			}
		}
		else {
			//switch to next unstructured task
			int studentChallenge = student.getLastStudentChallenge();
			if (studentChallenge == StudentChallenge.flow) studentChallenge = StudentChallenge.underChallenged;
			nextTask=calculateNextUnstructuredTask(student.getLastExploratoryExercise(), studentChallenge);
		}
		sna.setNextTask(nextTask);
		if (message!=null && message.length()>0 ){
			throw new SNAException(new Exception(),message);
		}
	}

	private String calculateNextFTtask(String currentTask) {
		String result = "Task2_graph_8-12";
		
		if (currentTask.equals("Task2_graph_9-12")){
			result = "Task2_graph_8-12";
		}
		else if (currentTask.equals("Task8_graph_1-5")){
			result = "Task5_graph_3-4";
		}
		else if (currentTask.equals("Task3_graph_1-2")){
			result = "Task3_graph_2-5";
		}
		else if (currentTask.equals("Task1_graph_3-7")){
			result = "Task1_graph_1-4";
		}	
		return result;
	}

	private String calculateNextWhizztask(String currentTask) {
		String result = "MA_GBR_0825CAx0100";
		/////
		if (currentTask.equals("MA_GBR_0800CAx0100")){
			result = "MA_GBR_0800CAp0100";
		}
		else if (currentTask.equals("MA_GBR_1125CAx0100")){
			result = "MA_GBR_1125CAp0100";
		}
		else if (currentTask.equals("MA_GBR_0850CAx0100")){
			result = "MA_GBR_0850CAp0100";
		}
		else if (currentTask.equals("MA_GBR_0950CAx0100")){
			result = "MA_GBR_0950CAp0100";
		}
		else if (currentTask.equals("MA_GBR_1150CAx0300")){
			result = "MA_GBR_1150CAp0300";
		}
		else if (currentTask.equals("MA_GBR_1150CAx0100")){
			result = "MA_GBR_1150CAp0100";
		}
		else if (currentTask.equals("MA_GBR_1150CAx0100")){
			result = "MA_GBR_1150CAp0100";
		}
		////
		else if (currentTask.equals("MA_GBR_0800CAp0100")){
			result = "MA_GBR_0825CAx0100";
		}
		else if (currentTask.equals("MA_GBR_1125CAp0100")){
			result = "MA_GBR_0700CAx0100";
		}
		else if (currentTask.equals("MA_GBR_0850CAp0100")){
			result = "MA_GBR_0700CAx0200";
		}
		else if (currentTask.equals("MA_GBR_0950CAp0100")){
			result = "MA_GBR_0825CAx0200";
		}
		else if (currentTask.equals("MA_GBR_1150CAp0300")){
			result = "MA_GBR_0900CAx0100";
		}
		else if (currentTask.equals("MA_GBR_1150CAp0100")){
			result = "MA_GBR_1200CAx0200";
		}
		else if (currentTask.equals("MA_GBR_1150CAp0100")){
			result = "MA_GBR_1200CAx0200";
		}
		////
		else if (currentTask.equals("MA_GBR_0825CAx0100")){
			result = "MA_GBR_0825CAp0100";
		}
		else if (currentTask.equals("MA_GBR_0700CAx0100")){
			result = "MA_GBR_0700CAp0100";
		}
		else if (currentTask.equals("MA_GBR_0700CAx0200")){
			result = "MA_GBR_0700CAp0200";
		}
		else if (currentTask.equals("MA_GBR_0825CAx0200")){
			result = "MA_GBR_0825CAp0200";
		}
		else if (currentTask.equals("MA_GBR_0900CAx0100")){
			result = "MA_GBR_0900CAp0100";
		}
		else if (currentTask.equals("MA_GBR_1200CAx0200")){
			result = "MA_GBR_1200CAp0200";
		}
		else if (currentTask.equals("MA_GBR_1200CAx0200")){
			result = "MA_GBR_1200CAp0200";
		}
		
		return result;
	}

}