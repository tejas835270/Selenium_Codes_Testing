package test.fillo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import test.utilities.ConfigurationProperties;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Test {

	public static List<List<String>>  executeQuery(String fileName, String query) throws FilloException{
		return executeExcelQuery(fileName, query);
	}

	public static String executeUpdateQuery(String fileName, String query) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);
		connection.executeUpdate(query);
		connection.close();
		return "Update was Successful";
	}

	/**
	 * This method treats excel as a database.<BR>You can get data from the excel. 
	 * file just using a basic query.<BR>
	 * @param fileName
	 * @param query
	 * @return
	 * @throws FilloException
	 */
	public static List<List<String>> executeExcelQuery(String fileName, String query) throws FilloException {
		List<List<String>> listOfLists = new ArrayList<List<String>>();
		List<String> someList = new ArrayList<String>();
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(fileName);
		query = query.toUpperCase();
		System.out.println(query);
		Recordset recordset = connection.executeQuery(query);
		System.out.println("total number of row returned " + recordset.getCount());
		while (recordset.next()) {
			ArrayList<String> dataColl = recordset.getFieldNames();
			//System.out.println("Total data column " + dataColl);
			Iterator<String> dataIterator = dataColl.iterator();
			String[] columns = columnsSplit(query);
			int width = 0;
			// Width size
			if (query.contains("*")) {
				width = dataColl.size();
				//System.out.println(width);
			} else {
				width = columns.length;
				//System.out.println(width);
			}
			String[] rowByRow = new String[width];
			int rowNo = 0;
			while (dataIterator.hasNext()) {
				for (int i = 0; i <= dataColl.size() - 1; i++) {
					String data = dataIterator.next();
					if (query.contains("*")) {
						String dataVal = recordset.getField(data);
						//System.out.println(dataVal);
						rowByRow[rowNo] = dataVal;
						rowNo++;
					} else {
						//System.out.println(columns.length);
						for (String column : columns) {
							if (column.length() > 0) {
								if (data.equalsIgnoreCase(column)) {
									String dataVal = recordset.getField(column);
									rowByRow[rowNo] = dataVal;
									//System.out.println(dataVal);
									rowNo++;
								}
							}
						}
					}
				}
				listOfLists.add(Arrays.asList(rowByRow));
				rowNo = 0;
				someList.iterator();
				break;
			}
		}
		recordset.close();
		connection.close();
		return listOfLists;
	}
	/**
	 * @param query
	 * @return
	 */
	private static String[] columnsSplit(String query) {
		int start = 7;
		int end = query.indexOf(" FROM");
		String columnsWithComma = query.substring(start, end);
		return columnsWithComma.split(",");
	}

	public static boolean updateQuery (String filePath, String sheetName, String transactionID, String value) {
		boolean flag = false;
		Fillo fillo=new Fillo();
		try {
			Connection connection=fillo.getConnection(filePath);
			String strQuery="Update " + sheetName + " Set TransStatus='" + value + "' where ID='" + transactionID + "'";
			connection.executeUpdate(strQuery);
			connection.close();
			flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	public static String getFileName(String fileOrigin) {
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + fileOrigin;
		filePath = filePath.replace("/", "\\");
		return filePath;
	}

	public static void main(String[] args) {
		ConfigurationProperties configurationProperties = new ConfigurationProperties();

		String orderFile = Test.getFileName(configurationProperties.getProperty("SourceFilePath"));
		String sourceColumns = configurationProperties.getProperty("Source_Columns");
		String sourceSheetName = configurationProperties.getProperty("Source_Sheet_Name");

		String transactionFile = Test.getFileName(configurationProperties.getProperty("DesinationFilePath"));
		String destinationColumns = configurationProperties.getProperty("Destination_Columns");
		String destinationSheetName = configurationProperties.getProperty("Destination_Sheet_Name");

		try {
			List<List<String>> orderList =  Test.executeQuery(orderFile, "Select " + sourceColumns + " From " + sourceSheetName);
			List<List<String>> transactionList = Test.executeQuery(transactionFile, "Select " + destinationColumns + " From " + destinationSheetName);
			int count = 0;
			for (List<String> s : orderList) {
				System.out.println(count++);
				boolean flag = false;
				for (String p : s) {
					for (List<String> t : transactionList) {
						for (String d : t) {
							if (d.equals(p)) {
								flag = true;
								String source = s.get(0) + ":" + s.get(1).substring(0, (s.get(1).length())-3) + ":" + s.get(2);
								String destination = t.get(0) + ":" + t.get(2) + ":" + t.get(1) ;
								if (source.equals(destination)) {
									Test.updateQuery(orderFile, sourceSheetName, s.get(3), "PASS");
								} else {
									Test.updateQuery(orderFile, sourceSheetName, s.get(3), source + "|" + destination);
								}
							}
							break;
						}
					}
					if(!flag) {
						Test.updateQuery(orderFile, sourceSheetName, s.get(3), "Order not found");
					}
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
