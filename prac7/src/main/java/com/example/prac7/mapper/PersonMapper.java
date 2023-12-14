package com.example.prac7.mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.example.prac7.dto.PersonDTO;
import com.example.prac7.entity.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO map(PersonEntity entity);

    @InheritInverseConfiguration
    PersonEntity map(PersonDTO dto);
}
