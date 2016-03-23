package bt.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Trade {

    static DateFormat dateFormatForFile = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.ENGLISH);

    
    String islemNo;
    Date islemTarihi;
    String paraBirimi;
    String zaman;
    String b_emirNo;
    String s_emirNo;
    String menkulKiymet;
    String pazar;
    BigDecimal fiyat;
    BigDecimal repo2Fiyati;
    int miktar;
    BigDecimal tlTutar;
    int repoFaizi;
    int stopaj;
    Date valor1;
    Date valor2;
    BigDecimal getiri;
    String repoSuresi;
    BigDecimal temizFiyat;
    BigDecimal islemisFaiz;
    BigDecimal islemisFaizTutari;
    BigDecimal anaparaTutari;
    BigDecimal takasFiyati;
    BigDecimal kirliFiyat;
    BigDecimal enflasyonKatsayisi;
    String kendineFon;
    Boolean isProcessed;
    
    public String getIslemNo() {
        return islemNo;
    }
    public void setIslemNo(String islemNo) {
        this.islemNo = islemNo;
    }
    public Date getIslemTarihi() {
        return islemTarihi;
    }
    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }
    public String getParaBirimi() {
        return paraBirimi;
    }
    public void setParaBirimi(String paraBirimi) {
        this.paraBirimi = paraBirimi;
    }
    public String getZaman() {
        return zaman;
    }
    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
    public String getB_emirNo() {
        return b_emirNo;
    }
    public void setB_emirNo(String b_emirNo) {
        this.b_emirNo = b_emirNo;
    }
    public String getS_emirNo() {
        return s_emirNo;
    }
    public void setS_emirNo(String s_emirNo) {
        this.s_emirNo = s_emirNo;
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
    public BigDecimal getTlTutar() {
        return tlTutar;
    }
    public void setTlTutar(BigDecimal tlTutar) {
        this.tlTutar = tlTutar;
    }
    public int getRepoFaizi() {
        return repoFaizi;
    }
    public void setRepoFaizi(int repoFaizi) {
        this.repoFaizi = repoFaizi;
    }
    public int getStopaj() {
        return stopaj;
    }
    public void setStopaj(int stopaj) {
        this.stopaj = stopaj;
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
    public String getRepoSuresi() {
        return repoSuresi;
    }
    public void setRepoSuresi(String repoSuresi) {
        this.repoSuresi = repoSuresi;
    }
    public BigDecimal getTemizFiyat() {
        return temizFiyat;
    }
    public void setTemizFiyat(BigDecimal temizFiyat) {
        this.temizFiyat = temizFiyat;
    }
    public BigDecimal getIslemisFaiz() {
        return islemisFaiz;
    }
    public void setIslemisFaiz(BigDecimal islemisFaiz) {
        this.islemisFaiz = islemisFaiz;
    }
    public BigDecimal getIslemisFaizTutari() {
        return islemisFaizTutari;
    }
    public void setIslemisFaizTutari(BigDecimal islemisFaizTutari) {
        this.islemisFaizTutari = islemisFaizTutari;
    }
    public BigDecimal getAnaparaTutari() {
        return anaparaTutari;
    }
    public void setAnaparaTutari(BigDecimal anaparaTutari) {
        this.anaparaTutari = anaparaTutari;
    }
    public BigDecimal getTakasFiyati() {
        return takasFiyati;
    }
    public void setTakasFiyati(BigDecimal takasFiyati) {
        this.takasFiyati = takasFiyati;
    }
    public BigDecimal getKirliFiyat() {
        return kirliFiyat;
    }
    public void setKirliFiyat(BigDecimal kirliFiyat) {
        this.kirliFiyat = kirliFiyat;
    }
    public BigDecimal getEnflasyonKatsayisi() {
        return enflasyonKatsayisi;
    }
    public void setEnflasyonKatsayisi(BigDecimal enflasyonKatsayisi) {
        this.enflasyonKatsayisi = enflasyonKatsayisi;
    }
    public String getKendineFon() {
        return kendineFon;
    }
    public void setKendineFon(String kendineFon) {
        this.kendineFon = kendineFon;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }
    public void setProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
    public String toStringCSV(){
        
        String tradeString="";
        tradeString+=islemNo+";";
        tradeString+=dateFormatForFile.format(islemTarihi)+";";
        tradeString+= paraBirimi+";";
        tradeString+=zaman+";";
        tradeString+=b_emirNo+";";
        tradeString+=s_emirNo+";";
        tradeString+=menkulKiymet+";";
        tradeString+=pazar+";";
        tradeString+=fiyat+";";
        tradeString+=checkNull(repo2Fiyati)+";";
        tradeString+=miktar+";";
        tradeString+=tlTutar+";";
        tradeString+=repoFaizi+";";
        tradeString+=stopaj+";";
        tradeString+= dateFormatForFile.format(valor1)+";";
        tradeString+= isNull(valor2)+";";
        tradeString+= getiri+";";
        tradeString+= repoSuresi+";";
        tradeString+= temizFiyat+";";
        tradeString+=islemisFaiz+";";
        tradeString+=islemisFaizTutari+";";
        tradeString+=anaparaTutari+";";
        tradeString+= takasFiyati+";";
        tradeString+= kirliFiyat+";";
        tradeString+= enflasyonKatsayisi+";";
        tradeString+= kendineFon+";";
        
        return tradeString;
}
    public String checkNull(BigDecimal b) {
        if(b==null)
            return "";
        return b.toString();
    }
    
    public Date getTime(){
        
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(this.getIslemTarihi());
        tmp.set(Calendar.HOUR, Integer.parseInt(this.getZaman().substring(0,2)));
        tmp.set(Calendar.MINUTE, Integer.parseInt(this.getZaman().substring(3,5)));
        tmp.set(Calendar.SECOND, Integer.parseInt(this.getZaman().substring(6,8)));
        return tmp.getTime();
        
//        try {
//            return dateFormat.parse(this.getIslemTarihi()+ " " + this.getZaman());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
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
