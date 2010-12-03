package com.n2napps.doctest;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.junit.Test;

import com.n2napps.doctest.manager.DocManager;

public class DocManagerSimpleTest {
    
    @Test
    public void testDocxToHtml() throws Exception {
      String inputFileString = "src/test/resources/DocManager/01_simple.docx";
      String outputFileString = "src/test/resources/DocManager/output/";
      
      File inputFile = new File(inputFileString);
      DocManager manager = new DocManager();
      
      //docx to html
      manager.docxToHtml(inputFile,outputFileString);
      
    }
    
    
    @Test
    public void testReplace() throws Exception {
        String inputFileString = "src/test/resources/DocManager/03_replace.docx";
        String outputFileString = "src/test/resources/DocManager/output/03_replace_test.docx";
      
        File inputFile = new File(inputFileString);
        DocManager manager = new DocManager();
        
        List<String> textToReplace = new ArrayList<String>();
        textToReplace.add("colour");
        textToReplace.add("icecream");
        
        List<String> newText = new ArrayList<String>();
        newText.add("green");
        newText.add("chocolate");
        
        manager.replace(inputFile, outputFileString, textToReplace, newText);
    }

}
