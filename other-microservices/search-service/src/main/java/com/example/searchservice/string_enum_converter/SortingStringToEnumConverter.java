package com.example.searchservice.string_enum_converter;

import com.example.searchservice.enums.Sorting;
import com.example.searchservice.exceptions.NotAllowedOperationException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class SortingStringToEnumConverter implements Converter<String, Sorting> {
    /**
     * Converts string into boolean
     *
     * @param source    enum string to be converted
     * @return          enum or null
     */
    @Override
    public Sorting convert(String source) {
        try{
            return Sorting.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){

            throw new NotAllowedOperationException(
                    "Could not convert " + source + " to sorting mode"
            );

        }
    }
}
