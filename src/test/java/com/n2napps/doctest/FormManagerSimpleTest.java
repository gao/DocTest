package com.n2napps.doctest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Test;

import com.n2napps.doctest.manager.FormManager;
import com.n2napps.doctest.utils.FolderUtils;

public class FormManagerSimpleTest {
    
    //use the file 01_simple.docx,have ${name} and ${address}
    /**
     * this method is for method getFields
     */
    @Test
    public void testGetFields() throws Docx4JException {
        String inputFilePath = "src/test/resources/DocManager/01_simple.docx";
      
        File inputFile = new File(inputFilePath);
        FormManager manager = new FormManager();
      
        //get fields
        List<Field> fileds = manager.getFields(inputFile);
      
        assertEquals("fileds size is 2",2, fileds.size());
        assertEquals("one field is name",fileds.get(0).getName(),"name");
        assertEquals("other field is address",fileds.get(1).getName(),"address");
    }
    

    /**
     * this test is for the method fillAndSave
     * @throws Docx4JException
     */
    @Test
    public void testFillAndSave() throws Docx4JException {
        //create the output folder if not exist
        FolderUtils.newFolder("/tmp/test/DocManager/output");
      
        String inputFilePath = "src/test/resources/DocManager/01_simple.docx";
        String outputFilePath = "tmp/test/DocManager/output/01_simple_test.docx";
      
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        FormManager manager = new FormManager();
      
        //get fields
        List<Field> fileds = manager.getFields(inputFile);
      
        assertEquals("values size is 2",2, fileds.size());
        assertEquals("one field is name",fileds.get(0).getName(),"name");
        assertEquals("other field is address",fileds.get(1).getName(),"address");
      
        List<FieldValues> fieldvalues = new ArrayList<FieldValues>();
        for(Field filed : fileds){
            FieldValues fv = new FieldValues();
            fv.setName(filed.getName());
            fv.setValue(filed.getName()+"value");
            fieldvalues.add(fv);
        }

        manager.fillAndSave(inputFile, outputFile, fieldvalues);
    }
    
    
    //use the file 02_redundant_field.docx,have 2 ${name} 2 ${address} and 1 ${phone}
    @Test
    public void testGetFieldsHaveSameField() throws Docx4JException {
        String inputFilePath = "src/test/resources/DocManager/02_redundant_field.docx";
      
        File inputFile = new File(inputFilePath);
        FormManager manager = new FormManager();
      
        //get fields
        List<Field> fileds = manager.getFields(inputFile);
      
        assertEquals("fileds size is 3",3, fileds.size());
        assertEquals("one field is name",fileds.get(0).getName(),"name");
        assertEquals("other field is address",fileds.get(1).getName(),"address");
        assertEquals("other field is phone",fileds.get(2).getName(),"phone");
    }
    
    
    @Test
    public void testFillAndSaveHaveSameField() throws Docx4JException {
        //create the output folder if not exist
        FolderUtils.newFolder("/tmp/test/DocManager/output");
      
        String inputFilePath = "src/test/resources/DocManager/02_redundant_field.docx";
        String outputFilePath = "tmp/test/DocManager/output/02_redundant_field_test.docx";
      
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        FormManager manager = new FormManager();
      
        //get fields
        List<Field> fileds = manager.getFields(inputFile);
      
        assertEquals("fileds size is 3",3, fileds.size());
        assertEquals("one field is name",fileds.get(0).getName(),"name");
        assertEquals("other field is address",fileds.get(1).getName(),"address");
        assertEquals("other field is phone",fileds.get(2).getName(),"phone");
      
        List<FieldValues> fieldvalues = new ArrayList<FieldValues>();
        for(Field filed : fileds){
            FieldValues fv = new FieldValues();
            fv.setName(filed.getName());
            fv.setValue(filed.getName()+"value");
            fieldvalues.add(fv);
        }

        manager.fillAndSave(inputFile, outputFile, fieldvalues);
    }

}
