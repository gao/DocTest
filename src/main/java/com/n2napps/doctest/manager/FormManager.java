package com.n2napps.doctest.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.Callback;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Text;

import com.n2napps.doctest.Field;
import com.n2napps.doctest.FieldValues;
import com.n2napps.doctest.utils.FieldUtils;

public class FormManager extends DocManager{
    
    /***
     * this method get the name of ${name} in the docxFile
     * this for the 01_sample.docx the style of ${} 
     * @param docxFile
     * @return
     * @throws Docx4JException
     */
    public List<Field> getFields(File docxFile) throws Docx4JException{
        
        final List<Field> fields = new ArrayList<Field>();
        
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();
        Body body = wmlDocumentEl.getBody();
        
        
        new TraversalUtil(body,

            new Callback() {
    
                List<Text> textList = new ArrayList();
                // Depth first
                @Override
                public void walkJAXBElements(Object parent) {
    
    
                    List children = getChildren(parent);
                    if (children != null) {
                        for (Object o : children) {
                            // if its wrapped in javax.xml.bind.JAXBElement, get its
                            // value
                            o = XmlUtils.unwrap(o);
    
                            this.apply(o);
    
                            if (this.shouldTraverse(o)) {
                                walkJAXBElements(o);
                            }
    
                        }
                    }
    
                }
    
                @Override
                public List<Object> getChildren(Object o) {
                    return TraversalUtil.getChildrenImpl(o);
                }
                
                @Override
                public List<Object> apply(Object o) {
    
                    if (o instanceof org.docx4j.wml.Text){
                        Text text = (org.docx4j.wml.Text) o;
                        if(text.getValue().indexOf("$")>=0){
                            textList.clear();
                            textList.add(text);
                            if(text.getValue().indexOf("}")>=0){
                                String fieldName = getFieldName(textList);
                                if(!FieldUtils.checkFieldName(fieldName,fields)){
                                    Field f = new Field();
                                    f.setName(fieldName);
                                    fields.add(f);
                                }
                                textList.clear();
                            }
                        }else if(textList.size()>0){
                            textList.add(text);
                            if(text.getValue().indexOf("}")>=0){
                                String fieldName = getFieldName(textList);
                                if(!FieldUtils.checkFieldName(fieldName,fields)){
                                    Field f = new Field();
                                    f.setName(fieldName);
                                    fields.add(f);
                                }
                                textList.clear();
                            }
                            
                        }
                    }
                    return null;
                }
    
                @Override
                public boolean shouldTraverse(Object o) {
                    return true;
                }
                
                private String getFieldName(List<Text> textList){
                    StringBuilder fieldName = new StringBuilder();
                    for(Text t : textList){
                        fieldName.append(t.getValue());
                    }
                    return fieldName.substring(2, fieldName.length()-1);
                }
                
    
            }

        );
        return fields;
     }


    /***
     * this method is to change the set the value of ${name} in the docxFile
     * @param docxFile
     * @param destinationDocxFile
     * @param values
     * @throws Docx4JException
     */
    public void fillAndSave(File docxFile, File destinationDocxFile, final List<FieldValues> values) throws Docx4JException{
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();
        Body body = wmlDocumentEl.getBody();
    
    
        new TraversalUtil(body,
            new Callback() {

                List<Text> textList = new ArrayList();
                // Depth first
                @Override
                public void walkJAXBElements(Object parent) {


                    List children = getChildren(parent);
                    if (children != null) {
                        for (Object o : children) {
                            // if its wrapped in javax.xml.bind.JAXBElement, get its
                            // value
                            o = XmlUtils.unwrap(o);

                            this.apply(o);

                            if (this.shouldTraverse(o)) {
                                walkJAXBElements(o);
                            }

                        }
                    }

                }

                @Override
                public List<Object> getChildren(Object o) {
                    return TraversalUtil.getChildrenImpl(o);
                }
            
                @Override
                public List<Object> apply(Object o) {

                    if (o instanceof org.docx4j.wml.Text){
                        Text text = (org.docx4j.wml.Text) o;
                        if(text.getValue().indexOf("$")>=0){
                            textList.clear();
                            textList.add(text);
                            if(text.getValue().indexOf("}")>=0){
                                int i = 0;
                                String value = getFieldValue(values,getFieldName(textList));
                                for(Text t : textList){
                                    if(i==0){
                                        t.setValue("  " + value + "  ");
                                    }else{
                                        t.setValue("");
                                    }
                                    i++;
                                }
                                textList.clear();
                            }
                        }else if(textList.size()>0){
                            textList.add(text);
                            if(text.getValue().indexOf("}")>=0){
                                int i = 0;
                                String value = getFieldValue(values,getFieldName(textList));
                                for(Text t : textList){
                                    if(i==0){
                                        t.setValue("  " + value + "  ");
                                    }else{
                                        t.setValue("");
                                    }
                                    i++;
                                }
                                textList.clear();
                            }
                        }
                    }
                    return null;
                }

                @Override
                public boolean shouldTraverse(Object o) {
                    return true;
                }
            
                private String getFieldValue(List<FieldValues> fieldValues, String name){
                    for(FieldValues field : fieldValues){
                        if(field.getName().equals(name)){
                            return field.getValue();
                        }
                    }
                    return "";
                }

                private String getFieldName(List<Text> textList){
                    StringBuilder fieldName = new StringBuilder();
                    for(Text t : textList){
                        fieldName.append(t.getValue());
                    }
                    return fieldName.substring(2, fieldName.length()-1);
                }
            }

        );
    
        wordMLPackage.save(destinationDocxFile);
    }
    
    /***
     * this method get the name of ${name} in the docxFile
     * this for the 04_simple.docx the style of ${} 
     * @param docxFile
     * @return
     * @throws Docx4JException
     */
    public List<Field> getAllFields(File docxFile) throws Docx4JException{
        List<Field> fields = new ArrayList<Field>();
        
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);           
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement(); 
                            
        //xml --> string
        String s = XmlUtils.marshaltoString(wmlDocumentEl, true);
        
        fields = FieldUtils.getField(s,0,new StringBuilder(),fields);
        
        return fields;
        
    }
   

}
