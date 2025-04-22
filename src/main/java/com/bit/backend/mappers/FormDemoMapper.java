package com.bit.backend.mappers;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.JobRoleDto;
import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.JobRoleEntitiy;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import java.util.List;

//convert entity to dto and dto to entity
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FormDemoMapper {

     FormDemoEntitiy toFormDemoEntity(FormDemoDto formDemoDto);
     FormDemoDto toFormDemoDto(FormDemoEntitiy formDemoEntitiy);
     List<FormDemoDto>  toFormDemoDtoList(List<FormDemoEntitiy> formDemoEntitiyList);
     List<JobRoleDto> toJobRoleDtoList(List<JobRoleEntitiy> jobRoleEntitiyList);
}
