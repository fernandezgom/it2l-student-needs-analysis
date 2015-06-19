package com.italk2learn.sna;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.italk2learn.sna.exception.SNAException;

import ptdFromAmplitudes.CreateWav;
import ptdFromAmplitudes.PtdFromAmplitudes;

public class Analysis {
	
	private static final Logger logger = LoggerFactory.getLogger(Analysis.class);
	
	StudentModel student;
	StudentNeedsAnalysis sna;
	
	public Analysis(StudentModel thisStudent, StudentNeedsAnalysis snaValue){
		student = thisStudent;
		sna = snaValue;
	}

	
	public void analyseSound(byte[] audioByteArray){
		int result;
		if (audioByteArray!=null && audioByteArray.length>0 ) {
			String wavname;
	        List<byte[]> exampleChunks = new ArrayList<byte[]>();
	        exampleChunks.add(audioByteArray);
	
	        int numberOfChunksToCombine = exampleChunks.size();
	
	        CreateWav wavcreation = new CreateWav();
	        for (int i = 0; i < numberOfChunksToCombine; i++) {
	            wavcreation.addChunk(exampleChunks.get(i));
	        }
	
	        // Initialize ptd classifier:
	        PtdFromAmplitudes ptdAmpl = new PtdFromAmplitudes();
	
	        // get perceived task difficulty (ptd):
	
	        // Create wav from the last x (here numberOfChunksToCombine) chunks (x = seconds/5)
	        //wavname = wavcreation.createWavFileMonoOrStereo(numberOfChunksToCombine);
	        wavname = wavcreation.createWavFile(numberOfChunksToCombine);
	        
	        result = ptdAmpl.getPTD(wavname);
        
		} else {
			result=-1;
		}
        
		sna.saveLog("SNA.sc.PTDC.value", ""+result);
		
        if (result == -1){
        	System.out.println("PTD: no result");
        	sna.saveLog("SNA.sc.PTDC", "noResult");
        }
        else if (result == 1){
        	System.out.println("PTD: overchallenged");
        	sna.saveLog("SNA.sc.PTDC", "overchallenged");
        }
        else if (result == 2){
        	System.out.println("PTD: flow");
        	sna.saveLog("SNA.sc.PTDC", "flow");
        }
        else if (result == 3){
        	System.out.println("PTD: underchallenged");
        	sna.saveLog("SNA.sc.PTDC", "underchallenged");
        }
        		
        if (student == null) student = new StudentModel();
		student.setPTD(result);
	}
	
	public void getNextStructuredTask(StudentNeedsAnalysis sna, int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial) throws SNAException{
		logger.info("JLF Analysis getNextStructuredTask() ---");
		calculateStudentChallenge();
		Reasoner reasoner = new Reasoner(student);
		try {
			reasoner.getNextStructuredTask(sna, whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
		} catch (SNAException e) {
			// TODO Auto-generated catch block
			throw new SNAException(new Exception(), e.getSnamessage());
		}
	}


	public void analyseFeedbackAndSetNewTask(StudentNeedsAnalysis sna) {
		calculateStudentChallenge();
		Reasoner reasoner = new Reasoner(student);
		reasoner.getNextTask(sna);
	}
	
	private void calculateStudentChallenge(){
		int studentChallenge = StudentChallenge.flow;
		int valuePTP = student.getPTD();
		int feedbackAmount = calculateFeedbackAmount();
		int mostAffectiveState = calculateAffectiveState();
		int lotsFeedback = 7;
		int someFeedback = 4;
		
		String lastFeedbackProvided = student.getLastFeedbackProvided();
		
		sna.saveLog("SNA.sc.feedbackAmount", ""+feedbackAmount);
		
		//this value needs to be checked.
		if (lastFeedbackProvided.equals("TASK_NOT_FINISHED")){
			sna.saveLog("SNA.sc.taskNotFinished", "feedback+5");
			feedbackAmount += 5;
		}
		
		System.out.println(":: calculateStudentChallenge ::");
		System.out.println(":: feedbackAmount :: "+feedbackAmount);
		System.out.println("lotsFeedback: "+lotsFeedback);
		System.out.println("someFeedback: "+someFeedback);
		/*
		 * perceived task difficulty classifier PTD
		 * 1=overchallenged
		 * 2=flow
		 * 3=underchallenged
		 */
		if (feedbackAmount > lotsFeedback){
			studentChallenge = StudentChallenge.overChallenged;
			if (valuePTP == 3){
				if ((mostAffectiveState == Affect.boredom) || (mostAffectiveState == Affect.flow)){
					studentChallenge = StudentChallenge.flow;
					sna.saveLog("SNA.sc.rule", ""+"lotsFeedbackAndOverChallenged1");
				}
				else {
					sna.saveLog("SNA.sc.rule", ""+"lotsFeedbackAndOverChallenged2");
				}
			}
			else {
				sna.saveLog("SNA.sc.rule", ""+"lotsFeedbackAndOverChallengedElse");
			}
		}
		else if (feedbackAmount > someFeedback){
			studentChallenge = StudentChallenge.flow;
			if ((valuePTP == 1) ||  (valuePTP == -1)){
				if ((mostAffectiveState == Affect.confusion) || (mostAffectiveState == Affect.frustration)){
					studentChallenge = StudentChallenge.overChallenged;
					sna.saveLog("SNA.sc.rule", ""+"SomeFeedbackAndFlow1");
				}
				else {
					sna.saveLog("SNA.sc.rule", ""+"SomeFeedbackAndFlow2");
				}
			}
			else if (valuePTP == 3){
				if ((mostAffectiveState == Affect.boredom) || (mostAffectiveState == Affect.flow)){
					sna.saveLog("SNA.sc.rule", ""+"UnderChallengedAndFlow1");
					studentChallenge = StudentChallenge.underChallenged;
				}
				else {
					sna.saveLog("SNA.sc.rule", ""+"UnderChallengedAndFlow2");
				}
			}
			else {
				sna.saveLog("SNA.sc.rule", ""+"SomeFeedbackAndFlowElse");
			}
		}
		else {
			studentChallenge = StudentChallenge.underChallenged;
			if (valuePTP == 1){
				studentChallenge = StudentChallenge.flow;
				if (mostAffectiveState == Affect.boredom){
					sna.saveLog("SNA.sc.rule", ""+"UnderChallengedAndBoredom1");
					studentChallenge = StudentChallenge.underChallenged;
				}
				else {
					sna.saveLog("SNA.sc.rule", ""+"UnderChallengedAndBoredom2");
				}
			}
			else if (valuePTP == 2){
				if ((mostAffectiveState == Affect.confusion) || (mostAffectiveState == Affect.frustration)){
					sna.saveLog("SNA.sc.rule", ""+"FlowdAndFrustration1");
					studentChallenge = StudentChallenge.flow;
				}
				else {
					sna.saveLog("SNA.sc.rule", ""+"FlowdAndFrustration2");
				}
			}
			else {
				if (mostAffectiveState == Affect.confusion){
					sna.saveLog("SNA.sc.rule", ""+"Confusion");
					studentChallenge = StudentChallenge.flow;
				}
			}
		}

		printStudentChallenge(studentChallenge);
		student.setStudentChallenge(studentChallenge);
	}

	private void printStudentChallenge(int studentChallenge){
		if (studentChallenge == StudentChallenge.overChallenged){
			System.out.println("::: student is over challenged :::");
			sna.saveLog("SNA.sc", ""+"overChallenged");
		}
		else if (studentChallenge == StudentChallenge.flow){
			System.out.println("::: student is in flow :::");
			sna.saveLog("SNA.sc", ""+"flow");
		}
		else if (studentChallenge == StudentChallenge.underChallenged){
			System.out.println("::: student is under challenged :::");
			sna.saveLog("SNA.sc", ""+"underChallenged");
		}
	}

	private int calculateAffectiveState() {
		int amountConfusion = student.getAmountConfusion();
		int amountFrustration = student.getAmountFrustration();
		int amountBoredom = student.getAmountBoredom();
		int amountFlow = student.getAmountFlow();
		int result = Affect.flow;
		
		if ((amountConfusion > amountFrustration) &&
				(amountConfusion > amountBoredom) &&
				(amountConfusion > amountFlow)){
			sna.saveLog("SNA.sc.TIS.affect", "confusion");
			result = Affect.confusion;
		}
		else if ((amountFrustration > amountConfusion) &&
				(amountFrustration > amountBoredom) &&
				(amountFrustration > amountFlow)){
			sna.saveLog("SNA.sc.TIS.affect", "frustration");
			result = Affect.frustration;
		}
		else if ((amountBoredom > amountConfusion) &&
				(amountBoredom > amountFrustration) &&
				(amountBoredom > amountFlow)){
			sna.saveLog("SNA.sc.TIS.affect", "boredom");
			result = Affect.boredom;
		}
		
		else if ((amountFlow > amountConfusion) &&
				(amountFlow > amountFrustration) &&
				(amountFlow > amountBoredom)){
			sna.saveLog("SNA.sc.TIS.affect", "flow");
			result = Affect.flow;
		}
		
		return result;
	}


	private int calculateFeedbackAmount() {
		int result = 0;
		
		result = student.getAmountNextStep() + student.getAmountProblemSolving() + 
				student.getAmountReflection() + student.getAmountAffectBoosts();
		
		return result;
	}
	
}