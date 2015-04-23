package com.italk2learn.sna;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.italk2learn.sna.exception.SNAException;
import com.italk2learn.sna.inter.IStudentNeedsAnalysis;


@Service("studentNeedsAnalysisService")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StudentNeedsAnalysis implements IStudentNeedsAnalysis {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentNeedsAnalysis.class);
	public byte[] audioStudent;
	public String nextTask;
	private StudentModel student;
	private boolean exploratoryExercise = true;
	private boolean whizzExercise = false;
	private boolean fractionsTutorExercise = false;
	private String taskDescription;
	private boolean[] representationsFL = {true,true,true,true};
	
	
	public StudentNeedsAnalysis(){
		student = new StudentModel();
	}
	
	public void setInEngland(boolean value){
		student.setInEngland(value);
	}
	
	public boolean getEngland(){
		return student.getInEngland();
	}
	
	public void sendRepresentationTypeToSNA(String representationType){
		String area1 = "HRects";
		String area2 = "VRects";
		String numb = "NumberedLines";
		String sets1 = "MoonSets";
		String sets2 = "StarSets";
		String sets3 = "HeartSets";
		String liqu = "LiquidMeasures";
		
		if (representationType.equals(area1) || representationType.equals(area2)){
			student.addAmountArea();
		}
		else if (representationType.equals(numb)){
			student.addAmountNumb();
		}
		else if (representationType.equals(sets1) || representationType.equals(sets2) || representationType.equals(sets3)){
			student.addAmountSets();
		}
		else if (representationType.equals(liqu)){
			student.addAmountLiqu();
		}
		
	}
	
	
	public void sendFeedbackTypeToSNA(String feedbackType){
		String talkAloud = "TALK_ALOUD";
		String affirmation = "AFFIRMATION";
		String talkMaths = "MATHS_VOCAB";
		String nextStep = "NEXT_STEP";
		String problemSolving = "PROBLEM_SOLVING";
		String reflection = "REFLECTION";
		
		System.out.println("::: send feedback to SNA ::: "+feedbackType);
		
		if (feedbackType.equals(talkAloud)) student.addAmountTalkAloud();
		else if (feedbackType.equals(affirmation)) student.addAmountAffirmation();
		else if (feedbackType.equals(talkMaths)) student.addAmountMathsVocab();
		else if (feedbackType.equals(nextStep)) student.addAmountNextStep();
		else if (feedbackType.equals(problemSolving)) student.addAmountProblemSolving();
		else if (feedbackType.equals(reflection)) student.addAmountReflection();
	}
	
	public void sendAffectToSNA(String affectType){
		student.setIncludesAffect(true);
		
		String confusion = "CONFUSION";
		String frustration = "FRUSTRATION";
		String boredom = "BOREDOM";
		String flow = "FLOW";
		String surprise = "SURPRISE";
		
		System.out.println("::: send affect to SNA ::: "+affectType);
		
		if (affectType.equals(confusion)) student.addAmountConfusion();
		else if (affectType.equals(frustration)) student.addAmountFrustration();
		else if (affectType.equals(boredom)) student.addAmountBoredom();
		else if (affectType.equals(flow)) student.addAmountFlow();
		else if (affectType.equals(surprise)) student.addAmountSurprise();
	}
	
	
	public void calculateNextTask(int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial) throws SNAException{
		logger.info("JLF StudentNeedsAnalysis calculateNextTask() ---");
		Analysis analysis = new Analysis(student);
		analysis.analyseSound(audioStudent);
		if (isExploratoryExercise()){
			int counter = student.getUnstructuredTaskCounter();
			counter +=1;
			student.setUnstructuredTaskCounter(counter);
			student.setStructuredTaskCounter(0);
			analysis.analyseFeedbackAndSetNewTask(this);
		}
		else {
			int counter = student.getStructuredTaskCounter();
			counter +=1;
			student.setStructuredTaskCounter(counter);
			student.setUnstructuredTaskCounter(0);
			try {
				analysis.getNextStructuredTask(this, whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
			} catch (SNAException e) {
				// TODO Auto-generated catch block
				throw new SNAException(new Exception(), e.getSnamessage());
			}
		}
			
		student.resetAffectValues();
		student.resetFeedbackValues();
	}
	
	public byte[] getAudio(){
		return audioStudent;
	}
	
	public void setAudio(byte[] currentAudioStudent){
		audioStudent = currentAudioStudent;
	}
	
	public void setNextTask(String task){
		nextTask = task;
		student.setCurrentExercise(task);
		TaskInformationPackage tip = new TaskInformationPackage();
		tip.calculateTaskDescriptionAndRepresentations(task, this);
	}


	public String getNextTask(){
		String result = nextTask;
		nextTask = "";
		return result;
	}
	
	public void setExploratoryExercise(boolean value){
		exploratoryExercise = value;
		if (value){
			setWhizzExercise(false);
			setFractionsTutorExercise(false);
		}
	}
	
	private boolean isExploratoryExercise(){
		return exploratoryExercise;
	}
	
	public void setWhizzExercise(boolean value){
		whizzExercise = value;
		if (value){
			setExploratoryExercise(false);
			setFractionsTutorExercise(false);
		}
	}
	
	public boolean isWhizzExercise(){
		return whizzExercise;
	}
	
	public void setFractionsTutorExercise(boolean value){
		fractionsTutorExercise = value;
		if (value){
			setExploratoryExercise(false);
			setWhizzExercise(false);
		}
	}
	
	public boolean isFractionsTutorExercise(){
		return fractionsTutorExercise;
	}
	
	public void setTaskDescription(String value){
		taskDescription = value;
	}
	
	public String getTaskDescription(){
		return taskDescription;
	}
	
	public void setAvailableRepresentationsInFL(boolean[] values){
		representationsFL = values;
	}
	
	public boolean[] getAvailableRepresentationsInFL(){
		return representationsFL;
	}
}
