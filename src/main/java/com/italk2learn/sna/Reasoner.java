package com.italk2learn.sna;

import java.sql.Timestamp;

import MFSeq.FTSequencer;
import MFSeq.WhizzSequencer;

public class Reasoner {
	StudentModel student;
	
	public Reasoner (StudentModel thisStudent){
		student = thisStudent;
	}

	public void getNextTask(StudentNeedsAnalysis sna) {
		int studentChallenge = student.getStudentChallenge();
		String nextTask = "";
		
		if (studentChallenge == StudentChallenge.overChallenged){
			System.out.println("::: next task ::: task2.1");
			nextTask = "task2.1";
		}
		else if (studentChallenge == StudentChallenge.underChallenged){
			System.out.println("::: next task ::: task2.4.setB.liqu");
			nextTask = "task2.4.setB.liqu";
		}
		else {
			System.out.println("::: next task ::: task2.4.setA.area");
			nextTask = "task2.4.setA.area";
		}
		sna.setNextTask(nextTask);
	}
	
	public void getNextStructuredTask(StudentNeedsAnalysis sna, int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, boolean Trial) {
		
		//Carlottas calculations
		String nextTask = "";
		if (sna.isWhizzExercise()) {
			nextTask= WhizzSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
		}
		else { 
			nextTask= FTSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion);
		}
		sna.setNextTask(nextTask);
	}

}
