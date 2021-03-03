package bt.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bt.domain.Order;
import bt.domain.Trade;
import bt.utils.DateComparator;
import bt.utils.TimeComparatorForIslem;



public class BoardIdSplitter {
    
    static ArrayList<Order> orderListFullMonth = new ArrayList<Order>();
    static ArrayList<Trade> tradeListFullMonth = new ArrayList<Trade>();
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static List<String> menkulKiymetListesi = new ArrayList<String> ();
    static DateFormat dateFormatForFileName = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int i = 1;
        String emirFile = "BAP_GUNICIEMIR.M.201301.csv";
        String islemFile = "BAP_GUNICIISLEM.M.201301.csv";

        readOrderMonthCSVFile(emirFile);
        
        readTradeMonthCSVFile(islemFile);

        DateComparator dateComparator = new DateComparator();
        TimeComparatorForIslem comparatorForIslem = new TimeComparatorForIslem();
        Collections.sort(orderListFullMonth, dateComparator);
        //Collections.sort(tradeListFullMonth, comparatorForIslem);  //bunu acinca dosya uretiminda fazlaliklar oluyor kontrol etmeyi unutma 3 Aralik notu

        //once her bir menkul kiymet icin bir aylik liste olustur:
        for (String menkulKiymet : menkulKiymetListesi) {
            splitOrdersToMenkulKiymetList(menkulKiymet);
        }
        
        for (String menkulKiymet : menkulKiymetListesi) {
            splitTradesToMenkulKiymetList(menkulKiymet);
        }
        
//        while(orderListFullMonth.size()>0){
//            
//            
//                splitEmirlist(i);
//                i++;
//        }       
//        i=1;
//        while(tradeListFullMonth.size()>0){
//                splitIslemlist(i);
//                i++;
//        }
        System.out.println("durduk");

    }

	private static void readTradeMonthCSVFile(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader bufRdr = new BufferedReader(new FileReader(file));
        String line = null;
        //String tmp;
        int row = 0;
        //read each line of text file
        while ((line = bufRdr.readLine()) != null) {
        	if(line.contains("KES")) {
        		Trade t = convertLineToTrade(line);
        		if(t!=null){  //t returns null if that trade is already in the trade list (remember similar B and S lines in trade file 
        			tradeListFullMonth.add(row, t);
        			row++;
        		}
        	}
        }
        bufRdr.close();
        
        System.out.println("trade dosya - menkulkiymet list size:"+menkulKiymetListesi.size());
        
    }

    private static Trade convertLineToTrade(String line) {
        
        Trade t = new Trade();
        bt.utils.Parse parser = new bt.utils.Parse(line, ";");        
        
        if (line != null) {
            //System.out.println("line:"+line);
            String tmp = parser.nextToken();
            
            if(thisTradeExistsThenUpdate(line)){
                //System.out.println("trade exists:"+line);
                return null;
            }
            
            while (tmp != null) {
                t.setIslemNo(tmp);
                //System.out.println(".."+tmp);
                
                String alis_satis = parser.nextToken(); //pass this - alis/satus
                //System.out.println(alis_satis);
                
                tmp = parser.nextToken();
                Date islemTarihi = null;
                try {
                    islemTarihi = dateFormat.parse(tmp);
                    t.setIslemTarihi(islemTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                t.setParaBirimi(tmp);
                
                tmp = parser.nextToken();
                t.setZaman(tmp);
                
                tmp = parser.nextToken(); //emir_no
                if(alis_satis.compareTo("B")==0)
                    t.setB_emirNo(tmp);
                else
                    t.setS_emirNo(tmp);
                
                tmp = parser.nextToken();
                t.setMenkulKiymet(tmp);
                
                tmp = parser.nextToken();
                t.setPazar(tmp);
                
                tmp = parser.nextToken();
                BigDecimal fiyat = new BigDecimal(tmp);
                t.setFiyat(fiyat);
                
                tmp = parser.nextToken();
                if(!tmp.isEmpty()){
                    BigDecimal repo2Fiyati = new BigDecimal(tmp);
                    t.setRepo2Fiyati(repo2Fiyati);
                    }
                    else
                        t.setRepo2Fiyati(null);

                tmp = parser.nextToken();
                int miktar = Integer.parseInt(tmp);
                t.setMiktar(miktar);
        
                tmp = parser.nextToken();
                if(!tmp.isEmpty()){
                    BigDecimal hacim = new BigDecimal(tmp);
                    t.setHacim(hacim);
                    }
                    else
                        t.setHacim(null);

                tmp = parser.nextToken();
                int repoFaizi = Integer.parseInt(tmp);
                t.setRepoFaizi(repoFaizi);
                
                tmp = parser.nextToken();
                int stopaj = Integer.parseInt(tmp);
                t.setStopaj(stopaj);

                tmp = parser.nextToken();//degistirmetarihi
                tmp = parser.nextToken();//degistirmezamani
                
                tmp = parser.nextToken();
                Date valor1 = null;
                try {
                    if(!tmp.isEmpty())
                        valor1 = dateFormat.parse(tmp);
                    t.setValor1(valor1);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                Date valor2 = null;
                try {
                    if(!tmp.isEmpty())
                        valor2 = dateFormat.parse(tmp);
                    t.setValor2(valor2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal getiri = new BigDecimal(tmp);
                    t.setGetiri(getiri);
                }else
                    t.setGetiri(null);
                
                tmp = parser.nextToken();
                t.setRepoSuresi(tmp);
                
                tmp = parser.nextToken();
                BigDecimal temizFiyat = new BigDecimal(tmp);
                t.setTemizFiyat(temizFiyat);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal islemisFaiz = new BigDecimal(tmp);
                    t.setIslemisFaiz(islemisFaiz);
                }else
                    t.setIslemisFaiz(null);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal islemisFaizTutari = new BigDecimal(tmp);
                    t.setIslemisFaizTutari(islemisFaizTutari);
                }else
                    t.setIslemisFaizTutari(null);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal anaparaTutari = new BigDecimal(tmp);
                    t.setAnaparaTutari(anaparaTutari);
                }else
                    t.setAnaparaTutari(null);
                
                tmp = parser.nextToken();//piyasayapici
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal takasFiyati = new BigDecimal(tmp);
                    t.setTakasFiyati(takasFiyati);
                }else
                    t.setTakasFiyati(null);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal kirliFiyat = new BigDecimal(tmp);
                    t.setKirliFiyat(kirliFiyat);
                }else
                    t.setKirliFiyat(null);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal enflasyonKatsayisi = new BigDecimal(tmp);
                    t.setEnflasyonKatsayisi(enflasyonKatsayisi);
                }else
                    t.setEnflasyonKatsayisi(null);
                
                
                tmp = parser.nextToken();
                t.setDurum(tmp);
                //System.out.println("-"+tmp);
                tmp = parser.nextToken();
                tmp = parser.nextToken();
                //System.out.println("--"+tmp);
            }
        }
        return t; 
    }

    private static boolean thisTradeExistsThenUpdate(String line) {
        
        bt.utils.Parse parser = new bt.utils.Parse(line, ",");        
        String islemNo = parser.nextToken();
        //System.out.println("tradeListSize:"+tradeListFullMonth.size());
        
        for(int i=0;i<tradeListFullMonth.size();i++){
            if(tradeListFullMonth.get(i).getIslemNo().compareTo(islemNo)==0){  //this trade already exists - update buy or sell
                String alis_satis = parser.nextToken();
                String tmp=parser.nextToken();//islemTarihi
                tmp=parser.nextToken();//parabirimi
                tmp=parser.nextToken();//zaman
                String emirNo=parser.nextToken();
                if(alis_satis.compareTo("S")==0)
                    tradeListFullMonth.get(i).setS_emirNo(emirNo);
                else
                    tradeListFullMonth.get(i).setB_emirNo(emirNo);
                return true;
            }
         }
        return false;
    }

    private static void splitOrdersToMenkulKiymetList(String menkulKiymet) throws Exception {
        ArrayList<Order> singleList = new ArrayList<Order>();
        //System.out.println(orderListFullMonth.size());
        for(int i=0;i<orderListFullMonth.size();i++){
            Order o=orderListFullMonth.get(i);
            //if(!(o.getDurum().compareTo("W")==0 && o.getMiktar()==o.getBakiye())){
                if(o.getMenkulKiymet().compareTo(menkulKiymet)==0)
                    singleList.add(o);
            //}
        }
        //System.out.println(singleList.size());
        int i=1;
        while(singleList.size()>0){
            splitEmirlist(i,singleList);
            i++;
        } 
    }
    
    private static void splitTradesToMenkulKiymetList(String menkulKiymet) throws Exception {
        ArrayList<Trade> singleList = new ArrayList<Trade>();
        for(int i=0;i<tradeListFullMonth.size();i++){
            Trade t=tradeListFullMonth.get(i);
                if(t.getMenkulKiymet().compareTo(menkulKiymet)==0)
                    singleList.add(t);
        }
        
        int i=1;
        while(singleList.size()>0){
            splitTradelist(i,singleList);
            i++;
    } 
    }

	public static void readOrderMonthCSVFile(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader bufRdr = new BufferedReader(new FileReader(file));
        String line = null;
        //String tmp;
        int row = 0;
        //read each line of text file
        while ((line = bufRdr.readLine()) != null) {
        	if(line.contains("KES")) {
        		Order e = convertLineToEmir(line);
        		orderListFullMonth.add(row, e);
        		row++;
        	}
        }
        bufRdr.close();
        
        System.out.println("order dosya - menkulkiymet list size:"+menkulKiymetListesi.size());
    }
    
    public static Order convertLineToEmir(String line) {
        Order e = new Order();
        bt.utils.Parse parser = new bt.utils.Parse(line, ";");

        if (line != null) {
            String tmp = parser.nextToken();
            while (tmp != null) {
                e.setEmirNumarasi(tmp);
                //System.out.println(e.getEmirNumarasi());
                tmp = parser.nextToken();
                Date emirTarihi = null;
                try {
                    emirTarihi = dateFormat.parse(tmp);
                    e.setEmirTarihi(emirTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                if(tmp.length()==8)
                	tmp="0"+tmp;
                e.setGirisSaati(tmp);
                
                tmp = parser.nextToken();
                e.setMenkulKiymet(tmp);
                if(!menkulKiymetListesi.contains(tmp)) 
                    menkulKiymetListesi.add(tmp);
                
                tmp = parser.nextToken();
                e.setPazar(tmp);
                
                tmp = parser.nextToken();
                e.setAlis_satis(tmp);
                
                tmp = parser.nextToken();
                BigDecimal fiyat = new BigDecimal(tmp);
                e.setFiyat(fiyat);
                
                tmp = parser.nextToken();
                if(!tmp.isEmpty()){
                    BigDecimal repo2Fiyati = new BigDecimal(tmp);
                    e.setRepo2Fiyati(repo2Fiyati);
                    }
                    else
                        e.setRepo2Fiyati(null);

                tmp = parser.nextToken();
                int miktar = Integer.parseInt(tmp);
                e.setMiktar(miktar);
                
                tmp = parser.nextToken();
                int bakiye = Integer.parseInt(tmp);
                e.setBakiye(bakiye);
                
                tmp = parser.nextToken();
                if(!tmp.isEmpty()){
                    BigDecimal kalanHacim = new BigDecimal(tmp);
                    e.setKalanHacim(kalanHacim);
                    }
                    else
                        e.setKalanHacim(null);
                
                
                tmp = parser.nextToken();
                Date sonDegistirmeTarihi = null;
                try {
                    if(!tmp.isEmpty())
                    	sonDegistirmeTarihi = dateFormat.parse(tmp);
                    e.setSonDegistirmeTarihi(sonDegistirmeTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                e.setSonDegistirmeSaati(tmp);
                
                tmp = parser.nextToken();
                e.setIlgiliEmirNumarasi(tmp);
                
                tmp = parser.nextToken();
                Date valor1 = null;
                try {
                    if(!tmp.isEmpty())
                        valor1 = dateFormat.parse(tmp);
                    e.setValor1(valor1);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                Date valor2 = null;
                try {
                    if(!tmp.isEmpty())
                        valor2 = dateFormat.parse(tmp);
                    e.setValor2(valor2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal getiri = new BigDecimal(tmp);
                    e.setGetiri(getiri);
                }else
                    e.setGetiri(null);
                
                tmp = parser.nextToken();
                e.setParaBirimi(tmp);
                
                tmp = parser.nextToken();
                e.setRepo(tmp);
                
                tmp = parser.nextToken();
                e.setHesabi(tmp);
                
                tmp = parser.nextToken();
                BigDecimal tlTutar = new BigDecimal(tmp);
                e.setTlTutar(tlTutar);
                
                tmp = parser.nextToken();
                BigDecimal temizFiyat = new BigDecimal(tmp);
                e.setTemizFiyat(temizFiyat);
                
                //System.out.println("--"+tmp);
                tmp = parser.nextToken();
                //System.out.println("--"+tmp);
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal takasFiyati = new BigDecimal(tmp);
                    e.setTakasFiyati(takasFiyati);
                }else
                    e.setTakasFiyati(null);
                
                tmp = parser.nextToken();
                e.setIlgiliEmir2(tmp);
                
                tmp = parser.nextToken();
                //System.out.println("--"+tmp);
                e.setDurum(tmp);
                
                
                //           parabirimi;hacim;temizfiyat;takasfiyati;ilgiliemir;durum;              

                
                tmp = parser.nextToken();
            }
        }
        return e;
    }

    public static void splitEmirlist(int fileNumber, ArrayList<Order> singleList) throws Exception {
        ArrayList<Order> orderListOneDay = new ArrayList<Order>();
        for (int i = 0; i < singleList.size(); i++) {
            Order order = singleList.get(i);
            if (i<singleList.size()-1 && order.getEmirTarihi().compareTo(singleList.get(i+1).getEmirTarihi())==0) {
                orderListOneDay.add(order);
            }
            else{
                orderListOneDay.add(order);//gunun son emri icin...       
                i=singleList.size();
            }
        }
        String nameOfFile=singleList.get(0).getMenkulKiymet()+"-"+dateFormatForFileName.format(singleList.get(0).getEmirTarihi())+"_emir.csv";
        WriteFile.writeCSVfileEmirDaily(orderListOneDay,nameOfFile);
        System.out.println("emir."+fileNumber+"="+nameOfFile);
        singleList.removeAll(orderListOneDay);
    }
    
    public static void splitTradelist(int fileNumber, ArrayList<Trade> singleList) throws Exception {
        ArrayList<Trade> tradeListOneDay = new ArrayList<Trade>();
        for (int i = 0; i < singleList.size(); i++) {
            Trade trade = singleList.get(i);
            if (i<singleList.size()-1 && trade.getIslemTarihi().compareTo(singleList.get(i+1).getIslemTarihi())==0) {
                tradeListOneDay.add(trade);
            }
            else{
                tradeListOneDay.add(trade);//gunun son emri icin...       
                i=singleList.size();
            }
        }
        String nameOfFile=singleList.get(0).getMenkulKiymet()+"-"+dateFormatForFileName.format(singleList.get(0).getIslemTarihi())+"_islem.csv";
        WriteFile.writeCSVfileTradeDaily(tradeListOneDay,nameOfFile);
        System.out.println("islem."+fileNumber+"="+nameOfFile);
        singleList.removeAll(tradeListOneDay);
    }
}
