package bt.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LOBLine {

	Order e;
	BigDecimal ask;
	BigDecimal bid;
	BigDecimal spread;
	BigDecimal midpoint;
	BigDecimal relativeSpread;
	int askVol;
	int bidVol;
	String aggressive;
	int index;
	int crossOrder;
	int countLOBLine;
	int countLOBLine_BUY;
	int countLOBLine_SELL;
	BigDecimal tradePrice;
	int volumeTraded;
	String extra2b2sdata;
	

    public BigDecimal getAsk() {
        return ask;
    }
    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }
    public BigDecimal getBid() {
        return bid;
    }
    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }
    public BigDecimal getSpread() {
        return spread;
    }
    public void setSpread(BigDecimal spread) {
        this.spread = spread;
    }
    public int getAskVol() {
        return askVol;
    }
    public void setAskVol(int askVol) {
        this.askVol = askVol;
    }
    public int getBidVol() {
        return bidVol;
    }
    public void setBidVol(int bidVol) {
        this.bidVol = bidVol;
    }
	public String getAggressive() {
		return aggressive;
	}
	public void setAggressive(String aggressive) {
		this.aggressive = aggressive;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Order getE() {
		return e;
	}
	public void setE(Order e) {
		this.e = e;
	}
	
	public BigDecimal getMidpoint() {
        return midpoint;
    }
    public void setMidpoint(BigDecimal midSpread) {
        this.midpoint = midSpread;
    }
    public BigDecimal getRelativeSpread() {
        return relativeSpread;
    }
    public void setRelativeSpread(BigDecimal relativeSpread) {
        this.relativeSpread = relativeSpread;
    }
    
    public int getCrossOrder() {
		return crossOrder;
	}
	public void setCrossOrder(int crossOrder) {
		this.crossOrder = crossOrder;
	}
	
	public int getCountLOBLine() {
        return countLOBLine;
    }
    public void setCountLOBLine(int countLOBLine) {
        this.countLOBLine = countLOBLine;
    }
    
    public BigDecimal getTradePrice() {
        return tradePrice;
    }
    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }
    public int getVolumeTraded() {
        return volumeTraded;
    }
    public void setVolumeTraded(int volumeTraded) {
        this.volumeTraded = volumeTraded;
    }
    
    public int getCountLOBLine_BUY() {
        return countLOBLine_BUY;
    }
    public void setCountLOBLine_BUY(int countLOBLine_BUY) {
        this.countLOBLine_BUY = countLOBLine_BUY;
    }
    public int getCountLOBLine_SELL() {
        return countLOBLine_SELL;
    }
    public void setCountLOBLine_SELL(int countLOBLine_SELL) {
        this.countLOBLine_SELL = countLOBLine_SELL;
    }
    
    public String getExtra2b2sdata() {
        return extra2b2sdata;
    }
    public void setExtra2b2sdata(String extra2b2sdata) {
        this.extra2b2sdata = extra2b2sdata;
    }
    public LOBLine() {
		// TODO Auto-generated constructor stub
	}
	public LOBLine(Order e, BigDecimal ask, BigDecimal bid, int askV, int sellV, int ind) {
		this.e = e;
		this.ask=ask;
		this.bid=bid;
		this.askVol=askV;
		this.bidVol=sellV;
		this.index=ind;
		if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.spread=ask.subtract(bid);
        if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.midpoint=(ask.add(bid)).divide(new BigDecimal(2),3,RoundingMode.HALF_UP);
        if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.relativeSpread=((ask.subtract(bid)).divide(ask.add(bid),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(200));
	}
	public LOBLine(Order e, BigDecimal ask, BigDecimal bid, int askV, int sellV, int ind, String aggr) {
		this.e = e;
		this.ask=ask;
		this.bid=bid;
		this.askVol=askV;
		this.bidVol=sellV;
		this.index=ind;
		this.aggressive=aggr;
		if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
			this.spread=ask.subtract(bid);
		if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.midpoint=(ask.add(bid)).divide(new BigDecimal(2),3,RoundingMode.HALF_UP);
        if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.relativeSpread=((ask.subtract(bid)).divide(ask.add(bid),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(200));
	}
	
	public LOBLine(Order e, BigDecimal ask, BigDecimal bid, int askV, int sellV, int ind, String aggr, int cross) {
		this.e = e;
		this.ask=ask;
		this.bid=bid;
		this.askVol=askV;
		this.bidVol=sellV;
		this.index=ind;
		this.aggressive=aggr;
		if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
			this.spread=ask.subtract(bid);
		if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.midpoint=(ask.add(bid)).divide(new BigDecimal(2),3,RoundingMode.HALF_UP);
        if((bid!=null && ask!=null) && (ask.compareTo(BigDecimal.ZERO)!=0 && bid.compareTo(BigDecimal.ZERO)!=0))
            this.relativeSpread=((ask.subtract(bid)).divide(ask.add(bid),2, RoundingMode.HALF_UP)).multiply(new BigDecimal(200));
        this.crossOrder=cross;
	}
	
	@Override
    public String toString(){
		String lobLineString="";
		//lobLineString+=index+";";
		lobLineString+=e.toStringCSV();
		lobLineString+=bid+";";
		lobLineString+=ask+";";
		lobLineString+=formatDisplay(spread,3)+";";
		lobLineString+=bidVol+";";
		lobLineString+=askVol+";";
		lobLineString+=aggressive+";";
		lobLineString+=crossOrder+";";
		lobLineString+=e.emirNumarasi+";";
		lobLineString+=formatDisplay(midpoint,4)+";";
		//lobLineString+=formatDisplay(relativeSpread)+";";
		return lobLineString;
	}
	
	public String toString2() {
		String lobLineString="";
		//lobLineString+=index+";";
		lobLineString+=e.toStringCSV();
		lobLineString+=bid+";";
		lobLineString+=ask+";";
		lobLineString+=formatDisplay(spread,3)+";";
		lobLineString+=bidVol+";";
		lobLineString+=askVol+";";
		lobLineString+=aggressive+";";
		lobLineString+=crossOrder+";";
		lobLineString+=e.emirNumarasi+";";
		lobLineString+=formatDisplay(midpoint,4)+";";
        //lobLineString+=formatDisplay(relativeSpread)+";";
		return lobLineString;
	}
	
	public String toStringDynamic() {
        String lobLineString="";
        //lobLineString+=index+";";
        lobLineString+=e.toStringCSV();
        lobLineString+=bid+";";
        lobLineString+=ask+";";
        lobLineString+=formatDisplay(spread,3)+";";
        lobLineString+=bidVol+";";
        lobLineString+=askVol+";";
        lobLineString+=aggressive+";";
        lobLineString+=crossOrder+";";
        //lobLineString+=e.index+";";
        //lobLineString+=e.tradeNo+";";
        lobLineString+=countLOBLine+";";
        lobLineString+=countLOBLine_BUY+";";
        lobLineString+=countLOBLine_SELL+";";
        //lobLineString+=tradePrice+";";
        //lobLineString+=volumeTraded+";";
        //lobLineString+=numberOfTrades+";";
        //lobLineString+=formatDisplay(midSpread)+";";
        //lobLineString+=formatDisplay(relativeSpread)+";";
        lobLineString+=formatDisplay(midpoint,4)+";";
        return lobLineString;
    }
	
	
	public String formatDisplay(BigDecimal spread2,int len) {
		String s = spread2 + "";
		if (s.contains(".")) {
			int ind = s.lastIndexOf(".");
			if(s.length()-ind>2){
				String t = s.substring(0, ind + len);
				return t;
			}
		}
		return s;
	}
}
