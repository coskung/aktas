package bt.utils;

import java.util.Comparator;

import bt.domain.Order;

public class TimeComparator implements Comparator<Order>{
    
        public int compare(Order e1, Order e2) {
          if (e1.getTime().before(e2.getTime())) {
            return -1;
          } else if (e1.getTime().after(e2.getTime())) {
            return 1;
          } else {
            return 0;
          }
        }
}