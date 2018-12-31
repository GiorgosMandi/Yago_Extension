package gr.uoa.di.kr.yagoextension.readers;

/**
 * This class is part of the YAGO Extension Project
 * Author: Nikos Karalis 
 * kr.di.uoa.gr
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.vividsolutions.jts.io.ParseException;
import gr.uoa.di.kr.yagoextension.structures.Entity;

import java.util.*;


public class TSVReader extends Reader {

	public TSVReader(String path) {
		super(path);
	}

	public void read() {

		/** prepare data */
		File tsvFile = new File(inputFile);
		Map<String, List<String>> labelsMap = new HashMap<String, List<String>>();
		Map<String, String> latiMap = new HashMap<String, String>();
		Map<String, String> longiMap = new HashMap<String, String>();

		try {
			CSVFormat csvFileFormat = CSVFormat.TDF.withQuote(null);
			CSVParser parser = CSVParser.parse(tsvFile, StandardCharsets.UTF_8, csvFileFormat);
			for(CSVRecord x : parser.getRecords()) {
				if(x.get(1).contains("label")) {
					String label = x.get(2).substring(x.get(2).indexOf("\"")+1, x.get(2).lastIndexOf("\"")); // keep the part that is between the quotes
					if(labelsMap.containsKey(x.get(0)))
						labelsMap.get(x.get(0)).add(label);
					else
						labelsMap.put(x.get(0), new ArrayList<String>(Arrays.asList(label)));
				}
				else if(x.get(1).contains("Latitude")){
					latiMap.put(x.get(0), x.get(2)); // CHANGE: here was "3" instead of "2" and it was throwing IndexOutOfBounds
				}
				else if(x.get(1).contains("Longitude"))
					longiMap.put(x.get(0), x.get(2)); // CHANGE: here was "3" instead of "2" and it was throwing IndexOutOfBounds
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		/** create new entities */
		for(String key : latiMap.keySet()) {
			if(labelsMap.get(key) == null) continue;
			try {
				entities.put(key, new Entity(key, labelsMap.get(key), latiMap.get(key), longiMap.get(key)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/** Returns a map with all the URIs and their properties
	 */
	public Map<String, List<String>> readFacts(){
		File tsvFile = new File(inputFile);
		Map<String, List<String>> facts = new HashMap<>();
		try {
			CSVFormat csvFileFormat = CSVFormat.TDF.withQuote(null);
			CSVParser parser = CSVParser.parse(tsvFile, StandardCharsets.UTF_8, csvFileFormat);
			for(CSVRecord x : parser) {
				String uri = x.get(1);
				String property = x.get(2);
				if(uri.charAt(0) == '<' && uri.charAt(uri.length()-1) == '>')
					uri = uri.substring(1, uri.length()-1);
				if(property.charAt(0) == '<' && property.charAt(property.length()-1) == '>')
					property = property.substring(1, property.length()-1);
				if (facts.containsKey(uri)){
					facts.get(uri).add(property);
					System.out.println(uri+"  "+ property+"\n\n\n\n");
				}
				else{
					List<String> properties = new ArrayList<>();
					properties.add(property);
					facts.put(uri, properties);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
