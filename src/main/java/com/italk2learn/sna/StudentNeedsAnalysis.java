package com.italk2learn.sna;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibernate.dto.Studentmodel;
import com.italk2learn.bo.inter.ILoginUserService;
import com.italk2learn.dao.inter.ISNALogDAO;
import com.italk2learn.dao.inter.IStudentModelDAO;
import com.italk2learn.exception.ITalk2LearnException;
import com.italk2learn.sna.exception.SNAException;
import com.italk2learn.sna.inter.IStudentNeedsAnalysis;
import com.italk2learn.vo.ExerciseSequenceRequestVO;
import com.italk2learn.vo.HeaderVO;


@Service("studentNeedsAnalysisService")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional(rollbackFor = { ITalk2LearnException.class, ITalk2LearnException.class })
public class StudentNeedsAnalysis implements IStudentNeedsAnalysis {
	
	@Autowired
	public IStudentModelDAO studentModelDAO;
	@Autowired
	public ILoginUserService loginUserService;
	@Autowired
	public ISNALogDAO snaLogDAO;
	


	private static final Logger logger = LoggerFactory.getLogger(StudentNeedsAnalysis.class);
	public byte[] audioStudent;
	public String nextTask;
	private StudentModel student;
	private boolean exploratoryExercise = true;
	private boolean whizzExercise = false;
	private boolean fractionsTutorExercise = false;
	private String taskDescription;
	private boolean[] representationsFL = {true,true,true,true};
	String nameForValueThatNeedsTogetSavedinDB = "";
	String valueThatNeedsTogetSavedinDB = "";
	
	public StudentNeedsAnalysis(){
		student=new StudentModel();
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
		
		saveLog("SNA.set.representation", representationType);
		
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
		String taskNotFinished = "TASK_NOT_FINISHED";
		
		saveLog("SNA.set.feedback.type", feedbackType);
		
		student.setLastFeedbackProvided(feedbackType);
		
		System.out.println("::: send feedback to SNA ::: "+feedbackType);
		
		if (feedbackType.equals(talkAloud)) student.addAmountTalkAloud();
		else if (feedbackType.equals(affirmation)) student.addAmountAffirmation();
		else if (feedbackType.equals(talkMaths)) student.addAmountMathsVocab();
		else if (feedbackType.equals(nextStep)) student.addAmountNextStep();
		else if (feedbackType.equals(problemSolving)) student.addAmountProblemSolving();
		else if (feedbackType.equals(reflection)) student.addAmountReflection();
		else if (feedbackType.equals(taskNotFinished)) student.addAmountTaskNotFinished();
	}
	
	public void sendAffectToSNA(String affectType){
		student.setIncludesAffect(true);
		
		String confusion = "CONFUSION";
		String frustration = "FRUSTRATION";
		String boredom = "BOREDOM";
		String flow = "FLOW";
		String surprise = "SURPRISE";
		
		saveLog("SNA.set.affect", affectType);
		
		System.out.println("::: send affect to SNA ::: "+affectType);
		
		if (affectType.equals(confusion)) student.addAmountConfusion();
		else if (affectType.equals(frustration)) student.addAmountFrustration();
		else if (affectType.equals(boredom)) student.addAmountBoredom();
		else if (affectType.equals(flow)) student.addAmountFlow();
		else if (affectType.equals(surprise)) student.addAmountSurprise();
	}
	
	public void setStudentModel(int idUser){
		//boolean isExploratoryExercise = true; 
		int studentChallenge = 0;
		String currentExercise = "task2.2"; 
		int unstructuredCounter = 0; 
		int structuredCounter = 0;
		String lastExploratoryExercise = "task2.2";
		try {
			Studentmodel sm = getStudentModelDAO().getCurrentStudentModelByUser(idUser);
			if (sm!=null) {
//				if(sm.getIsExploratoryExercise()==0)
//					isExploratoryExercise = false;
				studentChallenge = sm.getStudentChallenge();
				currentExercise = sm.getCurrentExercise();
				unstructuredCounter = sm.getUnstructuredCounter();
				structuredCounter = sm.getStructuredCounter();
				lastExploratoryExercise = sm.getLastExploratoryExercise();
			}
			
			if (currentExercise.contains("task2.")){
				exploratoryExercise = true;
			}
			else {
				exploratoryExercise = false;
			}
			//exploratoryExercise = isExploratoryExercise;
			student.setStudentChallenge(studentChallenge);
			student.setCurrentExercise(currentExercise);
			student.setUnstructuredTaskCounter(unstructuredCounter);
			student.setStructuredTaskCounter(structuredCounter);
			student.setLastExploratoryExercise(lastExploratoryExercise);
			
			saveLog("SNA.set.student.idUser", ""+idUser);
			saveLog("SNA.set.student.challenge", ""+studentChallenge);
			saveLog("SNA.set.student.exercise", currentExercise);
			saveLog("SNA.set.student.counter.unstructured", ""+unstructuredCounter);
			saveLog("SNA.set.student.counter.structured", ""+structuredCounter);
		} catch (ITalk2LearnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveStudentModel(int idUser){
		boolean isExploratoryExercise = exploratoryExercise;
		int studentChallenge = student.getStudentChallenge();
		String currentExercise = student.getCurrentExercise();
		int unstructuredCounter = student.getUnstructuredTaskCounter();
		int structuredCounter = student.getStructuredTaskCounter();
		String lastExploratoryExercise = student.getLastExploratoryExercise();
		
		saveLog("SNA.save.student.idUser", ""+idUser);
		saveLog("SNA.save.student.challenge", ""+studentChallenge);
		saveLog("SNA.wave.student.exercise", currentExercise);
		saveLog("SNA.save.student.counter.unstructured", ""+unstructuredCounter);
		saveLog("SNA.save.student.counter.structured", ""+structuredCounter);
		saveLog("SNA.save.student.lastExploratoryExercise", ""+lastExploratoryExercise);
		try {
			getStudentModelDAO().insertCurrentStudentModelByUser(idUser, isExploratoryExercise, studentChallenge, currentExercise, unstructuredCounter, structuredCounter, lastExploratoryExercise);
		} catch (ITalk2LearnException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}
	}
	
	public void saveLog(String name, String value){
		ExerciseSequenceRequestVO request= new ExerciseSequenceRequestVO();
		LdapUserDetailsImpl	user = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.setHeaderVO(new HeaderVO());
		request.getHeaderVO().setLoginUser(user.getUsername());
		try {
			getSnaLogDAO().storeDataSNA(getLoginUserService().getIdUserInfo(request.getHeaderVO()), name, value);
		} catch (ITalk2LearnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nameForValueThatNeedsTogetSavedinDB = name;
		valueThatNeedsTogetSavedinDB = value;
	}
	
	public String getLogName(){
		String result = "";
		if (!nameForValueThatNeedsTogetSavedinDB.equals("")){
			result = nameForValueThatNeedsTogetSavedinDB;
			nameForValueThatNeedsTogetSavedinDB = "";
		}
		return result;
	}
	
	public String getLogValue(){
		String result = "";
		if (!valueThatNeedsTogetSavedinDB.equals("")){
			result = valueThatNeedsTogetSavedinDB;
			valueThatNeedsTogetSavedinDB = "";
		}
		return result;
	}
	
	
	public void calculateNextTask(int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, boolean firstTask) throws SNAException{
		logger.info("JLF StudentNeedsAnalysis calculateNextTask() ---");
		if(firstTask){
			setNextTask(student.getCurrentExercise(), "firstTaskFromStudentModel");
		}
		else{
		
		Analysis analysis = new Analysis(student, this);
		
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
	}
	
	public byte[] getAudio(){
		return audioStudent;
	}
	
	public void setAudio(byte[] currentAudioStudent){
		audioStudent = currentAudioStudent;
	}
	
	public void setNextTask(String task, String rule){
		nextTask = task;
		student.setCurrentExercise(task);
		TaskInformationPackage tip = new TaskInformationPackage();
		tip.calculateTaskDescriptionAndRepresentations(task, this, student.getInEngland());
		
		saveLog("sna.feedback.nextStep", ""+student.getAmountNextStep());
		saveLog("sna.feedback.problemSolving", ""+student.getAmountProblemSolving());
		saveLog("sna.feedback.affectBoosts", ""+student.getAmountAffectBoosts());
		saveLog("sna.feedback.mathsVocab", ""+student.getAmountMathsVocab());
		saveLog("sna.feedback.talkAloud", ""+student.getAmountTalkAloud());
		saveLog("sna.feedback.affirmation", ""+student.getAmountAffirmation());
		saveLog("sna.feedback.taskNotFinished", ""+student.getAmountTaskNotFinished());
		
		saveLog("sna.setNextTask.sc",student.getStudentChallengeAsString());
		saveLog("sna.setNextTask.rule",rule);
		saveLog("sna.setNextTask.task",task);
	}


	public String getNextTask(){
		String result = nextTask;
		nextTask = "";
		return result;
	}
	
	public void setExploratoryExercise(boolean value){
		exploratoryExercise = value;
		saveLog("sna.set.Exploratory", ""+value);
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
		saveLog("sna.set.Whizz", ""+value);
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
		saveLog("sna.set.FractionsTutor", ""+value);
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

	public IStudentModelDAO getStudentModelDAO() {
		return studentModelDAO;
	}

	public void setStudentModelDAO(IStudentModelDAO studentModelDAO) {
		this.studentModelDAO = studentModelDAO;
	}
	
	public ILoginUserService getLoginUserService() {
		return loginUserService;
	}
	
	public ISNALogDAO getSnaLogDAO() {
		return snaLogDAO;
	}
}