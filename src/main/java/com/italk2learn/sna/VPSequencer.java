package com.italk2learn.sna;

import java.sql.Timestamp;

import MFSeq.FTSequencer;
import MFSeq.WhizzSequencer;

import com.italk2learn.sna.inter.Sequencer;



public class VPSequencer implements Sequencer {

	public VPSequencer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String next (int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, boolean type) {
			if (type == false)
				return FTSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
			else 
				return WhizzSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
	}
}
