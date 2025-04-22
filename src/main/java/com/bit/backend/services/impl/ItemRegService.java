package com.bit.backend.services.impl;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.ItemRegDTO;
import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.ItemRegEntitiy;
import com.bit.backend.mappers.ItemRegMapper;
import com.bit.backend.repositories.ItemRegRepository;
import com.bit.backend.services.FormDemoServiceI;
import com.bit.backend.services.ItemRegServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

//class of the interface
@Service
public class ItemRegService implements ItemRegServiceI {

    //create repository object and mapper object
    private final ItemRegRepository itemRegRepository;
    private final ItemRegMapper itemRegMapper;
//    private final JobRoleRepository jobRoleRepository;

    //why put them into the constructor ask mendi?

    public ItemRegService(ItemRegRepository itemRegRepository, ItemRegMapper itemRegMapper) {
        this.itemRegRepository = itemRegRepository;
        this.itemRegMapper = itemRegMapper;
    }

    //override the method of interface
    //return type and parameters are FormDemoDto type
    @Override
    public ItemRegDTO addItemRegEntity(ItemRegDTO itemRegDTO) {
        System.out.println("*************In Backend***************");

        //need to convert dto to entity type to save from repository
        ItemRegEntitiy itemRegEntitiy = itemRegMapper.toItemRegEntity(itemRegDTO);

        //save from repository
        ItemRegEntitiy savedItem = itemRegRepository.save(itemRegEntitiy);

        //return type is dto and again convert from entity to dto
        ItemRegDTO savedDto = itemRegMapper.toItemRegDto(savedItem);
        return savedDto;
    }




    @Override
    public List<ItemRegDTO> getData() {
        List<ItemRegEntitiy> itemRegEntitiyList = itemRegRepository.findAll();
        List<ItemRegDTO> itemRegDTOList = itemRegMapper.toItemRegDtoList(itemRegEntitiyList);
        return itemRegDTOList;
    }
//
//    @Override
//    public List<JobRoleDto> getJobRole() {
//        List<JobRoleEntitiy> jobRoleEntitiyList = jobRoleRepository.getJobRole();
//        List<JobRoleDto> JobRoleDtoList = formDemoMapper.toJobRoleDtoList(jobRoleEntitiyList);
//        return JobRoleDtoList;
//    }

//    @Override
//    public FormDemoDto updateFormDemo(long id, FormDemoDto formDemoDto) {
//
//       Optional<FormDemoEntitiy> opformDemoEntity =  formDemoRepository.findById(id);
//
//       if (!opformDemoEntity.isPresent()) {
//           throw  new AppException("form demo entity not exists", HttpStatus.BAD_REQUEST);
//       }
//
//        //need to convert dto to entity type to save from repository
//        FormDemoEntitiy newformDemoEntitiy = formDemoMapper.toFormDemoEntity(formDemoDto);
//
//        //save from repository
//        newformDemoEntitiy.setId(id);
//        FormDemoEntitiy formDemoEntitiy = formDemoRepository.save(newformDemoEntitiy);
//
//        //return type is dto and again convert from entity to dto
//        FormDemoDto savedDto = formDemoMapper.toFormDemoDto(formDemoEntitiy);
//        return savedDto;
//    }
//
//    @Override
//    public  FormDemoDto deleteFormDemo(long id) {
//
//        Optional<FormDemoEntitiy> opformDemoEntity =  formDemoRepository.findById(id);
//
//        if (!opformDemoEntity.isPresent()) {
//            throw  new AppException("form demo entity not exists", HttpStatus.BAD_REQUEST);
//        }
//
//         formDemoRepository.deleteById(id);
//
//        FormDemoDto deleteDTO = formDemoMapper.toFormDemoDto(opformDemoEntity.get());
//
//        return deleteDTO;
//    }
}
