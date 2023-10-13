package com.mrgenius.learningjava.ToDoList.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNoNullProperties(Object source, Object target ){
        BeanUtils.copyProperties(source, target, getNullPropryteNames(source));
    }
    
    public static String [] getNullPropryteNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
        src.getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor[] = src.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for(PropertyDescriptor property : propertyDescriptor ){
           Object srcValues = src.getPropertyValue(property.getName());
           if(srcValues == null){
                emptyName.add(property.getName());
           }
        }
        String result []= new String[emptyName.size()];
        return emptyName.toArray(result);
    }
}
