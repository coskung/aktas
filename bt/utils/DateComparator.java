package bt.utils;

import java.util.Comparator;

import bt.domain.Order;

public class DateComparator implements Comparator<Order>{
    
	public int compare(Order e1, Order e2) {
		if (e1.getEmirTarihi().compareTo(e2.getEmirTarihi()) < 0) {
			return -1;
		} else if (e1.getEmirTarihi().compareTo(e2.getEmirTarihi()) > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
