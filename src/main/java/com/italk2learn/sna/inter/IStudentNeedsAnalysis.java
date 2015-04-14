package com.italk2learn.sna.inter;

import java.sql.Timestamp;

public interface IStudentNeedsAnalysis {
	
	public void sendFeedbackTypeToSNA(String feedbackType);
	
	public void setAudio(byte[] currentAudioStudent);
	
	public void calculateNextTask(int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, boolean Trial);
	
	public String getNextTask();
	
	public void setExploratoryExercise(boolean value);
	
	public void setWhizzExercise(boolean value);
	
	public void setFractionsTutorExercise(boolean value);

}
