package gr.uoa.di.kr.yagoextension.util;

/**
 * This class is part of the YAGO Extension Project
 * Author: Nikos Karalis 
 * kr.di.uoa.gr
 */

import java.util.Iterator;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import gr.uoa.di.kr.yagoextension.structures.Entity;

public class Blacklist {
	
	public static void removeMatchedEntities(Map<String, Entity> yago, Map<String, Entity> ds, String matchesFile) {

		Model matches = RDFDataMgr.loadModel(matchesFile);
		StmtIterator stmts = matches.listStatements();
		while(stmts.hasNext()) {
			Statement stmt = stmts.next();
			String subj = stmt.getSubject().getURI();
			String objURI = stmt.getObject().asResource().getURI();
			String obj = objURI.split("/")[4].split(">")[0];
			yago.remove("<"+obj+">");
			ds.remove(subj);
		}
	}

	public static void removeMergedEntities(Map<String, Entity> yago){
		Iterator it = yago.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			String key = pair.getKey().toString();
			if (key.contains("-"))
				it.remove();
		}
	}

}
