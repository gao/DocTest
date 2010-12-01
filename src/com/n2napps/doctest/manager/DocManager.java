package com.n2napps.doctest.manager;

import java.io.File;
import java.util.List;

import com.n2napps.doctest.FieldValues;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;


public class DocManager {
    
    public List<FieldValues> getFields(File docxFile){
        
        try {
            //To load a document
            WordprocessingMLPackage wordMLPackage =  WordprocessingMLPackage.load(docxFile);
            
            //get the main document part
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            
            
            
        } catch (Docx4JException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public void fillAndSave(File docxFile, File destinationDocxFile, List<FieldValues> values){
        
    }

}