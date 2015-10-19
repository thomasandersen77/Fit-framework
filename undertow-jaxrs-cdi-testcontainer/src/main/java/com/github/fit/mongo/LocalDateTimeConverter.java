package com.github.fit.mongo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.mongodb.morphia.converters.ConverterException;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

public class LocalDateTimeConverter extends TypeConverter implements SimpleValueConverter {

    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    @Override
    public Object decode(Class<?> targetClass, Object val, MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if(val instanceof Date) {
            Instant instant = Instant.ofEpochMilli(((Date) val).getTime());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } else {
            throw new ConverterException("Kunne ikke konvertere fra String til LocalDateTime");
        }
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if(value == null) return null;
        
        if(value instanceof LocalDateTime) {
            Instant instant = ((LocalDateTime)value).atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }
        throw new ConverterException("Kunne ikke konvertere from [LocalDateTime] to [String]");
    }
}
