package bt.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bt.domain.LOBLine;
import bt.domain.Order;
import bt.domain.Trade;

public class islemLOBComposer {

	static ArrayList<LOBLine> dynamicLobList = new ArrayList<LOBLine>();
	static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm:ss", Locale.ENGLISH);
	static DateFormat basicDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
	static DateFormat dailyDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
	static DateFormat dailyConcatDateFormat = new SimpleDateFormat("HHmmss", Locale.ENGLISH);
	static int timeDifference = 15; // assign it 1 or 5 or 15
	static String sonTrade = "17:00:00";
	// static int lineCount=19802;
	static int lineCount = 21602;

	public static void composeTradeAndDynamicLOB(ArrayList<Trade> islemList, String dynamicLOBfileName, String fileName, ArrayList<Order> emirList,
			int fileIndex, Trade lastTradeOfYesterday) throws Exception {
		try {
			// Create file
			FileWriter fstream = new FileWriter(fileName, true);
			BufferedWriter out = new BufferedWriter(fstream);
			StringBuilder islemTime = new StringBuilder(dailyDateFormat.format(islemList.get(islemList.size() - 1).getTime()));
			sonTrade = islemTime.toString();
			readCSVfileDynamicLOB(dynamicLOBfileName);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("dynamic lob list size:" + dynamicLobList.size());
			System.out.println("islem size:" + islemList.size());
			// addTradeDirectionColumns(fileIndex);
			// emirList.remove(0);
			int islemindex = 0;
			for (Trade islem : islemList) {
				System.out.println("islem:" + islem.toStringCSV());
				// islem.setEski_yeni(getLaterOrderInfo(islem, emirList));
				// out.write(islem.toStringCSV() +
				// getDynamicLobPart(islem,fileIndex)+"\n");
				out.append(islem.toStringCSV() + getDynamicLobPart(islem, fileIndex, islemindex, islemList, lastTradeOfYesterday) + "\n");
				islemindex++;
			}
			out.close();
			dynamicLobList.clear();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static ArrayList<Order> findOrdersWithThisBuyerIdOrSellerId(String buyerOrderId, String sellerOrderId, ArrayList<Order> emirList) {
		ArrayList<Order> list = new ArrayList<Order>();
		for (int i = 0; i < emirList.size(); i++) {
			Order emir = emirList.get(i);
			if (buyerOrderId.equalsIgnoreCase(emir.getEmirNumarasi()) || sellerOrderId.equalsIgnoreCase(emir.getEmirNumarasi())) {
				list.add(emir);
			}
		}
		return list;
	}

	// private static String getLaterOrderInfo(Trade islem,
	// ArrayList<Order> emirList) {
	// ArrayList<Order> emirlerlistesi = new ArrayList<Order>();
	// emirlerlistesi = findOrdersWithThisBuyerIdOrSellerId(
	// islem.getBuyerOrderId(), islem.getSellerOrderId(), emirList);
	// for (int i = 0; i < emirlerlistesi.size(); i++) {
	// Order emir = emirlerlistesi.get(i);
	// if (emir.getTime().compareTo(islem.getTime()) == 0) {
	// if ("ACP".contains(emir.getOrderType()))
	// return "A";
	// else if ("SDRQTL".contains(emir.getOrderType()))
	// return "S";
	//
	// }
	// }
	// // Order latest=emirlerlistesi.get(0);
	// // for (int i = 0; i < emirlerlistesi.size(); i++) {
	// // Order emir = emirlerlistesi.get(i);
	// // if (emir.getTime().compareTo(islem.getTime())<0 &&
	// // emir.getTime().compareTo(latest.getTime())>0) {
	// // latest=emir;
	// //
	// // }
	// // }
	// if (islem.getBuyerOrderId().compareTo(islem.getSellerOrderId()) < 0)
	// return "S";
	// else
	// return "A";
	// }

	public static String getDynamicLobPart(Trade islem, int fileIndex, int islemindex, ArrayList<Trade> islemList, Trade lastTradeOfYesterday) {

		System.out.println("getDynamicLobPart, islem zamani:" + islem.getTime().toString());
		StringBuilder islemTime = new StringBuilder(dailyConcatDateFormat.format(islem.getTime()));
		System.out.println("getDynamicLobPart, islem zamani converted:" + islemTime.toString());

		LOBLine line0 = getCorrespondingDynamicLobFromList(islemTime.toString(), 0, fileIndex);
		// LOBLine line1 = getCorrespondingDynamicLobFromList(islemTime.toString(), 1, fileIndex);
		// LOBLine line5 = getCorrespondingDynamicLobFromList(islemTime.toString(), 5, fileIndex);
		System.out.println("line0" + line0);

		String t = getOneDynamicLobLine(line0);

		System.out.println("t" + t);
		// String tminus1 = getOneDynamicLobLine(line1);
		// String tminus5 = getOneDynamicLobLine(line5);

		// t = t + getTradeDirectionColumns(line0, islem, 0, fileIndex, islemindex, islemList, lastTradeOfYesterday);
		// tminus1 = tminus1 + getTradeDirectionColumns(line1, islem, 1, fileIndex, islemindex, islemList,
		// lastTradeOfYesterday);
		// tminus5 = tminus5 + getTradeDirectionColumns(line5, islem, 5, fileIndex, islemindex, islemList,
		// lastTradeOfYesterday);

		// String secondToTenBestColumns = getSecondToTenBestColumns(line0);
		//
		// if (timeDifference == 15)
		// return t + tminus1 + tminus5 + secondToTenBestColumns;
		// else if (timeDifference == 1)
		// return t + tminus1 + secondToTenBestColumns;
		// else if (timeDifference == 5)
		// return t + tminus5 + secondToTenBestColumns;
		// else
		// return t + secondToTenBestColumns;

		return t;

	}

	// public static String getSecondToTenBestColumns(LOBLine line1) {
	// String line1addition = "";
	//
	// line1addition += line1.getSecondBestSellPrice() + ";";
	// line1addition += line1.getSecondBestSellVolume() + ";";
	// line1addition += line1.getThirdBestSellPrice() + ";";
	// line1addition += line1.getThirdBestSellVolume() + ";";
	// line1addition += line1.getFourthBestSellPrice() + ";";
	// line1addition += line1.getFourthBestSellVolume() + ";";
	// line1addition += line1.getFifthBestSellPrice() + ";";
	// line1addition += line1.getFifthBestSellVolume() + ";";
	// line1addition += line1.getSixthBestSellPrice() + ";";
	// line1addition += line1.getSixthBestSellVolume() + ";";
	// line1addition += line1.getSeventhBestSellPrice() + ";";
	// line1addition += line1.getSeventhBestSellVolume() + ";";
	// line1addition += line1.getEighthBestSellPrice() + ";";
	// line1addition += line1.getEighthBestSellVolume() + ";";
	// line1addition += line1.getNinthBestSellPrice() + ";";
	// line1addition += line1.getNinthBestSellVolume() + ";";
	// line1addition += line1.getTenthBestSellPrice() + ";";
	// line1addition += line1.getTenthBestSellVolume() + ";";
	//
	// line1addition += line1.getSecondBestBuyPrice() + ";";
	// line1addition += line1.getSecondBestBuyVolume() + ";";
	// line1addition += line1.getThirdBestBuyPrice() + ";";
	// line1addition += line1.getThirdBestBuyVolume() + ";";
	// line1addition += line1.getFourthBestBuyPrice() + ";";
	// line1addition += line1.getFourthBestBuyVolume() + ";";
	// line1addition += line1.getFifthBestBuyPrice() + ";";
	// line1addition += line1.getFifthBestBuyVolume() + ";";
	// line1addition += line1.getSixthBestBuyPrice() + ";";
	// line1addition += line1.getSixthBestBuyVolume() + ";";
	// line1addition += line1.getSeventhBestBuyPrice() + ";";
	// line1addition += line1.getSeventhBestBuyVolume() + ";";
	// line1addition += line1.getEighthBestBuyPrice() + ";";
	// line1addition += line1.getEighthBestBuyVolume() + ";";
	// line1addition += line1.getNinthBestBuyPrice() + ";";
	// line1addition += line1.getNinthBestBuyVolume() + ";";
	// line1addition += line1.getTenthBestBuyPrice() + ";";
	// line1addition += line1.getTenthBestBuyVolume() + ";";
	//
	// line1addition += line1.getMidpointAfter15() + ";";
	// line1addition += line1.getMidpointAfter30() + ";";
	//
	// return line1addition;
	//
	// }

	public static String getOneDynamicLobLine(LOBLine line1) {
		String line1addition = "";
		line1addition += line1.getE().getAlis_satis() + ";";
		line1addition += line1.getE().getGirisSaati() + ";";
		line1addition += line1.getBid() + ";";
		line1addition += line1.getAsk() + ";";
		line1addition += line1.getSpread() + ";";
		line1addition += line1.getBidVol() + ";";
		line1addition += line1.getAskVol() + ";";
		line1addition += line1.getMidpoint() + ";";

		/*
		 * line1addition+=line1.getTickRule()+";"; line1addition+=line1.getReverseTickRule()+";";
		 * line1addition+=line1.getQuoteRule()+";"; line1addition+=line1.getAtTheQuoteRule()+";";
		 * line1addition+=line1.getLr()+";"; line1addition+=line1.getEmoRule()+";";
		 */

		return line1addition;
	}

	private static LOBLine getCorrespondingDynamicLobFromList(String time, int diff, int fileIndex) {
		System.out.println("getCorrespondingDynamicLobFromList");
		// System.out.println("diff:" + diff);
		// System.out.println("fileindex:" + fileIndex);

		int start = 0;
		start = (fileIndex - 1) * lineCount;

		// System.out.println("start:" + start);
		// System.out.println("dyn list size:" + dynamicLobList.size());
		// System.out.println("time:" + time);

		for (int i = start; i < dynamicLobList.size(); i++) {
			LOBLine dline = dynamicLobList.get(i);
			// System.out.println(dline.getE().getGirisSaati().substring(0, 6)); // 133254537
			if (dline.getE().getGirisSaati().substring(0, 6).compareTo(time) >= 0) {
				// System.out.println("dline:" + dline.toStringDynamic());
				if (i > diff) {
					// if(diff==0)
					// return dynamicLobList.get(i);
					// return
					// fixTradeDirectionColumns(dynamicLobList.get(i),i,diff,
					// fileIndex);
					return dynamicLobList.get(i - diff);
				} else
					return null;
			}
		}
		return null;
	}

	// private static int getDynamicLobFromIndex(
	// String time, int diff, int fileIndex) {
	// // System.out.println("getCorrespondingDynamicLobFromList");
	//
	// int start = 0;
	// start = (fileIndex - 1) * lineCount;
	//
	// for (int i = start; i < dynamicLobList.size(); i++) {
	// LOBLine dline = dynamicLobList.get(i);
	// if (dline.getZaman().compareTo(time) == 0) {
	// // System.out.println("dline:"+dline.toStringDynamic());
	// if (i > diff) {
	// return (i - diff);
	// } else
	// return -1;
	// }
	// }
	// return -1;
	// }

	public static void readCSVfileDynamicLOB(String fileName) throws Exception {
		File file = new File(fileName);
		System.out.println("filename:" + fileName);
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;
		int row = 0;
		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			// System.out.println("line:" + line);

			LOBLine e = convertLineToDynamicLOB(line);

			// System.out.println("e icerigi:"+e.getMidpoint());
			dynamicLobList.add(row, e);
			// if(e.getZaman().equalsIgnoreCase(sonTrade))
			// break;
			row++;
		}
		System.out.println("readCSVfileDynamicLOB metodu sonu dynamic lob list size:" + dynamicLobList.size());
		bufRdr.close();
	}

	// private static String getTradeDirectionColumns(LOBLine dynamicLOBLine, Trade islem, int diff, int fileIndex, int
	// islemindex, ArrayList<Trade> islemList,
	// Trade lastTradeOfYesterday) {
	//
	// StringBuilder islemTime = new StringBuilder(dailyDateFormat.format(islem.getTime()));
	// int dynamicLobIndex=getDynamicLobFromIndex(islemTime.toString(), diff, fileIndex);
	//
	// LOBLine currentLobLine = new LOBLine();
	// currentLobLine.setTradePrice(islem.getPrice());
	// currentLobLine.setMidpoint(dynamicLOBLine.getMidpoint());
	// currentLobLine.setAsk(dynamicLOBLine.getAsk());
	// currentLobLine.setBid(dynamicLOBLine.getBid());
	// //currentLobLine = dynamicLOBLine;
	// //LOBLine previousLobLine = dynamicLobList.get(i-diff);
	//
	// /*
	// * TICK RULE:
	// * */
	// if (islemindex > 0) {
	// Trade previousTrade = islemList.get(islemindex - 1);
	// if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) > 0)
	// currentLobLine.setTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) < 0)
	// currentLobLine.setTickRule("S");
	// else {
	// for (int j = islemindex - 2; j > 0; j--) {
	// previousTrade = islemList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) > 0) {
	// currentLobLine.setTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) < 0) {
	// currentLobLine.setTickRule("S");
	// break;
	// }
	// }
	// //yeni gunun ilk islemini asagidaki islemindex ve fileindex>1 kosuluyla cozmustuk. yeni gunun
	// //ikinci islemi icin, ilk islemle tick hesaplanamadiysa onceki gunun kapanisina bak demek lazim:
	// if(currentLobLine.getTickRule()==null && fileIndex>1){
	// previousTrade = lastTradeOfYesterday;
	// if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) > 0)
	// currentLobLine.setTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) < 0)
	// currentLobLine.setTickRule("S");
	// }
	// }
	// }
	// else if(islemindex==0 && fileIndex>1){
	// Trade previousTrade = lastTradeOfYesterday;
	// if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) > 0)
	// currentLobLine.setTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) < 0)
	// currentLobLine.setTickRule("S");
	//
	// //son gunun kapanis fiyatini aldigimiz icin daha fazla geri gitmiyoruz..
	// // else {
	// // for (int j = islemindex - 2; j > 0; j--) {
	// // previousTrade = islemList.get(j);
	// // if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) > 0) {
	// // currentLobLine.setTickRule("A");
	// // break;
	// // } else if (currentLobLine.getTradePrice().compareTo(previousTrade.getPrice()) < 0) {
	// // currentLobLine.setTickRule("S");
	// // break;
	// // }
	// // }
	// // }
	// }
	// /*
	// * REVERSE TICK RULE:
	// * */
	// if (islemindex < islemList.size()-1) {
	// Trade nextTrade = islemList.get(islemindex + 1);
	// if (currentLobLine.getTradePrice().compareTo(nextTrade.getPrice()) > 0)
	// currentLobLine.setReverseTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(nextTrade.getPrice()) < 0)
	// currentLobLine.setReverseTickRule("S");
	// else {
	// for (int j = islemindex + 2; j<islemList.size(); j++) {
	// nextTrade = islemList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(nextTrade.getPrice()) > 0) {
	// currentLobLine.setReverseTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(nextTrade.getPrice()) < 0) {
	// currentLobLine.setReverseTickRule("S");
	// break;
	// }
	// }
	// }
	// }
	//
	// /*
	// * QUOTE RULE
	// * */
	// //previousLobLine = dynamicLobList.get(i - diff);
	// if (islem.getPrice().compareTo(dynamicLOBLine.getMidpoint()) > 0)
	// currentLobLine.setQuoteRule("A");
	// else if (islem.getPrice().compareTo(dynamicLOBLine.getMidpoint()) < 0)
	// currentLobLine.setQuoteRule("S");
	// else
	// currentLobLine.setQuoteRule("O");
	//
	// /*
	// * AT THE QUOTE RULE
	// * */
	// if (islem.getPrice().compareTo(dynamicLOBLine.getAsk()) == 0)
	// currentLobLine.setAtTheQuoteRule("A");
	// if (islem.getPrice().compareTo(dynamicLOBLine.getBid()) == 0)
	// currentLobLine.setAtTheQuoteRule("S");
	//
	// /*
	// * THE LR ALGORITHM
	// * */
	//
	// LOBLine previousLobLine = dynamicLobList.get(dynamicLobIndex - diff);
	// if (islem.getPrice().compareTo(dynamicLOBLine.getMidpoint()) > 0)
	// currentLobLine.setLr("A");
	// else if (islem.getPrice().compareTo(dynamicLOBLine.getMidpoint()) < 0)
	// currentLobLine.setLr("S");
	// else {
	// for (int j = dynamicLobIndex - 2 - diff; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (dynamicLOBLine.getMidpoint().compareTo(previousLobLine.getMidpoint()) > 0) {
	// currentLobLine.setLr("A");
	// break;
	// } else if (dynamicLOBLine.getMidpoint().compareTo(previousLobLine.getMidpoint()) < 0) {
	// currentLobLine.setLr("S");
	// break;
	// }
	// }
	// }
	//
	// /*
	// * THE EMO RULE
	// * */
	// if (islem.getPrice().compareTo(dynamicLOBLine.getAsk()) == 0)
	// currentLobLine.setEmoRule("A");
	// if (islem.getPrice().compareTo(dynamicLOBLine.getBid()) == 0)
	// currentLobLine.setEmoRule("S");
	// else {
	// for (int j = dynamicLobIndex - 2 - diff; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (islem.getPrice().compareTo(previousLobLine.getTradePrice()) > 0) {
	// currentLobLine.setEmoRule("A");
	// break;
	// } else if (islem.getPrice().compareTo(previousLobLine.getTradePrice()) < 0) {
	// currentLobLine.setEmoRule("S");
	// break;
	// }
	// }
	// }
	//
	// String line1addition = "";
	// line1addition+=currentLobLine.getTickRule()+";";
	// line1addition+=currentLobLine.getReverseTickRule()+";";
	// line1addition+=currentLobLine.getQuoteRule()+";";
	// line1addition+=currentLobLine.getAtTheQuoteRule()+";";
	// line1addition+=currentLobLine.getLr()+";";
	// line1addition+=currentLobLine.getEmoRule()+";";
	// return line1addition;
	// }

	// private static LOBLine fixTradeDirectionColumns(LOBLine dynamicLOBLine, int i, int diff, int fileIndex) {
	//
	// int start = 0;
	// start = (fileIndex - 1) * lineCount;
	//
	// System.out.println("fixTradeDirectionColumns");
	// // for (int i=start; i < dynamicLobList.size(); i++) {
	// // System.out.println("i:"+i);
	// // tick rule:
	// // if (i > 0) {
	// // Order previousLine = dynamicLobList.get(i - 1).getE();
	//
	// System.out.println("dynamicLobList.get(i):" + dynamicLobList.get(i));
	// LOBLine currentLobLine = new LOBLine();
	// currentLobLine.setTradePrice(dynamicLOBLine.getTradePrice());
	// currentLobLine.setMidpoint(dynamicLOBLine.getMidpoint());
	// currentLobLine.setAsk(dynamicLOBLine.getAsk());
	// currentLobLine.setBid(dynamicLOBLine.getBid());
	// // currentLobLine = dynamicLOBLine;
	// LOBLine previousLobLine = dynamicLobList.get(i - diff);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0)
	// currentLobLine.setTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0)
	// currentLobLine.setTickRule("S");
	// else {
	// for (int j = i - 2 - diff; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0) {
	// currentLobLine.setTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0) {
	// currentLobLine.setTickRule("S");
	// break;
	// }
	// }
	// }
	//
	// // the LR algorithm
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) > 0)
	// currentLobLine.setLr("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) < 0)
	// currentLobLine.setLr("S");
	// else {
	// for (int j = i - 2 - diff; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getMidpoint().compareTo(
	// previousLobLine.getMidpoint()) > 0) {
	// currentLobLine.setLr("A");
	// break;
	// } else if (currentLobLine.getMidpoint().compareTo(
	// previousLobLine.getMidpoint()) < 0) {
	// currentLobLine.setLr("S");
	// break;
	// }
	// }
	// }
	//
	// // the EMO rule
	// if (currentLobLine.getTradePrice().compareTo(currentLobLine.getAsk()) == 0)
	// currentLobLine.setEmoRule("A");
	// if (currentLobLine.getTradePrice().compareTo(currentLobLine.getBid()) == 0)
	// currentLobLine.setEmoRule("S");
	// else {
	// for (int j = i - 2 - diff; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0) {
	// currentLobLine.setEmoRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0) {
	// currentLobLine.setEmoRule("S");
	// break;
	// }
	// }
	// }
	//
	// // }
	// // reverse tick rule:
	// if (i < dynamicLobList.size() - 1) {
	// // Order nextLine = dynamicLobList.get(i + 1).getE();
	// LOBLine nextLobLine = dynamicLobList.get(i + 1);
	// // LOBLine currentLobLine = dynamicLobList.get(i);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) > 0)
	// currentLobLine.setReverseTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) < 0)
	// currentLobLine.setReverseTickRule("S");
	// else {
	// for (int j = i + 2; j < dynamicLobList.size(); j++) {
	// nextLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) > 0) {
	// currentLobLine.setReverseTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) < 0) {
	// currentLobLine.setReverseTickRule("S");
	// break;
	// }
	// }
	// }
	// }
	// // quote rule:
	// // LOBLine currentLobLine = dynamicLobList.get(i);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// previousLobLine = dynamicLobList.get(i - diff);
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getMidpoint()) > 0)
	// currentLobLine.setQuoteRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getMidpoint()) < 0)
	// currentLobLine.setQuoteRule("S");
	// else
	// currentLobLine.setQuoteRule("O");
	//
	// // at the quote rule:
	// if (currentLobLine.getTradePrice().compareTo(previousLobLine.getAsk()) == 0)
	// currentLobLine.setAtTheQuoteRule("A");
	// if (currentLobLine.getTradePrice().compareTo(previousLobLine.getBid()) == 0)
	// currentLobLine.setAtTheQuoteRule("S");
	// // System.out.println("at the quote input for line:"+i);
	// // System.out.println("trade price:"+currentLobLine.getTradePrice());
	// // System.out.println("currentLobLine.getAsk():"+currentLobLine.getAsk());
	// // System.out.println("currentLobLine.getBid():"+currentLobLine.getBid());
	// // }
	// // System.out.println("end of addtradedirectioncolumns");
	//
	// System.out.println("print time");
	//
	// currentLobLine.setE(dynamicLOBLine.getE());
	// currentLobLine.setZaman(dynamicLOBLine.getZaman());
	// currentLobLine.setBid(dynamicLOBLine.getBid());
	// currentLobLine.setAsk(dynamicLOBLine.getAsk());
	// currentLobLine.setSpread(dynamicLOBLine.getSpread());
	// currentLobLine.setBidVol(dynamicLOBLine.getBidVol());
	// currentLobLine.setAskVol(dynamicLOBLine.getAskVol());
	//
	// // System.out.println("currentLobLine       :"+currentLobLine);
	// System.out.println("dynamicLobList.get(G):" + dynamicLobList.get(i));
	// return currentLobLine;
	// }

	// private static void addTradeDirectionColumns(int fileIndex) {
	//
	// int start = 0;
	// start = (fileIndex - 1) * lineCount;
	//
	// System.out.println("\n\naddTradeDirectionColumns\n");
	// System.out.println("start:" + start);
	// System.out.println("\n\nloblist size:" + dynamicLobList.size());
	// for (int i = start; i < dynamicLobList.size(); i++) {
	// // System.out.println("i:"+i);
	// // tick rule:
	// if (i > 0) {
	// // Order previousLine = dynamicLobList.get(i - 1).getE();
	// LOBLine currentLobLine = dynamicLobList.get(i);
	// LOBLine previousLobLine = dynamicLobList.get(i - 1);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0)
	// currentLobLine.setTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0)
	// currentLobLine.setTickRule("S");
	// else {
	// for (int j = i - 2; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0) {
	// currentLobLine.setTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0) {
	// currentLobLine.setTickRule("S");
	// break;
	// }
	// }
	// }
	//
	// // the LR algorithm
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) > 0)
	// currentLobLine.setLr("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) < 0)
	// currentLobLine.setLr("S");
	// else {
	// for (int j = i - 2; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getMidpoint().compareTo(
	// previousLobLine.getMidpoint()) > 0) {
	// currentLobLine.setLr("A");
	// break;
	// } else if (currentLobLine.getMidpoint().compareTo(
	// previousLobLine.getMidpoint()) < 0) {
	// currentLobLine.setLr("S");
	// break;
	// }
	// }
	// }
	//
	// // the EMO rule
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getAsk()) == 0)
	// currentLobLine.setEmoRule("A");
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getBid()) == 0)
	// currentLobLine.setEmoRule("S");
	// else {
	// for (int j = i - 2; j > 0; j--) {
	// previousLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) > 0) {
	// currentLobLine.setEmoRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// previousLobLine.getTradePrice()) < 0) {
	// currentLobLine.setEmoRule("S");
	// break;
	// }
	// }
	// }
	//
	// }
	// // reverse tick rule:
	// if (i < dynamicLobList.size() - 1) {
	// // Order nextLine = dynamicLobList.get(i + 1).getE();
	// LOBLine nextLobLine = dynamicLobList.get(i + 1);
	// LOBLine currentLobLine = dynamicLobList.get(i);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) > 0)
	// currentLobLine.setReverseTickRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) < 0)
	// currentLobLine.setReverseTickRule("S");
	// else {
	// for (int j = i + 2; j < dynamicLobList.size(); j++) {
	// nextLobLine = dynamicLobList.get(j);
	// if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) > 0) {
	// currentLobLine.setReverseTickRule("A");
	// break;
	// } else if (currentLobLine.getTradePrice().compareTo(
	// nextLobLine.getTradePrice()) < 0) {
	// currentLobLine.setReverseTickRule("S");
	// break;
	// }
	// }
	// }
	// }
	// // quote rule:
	// LOBLine currentLobLine = dynamicLobList.get(i);
	// // Order currentLine = dynamicLobList.get(i).getE();
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) > 0)
	// currentLobLine.setQuoteRule("A");
	// else if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getMidpoint()) < 0)
	// currentLobLine.setQuoteRule("S");
	// else
	// currentLobLine.setQuoteRule("O");
	//
	// // at the quote rule:
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getAsk()) == 0)
	// currentLobLine.setAtTheQuoteRule("A");
	// if (currentLobLine.getTradePrice().compareTo(
	// currentLobLine.getBid()) == 0)
	// currentLobLine.setAtTheQuoteRule("S");
	// System.out.println("at the quote input for line:" + i);
	// System.out.println("trade price:" + currentLobLine.getTradePrice());
	// System.out.println("currentLobLine.getAsk():"
	// + currentLobLine.getAsk());
	// System.out.println("currentLobLine.getBid():"
	// + currentLobLine.getBid());
	// }
	// System.out.println("end of addtradedirectioncolumns");
	// }

	public static LOBLine convertLineToDynamicLOB(String line) {
		LOBLine dll = new LOBLine();
		Parse parser = new Parse(line, ";");

		if (line != null) {
			Order e = new Order();
			String tmp = parser.nextToken();
			while (tmp != null) {
				// System.out.println("-----------------------------" + tmp);
				e.setEmirNumarasi(tmp);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				Date emirDate = null;
				try {
					emirDate = basicDateFormat.parse(tmp);
					e.setEmirTarihi(emirDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setGirisSaati(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setMenkulKiymet(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setPazar(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setAlis_satis(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				BigDecimal price = new BigDecimal(tmp);
				e.setFiyat(price);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					BigDecimal repo2fiyat = new BigDecimal(tmp);
					e.setRepo2Fiyati(repo2fiyat);
				}

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setMiktar(Integer.parseInt(tmp));

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setBakiye(Integer.parseInt(tmp));

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setDurum(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setSonDegistirmeSaati(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setIlgiliEmirNumarasi(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					Date valor1 = null;
					try {
						valor1 = basicDateFormat.parse(tmp);
						e.setValor1(valor1);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}

				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					Date valor2 = null;
					try {
						valor2 = basicDateFormat.parse(tmp);
						e.setValor2(valor2);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				tmp = parser.nextToken();
				// System.out.println(tmp);
				BigDecimal getiri = new BigDecimal(tmp);
				e.setGetiri(getiri);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setParaBirimi(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setRepo(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				e.setHesabi(tmp);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					BigDecimal tltutar = new BigDecimal(tmp);
					e.setTlTutar(tltutar);
				}
				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					BigDecimal temiz = new BigDecimal(tmp);
					e.setTemizFiyat(temiz);
				}
				tmp = parser.nextToken();
				// System.out.println(tmp);
				if (tmp.length() > 0) {
					BigDecimal takasfiyati = new BigDecimal(tmp);
					e.setTakasFiyati(takasfiyati);
				}

				dll.setE(e);

				tmp = parser.nextToken();
				// System.out.println(tmp);
				BigDecimal bid = new BigDecimal(tmp);
				dll.setBid(bid);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				BigDecimal ask = new BigDecimal(tmp);
				dll.setAsk(ask);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				BigDecimal spread = new BigDecimal(tmp);
				dll.setSpread(spread);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				int bidVol = Integer.parseInt(tmp);
				dll.setBidVol(bidVol);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				int askVol = Integer.parseInt(tmp);
				dll.setAskVol(askVol);
				tmp = parser.nextToken();
				// System.out.println(tmp);
				dll.setAggressive(tmp);

				// tmp = parser.nextToken();
				// System.out.println(tmp);
				// int crossOrder = Integer.parseInt(tmp);
				// dll.setCrossOrder(crossOrder);
				//
				// tmp = parser.nextToken();
				// System.out.println(tmp);
				// e.setEmirNumarasi(tmp);
				//
				// tmp = parser.nextToken();
				// System.out.println(tmp);
				// BigDecimal midPoint = new BigDecimal(tmp);
				// dll.setMidpoint(midPoint);


				for (int i = 0; i < 43; i++) {
					tmp = parser.nextToken();
					// System.out.println(tmp);
				}

				/*
				 * tmp = parser.nextToken(); // System.out.println(tmp); int countLobLine = Integer.parseInt(tmp);
				 * dll.setCountLOBLine(countLobLine); tmp = parser.nextToken(); // System.out.println(tmp); int
				 * countLobLineBuy = Integer.parseInt(tmp); dll.setCountLOBLine_BUY(countLobLineBuy); tmp =
				 * parser.nextToken(); // System.out.println(tmp); int countLobLineSell = Integer.parseInt(tmp);
				 * dll.setCountLOBLine_SELL(countLobLineSell); tmp = parser.nextToken(); // System.out.println(tmp);
				 * BigDecimal midPoint = new BigDecimal(tmp); dll.setMidpoint(midPoint); tmp = parser.nextToken(); //
				 * System.out.println(tmp); dll.setTickRule(tmp); tmp = parser.nextToken(); // System.out.println(tmp);
				 * dll.setReverseTickRule(tmp); tmp = parser.nextToken(); // System.out.println(tmp);
				 * dll.setQuoteRule(tmp); tmp = parser.nextToken(); // System.out.println(tmp);
				 * dll.setAtTheQuoteRule(tmp); tmp = parser.nextToken(); // System.out.println(tmp); dll.setLr(tmp); tmp
				 * = parser.nextToken(); // System.out.println(tmp); dll.setEmoRule(tmp); tmp = parser.nextToken(); //
				 * System.out.println(tmp); dll.setNumberOfTrades(tmp); tmp = parser.nextToken(); //
				 * System.out.println(tmp); BigDecimal tradePrice = new BigDecimal(tmp); dll.setTradePrice(tradePrice);
				 * tmp = parser.nextToken(); // System.out.println(tmp); int volumeTraded = Integer.parseInt(tmp);
				 * dll.setVolumeTraded(volumeTraded); tmp = parser.nextToken(); // System.out.println(tmp);
				 * dll.setNumberOfOrders(tmp); tmp = parser.nextToken(); // System.out.println(tmp); dll.setZaman(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setSecondBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setSecondBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setThirdBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setThirdBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setFourthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setFourthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setFifthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setFifthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setSixthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setSixthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setSeventhBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setSeventhBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setEighthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setEighthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setNinthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setNinthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setTenthBestSellPrice(tmp); tmp = parser.nextToken();
				 * dll.setTenthBestSellVolume(tmp);
				 * 
				 * tmp = parser.nextToken(); dll.setSecondBestBuyPrice(tmp); tmp = parser.nextToken();
				 * dll.setSecondBestBuyVolume(tmp); tmp = parser.nextToken(); dll.setThirdBestBuyPrice(tmp); tmp =
				 * parser.nextToken(); dll.setThirdBestBuyVolume(tmp); tmp = parser.nextToken();
				 * dll.setFourthBestBuyPrice(tmp); tmp = parser.nextToken(); dll.setFourthBestBuyVolume(tmp); tmp =
				 * parser.nextToken(); dll.setFifthBestBuyPrice(tmp); tmp = parser.nextToken();
				 * dll.setFifthBestBuyVolume(tmp); tmp = parser.nextToken(); dll.setSixthBestBuyPrice(tmp); tmp =
				 * parser.nextToken(); dll.setSixthBestBuyVolume(tmp); tmp = parser.nextToken();
				 * dll.setSeventhBestBuyPrice(tmp); tmp = parser.nextToken(); dll.setSeventhBestBuyVolume(tmp); tmp =
				 * parser.nextToken(); dll.setEighthBestBuyPrice(tmp); tmp = parser.nextToken();
				 * dll.setEighthBestBuyVolume(tmp); tmp = parser.nextToken(); dll.setNinthBestBuyPrice(tmp); tmp =
				 * parser.nextToken(); dll.setNinthBestBuyVolume(tmp); tmp = parser.nextToken();
				 * dll.setTenthBestBuyPrice(tmp); tmp = parser.nextToken(); dll.setTenthBestBuyVolume(tmp);
				 * 
				 * // System.out.println("bu sondan ikinci:"); tmp = parser.nextToken(); //
				 * System.out.println("check 1"+tmp); dll.setMidpointAfter15(tmp);
				 * 
				 * // System.out.println("bu son:"); tmp = parser.nextToken(); // System.out.println("check 2"+tmp);
				 * dll.setMidpointAfter30(tmp); // System.out.println(tmp);
				 * 
				 * tmp = parser.nextToken(); // System.out.println("check 3"+tmp); tmp = parser.nextToken(); //
				 * System.out.println("check 4"+tmp);
				 */
			}
		}
		// System.out.println("dll.midpoint:"+dll.getMidpoint());
		return dll;
	}

}
