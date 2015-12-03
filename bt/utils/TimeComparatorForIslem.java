package bt.utils;

import java.util.Comparator;

import bt.domain.Trade;

public class TimeComparatorForIslem implements Comparator<Trade>{
    
	public int compare(Trade e1, Trade e2) {
	    return e1.getZaman().compareTo(e2.getZaman());
//	    if (e1.get.before(e2.getTime())) {
//	          return -1;
//	        } else if (e1.getTime().after(e2.getTime())) {
//	          return 1;
//	        } else {
//	          return 0;
//	        }
	}
}
