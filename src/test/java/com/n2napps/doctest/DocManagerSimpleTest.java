package com.n2napps.doctest;


import java.io.File;
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

}
