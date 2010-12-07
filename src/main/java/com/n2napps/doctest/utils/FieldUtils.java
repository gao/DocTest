package com.n2napps.doctest.utils;

import java.util.List;

import com.n2napps.doctest.Field;

public class FieldUtils {
    
    public static List<Field> getField(String s, int offset, StringBuilder b,List<Field> fields) {
        int startKey = s.indexOf("${", offset);
        if (startKey == -1)
           return fields;
        else {
           b.append(s.substring(offset, startKey));
           int keyEnd = s.indexOf('}', startKey);
           String key = s.substring(startKey + 2, keyEnd);
           if(!checkFieldName(key,fields)){
               Field f = new Field();
               f.setName(key);
               fields.add(f);
           }
           return getField(s, keyEnd + 1, b, fields);
        }
     }
    
    
    public static boolean checkFieldName(String filedName,List<Field> fields){
        boolean isHave = false;
        for(int i=0;i<fields.size();i++){
            if(fields.get(i).getName().equals(filedName)){
                isHave = true;
            }
        }
        return isHave;
    }

}
