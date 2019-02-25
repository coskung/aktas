
package bt.simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import bt.domain.LOBLine;
import bt.domain.Order;
import bt.domain.Trade;
import bt.file.WriteFile;
import bt.utils.PropertyLoader;
import bt.utils.TimeComparator;

public class AktasSimulator {
    static ArrayList<Order> OrderList = new ArrayList<Order>();
    static ArrayList<Order> OrderListCumulative = new ArrayList<Order>();
    static ArrayList<Order> OrderListToDelete = new ArrayList<Order>();
    static ArrayList<Order> WYZListCumulative = new ArrayList<Order>();
    static ArrayList<Order> acilisListCumulative = new ArrayList<Order>();
    static ArrayList<Order> crossOrderList = new ArrayList<Order>();
    //static ArrayList<ModificationLine> modificationList = new ArrayList<ModificationLine>();
    static ArrayList<Trade> TradeList = new ArrayList<Trade>();
    static Trade lastTradeOfYesterday;
    static ArrayList<LOBLine> lobList = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> lobListCumulative = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> lobListDeleted = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> lobListDeletedBecause0Validity = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> lobListOfMorning = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> lobListOfMorningCumulative = new ArrayList<LOBLine>();
    static ArrayList<LOBLine> dynamicLob = new ArrayList<LOBLine>();
    static List<String> dynamicLobString = new ArrayList<String> ();
    static List<String> OrderlistModifiedamaaslindaLOBlistesi = new ArrayList<String> ();
    static List<String> OrderlistModifiedamaaslindaLOBlistesiCumulative = new ArrayList<String> ();
    static List<String> XWorYW = new ArrayList<String> ();
    static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.ENGLISH);
    static DateFormat emirDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static DateFormat dailyDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    static int DYNAMIC_LOB_COUNTER=2;
    //static int TARGET=19802;
    static int TARGET=21602;
    static int volumeAtBid;
    static int volumeAtAsk;
    static BigDecimal bidPrice;
    static BigDecimal askPrice;
    static String interruptOrder;
    static Date interruptTime;
    static String interruptTimeString;
    static BigDecimal askPriceMemory;
    static int volumeAtAskMemory;
    static int volumeAtBidMemory;
    static BigDecimal bidPriceMemory;
    
    static String seans="09:30:00";
    static String ilkseanssonu="12:00:00";
    static String ilkseanssonuPlainString="120000";
    static int CROSS_ORDER_INDICATOR=3000;
    

    public static void readCSVfileOrder(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader bufRdr = new BufferedReader(new FileReader(file));
        String line = null;
        int row = 0;
        //read each line of text file
        while ((line = bufRdr.readLine()) != null) {
            Order e = convertLineToOrder(line);
            OrderList.add(row, e);
            row++;
        }
        bufRdr.close();
    }

    public static void readCSVfileTrade(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader bufRdr = new BufferedReader(new FileReader(file));
        String line = null;
        int row = 0;
        //read each line of text file
        while ((line = bufRdr.readLine()) != null) {
            Trade e = convertLineToTrade(line);
            TradeList.add(row, e);
            row++;
        }
        bufRdr.close();
    }

    public static Order convertLineToOrder(String line) {
        Order e = new Order();
        bt.utils.Parse parser = new bt.utils.Parse(line, ",");

        if (line != null) {
            //System.out.println("line:"+line);
            String tmp = parser.nextToken();
            while (tmp != null) {
                e.setEmirNumarasi(tmp);
                
                tmp = parser.nextToken();
                Date emirTarihi = null;
                try {
                    emirTarihi = emirDateFormat.parse(tmp);
                    e.setEmirTarihi(emirTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                e.setGirisSaati(tmp);
                
                tmp = parser.nextToken();
                e.setMenkulKiymet(tmp);
                
                tmp = parser.nextToken();
                e.setPazar(tmp);
                
                tmp = parser.nextToken();
                e.setAlis_satis(tmp);
                
                tmp = parser.nextToken();
                BigDecimal fiyat = new BigDecimal(tmp);
                e.setFiyat(fiyat);
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
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
                e.setDurum(tmp);
                
                tmp = parser.nextToken();
                e.setSonDegistirmeSaati(tmp);
                
                tmp = parser.nextToken();
                e.setIlgiliEmirNumarasi(tmp);
                
                tmp = parser.nextToken();
                Date valor1 = null;
                try {
                    if(tmp!=null && !tmp.isEmpty())
                        valor1 = emirDateFormat.parse(tmp);
                    e.setValor1(valor1);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                Date valor2 = null;
                try {
                    if(tmp!=null && !tmp.isEmpty())
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
                
                tmp = parser.nextToken();
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal takasFiyati = new BigDecimal(tmp);
                    e.setTakasFiyati(takasFiyati);
                }else
                    e.setTakasFiyati(null);
                
                tmp = parser.nextToken();
                tmp = parser.nextToken();
            }
        }
        return e;
    }

    public static Trade convertLineToTrade(String line) {
        
        Trade t = new Trade();
        bt.utils.Parse parser = new bt.utils.Parse(line, ";");       
        t.setProcessed(false);
        
        if (line != null) {
            //System.out.println("line:"+line);
            String tmp = parser.nextToken();
            
            while (tmp != null) {
                t.setIslemNo(tmp);
                
                //String alis_satis = parser.nextToken(); //pass this - alis/satus
                //System.out.println(alis_satis);
                
                tmp = parser.nextToken();
                //System.out.println(tmp);
                Date islemTarihi = null;
                try {
                    islemTarihi = emirDateFormat.parse(tmp);
                    t.setIslemTarihi(islemTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                t.setParaBirimi(tmp);
                
                tmp = parser.nextToken();
                t.setZaman(tmp);
                
                tmp = parser.nextToken(); //b_emir_no
                    t.setB_emirNo(tmp);
                
                tmp = parser.nextToken(); //s_emir_no
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
                BigDecimal tlTutar = new BigDecimal(tmp);
                t.setTlTutar(tlTutar);
                
                tmp = parser.nextToken();
                int repoFaizi = Integer.parseInt(tmp);
                t.setRepoFaizi(repoFaizi);
                
                tmp = parser.nextToken();
                int stopaj = Integer.parseInt(tmp);
                t.setStopaj(stopaj);
                
                tmp = parser.nextToken();
                Date valor1 = null;
                try {
                    if(!tmp.isEmpty())
                        valor1 = emirDateFormat.parse(tmp);
                    t.setValor1(valor1);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
                Date valor2 = null;
                try {
                    if(tmp!=null && !tmp.isEmpty())
                        valor2 = emirDateFormat.parse(tmp);
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
                t.setKendineFon(tmp);
                
                tmp = parser.nextToken();
                tmp = parser.nextToken();
            }
        }
        return t; 
    }
    
    public static void mergeTradeLines() throws Exception{
    	WriteFile.writeCSVfileTrade(TradeList, "Trade_after_modifications1.csv");
    	System.out.println(TradeList.size());
    	ArrayList<Trade> TradeListToDelete = new ArrayList<Trade>();
    	for(int i=0;i<TradeList.size()-1;i=i+2){
    		if(TradeList.get(i).getB_emirNo() == null || "null".equalsIgnoreCase(TradeList.get(i).getB_emirNo())){
    			TradeList.get(i).setB_emirNo(TradeList.get(i+1).getB_emirNo());
    		}
    		else if(TradeList.get(i).getS_emirNo() == null|| "null".equalsIgnoreCase(TradeList.get(i).getS_emirNo())){
    			TradeList.get(i).setS_emirNo(TradeList.get(i+1).getS_emirNo());
    		}
    		TradeListToDelete.add(TradeList.get(i+1));
    	}
    	TradeList.removeAll(TradeListToDelete);
    	System.out.println(TradeList.size());
    	WriteFile.writeCSVfileTrade(TradeList, "Trade_after_modifications2.csv");
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int i = 1;
        String OrderFile="";
        String TradeFile="";
        while (PropertyLoader.getProperty("emir." + i) != null) {
            OrderFile = PropertyLoader.getProperty("emir." + i);
            TradeFile = PropertyLoader.getProperty("islem." + i);

            readCSVfileOrder(OrderFile);
            readCSVfileTrade(TradeFile);
            mergeTradeLines();//her islem iki satirda yer aldigi icin birlesmesi gerekiyor
            //24.12.15 not: Order listesi zaten zamana gore sirali oldugu icin bu siralamaya gerek yok
            TimeComparator timeComparator = new TimeComparator();
            Collections.sort(OrderList, timeComparator);
            
            //24.12.15 not: asagidaki 3 metoda ihtiyac olup olmadigi Osman'a soruldu
            //filterRuchanFromOrder();  //29.12 ihtiyac yok yaniti geldi
            //filterRuchanFromTrade();  //29.12 ihtiyac yok yaniti geldi
            //WriteFile.writeCSVfileTradeAppend(TradeList, "Trade_after_ruchan_cumulative.csv");
            
            //29.12.15 not: seans oncesi filtreleme vb kontrollere dair simdilik bir bilgi olmadigindan bu metodlari devre disi birakiyorum
//            valid_only_control();
//            deleteWLinesAfter17();
//            deleteWLinesBetween1230and14();
//            deleteWifKTRisE();
              filterWordersforGirisSaatiBuyukturSonDegistirmeSaatiAndQuantityEqualsBalance();
              filterWordersforQuantityEqualsBalance();
              int countW = countWLines();
              System.out.println("W lines left:" + countW);
              
              
              
//            filterXY();
//            filterModifiedOrders();
//            filterKTR();
//            ayniSaniyedeGelen_C_D_T_duzenlemesi();
//            WriteFile.writeCSVfileTrade(TradeList, "Trade_after_modifications.csv");
//            WriteFile.writeCSVfileOrder(OrderList);
//            WYZListCumulative.addAll(OrderList);
//            WriteFile.writeCSVfileOrderAppend(WYZListCumulative, "Order_afterManipulations_cumulative.csv");
//            WYZListCumulative.clear();
//            Collections.sort(OrderList, timeComparator);
//            for (int ind = 1; ind <= OrderList.size(); ind++) {
//                Order Order = OrderList.get(ind-1);
//                Order.setIndex(ind);
//                OrderList.set(ind-1, Order);
//            }
            
            //05.01.16 acilis seansi gibi bir mantik repoda olmadigi icin ilgili prosedurler kalkiyor
//            acilisSeansi();
//            acilisListCumulative.addAll(OrderList);
//            WriteFile.writeCSVfileOrderAppend(acilisListCumulative, "acilis_seansi_cumulative.csv");
//            acilisListCumulative.clear();
            
            interruptOrder = PropertyLoader.getProperty("lastorder");
            interruptTimeString = PropertyLoader.getProperty("timetostop");
            if (!interruptTimeString.equalsIgnoreCase(""))
                interruptTime = dailyDateFormat.parse(interruptTimeString);
            System.out.println("interruptTime"+interruptTime);
            
            
            WriteFile.writeCSVfileEmirDaily(OrderList, "initcreateloboncesi_orderlist.csv");
            initCreateLOB();
            
            //29.12.15 alttaki kisimlarda (if blogu) simdilik commentlendi, ihtiyaca gore degerlendirilecek
//            if(DYNAMIC_LOB_COUNTER!=-1){
//                fixDynamicLOB();
//                finalAdditionsToDynamicLOBString();
//            }
            
            //addTradeDirectionColumns();   //29.12'den gelen bilgiye gore LR, tick rule vb bigileri lob'da artik olmayacak
            
            ArrayList<LOBLine> lobListFinal = new ArrayList<LOBLine>();
            lobListFinal.addAll(lobList);
            WriteFile.writeCSVfileLOB(lobListFinal);
            lobListCumulative.addAll(lobListFinal);
            
            WriteFile.writeCSVfileStringAppend(OrderlistModifiedamaaslindaLOBlistesiCumulative, "modified_order_cumulative.csv");
            WriteFile.writeCSVfileLOBAppend(lobListCumulative, "LOB_cumulative.csv");
            lobListOfMorningCumulative.clear();
            OrderlistModifiedamaaslindaLOBlistesiCumulative.clear();
            lobListCumulative.clear();
            
            WriteFile.writeCSVfileString(OrderlistModifiedamaaslindaLOBlistesi, "modified_order.csv");
            OrderlistModifiedamaaslindaLOBlistesiCumulative.addAll(OrderlistModifiedamaaslindaLOBlistesi);
            //WriteFile.writeCSVfileString(XWorYW, "XWorYW.csv");
            OrderlistModifiedamaaslindaLOBlistesi.clear();
            lobList.clear();
            lobListOfMorning.clear();
            if(DYNAMIC_LOB_COUNTER!=-1){
                WriteFile.writeCSVfileStringAppend(dynamicLobString, "dynamicLob.csv");
                dynamicLob.clear();
                dynamicLobString.clear();
            }
			//29.12.15 - comment out: TradeLOBComposer.composeTradeAndDynamicLOB(TradeList, "dynamicLob.csv", "TradeComposedDynamicLOB.csv",OrderList,i,lastTradeOfYesterday);
			lastTradeOfYesterday=TradeList.get(TradeList.size()-1);
			OrderList.clear();
            TradeList.clear();
            i++;
            }
        
        WriteFile.writeCSVfileLOBAppend(lobListOfMorningCumulative, "LOB_morning_cumulative.csv");
        WriteFile.writeCSVfileStringAppend(OrderlistModifiedamaaslindaLOBlistesiCumulative, "modified_order_cumulative.csv");
        WriteFile.writeCSVfileLOBAppend(lobListCumulative, "LOB_cumulative.csv");
        System.out.println("------DONE------");
    }
    
//	private static void summarizeRepeatingTransactions() {
//        System.out.println("\n\nsummarizeRepeatingTransactions\n");
//        ArrayList<Trade> TradeListToDelete = new ArrayList<Trade>();
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            int tmpVol=Trade.getVolume();
//            for (int j = i+1; j < TradeList.size(); j++) {
//                Trade sonrakiTrade = TradeList.get(j);
//                if(Trade.getBuyerOrderId().equalsIgnoreCase(sonrakiTrade.getBuyerOrderId()) && Trade.getSellerOrderId().equalsIgnoreCase(sonrakiTrade.getSellerOrderId())&& Trade.getPrice().compareTo(sonrakiTrade.getPrice())==0){
//                    tmpVol+=sonrakiTrade.getVolume();
//                    TradeListToDelete.add(sonrakiTrade);
//                    System.out.println("silinen Trade:"+sonrakiTrade);
//                }
//            
//            if(TradeListToDelete.size()>0){
//                TradeList.removeAll(TradeListToDelete);
//                TradeListToDelete.clear();
//                Trade.setVolume(tmpVol);
//            }
//            }
//        }
//        System.out.println("\n\nsummarizeRepeatingTransactions-----------------------DONE\n");
//    }

//	public static void ayniSaniyedeGelen_C_D_T_duzenlemesi() {
//
//        System.out.println("ayniSaniyedeGelen_C_D_T_duzenlemesi");
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if(hem_C_hem_DveyaT_ayniAndaGelmisVeEslesmis(Trade.getBuyerOrderId(), Trade.getSellerOrderId(), Trade.getTime())){
//                Date degisecek_zaman=Trade.getTime();
//                System.out.println("bulunan Trade: "+Trade.toString());
//                Calendar cal=Calendar.getInstance();
//                cal.setTime(Trade.getTime());
//                cal.add(Calendar.SECOND, 1);
//                Trade.setTime(cal.getTime());
//                TradeList.set(i, Trade);
//                bu_C_nin_ayni_saniyede_baska_Eslesmesi_Varsa_onun_da_Trade_saatini_guncelle(Trade.getBuyerOrderId(), degisecek_zaman);
//            }    
//        }
//    }
//
//    public static void bu_C_nin_ayni_saniyede_baska_Eslesmesi_Varsa_onun_da_Trade_saatini_guncelle(
//            String buyerOrderId, Date degisecek_zaman) {
//        System.out.println("\nbu_C_nin_ayni_saniyede_baska_Eslesmesi_Varsa_onun_da_Trade_saatini_guncelle. C'li Order: "+buyerOrderId);
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if(Trade.getBuyerOrderId().equalsIgnoreCase(buyerOrderId) && Trade.getTime().compareTo(degisecek_zaman)==0){
//                System.out.println(Trade.toString());
//                Calendar cal=Calendar.getInstance();
//                cal.setTime(Trade.getTime());
//                cal.add(Calendar.SECOND, 1);
//                Trade.setTime(cal.getTime());
//                TradeList.set(i, Trade);
//            }    
//        }
//        System.out.println("bu_C_nin_ayni_saniyede_baska_Eslesmesi_Varsa_onun_da_Trade_saatini_guncelle - BITTI\n");
//    }
//
//    public static boolean hem_C_hem_DveyaT_ayniAndaGelmisVeEslesmis(String buyerOrderId, String sellerOrderId,
//            Date time) {
//        
//        for (int i = 0; i < OrderList.size()-1; i++) {
//            Order Order = OrderList.get(i);
//            if(Order.getOrderId().equalsIgnoreCase(buyerOrderId) && Order.getOrderType().equalsIgnoreCase("C")){
//                for (int j = 0; j < OrderList.size()-1; j++) {
//                    Order digerOrder = OrderList.get(j);
//                    if(digerOrder.getOrderId().equalsIgnoreCase(sellerOrderId) && (digerOrder.getOrderType().equalsIgnoreCase("D") || digerOrder.getOrderType().equalsIgnoreCase("T"))){
//                        if(Order.getTime().compareTo(digerOrder.getTime())==0 && Order.getTime().compareTo(time)==0){
//                            Calendar cal=Calendar.getInstance();
//                            cal.setTime(Order.getTime());
//                            cal.add(Calendar.SECOND, 1);
//                            Order.setTime(cal.getTime());
//                            OrderList.set(i, Order);
//                            return true;
//                        }    
//                    }
//            }
//        }
//        }
//        return false;
//    }
    
//    public static void filterModifiedOrders() throws ParseException {
//        System.out.println("####-----------     filterModifiedOrders       -----------####");
//        ArrayList<Order> filteredModifiedOrders = new ArrayList<Order>();
//        OrderIdComparator orderIdComparator = new OrderIdComparator();
//        Collections.sort(OrderList, orderIdComparator);
//        
//        /*
//         * Duzeltme 1: time & Price tamamen ayni olan 2 tane Order varsa, ikinciyi sil
//         * */
//        for (int i = 0; i < OrderList.size()-1; i++) {
//            Order Order = OrderList.get(i);
//            Order sonrakiOrder = OrderList.get(i+1);
//            if (Order.getOrderId().equalsIgnoreCase(sonrakiOrder.getOrderId()) && Order.getTime().compareTo(sonrakiOrder.getTime())==0 && Order.getPrice().compareTo(sonrakiOrder.getPrice())==0 && Order.getVolume()==sonrakiOrder.getVolume()) {
//                if(!sonrakiOrder.getTime().before(dateFormat.parse(sonrakiOrder.getDate() + " " + seans)))
//                    filteredModifiedOrders.add(sonrakiOrder);
//            } 
//        }
//        System.out.println("Duzeltme 1");
//        printFullLineList(filteredModifiedOrders);
//        OrderList.removeAll(filteredModifiedOrders);
//        filteredModifiedOrders.clear();
//        
//        /*
//         * Duzeltme 2: orginal Orderden sonra bunun vol daha buyuk duzeltmesi gelmis!!!
//         * */
//        
//        for (int i = 0; i < OrderList.size()-1; i++) {
//            Order Order = OrderList.get(i);
//            Order sonrakiOrder = OrderList.get(i+1);
//            int j=i+1;
//            while(Order.getOrderId().equalsIgnoreCase(sonrakiOrder.getOrderId()) && j<OrderList.size()-1){
//                if (Order.getVolume()<sonrakiOrder.getVolume()) {
//                    if(!sonrakiOrder.getTime().before(dateFormat.parse(sonrakiOrder.getDate() + " " + seans)))
//                            filteredModifiedOrders.add(sonrakiOrder);
//                } 
//                j++;
//                sonrakiOrder = OrderList.get(j);
//            }
//            i=j-1;
//        }
//        
//        System.out.println("Duzeltme 2");
//        printFullLineList(filteredModifiedOrders);
//        OrderList.removeAll(filteredModifiedOrders);
//        filteredModifiedOrders.clear();
//        
//        /*
//         * Duzeltme 3:  alim emri verdim 119 lottan, sonra HIC ESLESMEDI ve bunun duzeltmesini verdim. Haliyle bunun duzeltmesinin vol=original_vol olmali. 
//         * Ama salak bir sekilde sistemde bu farkli olabiliyor!!
//         * */
//        
//        for (int i = 0; i < OrderList.size()-1; i++) {
//            Order Order = OrderList.get(i);
//            Order modifiedOrder = OrderList.get(i+1);
//            int j=i+1;
//            while(Order.getOrderId().equalsIgnoreCase(modifiedOrder.getOrderId()) && j<OrderList.size()-1){
//                if (Order.getVolume()!=modifiedOrder.getVolume()) {
//                    if(!orderIdExistsInSplitColumn(Order.getOrderId())){
//                        int dummyVol = go_to_Tradelist_get_the_total_vol_executed_between_time_of_original_Order_DAHIL_and_time_of_modified_Order_DAHIL_degil(Order,modifiedOrder);
//                        if(modifiedOrder.getVolume()!=Order.getVolume()-dummyVol){
//                            if(!modifiedOrder.getTime().before(dateFormat.parse(modifiedOrder.getDate() + " " + seans)))
//                                filteredModifiedOrders.add(modifiedOrder);
//                        }    
//                    }
//                } 
//                j++;
//                modifiedOrder = OrderList.get(j);
//            }
//            i=j-1;
//        }
//        
//        System.out.println("Duzeltme 3");
//        printFullLineList(filteredModifiedOrders);
//        OrderList.removeAll(filteredModifiedOrders);
//        filteredModifiedOrders.clear();
//    }

//    public static int go_to_Tradelist_get_the_total_vol_executed_between_time_of_original_Order_DAHIL_and_time_of_modified_Order_DAHIL_degil(
//            Order Order, Order modifiedOrder) {
//        
//        int vol=0;
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if (Trade.getTime().before(modifiedOrder.getTime()) && !Trade.getTime().before(Order.getTime()) 
//                    && (Trade.getBuyerOrderId().equalsIgnoreCase(Order.getOrderId()) || Trade.getSellerOrderId().equalsIgnoreCase(Order.getOrderId()))) {
//               vol+=Trade.getVolume();     
//            }
//        }
//        return vol;
//    }

//    public static boolean orderIdExistsInSplitColumn(String orderId) {
//        for (int i = 0; i < OrderList.size()-1; i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getSplitOrderId().equalsIgnoreCase(orderId))
//                return true;
//            } 
//        return false;
//    }

//    public static void acilisSeansi() throws Exception {
//
//        //filterOrdersFirstOccurencesIfCorDcomes();
//        
//        ArrayList<Order> acilisListesi = new ArrayList<Order>();
//        ArrayList<Order> acilisListesiAlis = new ArrayList<Order>();
//        ArrayList<Order> acilisListesiSatis = new ArrayList<Order>();
//        boolean acilis = true;
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            //System.out.println("---" + Order.getTime() + "--");
//            if (Order.getTime().before(dateFormat.parse(Order.getEmirTarihi() + " " + seans)) && acilis) {
//                acilisListesi.add(Order);
//                if ("A".equalsIgnoreCase(Order.getAlis_satis())) {
//                    acilisListesiAlis.add(Order);
//                } else {
//                    acilisListesiSatis.add(Order);
//                }
//            } else if (acilis) {
//                runAcilisSeansProcedure(acilisListesiAlis, acilisListesiSatis);
//                acilisListesi.clear();
//                acilisListesiAlis.clear();
//                acilisListesiSatis.clear();
//                acilis = false;
//            } else if (!acilis
//                    && (i < OrderList.size() - 1 && Order.getEmirTarihi().compareTo(OrderList.get(i + 1).getEmirTarihi()) != 0)) {
//                acilis = true;
//            }
//        }
//        WriteFile.writeCSVfileOrder(OrderList, "acilis.csv");
//    }

    
//    public static void filterOrdersFirstOccurencesIfCorDcomes() throws Exception {
//
//        ArrayList<Order> acilisListesi = new ArrayList<Order>();
//        ArrayList<Order> acilisListesiToDelete = new ArrayList<Order>();
//        
//      //burada Trade listesinden acilistaki, yani 9.45'den onceki Tradeler aliniyor
//        ArrayList<Trade> acilisListesiTradeleri = new ArrayList<Trade>();
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if (Trade.getTime().before(dateFormat.parse(Trade.getDate() + " " + seans))) {
//                acilisListesiTradeleri.add(Trade);
//            }
//            else
//                i = TradeList.size();
//        }
//        
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getTime().before(dateFormat.parse(Order.getDate() + " " + seans))) {
//                acilisListesi.add(Order);
//            } 
//        }
//        OrderIdComparator orderIdComparator = new OrderIdComparator();
//        Collections.sort(acilisListesi, orderIdComparator);
//        
//        for (int i = 0; i < acilisListesi.size()-1; i++) {
//            Order Order = acilisListesi.get(i);
//            Order sonrakiOrder = acilisListesi.get(i+1);
//            if (Order.getOrderId().equalsIgnoreCase(sonrakiOrder.getOrderId()) && OrderAcilistaEslesiyormu(acilisListesiTradeleri,Order)) {
//                acilisListesiToDelete.add(Order);
//            } 
//        }
//        System.out.println("filterOrdersFirstOccurencesIfCorDcomes");
//        printFullLineList(acilisListesiToDelete);
//        OrderList.removeAll(acilisListesiToDelete);
//    }
    
    
//    public static boolean OrderAcilistaEslesiyormu(ArrayList<Trade> acilisListesiTradeleri, Order Order) {
//        for (Trade Trade : acilisListesiTradeleri) {  //acilis listesindeki her Trade icin sirayla:
//            String satisOrderId=Trade.getSellerOrderId();  //Tradedeki satis emrinin orderid'sini al
//            String alisOrderId=Trade.getBuyerOrderId();   //Tradedeki alim emrinin orderid'sini al
//            if(satisOrderId.equalsIgnoreCase(Order.getOrderId()) || alisOrderId.equalsIgnoreCase(Order.getOrderId()))
//                return true;
//            }
//        return false;
//    }

//    public static void runAcilisSeansProcedure(ArrayList<Order> acilisListesiAlis, ArrayList<Order> acilisListesiSatis)
//            throws Exception {
//        
//        //bu metoda, acilisSeansi metodundan, acilista eslesen alis ve satis Orderlerinin listeleri geliyor.
//        
//        ArrayList<Order> acilisListesiAlisEslesen = new ArrayList<Order>();
//        ArrayList<Order> acilisListesiSatisEslesen = new ArrayList<Order>();
//        //altta bunlarin siralamalari yapiliyor. 
//        //29.12.15 - artik bu siralamalara gerek kalmadi
////        PriceDescTimeOrderIdComparator priceDescTimeOrderIdComparator = new PriceDescTimeOrderIdComparator();
////        PriceAscTimeOrderIdComparator priceAscTimeOrderIdComparator = new PriceAscTimeOrderIdComparator();
////        Collections.sort(acilisListesiAlis, priceDescTimeOrderIdComparator);
////        Collections.sort(acilisListesiSatis, priceAscTimeOrderIdComparator);
//        
//        //29.12.15 - marketPrice proseduru kaldirildi
//        //burada eskiden kullandigimiz marketprice hesaplaniyor. artik kullanmiyoruz ama comment out etmedim yine de . bir zarari yok
////        BigDecimal marketPrice = null;
////        for (int i = 0; i < TradeList.size(); i++) {
////            Trade Trade = TradeList.get(i);
////            if (Trade.getTime().before(dateFormat.parse(Trade.getIslemTarihi() + " " + seans))
////                    && !TradeList.get(i + 1).getTime().before((dateFormat.parse(Trade.getIslemTarihi() + " " + seans)))) {
////                marketPrice = Trade.getFiyat();
////                i = TradeList.size();
////            }
////        }
//        
//        //burada Trade listesinden acilistaki, yani 9.45'den onceki Tradeler aliniyor
//        ArrayList<Trade> acilisListesiTradeleri = new ArrayList<Trade>();
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if (Trade.getIslemTarihi().before(dateFormat.parse(Trade.getIslemTarihi() + " " + seans))) {
//                acilisListesiTradeleri.add(Trade);
//            }
//            else
//                i = TradeList.size();
//        }
//        
//        //Collections.reverse(acilisListesiSatis);
//        int alisVolumeToplami = 0;
//        int satisVolumeToplami = 0;
//        //alttaki iki for blogu da yeni acilis procedure icin feda edildi :)  1 agu..
//        /*for (int i = 0; i < acilisListesiAlis.size(); i++) {
//            Order alisEmri = acilisListesiAlis.get(i);
//            if (alisEmri.getPrice().compareTo(marketPrice) >= 0) {
//                alisVolumeToplami += alisEmri.getVolume();
//                acilisListesiAlisEslesen.add(alisEmri);
//            }
//        }
//        for (int i = 0; i < acilisListesiSatis.size(); i++) {
//            Order satisEmri = acilisListesiSatis.get(i);
//            if (satisEmri.getPrice().compareTo(marketPrice) <= 0) {
//                satisVolumeToplami += satisEmri.getVolume();
//                acilisListesiSatisEslesen.add(satisEmri);
//            }
//        }*/
//        
//        //burada bunlarin ekrana yazilmasi var.
//        //Collections.sort(acilisListesiAlisEslesen, priceDescTimeOrderIdComparator);
//        //Collections.sort(acilisListesiSatisEslesen, priceAscTimeOrderIdComparator);
//        
//        //yeni procedure:  (9.45'ten onceki Tradeler de elimizde hazir)
//        for (Trade Trade : acilisListesiTradeleri) {  //acilis listesindeki her Trade icin sirayla:
//            String satisOrderId=Trade.getS_emirNo();  //Tradedeki satis emrinin orderid'sini al
//            String alisOrderId=Trade.getB_emirNo();   //Tradedeki alim emrinin orderid'sini al
//            int volumeOfTrade=Trade.getMiktar();      //Tradein volume'una bak
//            for (int i = 0; i < acilisListesiAlis.size(); i++) {    //acilis listesindeki alis Orderlerinin listesini gez
//                Order alisEmri = acilisListesiAlis.get(i);    
//                if(alisEmri.getEmirNumarasi().equalsIgnoreCase(alisOrderId)){  //Tradedeki orderid'yi yakaladiysak
//                    if(alisEmri.getMiktar()==volumeOfTrade){     //emrin volume'u Tradein volume'una esitse
//                        acilisListesiAlisEslesen.add(alisEmri);   //acilisListesiAlisEslesen listesine bu emri ekle
//                    }
//                    else{                        //emrin volume'u Tradein volume'una esit degilse
//                        alisEmri.setBakiye(alisEmri.getMiktar()-volumeOfTrade);   //emrin volume'unu azalt
//                        acilisListesiAlisEslesen.remove(alisEmri);     //acilisListesiAlisEslesen listesinden bu emri cikart. BURASI HATALI GIBI,
//                        //DAHA DOGRUDU ANLAMSIZ CUNKU BU Order O LISTEYE HIC GIRMEDI SANIRIM. BIRDEN FAZLA KERE ESLESME DURUMU OLMADIYSA TABI
//                        //TODO 29.12.15 alttaki iki satir commentlendi ama tekrar acilmasi ve ilgili kodun devreye alinmasi gerekecek
//                        //int indexToUpdate = findIndexOfAnOrder(alisEmri);
//                        //OrderList.set(indexToUpdate, alisEmri);  //volume'u guncellenen emri ana Order listesinde guncelle yeniden
//                    }
//                }
//            }
//            //BU FOR USTTEKININ SIMETRIGI
//            for (int i = 0; i < acilisListesiSatis.size(); i++) {
//                Order satisEmri = acilisListesiSatis.get(i);
//                if(satisEmri.getEmirNumarasi().equalsIgnoreCase(satisOrderId)){
//                    if(satisEmri.getMiktar()==volumeOfTrade){
//                        acilisListesiSatisEslesen.add(satisEmri);
//                    }
//                    else{
//                        satisEmri.setBakiye(satisEmri.getMiktar()-volumeOfTrade);
//                        //System.out.println("guncellenen satis emri:"+satisEmri);
//                        acilisListesiSatisEslesen.remove(satisEmri);
//                      //TODO 29.12.15 alttaki iki satir commentlendi ama tekrar acilmasi ve ilgili kodun devreye alinmasi gerekecek
//                       //int indexToUpdate = findIndexOfAnOrder(satisEmri);
//                       //OrderList.set(indexToUpdate, satisEmri);
//                    }
//                }
//            }
//        }
//        
//        System.out.println("acilisListesiSatisEslesen: \n");
//        printFullLineList(acilisListesiSatisEslesen);
//        System.out.println("\n\nacilisListesiAlisEslesen: \n");
//        printFullLineList(acilisListesiAlisEslesen);
//        //acilisListesiAlisEslesen VE acilisListesiSatisEslesen listesindeki Orderleri ana Order listemizden cikar.
//        OrderList.removeAll(acilisListesiAlisEslesen);
//        OrderList.removeAll(acilisListesiSatisEslesen);
// 
//    }

//    public static int findIndexOfAnOrder(Order e) {
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getOrderId().equalsIgnoreCase(e.getOrderId()) && Order.getTime().compareTo(e.getTime()) == 0) {
//                return i;
//            }
//        }
//        return -2;
//    }

//    public static int findIndexOfAnOrder(String orderid) {
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getOrderId().equalsIgnoreCase(orderid)) {
//                return i;
//            }
//        }
//        return -3;
//    }
    
//    public static int findIndexOfAnOrderAtLobList(String orderId) {
//        for (int i = 0; i < lobList.size(); i++) {
//            Order Order = lobList.get(i).getE();
//            if (Order.getOrderId().equalsIgnoreCase(orderId)) {
//                return i;
//            }
//        }
//        return -1;
//    }

//    private static ArrayList<Order> findOrdersWithThisBuyerIdOrSellerId(String buyerOrderId, String sellerOrderId) {
//        ArrayList<Order> list = new ArrayList<Order>();
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (buyerOrderId.equalsIgnoreCase(Order.getOrderId()) || sellerOrderId.equalsIgnoreCase(Order.getOrderId())) {
//                list.add(Order);
//                //System.out.println("-----------------------" + Order.getOrderId() + Order.getOrderType());
//            }
//        }
//        return list;
//    }

    public static void printList(ArrayList<Order> OrderList) {
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            System.out.println(Order.getEmirNumarasi() + Order.getAlis_satis());
        }
    }

//    public static void printWList(ArrayList<Order> OrderList) {
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getOrderType().equalsIgnoreCase("W"))
//                System.out.println(Order.getOrderId() + Order.getOrderType() + "  i:" + i);
//        }
//    }

    public static void printFullLineList(ArrayList<Order> OrderList) {
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            System.out.println(Order.toString());
        }
    }

    public static void printFullLineListTrade(ArrayList<Trade> TradeList) {
        for (Trade Trade : TradeList) {
            System.out.println(Trade.toString());
        }
    }

    public static void printFullLineListLobLine(ArrayList<LOBLine> lobLineList) {

        //NOTE for ilknur
        //for (LOBLine line : lobListOfMorning) {
        //    System.out.println(line.toString());
        //}
        //eger ekran goruntusunde de oglene kadar olusan lobu istersen, ustteki 3 satirin basindaki comment isaretlerini kaldir!!!

        for (LOBLine line : lobLineList) {
            System.out.println(line.toString());
        }
    }
    
    public static void printFullLineListDynamicLobLine(ArrayList<LOBLine> lobLineList) {
        for (LOBLine line : lobLineList) {
            System.out.println(line.toStringDynamic());
        }
    }

//    public static void filterZeroVolumesFromWYZ() {
//        ArrayList<Order> OrderListToDelete = new ArrayList<Order>();
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getVolume()==0) {
//                if("C".equalsIgnoreCase(Order.getOrderType()) || "D".equalsIgnoreCase(Order.getOrderType())){
//                    for (int j = 0; j < OrderList.size(); j++) {
//                        Order Order2 = OrderList.get(j);
//                        if(Order2.getOrderId().equalsIgnoreCase(Order.getOrderId()) && Order2.getPrice().compareTo(Order.getPrice())==0){
//                            OrderListToDelete.add(Order2);
//                        }
//                }
//                OrderListToDelete.add(Order);
//                System.out.println("-----------------------" + Order.getOrderId() + Order.getOrderType());
//            }
//        }
//        }
//        OrderList.removeAll(OrderListToDelete);
//    }
    
    
//    public static boolean similarOrderWithXorY(String orderId) {
//        System.out.println("similarOrderWithXorY:" + orderId);
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getOrderId().equalsIgnoreCase(orderId) && (Order.getOrderType()).equalsIgnoreCase("X")
//                    || (Order.getOrderType()).equalsIgnoreCase("Y")) {
//                return true;
//                //System.out.println("-----------------------" + Order.getOrderId() + Order.getOrderType());
//            }
//        }
//        return false;
//    }

//    public static boolean isItASingleLine() {
//        boolean isSingle = true;
//        for (int i = 1; i < OrderList.size() - 1; i++) {
//            Order Order = OrderList.get(i);
//            if ((Order.getOrderId()).equalsIgnoreCase(OrderList.get(i - 1).getOrderId())
//                    || (Order.getOrderId()).equalsIgnoreCase(OrderList.get(i + 1).getOrderId())) {
//                isSingle = false;
//            }
//        }
//        return isSingle;
//    }

//    public static void removeAllWithSameOrderId(String id) throws ParseException {
//        ArrayList<Order> OrderListToDelete = new ArrayList<Order>();
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (id.equalsIgnoreCase(Order.getOrderId()) && notInTradeList(Order.getOrderId())) {
//                OrderListToDelete.add(Order);
//            }
//        }
//        OrderList.removeAll(OrderListToDelete);
//    }

//    public static boolean notInTradeList(String orderid){
//    	for (int i = 0; i < TradeList.size(); i++) {
//            Trade p = TradeList.get(i);
//            if (orderid.equalsIgnoreCase(p.getBuyerOrderId()) || orderid.equalsIgnoreCase(p.getSellerOrderId())) {
//            	System.out.println("filterXWorYW wants to delete this but it is in Trade list!!!: "+orderid);
//            	findSumsOfOslemAndUpdateOrder(orderid);
//            	XWorYW.add(orderid);
//            	return false;
//            }
//        }
//    	return true;
//    }
    
//    public static boolean notInTradeListKTR(String orderid){
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade p = TradeList.get(i);
//            if (orderid.equalsIgnoreCase(p.getBuyerOrderId()) || orderid.equalsIgnoreCase(p.getSellerOrderId())) {
//                findSumsOfOslemAndUpdateOrder(orderid);
//                //XWorYW.add(orderid);
//                return false;
//            }
//        }
//        return true;
//    }
    
//    public static void findSumsOfOslemAndUpdateOrder(String orderid){
//    	int sum=0;
//    	for (int i = 0; i < TradeList.size(); i++) {
//            Trade p = TradeList.get(i);
//            if (orderid.equalsIgnoreCase(p.getBuyerOrderId()) || orderid.equalsIgnoreCase(p.getSellerOrderId())) {
//            	sum+=p.getVolume();
//            }
//        }
//    	int index=findIndexOfAnOrder(orderid);
//    	Order e = OrderList.get(index);
//    	e.setVolume(sum);
//    	OrderList.set(index, e);
//    }
    
//    public static void removeAllWithSameOrderIdForAcilisSeansi(String id) throws ParseException {
//        ArrayList<Order> OrderListToDelete = new ArrayList<Order>();
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (id.equalsIgnoreCase(Order.getOrderId())
//                    && Order.getTime().before(dateFormat.parse(Order.getDate() + " "+seans))) {
//                OrderListToDelete.add(Order);
//                System.out.println("-----------------------" + Order.getOrderId() + Order.getDurum());
//            }
//        }
//        OrderList.removeAll(OrderListToDelete);
//    }

//    public static int removeAllWithSameOrderIdExceptModifiedLine(Order e) throws ParseException {
//        System.out.println("removeAllWithSameOrderIdExceptModifiedLine, Order:"+e);
//        String typeOfModification = e.getDurum();
//        ArrayList<Order> OrderListToDelete = new ArrayList<Order>();
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (e.getOrderId().equalsIgnoreCase(Order.getOrderId())
//                    && !typeOfModification.equalsIgnoreCase(Order.getDurum())) {
//                OrderListToDelete.add(Order);
//                LOBLine line = new LOBLine(Order, BigDecimal.ZERO, BigDecimal.ZERO, 0, 0, 0);
//                System.out.println("modification icin yakaladi:"+line);
//                ModificationLine mLine = new ModificationLine(line, e.getTime());
//                modificationList.add(mLine);
//            }
//        }
//        
//        System.out.println("\nOrderListToDelete.size():"+OrderListToDelete.size());
//        //OrderList.removeAll(OrderListToDelete);
//        int startingLineForUpdate = lobList.size();
//        ArrayList<LOBLine> lobListToDelete = new ArrayList<LOBLine>();
//        for (int j = 0; j < OrderListToDelete.size(); j++) {
//            String sellerOrderId = OrderListToDelete.get(j).getOrderId();
//            for (int i = 0; i < lobList.size(); i++) {
//                LOBLine line = lobList.get(i);
//                if (sellerOrderId.equalsIgnoreCase(line.getE().getOrderId())) {
//                    lobListToDelete.add(line);
//                    System.out.println("------D Yuzunden silinen-----------------" + line.getE().getOrderId()
//                            + line.getE().getDurum() + " i=" + i);
//                    startingLineForUpdate = i;
//                }
//            }
//        }
//        if(lobListToDelete.size()==0){
//            System.out.println("\n\n C-D-T yuzunden silinmeye calisilan ama lobda bulunamayan Order var!!!!"+e.getOrderId()+"\n\n");
//            return 0;
//        }   
//        
//        
//        lobList.removeAll(lobListToDelete);
//        lobListDeleted.addAll(lobListToDelete);
//        updateLobListAfterRemoval(startingLineForUpdate);
//        return 1;
//    }

    public static int countWLines() throws ParseException {
        int count = 0;
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            if ((Order.getDurum()).equalsIgnoreCase("W")) {
                count++;
            }
        }
        return count;
    }
    
    public static void filterWordersforGirisSaatiBuyukturSonDegistirmeSaatiAndQuantityEqualsBalance() throws Exception {
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            if ((Order.getDurum()).equalsIgnoreCase("W") && Order.getBakiye()==Order.getMiktar()
            		&& Order.getGirisSaati().compareTo(Order.getSonDegistirmeSaati())>=0) {
            	OrderListToDelete.add(Order);
            }
        }
        WriteFile.writeCSVfileEmirDaily(OrderListToDelete, "deleted_W_orders.csv");
        OrderList.removeAll(OrderListToDelete);
    }
    
    public static void filterWordersforQuantityEqualsBalance() throws Exception {
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            if ((Order.getDurum()).equalsIgnoreCase("W") && Order.getBakiye()==Order.getMiktar()) {
            	OrderListToDelete.add(Order);
            }
        }
        WriteFile.writeCSVfileEmirDaily(OrderListToDelete, "deleted_W_orders.csv");
        OrderList.removeAll(OrderListToDelete);
    }
    
    public static void filterTheRestOfWorders() throws Exception {
        for (int i = 0; i < OrderList.size(); i++) {
            Order Order = OrderList.get(i);
            if ((Order.getDurum()).equalsIgnoreCase("W")) {
            	OrderListToDelete.add(Order);
            }
        }
        WriteFile.writeCSVfileEmirDaily(OrderListToDelete, "deleted_EndOfDay_W_orders.csv");
        OrderList.removeAll(OrderListToDelete);
    }

//    public static int findSmaller(int v1, int v2) {
//        if (v1 > v2)
//            return v2;
//        return v1;
//    }
    
//    public static void clearValidOnlyMorning() throws Exception {
//        System.out.println("\nSEANS BITIMI - § procedure running\n");
//        ArrayList<LOBLine> lobListToDelete = new ArrayList<LOBLine>();
//        int startingLineForUpdate = lobList.size();
//        for (int i = 0; i < lobList.size(); i++) {
//            LOBLine line = lobList.get(i);
//            if ("0".equalsIgnoreCase(line.getE().getValidFor())) {
//                if (i < startingLineForUpdate)
//                    startingLineForUpdate = i;
//                lobListToDelete.add(line);
//                lobListDeletedBecause0Validity.add(line);
//                // System.out.println("-----------------------" +
//                // Order.getOrderId() + Order.getOrderType());
//            }
//        }
//        lobList.removeAll(lobListToDelete);
//        lobListDeleted.addAll(lobListToDelete);
//        ArrayList<Order> sabahKalanValidOrderList = new ArrayList<Order>();
//        for (LOBLine line : lobList) {
//            sabahKalanValidOrderList.add(line.getE());
//        }
//        try {
//            createLOBMorning(sabahKalanValidOrderList);
//            //run1400procedure();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    
//    private static void run1400procedure() throws Exception {
//        System.out.println("\nrun1400procedure\n");
//        ArrayList<Order> OrderList1400 = new ArrayList<Order>();
//        ArrayList<Trade> TradeList1400 = new ArrayList<Trade>();
//        LOBLine line = null;
//        int index;
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order Order = OrderList.get(i);
//            if (Order.getTime().compareTo(dateFormat.parse(Order.getDate() + " 14:00:00"))==0) {
//                OrderList1400.add(Order);
//                //line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, "DDDDDDD");
//                //lobList.add(line);
//            }
//        }
//        for (int j = 0; j < TradeList.size(); j++) {
//            Trade Trade=TradeList.get(j);
//            if (Trade.getTime().compareTo(dateFormat.parse(Trade.getDate() + " " + "14:00:00"))==0) {
//                TradeList1400.add(Trade);
//            }
//        }
//        for (int k = 0; k < TradeList1400.size(); k++) {
//            Trade Trade=TradeList1400.get(k);
//            index=findIndexOfAnOrderAtLobList(Trade.getBuyerOrderId());
//            if(index==-1){
//                index=findIndexOfAnOrderAtLobList(Trade.getSellerOrderId());
//                //metodu sell parametresi ile burada cagir
//                specialMatching14(index, Trade, "sell");
//            }
//            else{
//                specialMatching14(index, Trade, "buy");
//            }
//        }
//        for (int k = 0; k < TradeList1400.size(); k++) {
//            Trade Trade=TradeList1400.get(k);
//            index=findIndexOfAnOrderAtLobList(Trade.getBuyerOrderId());
//            if(index==-1){
//                specialMatching14forAfternoonOrder(Trade, "buy", OrderList1400);
//            }
//            else{
//                specialMatching14forAfternoonOrder(Trade, "sell", OrderList1400);
//            }
//        }
//    }

//    private static void specialMatching14forAfternoonOrder(Trade Trade, String tip, ArrayList<Order> OrderList1400) throws Exception {
//        ArrayList<Trade> eslesenler = new ArrayList<Trade>();
//        if("sell".equalsIgnoreCase(tip)){
//            eslesenler=findMatchingLinesAtTradeList(Trade.getSellerOrderId(), Trade.getTime(), tip);
//            int eslesenVolumelerToplami = sumVolumesOfTradeListesi(eslesenler);
//             for (int i = 0; i < OrderList1400.size(); i++) {
//                    Order Order = OrderList1400.get(i);
//                    if (Order.getOrderId().equalsIgnoreCase(Trade.getSellerOrderId())) {
//                        if(Order.getVolume()==eslesenVolumelerToplami){
//                            //o emri hic loba koymayacagim
//                        }
//                        else{
//                            LOBLine linex = new LOBLine(Order, Order.getPrice(), bidPrice, Order.getVolume(), volumeAtBid, lobList.size(),"AAAA");
//                            OrderlistModifiedamaaslindaLOBlistesi.add(linex.toString2());
//                            Order.setVolume(Order.getVolume()-eslesenVolumelerToplami);
//                            LOBLine line = new LOBLine(Order, Order.getPrice(), bidPrice, Order.getVolume(), volumeAtBid, lobList.size(),
//                                    "AAAA");
//                            lobList.add(line);
//                            volumeAtAsk = Order.getVolume();
//                            askPrice = Order.getPrice();
//                        }
//                    }
//                }
//        }
//    }

//    private static void specialMatching14(int index, Trade Trade, String tip) {
//        ArrayList<Trade> TradeList1400 = new ArrayList<Trade>();
//        TradeList1400.add(Trade);
//        if("sell".equalsIgnoreCase(tip)){
//            String sellorderIdToChange = Trade.getSellerOrderId();
//            Order sellOrderToChange = getIndexOfaLineFromLOBList(sellorderIdToChange);
//            //eslesen son emrin hacmi-eslesmelerin toplami kadar azalir
//            sellOrderToChange.setVolume(sellOrderToChange.getVolume() - Trade.getVolume());
//            int sellindexToUpdate = getIndexOfaLineFromLOBList(sellOrderToChange);
//            LOBLine lobLineToUpdateSell = lobList.get(sellindexToUpdate);
//            lobLineToUpdateSell.setE(sellOrderToChange);
//            lobList.set(sellindexToUpdate, lobLineToUpdateSell);
//            
//            removeLinesFromLOBWhoseOrderVolumeisZero(TradeList1400, "sell");
//            yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(sellorderIdToChange,"sell");
//        }
//        else{
//            String buyorderIdToChange = Trade.getBuyerOrderId();
//            Order buyOrderToChange = getIndexOfaLineFromLOBList(buyorderIdToChange);
//            //eslesen son emrin hacmi-eslesmelerin toplami kadar azalir
//            buyOrderToChange.setVolume(buyOrderToChange.getVolume() - Trade.getVolume());
//            int buyindexToUpdate = getIndexOfaLineFromLOBList(buyOrderToChange);
//            LOBLine lobLineToUpdateBuy = lobList.get(buyindexToUpdate);
//            lobLineToUpdateBuy.setE(buyOrderToChange);
//            lobList.set(buyindexToUpdate, lobLineToUpdateBuy);
//            
//            removeLinesFromLOBWhoseOrderVolumeisZero(TradeList1400, "buy");
//            yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(buyorderIdToChange,"buy");
//        }
//        
//    }

    private static void initCreateLOB() throws Exception {
        Order ilkOrder = OrderList.get(0);
        LOBLine line1 = null;
        System.out.println("initCreateLOB");
        if ("B".equalsIgnoreCase(ilkOrder.getAlis_satis())) {
            bidPrice = ilkOrder.getFiyat();
            volumeAtBid = ilkOrder.getMiktar();
            askPrice = new BigDecimal(100);  //TODO check this
            volumeAtAsk = 0;
            line1 = new LOBLine(ilkOrder, askPrice, bidPrice, 0, volumeAtBid, 1,"5B");
        } else if ("S".equalsIgnoreCase(ilkOrder.getAlis_satis())) {
            askPrice = ilkOrder.getFiyat();
            volumeAtAsk = ilkOrder.getMiktar();
            bidPrice = new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP);
            volumeAtBid = 0;
            line1 = new LOBLine(ilkOrder, askPrice, bidPrice, volumeAtAsk, 0, 1,"5S");
        }
        lobList.add(line1);
        OrderlistModifiedamaaslindaLOBlistesi.add(line1.toString2());
        boolean morning = true;
        boolean morningFlagForDynamicLob = true;
        //System.out.println("orderlist size:"+OrderList.size());
        for (int i = 1; i < OrderList.size(); i++) {        	
            Order order= OrderList.get(i);
            if (DYNAMIC_LOB_COUNTER != -1) {
//                if (morning == false && morningFlagForDynamicLob == true) {
//                    fixDynamicLOBAfterLunch(i - 1);//i-1 cunku ogleden sonraki ilk Order az once alinmisti arada i artti. geri donmek lazim
//                    morningFlagForDynamicLob = false;
//                }
                System.out.println(order.getEmirNumarasi());
                if (order.getTime().after(OrderList.get(i - 1).getTime())) {
                //if (orderTimeComparator(order, OrderList.get(i - 1))) {
                    int dif = (int) (order.getTime().getTime() - OrderList.get(i - 1).getTime().getTime()) / 1000;
                    for (int k = 0; k < dif && dif < 3000; k++) {
                        LOBLine line = lobList.get(lobList.size() - 1);
                        //if(k==0){
                        line.setCountLOBLine(countLOBLinesAtTimeOfGivenOrder(order));
                        line.setCountLOBLine_BUY(countLOBLines_BUY_AtTimeOfGivenOrder(order));
                        line.setCountLOBLine_SELL(countLOBLines_SELL_AtTimeOfGivenOrder(order));
                        line.setTradePrice(findTradePriceAtTimeOfGivenOrder(order.getTime()));
                        line.setVolumeTraded(findVolumeTradedAtTimeOfGivenOrder(order.getTime()));
                        line.setExtra2b2sdata(add2b2sColumns(order.getTime()));
                        //}
                        dynamicLob.add(line);
                        //WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
                        dynamicLobString.add(line.toStringDynamic()+"%"+line.getExtra2b2sdata());
                    }
                    if (dif > 3000) {
                        //dynamicLob.add(lobList.get(lobList.size()-1));

                        LOBLine line = lobList.get(lobList.size() - 1);
                        //if(k==0){
                        line.setCountLOBLine(countLOBLinesAtTimeOfGivenOrder(order));
                        line.setCountLOBLine_BUY(countLOBLines_BUY_AtTimeOfGivenOrder(order));
                        line.setCountLOBLine_SELL(countLOBLines_SELL_AtTimeOfGivenOrder(order));
                        line.setTradePrice(findTradePriceAtTimeOfGivenOrder(order.getTime()));
                        line.setVolumeTraded(findVolumeTradedAtTimeOfGivenOrder(order.getTime()));
                        line.setExtra2b2sdata(add2b2sColumns(order.getTime()));
                        //}
                        dynamicLob.add(line);
                        //WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
                        dynamicLobString.add(line.toStringDynamic()+"%"+line.getExtra2b2sdata());
                        //System.out.println("dif>3000 iken eldeki Order:"+Order);
                        fixDynamicLOBEndOfMorning(i);
                    }
                }
            }
            //if(i==OrderList.size()-1)
            //    fixDynamicLOB();
            
            //22 Ocak 2016, order icin tradeNo alanina gerek olmadigi goruldu
            //int tradeNo=countTradesUntilGivenTime(order.getTime());
            //order.setTradeNo(tradeNo);
            
            i = anyInterrupts(i, order);
            if(interruptTime!=null && anyInterruptsByTime(i, order)){
                printFullLineListLobLine(lobList);
                return;
            }
            
            
            //if (morning && order.getTime().after(dateFormat.parse(order.getEmirTarihi()+ " "+ilkseanssonu))) {
            if (morning && (order.getGirisSaati().compareTo(ilkseanssonuPlainString)>0)) {
                System.out.println("\n\n SABAH BITTI clearValidOnlyMorning oncesi lob:\n\n");
                printFullLineListLobLine(lobList);
                lobListOfMorning.addAll(lobList);
                WriteFile.writeCSVfileLOB(lobListOfMorning, "lobMorning.csv");
                lobListOfMorningCumulative.addAll(lobListOfMorning);
                WriteFile.writeCSVfileLOBAppend(lobListOfMorningCumulative, "LOB_morning_cumulative.csv");
                morning = false;
                //22.01.16 - artik validity kontrolumuz validFor alanina bakmiyor ve sabah seansi sonrasi temizligi yok
                //clearValidOnlyMorning();
            }
            

            LOBLine line;
            if ("B".equalsIgnoreCase(order.getAlis_satis())
                    ) { //case 1 and case 3
                //if(bidPrice==null || bidPrice.compareTo(BigDecimal.ZERO)==0)//ilk ALIS emri bu if'e girecek. 
                //    bidPrice=Order.getPrice();
                //aggressiveness part:
                String aggressiveness;
                if (order.getFiyat().compareTo(askPrice) >= 0 && order.getMiktar() >= volumeAtAsk) {
                    aggressiveness = "1B";
                } else if (order.getFiyat().compareTo(askPrice) >= 0 && order.getMiktar() < volumeAtAsk) {
                    aggressiveness = "2B";
                } else if (askPrice.compareTo(order.getFiyat()) > 0 && order.getFiyat().compareTo(bidPrice) > 0) {
                    aggressiveness = "3B";
                } else if (order.getFiyat().compareTo(bidPrice) == 0) {
                    aggressiveness = "4B";
                } else {// if(Order.getPrice()<bidPrice){
                    aggressiveness = "5B";
                }
                //System.out.println("aggr: " + aggressiveness);
//                if ("C".equalsIgnoreCase(order.getOrderType())) { //initial step of case 3
//                    removeAllWithSameOrderIdExceptModifiedLine(order);
//                    if(order.getVolume()==0){
//                    	System.out.println("C emri ama volume 0. O yuzden loba yazmadan devam et: "+order.getOrderId());
//                    	continue;
//                    }
//                }

                if (order.getFiyat().compareTo(bidPrice) < 0) { //case 1.1
                    line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if ("E".equalsIgnoreCase(order.getKtr())){
//                        lobListDeleted.add(line);
//                        lobList.add(line);
//                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString2());
//                    }
//                    else{
//                        lobList.add(line);
//                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString2());
//                    }
//                    System.out.println("TEST 1");
                    if(findMatchingLinesAtTradeList(order.getEmirNumarasi(), order.getTime(), "buy").size()>0)
                        buyMatchingCase(order, i, aggressiveness);
                } else if (order.getFiyat().compareTo(bidPrice) > 0) { //case 1.2
                    volumeAtBidMemory = volumeAtBid;
                    bidPriceMemory = bidPrice;
                    bidPrice = order.getFiyat();
                    volumeAtBid = order.getMiktar();
                    line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {//case 1.2.1
//                        if ("E".equalsIgnoreCase(order.getKtr())){
//                          //bu iki satir, 6 aralik'ta olusan hatadan sonra eklendi:
//                            lobList.add(line);
//                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                            lobListDeleted.add(line);
//                        }
//                        else{
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString2());
                        //}
                        System.out.println("TEST 2");
                        buyMatchingCase(order, i, aggressiveness);
                    } 
                    else {  //spread kucuk esit 0. mutlaka eslesme.
                        //ESLESME DURUMU. Alis emri geldi, ustten bir veya daha fazla satis emri ile eslesecek.
                        //burada 3 durum var, 
                        //1. eslesen butun Orderlerin ve yeni gelen Orderin silinmesi
                        //2. yeni Orderin silinmesi, ustte match edennin volumunun azalmasi
                        //3. ustteki Orderin silinmesi, yeni Orderin volumunun azaltilarak loba yazilmasi
                        
//                        if ("E".equalsIgnoreCase(order.getKtr())) {
//                            ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
//                            eslesenTradelerListesi = findMatchingLinesAtTradeList(order.getOrderId(), order.getTime(),"buy");
//                            int eslesenSayisi=eslesenTradelerListesi.size();
//                            if(eslesenSayisi==0){
//                                lobListDeleted.add(line);
//                                continue;
//                            }
//                        }
                        lobList.add(line);
                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                        System.out.println("TEST 3");
                        buyMatchingCase(order, i, aggressiveness);
                    }
                } else {//case 1.3  yeni gelen Orderin fiyati bidpricea esit
                    line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {
//                        if ("E".equalsIgnoreCase(order.getKtr())) {
//                            line = new LOBLine(order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                          //bu iki satir, 26 kasim'da olusan hatadan sonra eklendi:
//                            lobList.add(line);
//                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                            lobListDeleted.add(line);
//                        } else {
                            volumeAtBid = volumeAtBid + order.getBakiye();
                            line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                        //}
                        System.out.println("TEST 4");
                        buyMatchingCase(order, i, aggressiveness);
                    }
                    else{//spread kucuk esit 0
                        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
                        eslesenTradelerListesi = findMatchingLinesAtTradeList(order.getEmirNumarasi(), order.getTime(),"buy");
                        //matchinglerden gelen sey 0 tane mi degil mi kontrolu
                        //sifir degilse::
                        System.out.println("\n\nCROSS DURUMU....\n\n");
                        if(eslesenTradelerListesi.size()>0){
                            volumeAtBid = volumeAtBid + order.getBakiye();
                            line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                            System.out.println("TEST 5");
                            buyMatchingCase(order, i, aggressiveness);
                        }
                        //sifirsa:
                        else{
//                            if ("E".equalsIgnoreCase(order.getKtr())){
//                                lobListDeleted.add(line);
//                            }
//                            else{
                            volumeAtBid = volumeAtBid + order.getBakiye();
                            line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness,CROSS_ORDER_INDICATOR);
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                            //}
                        }   
                    }
                }
            } else if (("S".equalsIgnoreCase(order.getAlis_satis()))) { //case 2 and case 4
                String aggressiveness;
                
                if (bidPrice != null && (order.getFiyat().compareTo(bidPrice) <= 0 && order.getMiktar() >= volumeAtBid)) {
                    aggressiveness = "1S";
                } else if (bidPrice != null
                        && (order.getFiyat().compareTo(bidPrice) <= 0 && order.getMiktar() < volumeAtBid)) {
                    aggressiveness = "2S";
                } else if (bidPrice != null
                        && (askPrice.compareTo(order.getFiyat()) > 0 && order.getFiyat().compareTo(bidPrice) > 0)) {
                    aggressiveness = "3S";
                } else if (order.getFiyat().compareTo(askPrice) == 0) {
                    aggressiveness = "4S";
                } else {// if(Order.getPrice()>askPrice){
                    aggressiveness = "5S";
                }

//                if (("D".equalsIgnoreCase(order.getOrderType()) || "T".equalsIgnoreCase(order.getOrderType()))) { //initial step of case 4
//                    int removing=removeAllWithSameOrderIdExceptModifiedLine(order);
//                    if(removing==0){
//                        System.out.println("D/T emri ama orjinali filterOrdersFirstOccurencesIfCorDcomes ile filtrelenmis. O yuzden loba yaz ve devam et: "+order.getOrderId());
//                        line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                        lobList.add(line);
//                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                        continue;
//                    }
//                    //removeAllWithSameOrderIdExceptModifiedLine(Order);
//                    if(order.getVolume()==0){
//                    	System.out.println("D/T emri ama volume 0. O yuzden loba yazmadan devam et: "+order.getOrderId());
//                    	continue;
//                    }
//                }
                if (order.getFiyat().compareTo(askPrice) > 0) { //case 2.1
                    if (bidPrice == null) {
                        line = new LOBLine(order, askPrice, BigDecimal.ZERO, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    } else
                        line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if ("E".equalsIgnoreCase(order.getKtr())){
//                        lobListDeleted.add(line);
//                        lobList.add(line);
//                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                    }
//                    else{
                        lobList.add(line);
                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                    //}
                    System.out.println("TEST 6");
                    if(findMatchingLinesAtTradeList(order.getEmirNumarasi(), order.getTime(), "sell").size()>0)
                        sellMatchingCase(order, i, aggressiveness);
                } else if (order.getFiyat().compareTo(askPrice) < 0) { //case 2.2
                    askPriceMemory = askPrice;
                    volumeAtAskMemory = volumeAtAsk;
                    askPrice = order.getFiyat();
                    volumeAtAsk = order.getMiktar();
                    line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {//case 2.2.1
//                        if ("E".equalsIgnoreCase(order.getKtr())){
//                          //bu iki satir, 6 aralik'ta olusan hatadan sonra eklendi:
//                            lobList.add(line);
//                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                            lobListDeleted.add(line);
//                        }    
//                        else{
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                       // }
                        System.out.println("TEST 7");
                        sellMatchingCase(order, i, aggressiveness);
                    } else {  //spread kucuk veya esittir 0
                        //yeni gelen satis emri sonrasi eslesme durumu. ustten bir veya daha fazla alis emri ile eslesecek.
                        //burada 3 durum var, 
                        //1. eslesen iki Orderin de silinmesi
                        //2. yeni Orderin silinmesi, ustte match edenin volumunun azalmasi
                        //3. ustteki Orderin silinmesi, yeni Orderin volumunun azaltilarak loba yazilmasi
//                        if ("E".equalsIgnoreCase(order.getKtr())) {
//                            ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
//                            eslesenTradelerListesi = findMatchingLinesAtTradeList(order.getOrderId(), order.getTime(),"sell");
//                            int eslesenSayisi=eslesenTradelerListesi.size();
//                            if(eslesenSayisi==0){
//                                lobListDeleted.add(line);
//                                continue;
//                            }
//                        }
                        lobList.add(line);
                        OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                        System.out.println("TEST 8");
                        sellMatchingCase(order, i, aggressiveness);
                    }
                } else {//case 2.3
                    line = new LOBLine(order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                   if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {
//                        if ("E".equalsIgnoreCase(order.getKtr())) {
//                            line = new LOBLine(order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            
//                            //bu iki satir, 26 kasim'da olusan hatadan sonra eklendi:
//                            lobList.add(line);
//                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
//                            lobListDeleted.add(line);
//                        } else {
                            volumeAtAsk = volumeAtAsk + order.getBakiye();
                            line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                        //}
                        System.out.println("TEST 9");
                        sellMatchingCase(order, i, aggressiveness);
                    }else{ //spread kucuk esit 0
                        System.out.println("\n\nCROSS DURUMU..sellmatchingcase..\n\n");
                        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
                        eslesenTradelerListesi = findMatchingLinesAtTradeList(order.getEmirNumarasi(), order.getTime(),"sell");
                        
                        if(eslesenTradelerListesi.size()>0){
                            volumeAtAsk = volumeAtAsk + order.getBakiye();
                            line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
                            lobList.add(line);
                            OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                            System.out.println("TEST 10");
                            sellMatchingCase(order, i, aggressiveness);
                        }
                        //sifirsa:
                        else{
//                            if ("E".equalsIgnoreCase(order.getKtr())){
//                                lobListDeleted.add(line);
//                            }
//                            else{
                                volumeAtAsk = volumeAtAsk + order.getBakiye();
                                line = new LOBLine(order, askPrice, bidPrice,
                                    volumeAtAsk, volumeAtBid, i, aggressiveness,CROSS_ORDER_INDICATOR);
                                lobList.add(line);
                                System.out.println("14..."+line.toString2());
                                OrderlistModifiedamaaslindaLOBlistesi.add(line.toString());
                            //}
                        }
                    }
                }
            }
        }
        System.out.println("EN SONA GELIP ISLENMEYEN VAR MI BAKISI");
        //checkIfUnprocessedLineExistAtEndOfLOBCreation();
        printFullLineListLobLine(lobList);
    }

    private static BigDecimal findTradePriceAtTimeOfGivenOrder(Date Ordertime) {
        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
        for (int i = 0; i < TradeList.size(); i++) {
            Trade Trade = TradeList.get(i);
            if (Ordertime.compareTo(Trade.getTime())==0){  
                eslesenTradelerListesi.add(Trade);
            }
        }
        int eslesenSayisi=eslesenTradelerListesi.size();
        if(eslesenSayisi==0){
            if(dynamicLob.size()>0)
                return dynamicLob.get(dynamicLob.size()-1).getTradePrice();
            return findMarketPrice();
        }
        return eslesenTradelerListesi.get(eslesenSayisi-1).getFiyat();
    }
    
//    private static BigDecimal findTradePriceAtTimeOfGivenOrderForFinalAddition(Date Ordertime, int currentLine) {
//        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//            if (Ordertime.compareTo(Trade.getTime())==0){  
//                eslesenTradelerListesi.add(Trade);
//            }
//        }
//        int eslesenSayisi=eslesenTradelerListesi.size();
//        if(eslesenSayisi==0){
//            if(currentLine>0)
//                return getTradePriceFromDynamicLobLineString(dynamicLobString.get(currentLine-1));
//            return findMarketPrice();
//        }
//        return eslesenTradelerListesi.get(eslesenSayisi-1).getPrice();
//    }
    
//    private static BigDecimal getTradePriceFromDynamicLobLineString(String line){
//        String tmp="999999";
//        for(int i=0;i<31;i++){
//            tmp=line.substring(line.indexOf(";"));
//            line=line.substring(line.length()-tmp.length()+1);
//        }
//        tmp=line.substring(0,line.indexOf(";"));
//        BigDecimal price = new BigDecimal(tmp);
//        return price;
//    }
    
    private static BigDecimal findMarketPrice(){
        BigDecimal marketPrice = null;
        for (int i = 0; i < TradeList.size(); i++) {
            Trade Trade = TradeList.get(i);
            try {
                //if (Trade.getTime().before(dateFormat.parse(Trade.getIslemTarihi() + " " + seans))
               //        && !TradeList.get(i + 1).getTime().before((dateFormat.parse(Trade.getIslemTarihi() + " " + seans)))) {
                if ((Trade.getZaman().compareTo(seans)<0)
                        && !(TradeList.get(i + 1).getZaman().compareTo(seans)<0)) {
                    marketPrice = Trade.getFiyat();
                    i = TradeList.size();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(marketPrice==null)
            marketPrice=TradeList.get(0).getFiyat();
        return marketPrice;
    }
    
    
    private static int findVolumeTradedAtTimeOfGivenOrder(Date Ordertime) {
        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
        for (int i = 0; i < TradeList.size(); i++) {
            Trade Trade = TradeList.get(i);
            if (Ordertime.compareTo(Trade.getTime())==0){  
                eslesenTradelerListesi.add(Trade);
            }
        }
        return sumVolumesOfTradeListesi(eslesenTradelerListesi);
    }

    public static int countLOBLinesAtTimeOfGivenOrder(Order Order) {
        int count=0;
        for (LOBLine line : lobList) {
            //if(Order.getTime().getTime()-line.getE().getTime().getTime()==1000)
            if(line.getE().getTime().before(Order.getTime()))
                count++;
        }
        return count;
    }
    
    public static int countLOBLines_BUY_AtTimeOfGivenOrder(Order Order) {
        int count=0;
        for (LOBLine line : lobList) {
            //if(Order.getTime().getTime()-line.getE().getTime().getTime()==1000)
            Order e = line.getE();
            if(line.getE().getTime().before(Order.getTime()) && "B".equalsIgnoreCase(e.getAlis_satis()))
                count++;
        }
        return count;
    }
    
    public static int countLOBLines_SELL_AtTimeOfGivenOrder(Order Order) {
        int count=0;
        for (LOBLine line : lobList) {
            //if(Order.getTime().getTime()-line.getE().getTime().getTime()==1000)
            Order e = line.getE();
            if(line.getE().getTime().before(Order.getTime()) && "S".equalsIgnoreCase(e.getAlis_satis()))
                count++;
        }
        return count;
    }

//    private static int countTradesUntilGivenTime(Date OrderTime) {
//        
//        //System.out.println("\n\ncountTradesUntilGivenTime, gelen zaman:"+OrderTime+"   Trade zamani:"+TradeList.get(0).getTime());
//        
//    	int count=0;
//        for (int i = 0; i < TradeList.size(); i++) {
//            Trade Trade = TradeList.get(i);
//                if (!Trade.getTime().after(OrderTime)&& !(OrderTime.getTime()-Trade.getTime().getTime()>36000000)){  
//                    count++;
//                }
//            }
//        return count;     
//	}
    
//    private static int countOrdersUntilGivenTime(Date OrderTime) {
//        
//        //System.out.println("\n\ncountTradesUntilGivenTime, gelen zaman:"+OrderTime+"   Trade zamani:"+TradeList.get(0).getTime());
//        
//        int count=0;
//        for (int i = 0; i < OrderList.size(); i++) {
//            Order e = OrderList.get(i);
//                if (!e.getTime().after(OrderTime)&& !(OrderTime.getTime()-e.getTime().getTime()>36000000)){  
//                    count++;
//                }
//            }
//        return count;     
//    }

	private static void sellMatchingCase(Order Order, int i, String aggressiveness) throws Exception {
        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
        eslesenTradelerListesi = findMatchingLinesAtTradeList(Order.getEmirNumarasi(), Order.getTime(),"sell");
        ArrayList<Trade> eslesenSifirlanmayanTradelerListesi = new ArrayList<Trade>();
        int eslesenSayisi=eslesenTradelerListesi.size();
        if(eslesenSayisi==0){
            //CROSS DURUMU
        }
        else {//eslesensayisi buyuk esit 1
            //elimizdeki satis ile eslesen alis Orderlerinin volumelerini azaltiyoruz
            boolean lastMatchisZero=true;
            for (int j=0;j<eslesenSayisi;j++) {
                String orderIdToChange = eslesenTradelerListesi.get(j).getB_emirNo();
                Order OrderToChange = getIndexOfaLineFromLOBList(orderIdToChange);
                if(OrderToChange == null){ //25.02.2019  henuz eslesmenin obur emri lob'da yok, bekle, islem obur emir gelince calistirilacak
                	System.out.println("ordertochange: HENUZ DIGER EMIR GELMEMIS");
                	return;
                }
                int volumeToSubtract = eslesenTradelerListesi.get(j).getMiktar();
                OrderToChange.setBakiye(OrderToChange.getBakiye() - volumeToSubtract);//eslesen son emrin hacmi-eslesme kadar azalir
                if(OrderToChange.getBakiye()!=0){
                    lastMatchisZero=false;
                    eslesenSifirlanmayanTradelerListesi.add(eslesenTradelerListesi.get(j));
                }   
                int indexToUpdate = getIndexOfaLineFromLOBList(OrderToChange);
                if (indexToUpdate != -1) {
                    LOBLine lobLineToUpdate = lobList.get(indexToUpdate);
                    lobLineToUpdate.setE(OrderToChange);
                    lobList.set(indexToUpdate, lobLineToUpdate);
                    System.out.println("CHECKPOINT");
                    System.out.println(OrderToChange.toString());
                }
                else{
                    eslesenTradelerListesi.remove(eslesenTradelerListesi.get(j));
                    if(eslesenTradelerListesi.size()==0)
                        return;
                    //eslesenSayisi--;
                    break;
                }
                    //return;
            }
            // elimizdeki satis emri loba yazilmisti. volume'unu azalt:
            int eslesenVolumelerToplami = sumVolumesOfTradeListesi(eslesenTradelerListesi);
            System.out.println("\neslesenVolumelerToplami:"+eslesenVolumelerToplami);
            String sellorderIdToChange = eslesenTradelerListesi.get(0).getS_emirNo();
            System.out.println("sellorderIdToChange:"+sellorderIdToChange);
            Order sellOrderToChange = getIndexOfaLineFromLOBList(sellorderIdToChange);
            System.out.println("sellOrderToChange:"+sellOrderToChange);
            //eslesen son emrin hacmi-eslesmelerin toplami kadar azalir
            sellOrderToChange.setBakiye(sellOrderToChange.getBakiye() - eslesenVolumelerToplami);
            System.out.println("sellOrderToChange.setVolume:"+sellOrderToChange.getBakiye());
            int sellindexToUpdate = getIndexOfaLineFromLOBList(sellOrderToChange);
            LOBLine lobLineToUpdateSell = lobList.get(sellindexToUpdate);
            System.out.println("lobLineToUpdateSell:"+lobLineToUpdateSell);
            lobLineToUpdateSell.setE(sellOrderToChange);
            lobList.set(sellindexToUpdate, lobLineToUpdateSell);
            
            removeLinesFromLOBWhoseOrderVolumeisZero(eslesenTradelerListesi, "sell");
            yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(sellorderIdToChange,"sell");
            //eslesenlerin sonuncusunun sifir olmama durumu:
            //BURAYA sifir olmama kontrolu lazim. sifir ise hata aliyoruz. 
            System.out.println("lastMatchisZero:"+lastMatchisZero);
            System.out.println("eslesenSifirlanmayanTradelerListesi.size():"+eslesenSifirlanmayanTradelerListesi.size());
            if (!lastMatchisZero) {
                for (int k = 0; k < eslesenSifirlanmayanTradelerListesi.size(); k++) {
                    String orderIdToChange = eslesenSifirlanmayanTradelerListesi.get(k).getB_emirNo();
                    Order OrderToChange = getIndexOfaLineFromLOBList(orderIdToChange);
                    // yani yeni gelen Orderin hacmi lob'da guncellenecek satirin Order kismi olustu. bunu yeniden lobline haline getir:
                    int indexToUpdate = getIndexOfaLineFromLOBList(OrderToChange);
                    if (indexToUpdate != -1) {
                        LOBLine lobLineToUpdate = lobList.get(indexToUpdate);
                        lobLineToUpdate.setBidVol(OrderToChange.getBakiye());
                        volumeAtBid = OrderToChange.getBakiye();
                        lobList.set(indexToUpdate, lobLineToUpdate);
                        // eslesen satis Orderlerinin sonuncusu da guncellendi.
                        // simdi bu satirdan itibaren lob dosyasindaki her satirin
                        // volumeAtAsk'i degisecek
                        updateLobListAfterRemoval(indexToUpdate);
                        askPrice = lobList.get(lobList.size() - 1).getAsk();
                        volumeAtAsk = lobList.get(lobList.size() - 1).getAskVol();
                    }
                }
            }
        }
    }
    
    private static void buyMatchingCase(Order Order, int i, String aggressiveness) throws Exception {
        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
        ArrayList<Trade> eslesenSifirlanmayanTradelerListesi = new ArrayList<Trade>();
        eslesenTradelerListesi = findMatchingLinesAtTradeList(Order.getEmirNumarasi(), Order.getTime(),"buy");
        int eslesenSayisi=eslesenTradelerListesi.size();
        if(eslesenSayisi==0){
            //CROSS DURUMU
        }
        else {//eslesensayisi buyuk esit 1
            //elimizdeki alis ile eslesen satis Orderlerinin volumelerini azaltiyoruz
            boolean lastMatchisZero=true;
            for (int j=0;j<eslesenSayisi;j++) {
                String orderIdToChange = eslesenTradelerListesi.get(j).getS_emirNo();
                Order OrderToChange = getIndexOfaLineFromLOBList(orderIdToChange);
                if(OrderToChange == null){ //25.02.2019  henuz eslesmenin obur emri lob'da yok, bekle, islem obur emir gelince calistirilacak
                	System.out.println("ordertochange: HENUZ DIGER EMIR GELMEMIS");
                	return;
                }
                int volumeToSubtract = eslesenTradelerListesi.get(j).getMiktar();
                OrderToChange.setBakiye(OrderToChange.getMiktar() - volumeToSubtract);//eslesen son emrin hacmi-eslesme kadar azalir
                if(OrderToChange.getBakiye()!=0){
                    lastMatchisZero=false;
                    eslesenSifirlanmayanTradelerListesi.add(eslesenTradelerListesi.get(j));
                }
                int indexToUpdate = getIndexOfaLineFromLOBList(OrderToChange);
                if (indexToUpdate != -1) {
                    LOBLine lobLineToUpdate = lobList.get(indexToUpdate);
                    lobLineToUpdate.setE(OrderToChange);
                    lobList.set(indexToUpdate, lobLineToUpdate);
                    //System.out.println("CHECKPOINT");
                    //System.out.println(OrderToChange.toString2());
                }
                else{ 
                    eslesenTradelerListesi.remove(eslesenTradelerListesi.get(j));
                    if(eslesenTradelerListesi.size()==0)
                        return;
                    //eslesenSayisi--;
                    break;
                }
                    //return;
            }
            //elimizdeki alis emri loba yazilmisti. volume'unu azalt:
            int eslesenVolumelerToplami = sumVolumesOfTradeListesi(eslesenTradelerListesi);
            //System.out.println("sumVolumesOfTradeListesi:"+eslesenVolumelerToplami);
            String buyorderIdToChange = eslesenTradelerListesi.get(0).getB_emirNo();
            Order buyOrderToChange = getIndexOfaLineFromLOBList(buyorderIdToChange);
            //eslesen son emrin hacmi-eslesmelerin toplami kadar azalir
            if(buyOrderToChange == null){ //25.02.2019  henuz eslesmenin obur emri lob'da yok, bekle, islem obur emir gelince calistirilacak
            	System.out.println("ordertochange: HENUZ DIGER EMIR GELMEMIS");
            	return;
            }
            buyOrderToChange.setBakiye(buyOrderToChange.getBakiye() - eslesenVolumelerToplami);
            int buyindexToUpdate = getIndexOfaLineFromLOBList(buyOrderToChange);
            LOBLine lobLineToUpdateBuy = lobList.get(buyindexToUpdate);
            lobLineToUpdateBuy.setE(buyOrderToChange);
            lobList.set(buyindexToUpdate, lobLineToUpdateBuy);
            removeLinesFromLOBWhoseOrderVolumeisZero(eslesenTradelerListesi, "buy");
            yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(buyorderIdToChange,"buy");
            //eslesenlerin sonuncusunun sifir olmama durumu:
            if(!lastMatchisZero){
                for (int k = 0; k < eslesenSifirlanmayanTradelerListesi.size(); k++) {
                    String orderIdToChange = eslesenSifirlanmayanTradelerListesi.get(k).getS_emirNo();
                    Order OrderToChange = getIndexOfaLineFromLOBList(orderIdToChange);
                    //lob'da guncellenecek satirin Order kismi olustu. bunu yeniden lobline haline getir:
                    int indexToUpdate = getIndexOfaLineFromLOBList(OrderToChange);
                    if (indexToUpdate != -1) {
                        LOBLine lobLineToUpdate = lobList.get(indexToUpdate);
                        lobLineToUpdate.setAskVol(OrderToChange.getBakiye());
                        volumeAtAsk = OrderToChange.getBakiye();
                        lobList.set(indexToUpdate, lobLineToUpdate);
                        //eslesen satis Orderlerinin sonuncusu da guncellendi. 
                        //simdi bu satirdan itibaren lob dosyasindaki her satirin volumeAtAsk'i degisecek
                        updateLobListAfterRemoval(indexToUpdate);
                        bidPrice = lobList.get(lobList.size() - 1).getBid();
                        volumeAtBid = lobList.get(lobList.size() - 1).getBidVol();
                    }
                }
            }   
        }
    }

    private static int getIndexOfaLineFromLOBList(Order OrderToChange) {
        
        if(OrderToChange==null)
            return -1;
        
        System.out.println("COSKUN: Ordertochange id:" + OrderToChange.getEmirNumarasi());
        System.out.println("COSKUN: Ordertochange time:" + OrderToChange.getTime());
        for (int i = 0; i < lobList.size(); i++) {
            LOBLine lobLine = lobList.get(i);
            if (lobLine.getE().getEmirNumarasi().equalsIgnoreCase(OrderToChange.getEmirNumarasi())) {
                System.out.println("order " + lobLine.getE().getEmirNumarasi() + " found at line " + i);
                return i;
            }
        }
        System.out.println("getIndexOfaLineFromLOBList  NOT FOUND");
        return -1;
    }
    
    private static int checkIfLobLineExists(String orderIdToChange) {
        System.out.println("COSKUN: Ordertochange id:" + orderIdToChange);
        for (int i = 0; i < lobList.size(); i++) {
            LOBLine lobLine = lobList.get(i);
            if (lobLine.getE().getEmirNumarasi().equalsIgnoreCase(orderIdToChange)) {
                System.out.println("order " + lobLine.getE().getEmirNumarasi() + " found at line " + i);
                return i;
            }
        }
        System.out.println("getIndexOfaLineFromLOBList  NOT FOUND");
        return -1;
    }
    
    public static Order getIndexOfaLineFromLOBList(String orderIdToChange) {
        System.out.println("asil aranan: "+orderIdToChange);
        for (int i = 0; i < lobList.size(); i++) {
            LOBLine lobLine = lobList.get(i);
            if (lobLine.getE().getEmirNumarasi().equalsIgnoreCase(orderIdToChange)) {
                System.out.println("order " + lobLine.getE().getEmirNumarasi() + " found at line " + i);
                return lobLine.getE();
            }
        }
        int index = -1;
        for (int i = 0; i < lobListDeleted.size(); i++) {
            LOBLine lobLine = lobListDeleted.get(i);
            if (lobLine.getE().getEmirNumarasi().equalsIgnoreCase(orderIdToChange)) {
                index = i;
            }
        }
        //System.out.println("deleted listesinden buldu, index:"+index);
        //System.out.println("bulunan Order:"+lobListDeleted.get(index).getE().toString());
        if(index>-1)
            return lobListDeleted.get(index).getE();
        return null;
    }
    
    public static ArrayList<Trade> findMatchingLinesAtTradeList(String orderId, Date time, String tip) throws Exception {
        System.out.println("findMatchingLinesAtTradeListFor "+tip+", orderId=" + orderId + " time:" + time);
        ArrayList<Trade> liste = new ArrayList<Trade>();
        ArrayList<Trade> out = new ArrayList<Trade>();
        for (int i = 0; i < TradeList.size(); i++) {
            Trade Trade = TradeList.get(i);
            if("buy".equalsIgnoreCase(tip)){
                //23.01.16 special14check is unnecessary
                if (orderId.equalsIgnoreCase(Trade.getB_emirNo())){// && time.equals(Trade.getTime()) /*&& special1400Check(Trade, orderId, tip)*/){  
                    //checkIfUnprocessedLineExistAbove(Trade,i);   25.02.2019 comment out
                    liste.add(Trade);
                    Trade.setProcessed(true);
                    TradeList.set(i, Trade);
                    System.out.println("-------------------buyer----" + Trade.getB_emirNo() + "-seller--"
                            + Trade.getS_emirNo());
                }
            }
            else if("sell".equalsIgnoreCase(tip)){
              //23.01.16 special14check is unnecessary
                if (orderId.equalsIgnoreCase(Trade.getS_emirNo())){// && time.equals(Trade.getTime()) /*&& special1400Check(Trade, orderId, tip)*/) {
                    //checkIfUnprocessedLineExistAbove(Trade,i);  25.02.2019 comment out
                    liste.add(Trade);
                    Trade.setProcessed(true);
                    TradeList.set(i, Trade);
                    System.out.println("-------------------buyer----" + Trade.getB_emirNo() + "-seller--"
                            + Trade.getS_emirNo());
                }
            }
        }
//        for (int i = 0; i < liste.size(); i++) {
//            Trade Trade = liste.get(i);
//            if("buy".equalsIgnoreCase(tip)){
//                if (checkIfLobLineExists(Trade.getS_emirNo())==-1){  
//                    out.add(Trade);
//                }
//            }
//            else if("sell".equalsIgnoreCase(tip)){
//                if (checkIfLobLineExists(Trade.getB_emirNo())==-1){  
//                    out.add(Trade);
//                }
//            }
//        }
        System.out.println("findMatchingLinesAtTradeListFor "+tip+" "+ liste.size());
        liste.removeAll(out);
        return liste;
    }
    
//    private static boolean special1400Check(Trade Trade, String orderId,
//            String tip) throws Exception {
//        if (Trade.getTime().compareTo(dateFormat.parse(Trade.getDate() + " " + "14:00:00")) == 0) {
//            if ("buy".equalsIgnoreCase(tip)) {
//                int indexOfOther= findIndexOfAnOrderAtLobList(Trade.getSellerOrderId());
//                    if(indexOfOther==-1)
//                        return false;   
//                    else if("CDT".contains(lobList.get(indexOfOther).getE().getOrderType()))
//                        return true;
//                    else{
//                        if(Trade.getSellerOrderId().compareTo(orderId)>0)
//                            return false;
//                    }
//            }else{
//                int indexOfOther= findIndexOfAnOrderAtLobList(Trade.getBuyerOrderId());
//                if(indexOfOther==-1)
//                    return false;   
//                else if("CDT".contains(lobList.get(indexOfOther).getE().getOrderType()))
//                    return true;
//                else{
//                    if(Trade.getBuyerOrderId().compareTo(orderId)>0)
//                        return false;
//                }
//            }
//        }
//        return true;
//    }

    private static void checkIfUnprocessedLineExistAbove(Trade Trade, int i) {
    	//System.out.println("checkIfUnprocessedLineExistAbove, i:"+i);
    	//System.out.println(Trade.toString());
        if(i>0 && Trade.getTime().after(TradeList.get(i-1).getTime())){
            int j=i-1;
            try {
            	//eski hali (todo list 6 oncesi, geriye dogru unprocessed line olarak sadece tek satir bulabiliyordu:
                //while(!TradeList.get(j).getTime().before(TradeList.get(i-1).getTime()) && TradeList.get(j).getTime().after(dateFormat.parse(TradeList.get(j).getDate() + " " + "09:45:00"))){
               	while(j>=0 && TradeList.get(j).getTime().before(TradeList.get(i).getTime()) && TradeList.get(j).getTime().after(dateFormat.parse(TradeList.get(j).getIslemTarihi() + " " + seans))){	
                    if(TradeList.get(j).getIsProcessed()==false){
                        System.out.println("\n\nALARM for the Trade line: "+TradeList.get(j).toString()+"\n\n");
                        updateUnprocessedTrade(TradeList.get(j).getB_emirNo(),"buy", TradeList.get(j));
                        updateUnprocessedTrade(TradeList.get(j).getS_emirNo(),"sell", TradeList.get(j));
                        TradeList.get(j).setProcessed(true);
                    }
                    //System.out.println("\n\nALARM sonrasi kontrol: "+TradeList.get(j-1).toString()+"\n\n");
                    //System.out.println("\n\nALARM sonrasi kontrol i-1: "+TradeList.get(i-1).toString()+"\n\n");
                    j--;
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    private static void checkIfUnprocessedLineExistAtEndOfLOBCreation() {
        System.out.println("checkIfUnprocessedLineExistAtEndOfLOBCreation");
        int i = TradeList.size() - 1;

        for (int j = 0; j < i; j++) {
            if (TradeList.get(i - j).getIsProcessed() == false) {
                updateUnprocessedTrade(TradeList.get(i - j).getB_emirNo(), "buy", TradeList.get(i - j));
                updateUnprocessedTrade(TradeList.get(i - j).getS_emirNo(), "sell", TradeList.get(i - j));
                TradeList.get(i - j).setProcessed(true);
                i--;
            }
        }
    }

    public static int anyInterrupts(int i, Order Order) {
        if (Order.getEmirNumarasi().equalsIgnoreCase(interruptOrder)) {
            System.out.println("------------------------------------------------------------");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ isi biten Order: "
                    + Order.getEmirNumarasi() + " askprice: " + askPrice + " volumeAtAsk: " + volumeAtAsk);
            printFullLineListLobLine(lobList);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ isi biten Order: "
                    + Order.getEmirNumarasi() + " askprice: " + askPrice + " volumeAtAsk: " + volumeAtAsk);
            i = OrderList.size();
            System.out.println("INTERRUPT ARRIVED");
        }
        return i;
    }
    
    public static boolean anyInterruptsByTime(int i, Order Order) throws Exception {
    	
    	/*if(DYNAMIC_LOB_COUNTER>-1 && DYNAMIC_LOB_COUNTER<19800){
    		interruptTime=dateFormat.parse(Order.getDate() + " " + dailyDateFormat.format(interruptTime));
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(interruptTime);
    		cal.add(Calendar.SECOND, DYNAMIC_LOB_COUNTER);
    		interruptTime=cal.getTime();
    	}
    	else{*/
    		//interruptTime=dateFormat.parse(Order.getEmirTarihi()+ " " + dailyDateFormat.format(interruptTime));
    	//}
    		
    	//29.12.15 onceden ustteki gibi bir formatta interruptTime kontrol ediliyormus. Sonra sadece alttaki satir birakilmis.
        //artik sadece string comparison yapacagiz.
        //interruptTime=dateFormat.parse(Order.getEmirTarihi()+ " " + dailyDateFormat.format(interruptTime));	
    		
    	
    	if (Order.getGirisSaati().compareTo(interruptTimeString)>=0) {
            System.out.println("------------------------------------------------------------");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ isi biten Order: "
                    + Order.getEmirNumarasi());
            //printFullLineListLobLine(lobList);
            dynamicLob.add(lobList.get(lobList.size()-1));
            //WriteFile.writeCSVfileLOBAppendLine(lobList.get(lobList.size()-1), "dynamicLob.csv");
            dynamicLobString.add(lobList.get(lobList.size()-1).toStringDynamic());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ isi biten Order: "
                    + Order.getEmirNumarasi() + " askprice: " + askPrice + " volumeAtAsk: " + volumeAtAsk);
            i = OrderList.size();
            System.out.println("TIME INTERRUPT ARRIVED");
            return true;
        }
        return false;
    }
    
    public static int sumVolumesOfTradeListesi(ArrayList<Trade> eslesenTradelerListesi) {
        int sum = 0;
        for (int i = 0; i < eslesenTradelerListesi.size(); i++) {
            sum += eslesenTradelerListesi.get(i).getMiktar();
        }
        return sum;
    }
    
    public static void removeLinesFromLOBWhoseOrderVolumeisZero(List<Trade> eslesenlerListesi, String tip) {
        ArrayList<LOBLine> lobListToDelete = new ArrayList<LOBLine>();
        int startingLineForUpdate = lobList.size();
        String orderId;
        for (int j = 0; j < eslesenlerListesi.size(); j++) {
            if("buy".equalsIgnoreCase(tip))  //buy ile eslesen sellerorderid'leri ara
                orderId = eslesenlerListesi.get(j).getS_emirNo();
            else //sell Tradei
                orderId = eslesenlerListesi.get(j).getB_emirNo(); //sell ile eslesen buyerorderid'leri ara
            for (int i = 0; i < lobList.size(); i++) {
                LOBLine line = lobList.get(i);
                if (orderId.equalsIgnoreCase(line.getE().getEmirNumarasi()) && line.getE().getBakiye()<=0) {
                    if (i < startingLineForUpdate)
                        startingLineForUpdate = i;
                    lobListToDelete.add(line);
                }
            }
        }
        lobList.removeAll(lobListToDelete);
        lobListDeleted.addAll(lobListToDelete);
        System.out.println("startingLineForUpdate:"+startingLineForUpdate);
        updateLobListAfterRemoval(startingLineForUpdate);
    }
    
    public static void yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(
            String orderIdToChange, String tip) {
        System.out.println("yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi");
        
        ArrayList<LOBLine> lobListToDelete = new ArrayList<LOBLine>();
        int startingLineForUpdate = lobList.size();
        for (int i = 0; i < lobList.size(); i++) {
            LOBLine line = lobList.get(i);
            if (orderIdToChange.equalsIgnoreCase(line.getE().getEmirNumarasi())) {
                if (line.getE().getBakiye() == 0) {
                    //System.out.println("yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi "+orderIdToChange);
                    if (i < startingLineForUpdate)
                        startingLineForUpdate = i;
                    lobListToDelete.add(line);
                    lobList.removeAll(lobListToDelete);
                    lobListDeleted.addAll(lobListToDelete);
                    //if("E".equalsIgnoreCase(line.getE().getKtr())){
                        if("sell".equalsIgnoreCase(tip)){
                        	//jan31-2012 ekleme askpricememory null kalabiliyordu, kontrol eklendi
                        	if(askPriceMemory!=null){
                        		askPrice=askPriceMemory;
                        		volumeAtAsk=volumeAtAskMemory;
                        	}
                            //System.out.println("price ve vol guncellendi. price:"+askPrice+" vol:"+volumeAtAsk);
                        }
                        else{
                            bidPrice=bidPriceMemory;
                            volumeAtBid=volumeAtBidMemory;
                        }
                    //}
                }/* else {  //BURAYI 34366 ile ilgili problemle karsilasinca kaldirdik
                    problem suydu, yeni gelen Order hemen lobda son satira yaziliyordu. sonra eslestigi Orderin silme/guncelleme Tradei
                    sonrasi bu Orderde en son satirda oldugundan guncelleniyordu.
                    ardindan yeni gelen Orderin lob satirinin guncellenmesi icin bu icinde bulundugumuz metod calisinca bir kez daha volume azaliyordu.
                    Bu kisimin kaldirilmasiyla, artik sadece yeni gelen Orderin silinmesi gerekiyorsa bu metod ise yarayacak. Guncelleme yapmayacak
                    cunku zaten guncellemesi daha once yapilmis oluyor.
                    if("buy".equalsIgnoreCase(tip)){
                        line.setBid(line.getE().getPrice());
                        line.setBidVol(line.getE().getVolume());
                    }
                    else if("sell".equalsIgnoreCase(tip)){
                        line.setAsk(line.getE().getPrice());
                        line.setAskVol(line.getE().getVolume());
                    }
                    System.out.println("\n\nahanda guncellendi: "+line.toString2());
                    lobList.set(i, line);
                }*/
            }
        }
    }
    
    public static void updateUnprocessedTrade(
            String orderIdToChange, String tip, Trade Trade) {
        ArrayList<LOBLine> lobListToDelete = new ArrayList<LOBLine>();
        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
        eslesenTradelerListesi.add(Trade);
        int startingLineForUpdate = lobList.size();
        for (int i = 0; i < lobList.size(); i++) {
            LOBLine line = lobList.get(i);
            if (orderIdToChange.equalsIgnoreCase(line.getE().getEmirNumarasi())) {
                    if("buy".equalsIgnoreCase(tip)){
                        System.out.println("Trade volume:"+Trade.getMiktar());
                        Order e = line.getE();
                        System.out.println("islenmemis lob satiri:"+line.toString2());
                        e.setBakiye(e.getBakiye()-Trade.getMiktar());
                        line.setE(e);
                        System.out.println("guncellenmis Order: "+e.toString());
                        line.setBidVol(line.getBidVol()-Trade.getMiktar());
                    }
                    else if("sell".equalsIgnoreCase(tip)){
                        Order e = line.getE();
                        e.setBakiye(e.getBakiye()-Trade.getMiktar());
                        line.setE(e);
                        line.setAskVol(line.getAskVol()-Trade.getMiktar());
                    }
                    System.out.println("\nupdateUnprocessedTrade guncelledi: lob line "+line.toString2());
                    lobList.set(i, line);
                    System.out.println("\neslesenTradelerListesi boyut: "+eslesenTradelerListesi.size() + "   tip: "+tip);
                    System.out.println("eslesme - ilk trade: "+eslesenTradelerListesi.get(0).toStringCSV());
                    removeLinesFromLOBWhoseOrderVolumeisZero(eslesenTradelerListesi, swapTip(tip));
                    //yeniGelenOrderinLOBSatirininSilmeVeyaGuncellemesi(orderIdToChange,tip);
                    //updateLobListAfterRemoval(i);
                }
        }
    }
    
    public static void updateLobListAfterRemoval(int start) {
        System.out.println("BU ADIMDAN SONRAKILER ONEMLI   start: "+start);
        if (start == 0) {
            Order ilkOrder = lobList.get(0).getE();
            LOBLine line1 = null;
            if ("B".equalsIgnoreCase(ilkOrder.getAlis_satis())) {
                bidPrice = ilkOrder.getFiyat();
                volumeAtBid = ilkOrder.getBakiye();
                askPrice = new BigDecimal(10000000);
                volumeAtAsk = 0;
                line1 = new LOBLine(ilkOrder, new BigDecimal(10000000), bidPrice, 0, volumeAtBid, 1,"5B");
            } else if ("S".equalsIgnoreCase(ilkOrder.getAlis_satis())) {
                askPrice = ilkOrder.getFiyat();
                volumeAtAsk = ilkOrder.getBakiye();
                bidPrice = new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP);
                volumeAtBid = 0;
                line1 = new LOBLine(ilkOrder, askPrice, bidPrice, volumeAtAsk, 0, 1,"5S");
            }
            lobList.set(0, line1);
            start = 1;
        }
        LOBLine prevLine = lobList.get(start - 1);
        System.out.println("prevline: " + prevLine.toString2());
        bidPrice = prevLine.getBid();
        askPrice = prevLine.getAsk();
        volumeAtBid = prevLine.getBidVol();
        volumeAtAsk = prevLine.getAskVol();
        for (int i = start; i < lobList.size(); i++) {
            LOBLine line = lobList.get(i);
            Order Order = line.getE();
            String aggressiveness = line.getAggressive();
            if ("B".equalsIgnoreCase(Order.getAlis_satis())){

                if (Order.getFiyat().compareTo(bidPrice) < 0) { //case 1.1
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    lobList.set(i, line);
                } else if (Order.getFiyat().compareTo(bidPrice) > 0) { //case 1.2
                    if(Order.getBakiye()!=0){
                        bidPrice = Order.getFiyat();
                        volumeAtBid = Order.getBakiye();
                    }
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    System.out.println("BURADA SET EDILDI:"+line);
                    if (line.getSpread().compareTo(BigDecimal.ZERO) >= 0) {//case 1.2.1
                        lobList.set(i, line);
                    }
                } else {//case 1.3
                    volumeAtBid = volumeAtBid + Order.getBakiye();
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    lobList.set(i, line);
                }
            } else if ("S".equalsIgnoreCase(Order.getAlis_satis())){

                if (Order.getFiyat().compareTo(askPrice) > 0) { //case 2.1
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    lobList.set(i, line);
                } else if (Order.getFiyat().compareTo(askPrice) < 0) { //case 2.2
                    askPrice = Order.getFiyat();
                    volumeAtAsk = Order.getBakiye();
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    if (line.getSpread().compareTo(BigDecimal.ZERO) >= 0) {//case 2.2.1
                        lobList.set(i, line);
                    }
                } else {//case 2.3
                    volumeAtAsk = volumeAtAsk + Order.getBakiye();
                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
                    lobList.set(i, line);
                }
            }
        }
    }

    public static String swapTip(String tip) {
        if("buy".equalsIgnoreCase(tip))
            return "sell";
        return "buy";
    }
    
//    private static void createLOBMorning(ArrayList<Order> sabahKalanValidOrderList) throws Exception {
//        //29.12.15 type'a gore siralama gereksinimi kalmadi
//        //DateTimeOrderIdTypeComparator dateTimeOrderIdComparator = new DateTimeOrderIdTypeComparator();
//        //Collections.sort(sabahKalanValidOrderList, dateTimeOrderIdComparator);
//        ArrayList<LOBLine> lobListForValidMorningOrders = new ArrayList<LOBLine>();
//        Order ilkOrder = sabahKalanValidOrderList.get(0);
//        LOBLine line1 = null;
//        System.out.println("createLOBMorning");
//        if ("A".equalsIgnoreCase(ilkOrder.getAlis_satis())) {
//            bidPrice = ilkOrder.getPrice();
//            volumeAtBid = ilkOrder.getVolume();
//            askPrice = new BigDecimal(100);
//            volumeAtAsk = 0;
//            line1 = new LOBLine(ilkOrder, askPrice, bidPrice, 0, volumeAtBid, 1,"5B");
//        } else if ("S".equalsIgnoreCase(ilkOrder.getOrderType()) || "Q".equalsIgnoreCase(ilkOrder.getOrderType())) {
//            askPrice = ilkOrder.getPrice();
//            volumeAtAsk = ilkOrder.getVolume();
//            bidPrice = new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP);
//            volumeAtBid = 0;
//            line1 = new LOBLine(ilkOrder, askPrice, bidPrice, volumeAtAsk, 0, 1,"5S");
//        }
//        
//        lobListForValidMorningOrders.add(line1);
//        boolean morning = true;
//        for (int i = 1; i < sabahKalanValidOrderList.size(); i++) {
//            Order Order = sabahKalanValidOrderList.get(i);
//            //System.out.println("############################################# simdi gelen Order: " + Order.getOrderId() + " " + Order.getOrderType() + " " + Order.getVolume());
//            //printFullLineListLobLine(lobList);
//            //System.out.println("############################################# simdi gelen Order: " + Order.getOrderId() + " " + Order.getOrderType() + " " + Order.getVolume());
//            if (morning && Order.getTime().after(dateFormat.parse(Order.getDate() + " "+ilkseanssonu))) {
//                System.out.println("\n\n SABAH BITTI clearValidOnlyMorning oncesi lob:\n\n");
//                printFullLineListLobLine(lobListForValidMorningOrders);
//                lobListOfMorning.addAll(lobListForValidMorningOrders);
//                WriteFile.writeCSVfileLOB(lobListOfMorning, "lobMorning.csv");
//                //lobListOfMorningCumulative.addAll(lobListOfMorning);
//                morning = false;
//                clearValidOnlyMorning();
//            }
//            i = anyInterrupts(i, Order);
//            if(interruptTime!=null && anyInterruptsByTime(i, Order)){
//                printFullLineListLobLine(lobListForValidMorningOrders);
//                return;
//            }
//
//            LOBLine line;
//            if ("A".equalsIgnoreCase(Order.getOrderType())
//                    || ("P".equalsIgnoreCase(Order.getOrderType()) || "C".equalsIgnoreCase(Order.getOrderType()))) { //case 1 and case 3
//                //if(bidPrice==null || bidPrice.compareTo(BigDecimal.ZERO)==0)//ilk ALIS emri bu if'e girecek. 
//                //    bidPrice=Order.getPrice();
//                //aggressiveness part:
//                String aggressiveness;
//                aggressiveness=lobList.get(findIndexOfAnOrderAtLobList(Order.getOrderId())).getAggressive();
//
//                if (Order.getPrice().compareTo(bidPrice) < 0) { //case 1.1
//                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if ("E".equalsIgnoreCase(Order.getKtr()))
//                        lobListDeleted.add(line);
//                    else
//                        lobListForValidMorningOrders.add(line);
//                } else if (Order.getPrice().compareTo(bidPrice) > 0) { //case 1.2
//                    int volumeAtBidMemory = volumeAtBid;
//                    BigDecimal bidPriceMemory = bidPrice;
//                    bidPrice = Order.getPrice();
//                    volumeAtBid = Order.getVolume();
//                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {//case 1.2.1
//                        if ("E".equalsIgnoreCase(Order.getKtr()))
//                            lobListDeleted.add(line);
//                        else
//                            lobListForValidMorningOrders.add(line);
//                    } 
//                    else {  //spread kucuk esit 0. mutlaka eslesme.
//                        //ESLESME DURUMU. Alis emri geldi, ustten bir veya daha fazla satis emri ile eslesecek.
//                        //burada 3 durum var, 
//                        //1. eslesen butun Orderlerin ve yeni gelen Orderin silinmesi
//                        //2. yeni Orderin silinmesi, ustte match edennin volumunun azalmasi
//                        //3. ustteki Orderin silinmesi, yeni Orderin volumunun azaltilarak loba yazilmasi
//                        lobListForValidMorningOrders.add(line);
//                        buyMatchingCase(Order, i, aggressiveness);
//                    }
//                } else {//case 1.3  yeni gelen Orderin fiyati bidpricea esit
//                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {
//                        if ("E".equalsIgnoreCase(Order.getKtr())) {
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListDeleted.add(line);
//                        } else {
//                            volumeAtBid = volumeAtBid + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                        }
//                    }
//                    else{//spread kucuk esit 0
//                        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
//                        eslesenTradelerListesi = findMatchingLinesAtTradeList(Order.getOrderId(), Order.getTime(),"buy");
//                        //matchinglerden gelen sey 0 tane mi degil mi kontrolu
//                        //sifir degilse::
//                        System.out.println("\n\nCROSS DURUMU....\n\n");
//                        if(eslesenTradelerListesi.size()>0){
//                            volumeAtBid = volumeAtBid + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                            buyMatchingCase(Order, i, aggressiveness);
//                        }
//                        //sifirsa:
//                        else{
//                            volumeAtBid = volumeAtBid + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                        }   
//                    }
//                }
//            } else if (("S".equalsIgnoreCase(Order.getOrderType()) || "Q".equalsIgnoreCase(Order.getOrderType()))
//                    || ("R".equalsIgnoreCase(Order.getOrderType()) || "L".equalsIgnoreCase(Order.getOrderType()))
//                    || ("D".equalsIgnoreCase(Order.getOrderType()) || "T".equalsIgnoreCase(Order.getOrderType()))) { //case 2 and case 4
//                String aggressiveness;
//                aggressiveness=lobList.get(findIndexOfAnOrderAtLobList(Order.getOrderId())).getAggressive();
//
//                if (Order.getPrice().compareTo(askPrice) > 0) { //case 2.1
//                    if (bidPrice == null) {
//                        line = new LOBLine(Order, askPrice, BigDecimal.ZERO, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    } else
//                        line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if ("E".equalsIgnoreCase(Order.getKtr()))
//                        lobListDeleted.add(line);
//                    else
//                        lobListForValidMorningOrders.add(line);
//                } else if (Order.getPrice().compareTo(askPrice) < 0) { //case 2.2
//                    BigDecimal askPriceMemory = askPrice;
//                    int volumeAtAskMemory = volumeAtAsk;
//                    askPrice = Order.getPrice();
//                    volumeAtAsk = Order.getVolume();
//                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {//case 2.2.1
//                        if ("E".equalsIgnoreCase(Order.getKtr()))
//                            lobListDeleted.add(line);
//                        else
//                            lobListForValidMorningOrders.add(line);
//                    } else {  //spread kucuk veya esittir 0
//                        //yeni gelen satis emri sonrasi eslesme durumu. ustten bir veya daha fazla alis emri ile eslesecek.
//                        //burada 3 durum var, 
//                        //1. eslesen iki Orderin de silinmesi
//                        //2. yeni Orderin silinmesi, ustte match edenin volumunun azalmasi
//                        //3. ustteki Orderin silinmesi, yeni Orderin volumunun azaltilarak loba yazilmasi
//                        lobListForValidMorningOrders.add(line);
//                        sellMatchingCase(Order, i, aggressiveness);
//                    }
//                } else {//case 2.3
//                    line = new LOBLine(Order, askPrice, bidPrice, volumeAtAsk, volumeAtBid, i, aggressiveness);
//                    if (line.getSpread().compareTo(BigDecimal.ZERO) > 0) {
//                        if ("E".equalsIgnoreCase(Order.getKtr())) {
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListDeleted.add(line);
//                        } else {
//                            volumeAtAsk = volumeAtAsk + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                        }
//                    }else{ //spread kucuk esit 0
//                        System.out.println("\n\nCROSS DURUMU..sellmatchingcase..\n\n");
//                        ArrayList<Trade> eslesenTradelerListesi = new ArrayList<Trade>();
//                        eslesenTradelerListesi = findMatchingLinesAtTradeList(Order.getOrderId(), Order.getTime(),"sell");
//                        
//                        if(eslesenTradelerListesi.size()>0){
//                            volumeAtAsk = volumeAtAsk + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                            sellMatchingCase(Order, i, aggressiveness);
//                        }
//                        //sifirsa:
//                        else{
//                            volumeAtAsk = volumeAtAsk + Order.getVolume();
//                            line = new LOBLine(Order, askPrice, bidPrice,
//                                    volumeAtAsk, volumeAtBid, i, aggressiveness);
//                            lobListForValidMorningOrders.add(line);
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("\n\nBURASI*****************************");
//        printFullLineListLobLine(lobListForValidMorningOrders);
//        System.out.println("***********************************\n");
//        lobList.clear();
//        lobList.addAll(lobListForValidMorningOrders);
//    }
    
//    private static void finalAdditionsToDynamicLOBString() throws ParseException {
//    	//int target_half=9001;
//    	int target_half=TARGET/2;
//        //for(int i=0; i<9001;i++){
//    	for(int i=0; i<target_half;i++){
//         Date morningTime =dailyDateFormat.parse("09:30:00");
//         morningTime=dateFormat.parse(dynamicLob.get(i).getE().getEmirTarihi() + " " + dailyDateFormat.format(morningTime));
//         morningTime.setTime(morningTime.getTime()+(i*1000));
//         int numberOfTrades = countTradesUntilGivenTime(morningTime);
//         BigDecimal tradePrice=findTradePriceAtTimeOfGivenOrderForFinalAddition(morningTime,i);
//         int volumeTraded=findVolumeTradedAtTimeOfGivenOrder(morningTime);
//         int numberOfOrders=countOrdersUntilGivenTime(morningTime);
//         int div_index=dynamicLobString.get(i).indexOf("%");
//         if(div_index==-1)
//             dynamicLobString.set(i, dynamicLobString.get(i)+numberOfTrades+";"+tradePrice+";"+volumeTraded+";"+numberOfOrders+";"+dailyDateFormat.format(morningTime)+";"+add2b2sColumns(morningTime));
//         else
//             dynamicLobString.set(i, dynamicLobString.get(i).substring(0,div_index)+numberOfTrades+";"+tradePrice+";"+volumeTraded+";"+numberOfOrders+";"+dailyDateFormat.format(morningTime)+";"+dynamicLobString.get(i).substring(div_index+1));
//     }
//        
//     //for(int i=0; i<TARGET-9001;i++){
//    	for(int i=0; i<TARGET-target_half;i++){
//         Date morningTime =dailyDateFormat.parse("14:00:00");
//         morningTime=dateFormat.parse(dynamicLob.get(i).getE().getEmirTarihi() + " " + dailyDateFormat.format(morningTime));
//         morningTime.setTime(morningTime.getTime()+(i*1000));
//         int numberOfTrades = countTradesUntilGivenTime(morningTime);
//         BigDecimal tradePrice=findTradePriceAtTimeOfGivenOrderForFinalAddition(morningTime,9001+i);
//         int volumeTraded=findVolumeTradedAtTimeOfGivenOrder(morningTime);
//         int numberOfOrders=countOrdersUntilGivenTime(morningTime);
//         int div_index=dynamicLobString.get((target_half)+i).indexOf("%");
//         if(div_index==-1)
//             dynamicLobString.set((target_half)+i, dynamicLobString.get((target_half)+i)+numberOfTrades+";"+tradePrice+";"+volumeTraded+";"+numberOfOrders+";"+dailyDateFormat.format(morningTime)+";"+add2b2sColumns(morningTime));
//         else
//             dynamicLobString.set((target_half)+i, dynamicLobString.get((target_half)+i).substring(0,div_index)+numberOfTrades+";"+tradePrice+";"+volumeTraded+";"+numberOfOrders+";"+dailyDateFormat.format(morningTime)+";"+dynamicLobString.get((target_half)+i).substring(div_index+1));
//     }
//    	
//    	//midpoint degerinin 15 ve 30 dakika sonraki hallerini en saga ekliyoruz
//    	//ogleden once ve aksam son 15 ve son 30 dakikada, veri olmayan yerlere N/A koyuyoruz.
//    	for(int i=0; i<TARGET-target_half-1800;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+dynamicLob.get(i+900).getMidpoint()+";"+dynamicLob.get(i+1800).getMidpoint());
//    	}
//    	for(int i=TARGET-target_half-1800; i<TARGET-target_half-900;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+dynamicLob.get(i+900).getMidpoint()+";"+"N/A");
//    	}
//    	for(int i=TARGET-target_half-900; i<TARGET-target_half;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+"N/A"+";"+"N/A");
//    	}
//    	for(int i=TARGET-target_half; i<TARGET-1800;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+dynamicLob.get(i+900).getMidpoint()+";"+dynamicLob.get(i+1800).getMidpoint());
//    	}
//    	for(int i=TARGET-1800; i<TARGET-900;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+dynamicLob.get(i+900).getMidpoint()+";"+"N/A");
//    	}
//    	for(int i=TARGET-900; i<TARGET;i++){
//    		dynamicLobString.set(i, dynamicLobString.get(i)+"N/A"+";"+"N/A");
//    	}
//     }

     private static String add2b2sColumns(Date currentTime) {
         Hashtable<BigDecimal,Integer> htSell = new Hashtable<BigDecimal, Integer>();
         Hashtable<BigDecimal,Integer> htBuy = new Hashtable<BigDecimal, Integer>();
         String s="";
         for (int i = 0; i < lobList.size(); i++) {
             Order e = lobList.get(i).getE();
             if (!e.getTime().after(currentTime)) {

                 if ("B".equalsIgnoreCase(e.getAlis_satis())){
                     if (htBuy.containsKey(e.getFiyat())) {
                         htBuy.put(e.getFiyat(), htBuy.get(e.getFiyat()) + e.getBakiye());
                     } else {
                         htBuy.put(e.getFiyat(), e.getBakiye());
                     }
                 } else if ("S".equalsIgnoreCase(e.getAlis_satis())){
                     if (htSell.containsKey(e.getFiyat())) {
                         htSell.put(e.getFiyat(), htSell.get(e.getFiyat()) + e.getBakiye());
                     } else {
                         htSell.put(e.getFiyat(), e.getBakiye());
                     }
                 }
             }
         }
         //System.out.println("-----");
         Vector<BigDecimal> vSell = new Vector<BigDecimal>(htSell.keySet());
         Collections.sort(vSell);
         int i=3;
         boolean thisisfirst=true;
         for (Enumeration<BigDecimal> e = vSell.elements(); e.hasMoreElements();) {
             BigDecimal key = (BigDecimal)e.nextElement();
             Integer val = (Integer)htSell.get(key);
             if(i<=11 && !thisisfirst){
                 s+=key+";"+val+";";
                 i++;
             }
             thisisfirst=false;
             //System.out.println("SELL Key: " + key + "     Val: " + val);
           }
         for(int k=i;k<=11;k++){
             s+=0+";"+0+";";
         }
         i=3;
         //System.out.println("*");
         Vector<BigDecimal> vBuy = new Vector<BigDecimal>(htBuy.keySet());
         Collections.sort(vBuy);
         Collections.reverse(vBuy);
         thisisfirst=true;
         for (Enumeration<BigDecimal> e = vBuy.elements(); e.hasMoreElements();) {
             BigDecimal key = (BigDecimal)e.nextElement();
             Integer val = (Integer)htBuy.get(key);
             if(i<=11 && !thisisfirst){
                 s+=key+";"+val+";";
                 i++;
             }
             thisisfirst=false;
             //System.out.println("BUY Key: " + key + "     Val: " + val);
           }
         for(int k=i;k<=11;k++){
             s+=0+";"+0+";";
         }
//         try {
//             Thread.currentThread();
//             Thread.sleep(200);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//         if(DYNAMIC_LOB_COUNTER==2)
             return s;
 //        return "";
     }

//     private static void fixDynamicLOBAfterLunch(int i) throws Exception{
//         
//         Order Order=OrderList.get(i);
//         System.out.println("\nfixDynamicLOBAfterLunch, ogleden sonra Order:"+Order);
//         System.out.println("\nsu anda lobun son satiri:"+lobList.get(lobList.size()-1));
//         String eveningTimeString = "14:00:00";
//         Date eveningTime = null;
//         try {
//             eveningTime = dailyDateFormat.parse(eveningTimeString);
//         } catch (ParseException e) {
//             e.printStackTrace();
//         }
//         try {
//             eveningTime=dateFormat.parse(Order.getEmirTarihi() + " " + dailyDateFormat.format(eveningTime));
//         } catch (ParseException e) {
//             e.printStackTrace();
//         }
//         int dif=(int)(Order.getTime().getTime()-eveningTime.getTime())/1000;
//         System.out.println("fixDynamicLOBAfterLunch, ogleden sonra icin fark:"+dif);
//         //System.out.println(Order.getTime());
//         //System.out.println(Order.getTime().getTime());
//         //System.out.println(eveningTime.getTime());
//         for(int k=0;k<dif && dif<3000;k++){
//             //dynamicLob.add(lobList.get(lobList.size()-1));
//             
//             LOBLine line=lobList.get(lobList.size()-1);
//             //if(k==0){
//                 line.setCountLOBLine(countLOBLinesAtTimeOfGivenOrder(Order));
//                 line.setCountLOBLine_BUY(countLOBLines_BUY_AtTimeOfGivenOrder(Order));
//                 line.setCountLOBLine_SELL(countLOBLines_SELL_AtTimeOfGivenOrder(Order));
//                 line.setTradePrice(findTradePriceAtTimeOfGivenOrder(Order.getTime()));
//                 line.setVolumeTraded(findVolumeTradedAtTimeOfGivenOrder(Order.getTime()));
//                 line.setExtra2b2sdata(add2b2sColumns(Order.getTime()));
//             //}
//             dynamicLob.add(line);
//             //WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
//             dynamicLobString.add(line.toStringDynamic()+"%"+line.getExtra2b2sdata());
//         }
//     }
     
     private static void fixDynamicLOBEndOfMorning(int i) throws Exception {
     	//sabah sonu
     	Order Order=OrderList.get(i-1);
     	
     	System.out.println("ogleden once Order:"+Order);
     	
     	String morningTimeString="";
     	morningTimeString = ilkseanssonu;
     	
//     	Date morningTime = null;
// 		try {
// 			morningTime = dailyDateFormat.parse(morningTimeString);
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
// 		try {
// 			morningTime=dateFormat.parse(Order.getEmirTarihi() + " " + dailyDateFormat.format(morningTime));
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
 		
 	Calendar morningTime = Calendar.getInstance();
 	morningTime.setTime(Order.getEmirTarihi());
 	morningTime.set(Calendar.HOUR, Integer.parseInt(morningTimeString.substring(0,2)));
 	morningTime.set(Calendar.MINUTE, Integer.parseInt(morningTimeString.substring(3,5)));
 	morningTime.set(Calendar.SECOND, Integer.parseInt(morningTimeString.substring(6,8)));	
 	
         int dif=(int)(morningTime.getTimeInMillis()-Order.getTime().getTime())/1000;
         System.out.println("fixDynamicLOBEndOfMorning, ogleden once icin fark:"+dif);
         //System.out.println(Order.getTime());
         //System.out.println(Order.getTime().getTime());
         //System.out.println(morningTime.getTime());
         for(int k=0;k<dif && dif<3000;k++){
         	LOBLine line = dynamicLob.get(dynamicLob.size()-1);
             dynamicLob.add(line);
         	//WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
             line.setExtra2b2sdata(add2b2sColumns(Order.getTime()));
             //dynamicLobString.add(line.toStringDynamic());
             dynamicLobString.add(line.toStringDynamic()+"%"+line.getExtra2b2sdata());
         }
 	}

//     private static void fixDynamicLOB() throws Exception {
//     	//sabah saatleri
//     	Order Order=dynamicLob.get(0).getE();
//     	String morningTimeString="";
//     	morningTimeString = "09:30:00";
//     	Date morningTime = null;
// 		try {
// 			morningTime = dailyDateFormat.parse(morningTimeString);
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
// 		try {
// 			morningTime=dateFormat.parse(Order.getEmirTarihi() + " " + dailyDateFormat.format(morningTime));
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
//         int dif=(int)(Order.getTime().getTime()-morningTime.getTime())/1000;
//         System.out.println("fixDynamicLOB, sabah icin fark:"+dif);
//         for(int k=0;k<dif && dif<3000;k++){
//         	dynamicLob.add(k, dynamicLob.get(0));
//         	dynamicLobString.add(k, dynamicLob.get(0).toStringDynamic());
//         	//WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
//         }	
//     	
//         //aksam saatleri
//         
//         LOBLine line = dynamicLob.get(dynamicLob.size()-1);
//         LOBLine lineFromLOB = lobList.get(lobList.size()-1);
//         Order=line.getE();
//         lineFromLOB.setCountLOBLine(countLOBLinesAtTimeOfGivenOrder(Order));
//         lineFromLOB.setCountLOBLine_BUY(countLOBLines_BUY_AtTimeOfGivenOrder(Order));
//         lineFromLOB.setCountLOBLine_SELL(countLOBLines_SELL_AtTimeOfGivenOrder(Order));
//         lineFromLOB.setTradePrice(findTradePriceAtTimeOfGivenOrder(Order.getTime()));
//         lineFromLOB.setVolumeTraded(findVolumeTradedAtTimeOfGivenOrder(Order.getTime()));
//         String eveningTimeString = "17:00:00";
//         if(TARGET==18002)
//             eveningTimeString = "16:30:00";
//     	Date eveningTime = null;
// 		try {
// 			eveningTime = dailyDateFormat.parse(eveningTimeString);
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
// 		try {
// 			eveningTime=dateFormat.parse(Order.getDate() + " " + dailyDateFormat.format(eveningTime));
// 		} catch (ParseException e) {
// 			e.printStackTrace();
// 		}
//         dif=(int)(eveningTime.getTime()-Order.getTime().getTime())/1000;
//         System.out.println("fixDynamicLOB, aksam icin fark:"+dif);
//         //System.out.println(Order.getTime());
//         //System.out.println(Order.getTime().getTime());
//         //System.out.println(eveningTime.getTime());
//         for(int k=0;k<dif && dif<3000 && dynamicLob.size()<TARGET;k++){
//         	dynamicLob.add(lineFromLOB);
//         	//WriteFile.writeCSVfileLOBAppendLine(line, "dynamicLob.csv");
//         	dynamicLobString.add(lineFromLOB.toStringDynamic());
//         }	
// 	}
     
     public static boolean orderTimeComparator (Order order1, Order order2){
         if(order1.getEmirTarihi().before(order2.getEmirTarihi()))
             return true;
         else if(order1.getEmirTarihi().after(order2.getEmirTarihi()))
             return false;
         else{
             if(order1.getGirisSaati().compareTo(order2.getGirisSaati())<1)
                 return true;
             else
                 return false;
         }
     } 
}