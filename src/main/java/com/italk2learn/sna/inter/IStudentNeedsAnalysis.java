package com.italk2learn.sna.inter;

public interface IStudentNeedsAnalysis {
	
	public void sendFeedbackTypeToSNA(String feedbackType);
	
	public void setAudio(byte[] currentAudioStudent);
	
	public void calculateNextTask();
	
	public String getNextTask();
	
	public void setExploratoryExercise(boolean value);
	
	public void setWhizzExercise(boolean value);
	
	public void setFractionsTutorExercise(boolean value);

}
