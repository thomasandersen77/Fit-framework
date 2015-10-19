package com.github.fit.mongo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.mongodb.morphia.converters.ConverterException;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

public class LocalDateConverter extends TypeConverter implements SimpleValueConverter {

    public LocalDateConverter() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate decode(Class<?> targetClass, Object val, MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }

        if(val instanceof Date) {
            Date date = (Date) val;
            Instant instant = Instant.ofEpochMilli(date.getTime());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        } else {
            throw new ConverterException("Kunne ikke konvertere fra [Date] til [LocalDate]");
        }
    }

    @Override
    public Date encode(Object value, MappedField optionalExtraInfo) {
        if(value == null) return null;

        if(value instanceof LocalDate) {
            Instant instant = ((LocalDate)value).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }
        throw new ConverterException("Kunne ikke konvertere from [LocalDate] to [Date]");
    }
}
