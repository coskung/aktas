package bt.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import bt.domain.Order;

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
	
}
