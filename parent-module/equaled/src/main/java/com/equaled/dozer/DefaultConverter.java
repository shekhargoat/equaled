package com.equaled.dozer;

import com.equaled.entity.BaseEntity;
import org.dozer.DozerConverter;


public class DefaultConverter extends DozerConverter<byte[],String> {

    public DefaultConverter(){
        super(byte[].class,String.class);
    }
    @Override
    public String convertTo(byte[] source, String destination) {
        if(source!=null)return BaseEntity.bytesToHexStringBySid(source);
        return null;
    }

    @Override
    public byte[] convertFrom(String source, byte[] destination) {
        if(source!=null) return BaseEntity.hexStringToByteArray(source);
        return BaseEntity.generateByteUuid();
    }

}
 