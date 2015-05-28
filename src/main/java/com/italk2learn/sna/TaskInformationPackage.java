package com.italk2learn.sna;

public class TaskInformationPackage {
	
	public TaskInformationPackage(){
		
	}

	public void calculateTaskDescriptionAndRepresentations(String task, StudentNeedsAnalysis sna, boolean inEngland) {
		String currentTask = task.substring(0, 7);
		String taskDescription = "";
		boolean[] representationsFL = {true,true,true,true};
			
		if (currentTask.equals("task2.1")){
			if (inEngland){
				taskDescription = TaskDescription.task2Point1;
			}
			else {
				taskDescription = TaskDescription.gtask2Point1;
			}
		}
		else if (currentTask.equals("task2.2")){
			if (inEngland){
				taskDescription = TaskDescription.task2Point2;
			}
			else {
				taskDescription = TaskDescription.gtask2Point2;
			}
		}
		else if (currentTask.equals("task2.4")){
			if (task.equals("task2.4.setA.area")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setAarea;
				} else{
					taskDescription = TaskDescription.gtask2Point4setAarea;
				}
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.area")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setBarea;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setBarea;
				}
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.area")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setCarea;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setCarea;
				}
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.numb")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setAnumb;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setAnumb;
				}
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.numb")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setBnumb;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setBnumb;
				}
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.numb")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setCnumb;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setCnumb;
				}
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.sets")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setAsets;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setAsets;
				}
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.sets")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setBsets;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setBsets;
				}
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.sets")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setCsets;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setCsets;
				}
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.liqu")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setAliqu;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setAliqu;	
				}
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.liqu")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setBliqu;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setBliqu;	
				}
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.liqu")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point4setCliqu;
				}
				else {
					taskDescription = TaskDescription.gtask2Point4setCliqu;
				}
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			} 
		}
		else if (currentTask.equals("task2.6")){
			if (task.equals("task2.6.setA")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point6setA;
				}
				else {
					taskDescription = TaskDescription.gtask2Point6setA;
				}
			}
			else if (task.equals("task2.6.setB")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point6setB;
				}
				else {
					taskDescription = TaskDescription.gtask2Point6setB;
				}
			}
			else if (task.equals("task2.6.setC")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point6setC;
				}
				else {
					taskDescription = TaskDescription.gtask2Point6setC;
				}
			}
		}
		else if (currentTask.equals("task2.7")){
			if (task.equals("task2.7.setA")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point7setA;
				}
				else {
					taskDescription = TaskDescription.gtask2Point7setA;	
				}
			}
			else if (task.equals("task2.7.setB")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point7setB;
				}
				else {
					taskDescription = TaskDescription.gtask2Point7setB;
				}
			}
			else if (task.equals("task2.7.setC")){
				if (inEngland){
					taskDescription = TaskDescription.task2Point7setC;
				}
				else {
					taskDescription = TaskDescription.gtask2Point7setC;	
				}
			}
			
		}
		sna.setTaskDescription(taskDescription);
		sna.setAvailableRepresentationsInFL(representationsFL);
	} 
			
	

}