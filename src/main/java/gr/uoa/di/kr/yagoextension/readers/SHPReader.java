package gr.uoa.di.kr.yagoextension.readers;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is part of the YAGO Extension Project
 * Author: Nikos Karalis
 * kr.di.uoa.gr
 */

public class SHPReader extends Reader {

	public SHPReader(String path) {
		super(path);
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> readURIs(){
		File tsvFile = new File(inputFile);
		Set<String> uris = new HashSet<String>();
		// TODO Auto-generated method stub
		return uris;
	}

}
