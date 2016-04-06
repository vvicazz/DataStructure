package com.akash.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadCVS {

	enum SplitPatternType {

		CSV_TAB("\t"), CSV_COMMA(",");

		SplitPatternType(String csvPattern) {
			this.csvPattern = csvPattern;
		}

		private String csvPattern;

		public String toString() {
			return csvPattern;
		}
	}

	public static void main(String[] args) {

		ReadCVS obj = new ReadCVS();
		List<Map<String, String>> csvData = new ArrayList<Map<String, String>>();
		List<String> headerList = new ArrayList<String>();
		String serverError = "";
		obj.loadCSV("test.csv", "C:/Users/Akash.Sharma/Desktop/",
				SplitPatternType.CSV_TAB, headerList, csvData, serverError);
		
		if (serverError.equals("")) {
			System.out.println("done");
		} else {
			System.out.println(serverError);
		}
	}

	public void loadCSV(String csvFileName, String filePath,
			SplitPatternType csvSplitPattern, List<String> headerList,
			List<Map<String, String>> csvData, String error) {

		String csvFile = filePath + csvFileName;
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {

				String[] country = line.split(csvSplitPattern.toString());
				if (country.length > 0) {
					if (headerList.size() > 0) {

						Map<String, String> singleObjectData = new HashMap<String, String>();
						for (int column = 0; column < headerList.size(); column++) {
							String key = headerList.get(column);
							String value = country[column];
							if (key != null) {
								key = key.trim();
							}
							if (value != null) {
								value = value.trim();
							}
							if (key != null) {
								singleObjectData.put(key, value);
							}
						}

						if (singleObjectData.entrySet().size() > 0) {
							csvData.add(singleObjectData);
						}

					} else if (headerList.size() == 0) {
						headerList.addAll(Arrays.asList(country));
					}
				}

			}

		} catch (FileNotFoundException e) {
			error = Arrays.asList(e.getStackTrace()).toString();
		} catch (IOException e) {
			error = Arrays.asList(e.getStackTrace()).toString();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					error = Arrays.asList(e.getStackTrace()).toString();
				}
			}
		}
	}
}