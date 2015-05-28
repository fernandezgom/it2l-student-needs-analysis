package com.italk2learn.sna;

import java.sql.Timestamp;

import MFSeq.FTSequencer;
import MFSeq.WhizzSequencer;

import com.italk2learn.sna.inter.Sequencer;

public class VPSequencer implements Sequencer {

	public VPSequencer() {
		super();
	}
	
	public String next (int whizzStudID, String whizzPrevContID, int prevScore, Timestamp timestamp, String WhizzSuggestion, int Trial, StructuredActivityType type) {
	    if (type == StructuredActivityType.FRACTIONS_TUTOR) {
		  return FTSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
	    } else if (type == StructuredActivityType.WHIZZ) {
		  return WhizzSequencer.next(whizzStudID, whizzPrevContID, prevScore, timestamp, WhizzSuggestion, Trial);
	    } else {
		  throw new IllegalStateException("Invalid structured activity type: " + type);
	    }
	}
	
	public String getRule() {
		// TODO Auto-generated method stub
		return null;
	}
}
