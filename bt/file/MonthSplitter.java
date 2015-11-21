package bt.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bt.domain.Order;



public class MonthSplitter {
    
    static ArrayList<Order> orderListFullMonth = new ArrayList<Order>();
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static List<String> menkulKiymetListesi = new ArrayList<String> ();
    static DateFormat dateFormatForFileName = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);

    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //int i = 1;
        String emirFile = "orders_1999_KES_8.csv";
        //String islemFile = "VESTL1112islem.csv";

        readOrderMonthCSVFile(emirFile);
        
        System.out.println(orderListFullMonth.size());
        //readCSVfileIslem(islemFile);
        //TimeComparator dateComparator = new TimeComparator();
        //TimeComparatorForIslem comparatorForIslem = new TimeComparatorForIslem();
        //Collections.sort(emirList, dateComparator);
        //Collections.sort(islemList, comparatorForIslem);

        //once her bir menkul kiymet icin bir aylik liste olustur:
        for (String menkulKiymet : menkulKiymetListesi) {
            splitOrdersToMenkulKiymetList(menkulKiymet);
        }
        
//        while(orderListFullMonth.size()>0){
//            
//            
//                //splitEmirlist(i);
//                i++;
//        }       
//        i=1;
//        while(islemList.size()>0){
//                splitIslemlist(i);
//                i++;
//        }
        System.out.println("durduk");

    }
    
    private static void splitOrdersToMenkulKiymetList(String menkulKiymet) throws Exception {
        ArrayList<Order> singleList = new ArrayList<Order>();
        for(int i=0;i<orderListFullMonth.size();i++){
            Order o=orderListFullMonth.get(i);
            if(!(o.getDurum().compareTo("W")==0 && o.getMiktar()==o.getBakiye())){
                if(o.getMenkulKiymet().compareTo(menkulKiymet)==0)
                    singleList.add(o);
            }
        }
        
        int i=1;
        while(singleList.size()>0){
            splitEmirlist(i,singleList);
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
            Order e = convertLineToEmir(line);
            orderListFullMonth.add(row, e);
            row++;
        }
        bufRdr.close();
        
        System.out.println("menkulkiymet list size:"+menkulKiymetListesi.size());
    }
    
    public static Order convertLineToEmir(String line) {
        Order e = new Order();
        bt.utils.Parse parser = new bt.utils.Parse(line, ",");

        if (line != null) {
            String tmp = parser.nextToken();
            while (tmp != null) {
                e.setEmirNumarasi(tmp);
                
                tmp = parser.nextToken();
                Date emirTarihi = null;
                try {
                    emirTarihi = dateFormat.parse(tmp);
                    e.setEmirTarihi(emirTarihi);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                
                tmp = parser.nextToken();
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
                e.setDurum(tmp);
                
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
                
                tmp = parser.nextToken();
                //System.out.println("--"+tmp);
                if(tmp!=null && !tmp.isEmpty()){
                    BigDecimal takasFiyati = new BigDecimal(tmp);
                    e.setTakasFiyati(takasFiyati);
                }else
                    e.setTakasFiyati(null);
                
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
}
