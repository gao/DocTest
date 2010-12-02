package com.n2napps.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.junit.Test;

import com.n2napps.doctest.manager.DocManager;
import com.n2napps.doctest.Field;

public class DocManagerSimpleTest {
    
    //use the file 01_simple.docx,have ${name} and ${address}
    @Test
    public void testGetFields() throws Docx4JException {
      String inputFileString = "src/test/resources/DocManager/01_simple.docx";
      
      File inputFile = new File(inputFileString);
      DocManager manager = new DocManager();
      
      //get fields
      List<Field> fileds = manager.getFields(inputFile);
      
      assertEquals("fileds size is 2",2, fileds.size());
      assertEquals("one field is name",fileds.get(0).getName(),"name");
      assertEquals("other field is address",fileds.get(1).getName(),"address");
    }
    
    @Test
    public void testFillAndSave() throws Docx4JException {
      String inputFileString = "src/test/resources/DocManager/01_simple.docx";
      String outputFileString = "src/test/resources/DocManager/output/01_simple_test.docx";
      
      File inputFile = new File(inputFileString);
      File outputFile = new File(outputFileString);
      DocManager manager = new DocManager();
      
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

}
