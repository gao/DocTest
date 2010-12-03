package com.n2napps.doctest.manager;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.Callback;
import org.docx4j.convert.out.html.AbstractHtmlExporter;
import org.docx4j.convert.out.html.HtmlExporterNG2;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Text;

import com.n2napps.doctest.Field;
import com.n2napps.doctest.FieldValues;


public class DocManager {
    
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
								if(!checkFieldName(fieldName,fields)){
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
								if(!checkFieldName(fieldName,fields)){
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
				
				private boolean checkFieldName(String filedName,List<Field> fields){
                    boolean isHave = false;
				    for(int i=0;i<fields.size();i++){
				        if(fields.get(i).getName().equals(filedName)){
				            isHave = true;
				        }
                    }
				    return isHave;
                }
	
			}

		);
		return fields;
     }
    
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
    
    public void docxToHtml(File docxFile,String outputFileString) throws Exception{                 
        // Load .docx
        //this need : commons-io-1.3.1.jar commons-logging-1.1.1.jar serializer-2.7.1.jar xalan-2.7.1.jar
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile); 
        AbstractHtmlExporter exporter = new HtmlExporterNG2();                        
                          
        OutputStream os = new java.io.FileOutputStream(outputFileString+"testDocToHtml.html"); 
                          
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(os); 
        exporter.html(wordMLPackage, result,outputFileString+"test_files"); 
        
    }

}