package bt.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import bt.domain.LOBLine;
import bt.domain.Order;
import bt.domain.Trade;

public class WriteFile {

	public static void writeCSVfileEmirDaily(ArrayList<Order> emirList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Order emir : emirList) {
            	out.write(emir.toStringCSV() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
	}

    public static void writeCSVfileTradeDaily(ArrayList<Trade> tradeListOneDay, String fileName) {
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Trade t : tradeListOneDay) {
                out.write(t.toStringCSV() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
        
    }
	
    ////////////////--------------transfer from old WriteFile class -----------------------
    
    public static void writeCSVfileLOB(ArrayList<LOBLine> lobList) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter("lob.csv");
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (LOBLine line : lobList) {
                out.write(line.toString2() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
        }
        
        public static void writeCSVfileLOB(ArrayList<LOBLine> incominglobList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (LOBLine line : incominglobList) {
                out.write(line.toString2() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        public static void writeCSVfileLOBAppend(ArrayList<LOBLine> incominglobList, String fileName) throws Exception{
            try{
                // Create file 
                FileWriter fstream = new FileWriter(fileName,true);
                BufferedWriter out = new BufferedWriter(fstream);
                //lobList.remove(0);
                for (LOBLine line : incominglobList) {
                    out.append(line.toString2() + "\n");
                }
                out.close();
            }catch (Exception e){//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        public static void writeCSVfileLOBAppendLine(LOBLine line, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName,true);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            out.append(line.toString2() + "\n");
            out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
        
        public static void writeCSVfileLOBDynamic(ArrayList<LOBLine> incominglobList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (LOBLine line : incominglobList) {
                out.write(line.toStringDynamic() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        public static void writeCSVfileLOBDynamicAppend(ArrayList<LOBLine> incominglobList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName,true);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (LOBLine line : incominglobList) {
                out.append(line.toStringDynamic() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        
        public static void writeCSVfileString(List<String> incomingList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (String line : incomingList) {
                out.write(line + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        public static void writeCSVfileStringAppend(List<String> incomingList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName,true);
            BufferedWriter out = new BufferedWriter(fstream);
            //lobList.remove(0);
            for (String line : incomingList) {
                out.append(line + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        public static void writeCSVfileOrder(ArrayList<Order> emirList) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter("emir_afterManipulations.csv");
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Order emir : emirList) {
                out.write(emir.toStringCSV() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
        }
        public static void writeCSVfileOrder(ArrayList<Order> emirList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Order emir : emirList) {
                out.write(emir.toString() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
        }
        
        public static void writeCSVfileOrderAppend(ArrayList<Order> emirList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName, true);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Order emir : emirList) {
                out.append(emir.toString() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        public static void writeCSVfileTradeAppend(ArrayList<Trade> islemList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName, true);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Trade islem : islemList) {
                out.append(islem.toString() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
    }
        
        
        public static void writeCSVfileTrade(ArrayList<Trade> islemList, String fileName) throws Exception{
        try{
            // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            //emirList.remove(0);
            for (Trade islem : islemList) {
                out.write(islem.toStringCSV() + "\n");
            }
            out.close();
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
    }
        }
    
}
