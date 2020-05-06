package bt.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Order {

    static DateFormat dateFormatForFile = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.ENGLISH);
    static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.ENGLISH);
    
    String emirNumarasi;
    Date emirTarihi;
    String girisSaati;
    String menkulKiymet;
    String pazar;
    String alis_satis;
    BigDecimal fiyat;
    BigDecimal repo2Fiyati;
    int miktar;
    int bakiye;
    String durum;
    String sonDegistirmeSaati;
    String ilgiliEmirNumarasi;
    Date valor1;
    Date valor2;
    BigDecimal getiri;
    String paraBirimi;
    String repo;
    String hesabi;
    BigDecimal tlTutar;
    BigDecimal temizFiyat;
    BigDecimal takasFiyati;
    
    public String getEmirNumarasi() {
        return emirNumarasi;
    }
    public void setEmirNumarasi(String emirNumarasi) {
        this.emirNumarasi = emirNumarasi;
    }
    public Date getEmirTarihi() {
        return emirTarihi;
    }
    public void setEmirTarihi(Date emirTarihi) {
        this.emirTarihi = emirTarihi;
    }
    public String getGirisSaati() {
        return girisSaati;
    }
    public void setGirisSaati(String girisSaati) {
        this.girisSaati = girisSaati;
    }
    public String getMenkulKiymet() {
        return menkulKiymet;
    }
    public void setMenkulKiymet(String menkulKiymet) {
        this.menkulKiymet = menkulKiymet;
    }
    public String getPazar() {
        return pazar;
    }
    public void setPazar(String pazar) {
        this.pazar = pazar;
    }
    public String getAlis_satis() {
        return alis_satis;
    }
    public void setAlis_satis(String alis_satis) {
        this.alis_satis = alis_satis;
    }
    public BigDecimal getFiyat() {
        return fiyat;
    }
    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }
    public BigDecimal getRepo2Fiyati() {
        return repo2Fiyati;
    }
    public void setRepo2Fiyati(BigDecimal repo2Fiyati) {
        this.repo2Fiyati = repo2Fiyati;
    }
    public int getMiktar() {
        return miktar;
    }
    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
    public int getBakiye() {
		// return bakiye;
		return getMiktar();
    }

	public int getDosyadakiBakiye() {
		return bakiye;
	}
    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }
    public String getDurum() {
        return durum;
    }
    public void setDurum(String durum) {
        this.durum = durum;
    }
    public String getSonDegistirmeSaati() {
        return sonDegistirmeSaati;
    }
    public void setSonDegistirmeSaati(String sonDegistirmeSaati) {
        this.sonDegistirmeSaati = sonDegistirmeSaati;
    }
    public String getIlgiliEmirNumarasi() {
        return ilgiliEmirNumarasi;
    }
    public void setIlgiliEmirNumarasi(String ilgiliEmirNumarasi) {
        this.ilgiliEmirNumarasi = ilgiliEmirNumarasi;
    }
    public Date getValor1() {
        return valor1;
    }
    public void setValor1(Date valor1) {
        this.valor1 = valor1;
    }
    public Date getValor2() {
        return valor2;
    }
    public void setValor2(Date valor2) {
        this.valor2 = valor2;
    }
    public BigDecimal getGetiri() {
        return getiri;
    }
    public void setGetiri(BigDecimal getiri) {
        this.getiri = getiri;
    }
    public String getParaBirimi() {
        return paraBirimi;
    }
    public void setParaBirimi(String paraBirimi) {
        this.paraBirimi = paraBirimi;
    }
    public String getRepo() {
        return repo;
    }
    public void setRepo(String repo) {
        this.repo = repo;
    }
    public String getHesabi() {
        return hesabi;
    }
    public void setHesabi(String hesabi) {
        this.hesabi = hesabi;
    }
    public BigDecimal getTlTutar() {
        return tlTutar;
    }
    public void setTlTutar(BigDecimal tlTutar) {
        this.tlTutar = tlTutar;
    }
    public BigDecimal getTemizFiyat() {
        return temizFiyat;
    }
    public void setTemizFiyat(BigDecimal temizFiyat) {
        this.temizFiyat = temizFiyat;
    }
    public BigDecimal getTakasFiyati() {
        return takasFiyati;
    }
    public void setTakasFiyati(BigDecimal takasFiyati) {
        this.takasFiyati = takasFiyati;
    }
    
    public String toStringCSV(){
        String orderString="";
        orderString+=emirNumarasi+";";
        orderString+=dateFormatForFile.format(emirTarihi)+";";
        orderString+=girisSaati+";";
        orderString+=menkulKiymet+";";
        orderString+=pazar+";";
        orderString+=alis_satis+";";
        orderString+=fiyat+";";
        orderString+=isNull(repo2Fiyati)+";";
        orderString+=miktar+";";
        orderString+=bakiye+";";
        orderString+=durum+";";
        orderString+=sonDegistirmeSaati+";";
        orderString+=ilgiliEmirNumarasi+";";
        orderString+= dateFormatForFile.format(valor1)+";";
        orderString+= isNull(valor2)+";";
        orderString+= getiri+";";
        orderString+= paraBirimi+";";
        orderString+= repo+";";
        orderString+= hesabi+";";
        orderString+= tlTutar+";";
        orderString+= temizFiyat+";";
        orderString+= isNull(takasFiyati)+";";
        return orderString;
}
    public Date getTime(){
        //try {
            Calendar tmp = Calendar.getInstance();
            tmp.setTime(this.getEmirTarihi());
            String girisSaati=this.getGirisSaati();
            if(girisSaati.length()==8)
            	girisSaati="0"+girisSaati;
            tmp.set(Calendar.HOUR, Integer.parseInt(girisSaati.substring(0,2)));
            tmp.set(Calendar.MINUTE, Integer.parseInt(girisSaati.substring(2,4)));
            tmp.set(Calendar.SECOND, Integer.parseInt(girisSaati.substring(4,6)));
            tmp.set(Calendar.MILLISECOND, Integer.parseInt(girisSaati.substring(6,9)));
            return tmp.getTime();
    }
    
    public String isNull(Object s){
        if(s==null)
            return "";
//        else if(s.compareTo("null")==0)
//            return "";
//        else if(s.length()==0)
//            return "";
        else return s.toString();
    }
    
}
