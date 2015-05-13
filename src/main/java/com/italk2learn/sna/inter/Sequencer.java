package com.italk2learn.sna.inter;

import java.sql.Timestamp;

public interface Sequencer {
	
	public String next (int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, boolean type);

}
