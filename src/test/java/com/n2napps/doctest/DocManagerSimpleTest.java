package com.n2napps.doctest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
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
      List<Field> values = manager.getFields(inputFile);
      
      assertEquals("values size is 2",2, values.size());
      assertEquals("one field is name",values.get(0).getName(),"name");
      assertEquals("other field is address",values.get(1).getName(),"address");
    }

}
