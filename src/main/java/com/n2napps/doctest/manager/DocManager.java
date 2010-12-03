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



public class DocManager {
    
    
    public void docxToHtml(File docxFile,String outputFileString) throws Exception{                 
        // Load .docx
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile); 
        AbstractHtmlExporter exporter = new HtmlExporterNG2();                        
                          
        OutputStream os = new java.io.FileOutputStream(outputFileString+"testDocToHtml.html"); 
                          
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(os); 
        exporter.html(wordMLPackage, result,outputFileString+"test_files"); 
        
    }
    
    
//    public void replace(File docxFile,final String textToReplace,final String newText) throws Docx4JException{
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxFile);
//        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
//
//        org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();
//        Body body = wmlDocumentEl.getBody();
//    
//    
//        new TraversalUtil(body,
//            new Callback() {
//
//                List<Text> textList = new ArrayList();
//                // Depth first
//                @Override
//                public void walkJAXBElements(Object parent) {
//
//                    List children = getChildren(parent);
//                    if (children != null) {
//                        for (Object o : children) {
//                            o = XmlUtils.unwrap(o);
//
//                            this.apply(o);
//
//                            if (this.shouldTraverse(o)) {
//                                walkJAXBElements(o);
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public List<Object> getChildren(Object o) {
//                    return TraversalUtil.getChildrenImpl(o);
//                }
//            
//                @Override
//                public List<Object> apply(Object o) {
//                    if (o instanceof org.docx4j.wml.Text){
//                        Text text = (org.docx4j.wml.Text) o;
//                        String textStr = text.getValue();
//                        int testIndexofValue = textStr.indexOf(textToReplace.charAt(0));
//                        if(testIndexofValue >= 0){
//                            textStr = textStr.substring(testIndexofValue);
//                            if(textStr.length() == textToReplace.length()){
//                                if(textStr.equals(textToReplace)){
//                                    textList.clear();
//                                    textList.add(text);
//                                    int i = 0;
//                                    for(Text t : textList){
//                                        if(i==0){
//                                            t.setValue("  " + newText + "  ");
//                                        }else{
//                                            t.setValue("");
//                                        }
//                                        i++;
//                                    }
//                                    textList.clear();
//                                }
//                            }else if(textStr.length() > textToReplace.length()){
//                                textStr = textStr.substring(testIndexofValue,textToReplace.length());
//                                
//                            }else{
//                                
//                            }
//                        }
//                    }
//                    return null;
//                }
//
//                @Override
//                public boolean shouldTraverse(Object o) {
//                    return true;
//                }
//            
//            }
//
//        );
//    
//        wordMLPackage.save(docxFile);
//    }

}