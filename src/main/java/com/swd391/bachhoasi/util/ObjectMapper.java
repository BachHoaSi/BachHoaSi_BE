package com.swd391.bachhoasi.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ObjectMapper {
    // public <S,T> T mapToEntity(S source, Class<T> targetClass) {
    //     try {
    //         Constructor<T> constructor = targetClass.getDeclaredConstructor();
    //         T target = constructor.newInstance();
    //         Collection<Field> sourceFields = List.of(source.getClass().getDeclaredFields());
    //         for(Field sourceField: sourceFields) {
    //             Field targetField = targetClass.getDeclaredField(sourceField.getName());
    //         }
    //         return target;
    //     }catch (Exception ex) {}
    // }
}
