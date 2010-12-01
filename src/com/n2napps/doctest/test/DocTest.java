
package com.n2napps.doctest.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.n2napps.doctest.FieldValues;
import com.n2napps.doctest.manager.DocManager;




public class DocTest {

	protected static String getInputFilePath(String[] args)
			throws IllegalArgumentException {
		if (args.length < 2)throw new IllegalArgumentException("Input file arg missing");
		if(args[0].equals("-f")){
			return args[1];
		}else{
			throw new IllegalArgumentException("Input file arg missing");
		}
	}

	protected static String getInputFields() {
		return "Friping";
	}
	protected static String getOutputFilePath(String[] args)
			throws IllegalArgumentException {
		if (args.length < 4)throw new IllegalArgumentException("Output file arg missing");
		if(args[2].equals("-o")){
			return args[3];
		}else{
			throw new IllegalArgumentException("Input file arg missing");
		}
	}

	public static void main(String[] args) throws Exception {
		String inputFileString = null;
		String outputFileString = null;
		try {
			inputFileString = getInputFilePath(args);
			outputFileString = getOutputFilePath(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		File inputFile = new File(inputFileString);
		File outputFile = new File(outputFileString);
		DocManager manager = new DocManager();
		
		// to get fields and set values.
		List<FieldValues> values = manager.getFields(inputFile);
		for(FieldValues o : values){
			InputStreamReader stdin = new InputStreamReader(System.in);
			BufferedReader bufin = new BufferedReader(stdin);
			try {
				System.out.print("please enter field '"+o.getName()+"': ");
				String value = bufin.readLine();
				System.out.println("the field '"+o.getName()+"' value you enter is  " + value);
				o.setValue(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		manager.fillAndSave(inputFile, outputFile, values);
		
	}
	
	
}
