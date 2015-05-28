package com.italk2learn.sna.inter;

import java.sql.Timestamp;

import com.italk2learn.sna.StructuredActivityType;

public interface Sequencer {
	
	public String next (int whizzStudID, String contID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, StructuredActivityType type);

	public String getRule();

	

}
