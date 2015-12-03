package bt.utils;

import java.util.Comparator;

import bt.domain.Trade;

public class TradeIdComparatorForIslem implements Comparator<Trade>{
    
	public int compare(Trade e1, Trade e2) {
	    
	    return e1.getIslemNo().compareTo(e2.getIslemNo());
	    
	    //if (e1.getTime().before(e2.getTime())) {
//	    	if (e1.getIslemNo().compareTo(e2.getIslemNo())<0) {
//	          return -1;
//	        } else if (e1.getSequence()>e2.getSequence()) {
//	          return 1;
//	        } else {
//	          return 0;
//	        }
	}
}
