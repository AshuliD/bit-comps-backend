package com.bit.backend.mappers;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.ItemRegDTO;
import com.bit.backend.dtos.JobRoleDto;
import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.ItemRegEntitiy;
import com.bit.backend.entities.JobRoleEntitiy;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

//convert entity to dto and dto to entity
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemRegMapper {

     ItemRegEntitiy toItemRegEntity(ItemRegDTO itemRegDTO);
     ItemRegDTO toItemRegDto(ItemRegEntitiy itemRegEntitiy);
     List<ItemRegDTO>  toItemRegDtoList(List<ItemRegEntitiy> itemRegEntitiyList);
//     List<JobRoleDto> toJobRoleDtoList(List<JobRoleEntitiy> jobRoleEntitiyList);
}
