package com.italk2learn.sna;

import java.sql.Timestamp;

import com.italk2learn.sna.inter.Sequencer;

public class SNASequencer implements Sequencer {

	public String next(int whizzStudID, String contID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, StructuredActivityType type) {
		String nextTask = "";
		if (type == StructuredActivityType.WHIZZ){
			nextTask = calculateNextWhizztask(contID);
		}
		else {
			nextTask=calculateNextFTtask(contID);
		}
		return nextTask;
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
