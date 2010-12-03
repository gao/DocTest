package com.n2napps.doctest.manager;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.convert.out.html.AbstractHtmlExporter;
import org.docx4j.convert.out.html.HtmlExporterNG2;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;



public class DocManager {
    
    
    public void docxToHtml(File docxFile,String outputFileString) throws Exception{                 
        // Load .docx
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile); 
        AbstractHtmlExporter exporter = new HtmlExporterNG2();                        
                          
        OutputStream os = new java.io.FileOutputStream(outputFileString+"testDocToHtml.html"); 
                          
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(os); 
        exporter.html(wordMLPackage, result,outputFileString+"test_files"); 
        
    }
    
    
    public void replace(File docxFile,String outputFileString ,List<String> textToReplace,List<String> newText) throws Docx4JException, JAXBException{
        boolean save = true;
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);           
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement(); 
                            
        //xml --> string
        String xml = XmlUtils.marshaltoString(wmlDocumentEl, true);
            
        HashMap<String, String> mappings = new HashMap<String, String>();
            
        for(int i=0;i<textToReplace.size();i++){
            mappings.put(textToReplace.get(i), newText.get(i));
        }
          
        //valorize template
        Object obj = XmlUtils.unmarshallFromTemplate(xml, mappings);
            
        //change  JaxbElement
        documentPart.setJaxbElement((Document) obj);

        // Save it        
        if (save) {     
            SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
            saver.save(outputFileString);
            System.out.println( "Saved output to:" + outputFileString );
        } else {
            // Display the Main Document Part.
        }
    }

}