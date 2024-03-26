package com.equaled.dozer;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DozerUtils {
    DozerBeanMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(DozerUtils.class);

    @PostConstruct
    private void init(){
        mapper=new DozerBeanMapper();
        List<String> mappingList = new ArrayList<String>();
        mappingList.add("dozerBeanMapping.xml");
        mapper.setMappingFiles(mappingList);
    }
    public <T> T convert(Object srcObject, Class<T> destnationObj){
        try{
            return (T) mapper.map(srcObject, destnationObj);
        }catch(Exception exception){
            logger.error("while converting source to distination, throwing error",exception);
            return null;
        }
    }

    public <T> T convert(Object srcObject, Class<T> destnationObj,String mapId){
        try{
            return (T) mapper.map(srcObject, destnationObj, mapId);
        }catch(Exception exception){
            logger.error("while converting source to distination, throwing error",exception);
            return null;
        }
    }
    public <T, U> List<T> convertList(List<U> srcObjects, Class<T> destnationObjClass,String mapId){
        List<T> list = null;
        try {
            if (srcObjects != null) {
                list = new ArrayList<T>();
                if (mapId == null) {
                    for (U srcObject : srcObjects) {
                        list.add(convert(srcObject, destnationObjClass));
                    }
                } else {
                    for (U srcObject : srcObjects) {
                        list.add(convert(srcObject, destnationObjClass, mapId));
                    }
                }
            } else {
                logger.error("List of object is null {}",srcObjects);
            }
        }catch (Exception e) {
            logger.error("while converting list of source to list of distination, throwing error",e);
        }
        return list;
    }
    public <T, U> List<T> convertList(List<U> srcObjects, Class<T> destnationObjClass){
        return convertList(srcObjects,destnationObjClass,null);
    }
}
