package com.n2napps.doctest;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.n2napps.doctest.manager.DocManager;
import com.n2napps.doctest.utils.FolderUtils;

public class DocManagerSimpleTest {
    
    @Test
    public void testDocxToHtml() throws Exception {
        //create the output folder if not exist
        FolderUtils.newFolder("\\tmp\\test\\DocManager\\output");
      
        String inputFileString = "src/test/resources/DocManager/01_simple.docx";
        String outputFileString = "tmp/test/DocManager/output/";
      
        File inputFile = new File(inputFileString);
        DocManager manager = new DocManager();
      
        //docx to html
        manager.docxToHtml(inputFile,outputFileString);
      
    }
    
    
    @Test
    public void testReplace() throws Exception {
        //create the output folder if not exist
        FolderUtils.newFolder("\\tmp\\test\\DocManager\\output");
        
        String inputFileString = "src/test/resources/DocManager/03_replace.docx";
        String outputFileString = "tmp/test/DocManager/output/03_replace_test.docx";
      
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
