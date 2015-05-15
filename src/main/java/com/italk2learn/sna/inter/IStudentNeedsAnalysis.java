package com.italk2learn.sna.inter;

import java.sql.Timestamp;

import com.italk2learn.sna.exception.SNAException;

public interface IStudentNeedsAnalysis {
	
	public void setInEngland(boolean value);
	
	public void sendRepresentationTypeToSNA(String representationType);
	
	public void sendFeedbackTypeToSNA(String feedbackType);
	
	public void setAudio(byte[] currentAudioStudent);
	
	public void calculateNextTask(int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, boolean firstTask) throws SNAException;
	
	public String getNextTask();
	
	public void setExploratoryExercise(boolean value);
	
	public void setWhizzExercise(boolean value);
	
	public void setFractionsTutorExercise(boolean value);
	
	public void sendAffectToSNA(String affectType);
	
	public boolean getEngland();
	
	public String getTaskDescription();
	
	public boolean[] getAvailableRepresentationsInFL();
	
	public void saveStudentModel(int idUser);
	
	public void setStudentModel(int idUser);

}
