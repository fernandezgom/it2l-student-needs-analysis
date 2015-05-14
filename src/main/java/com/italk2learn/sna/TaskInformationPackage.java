package com.italk2learn.sna;

public class TaskInformationPackage {
	
	public TaskInformationPackage(){
		
	}

	public void calculateTaskDescriptionAndRepresentations(String task, StudentNeedsAnalysis sna) {
		String currentTask = task.substring(0, 7);
		String taskDescription = "";
		boolean[] representationsFL = {true,true,true,true};
			
		if (currentTask.equals("task2.1")){
			taskDescription = TaskDescription.task2Point1;
		}
		else if (currentTask.equals("task2.2")){
			taskDescription = TaskDescription.task2Point2;
		}
		else if (currentTask.equals("task2.4")){
			if (task.equals("task2.4.setA.area")){
				taskDescription = TaskDescription.task2Point4setAarea;
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.area")){
				taskDescription = TaskDescription.task2Point4setBarea;
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.area")){
				taskDescription = TaskDescription.task2Point4setCarea;
				boolean[] newRepresentationsFL = {false,true,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.numb")){
				taskDescription = TaskDescription.task2Point4setAnumb;
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.numb")){
				taskDescription = TaskDescription.task2Point4setBnumb;
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.numb")){
				taskDescription = TaskDescription.task2Point4setCnumb;
				boolean[] newRepresentationsFL = {true,false,false,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.sets")){
				taskDescription = TaskDescription.task2Point4setAsets;
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.sets")){
				taskDescription = TaskDescription.task2Point4setBsets;
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.sets")){
				taskDescription = TaskDescription.task2Point4setCsets;
				boolean[] newRepresentationsFL = {false,false,true,false};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setA.liqu")){
				taskDescription = TaskDescription.task2Point4setAliqu;
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setB.liqu")){
				taskDescription = TaskDescription.task2Point4setBliqu;
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			}
			else if (task.equals("task2.4.setC.liqu")){
				taskDescription = TaskDescription.task2Point4setCliqu;
				boolean[] newRepresentationsFL = {false,false,false,true};
				representationsFL = newRepresentationsFL;
			} 
		}
		else if (currentTask.equals("task2.6")){
			if (task.equals("task2.6.setA")){
				taskDescription = TaskDescription.task2Point6setA;
			}
			else if (task.equals("task2.6.setB")){
				taskDescription = TaskDescription.task2Point6setB;
			}
			else if (task.equals("task2.6.setC")){
				taskDescription = TaskDescription.task2Point6setC;
			}
		}
		else if (currentTask.equals("task2.7")){
			if (task.equals("task2.7.setA")){
				taskDescription = TaskDescription.task2Point7setA;
			}
			else if (task.equals("task2.7.setB")){
				taskDescription = TaskDescription.task2Point7setB;
			}
			else if (task.equals("task2.7.setC")){
				taskDescription = TaskDescription.task2Point7setC;
			}
			
		}
		sna.setTaskDescription(taskDescription);
		sna.setAvailableRepresentationsInFL(representationsFL);
	} 
			
	

}