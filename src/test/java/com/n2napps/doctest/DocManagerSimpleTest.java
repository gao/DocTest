package com.n2napps.doctest;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.n2napps.doctest.manager.DocManager;
import com.n2napps.doctest.utils.FolderUtils;

public class DocManagerSimpleTest {
    
    /**
     * this test is for the method docxToHtml
     * @throws Exception
     */
    @Test
    public void testDocxToHtml() throws Exception {
        //create the output folder if not exist
        FolderUtils.newFolder("\\tmp\\test\\DocManager\\output");
      
        String inputFilePath = "src/test/resources/DocManager/01_simple.docx";
        String outputFilePath = "tmp/test/DocManager/output/";
      
        File inputFile = new File(inputFilePath);
        DocManager manager = new DocManager();
      
        //docx to html
        manager.docxToHtml(inputFile,outputFilePath);
      
    }
    
    
    /**
     * this test is for the method replace
     * @throws Exception
     */
    @Test
    public void testReplace() throws Exception {
        //create the output folder if not exist
        FolderUtils.newFolder("\\tmp\\test\\DocManager\\output");
        
        String inputFilePath = "src/test/resources/DocManager/03_replace.docx";
        String outputFilePath = "tmp/test/DocManager/output/03_replace_test.docx";
      
        File inputFile = new File(inputFilePath);
        DocManager manager = new DocManager();
        
        List<String> textToReplace = new ArrayList<String>();
        textToReplace.add("colour");
        textToReplace.add("icecream");
        
        List<String> newText = new ArrayList<String>();
        newText.add("green");
        newText.add("chocolate");
        
        manager.replace(inputFile, outputFilePath, textToReplace, newText);
    }

}
