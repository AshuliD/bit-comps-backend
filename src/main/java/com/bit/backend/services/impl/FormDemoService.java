package com.bit.backend.services.impl;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.JobRoleDto;
import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.JobRoleEntitiy;
import com.bit.backend.exceptions.AppException;
import com.bit.backend.mappers.FormDemoMapper;
import com.bit.backend.repositories.FormDemoRepository;
import com.bit.backend.repositories.JobRoleRepository;
import com.bit.backend.services.FormDemoServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//class of the interface
@Service
public class FormDemoService implements FormDemoServiceI {

    //create repository object and mapper object
    private final FormDemoRepository formDemoRepository;
    private final FormDemoMapper formDemoMapper;
    private final JobRoleRepository jobRoleRepository;

    //why put them into the constructor ask mendi?
    public FormDemoService(FormDemoRepository formDemoRepository, FormDemoMapper formDemoMapper, JobRoleRepository jobRoleRepository) {
        this.formDemoRepository = formDemoRepository;
        this.formDemoMapper = formDemoMapper;
        this.jobRoleRepository = jobRoleRepository;
    }

    //override the method of interface
    //return type and parameters are FormDemoDto type
    @Override
    public FormDemoDto addFormDemoEntity(FormDemoDto formDemoDto) {
        System.out.println("*************In Backend***************");

        //need to convert dto to entity type to save from repository
        FormDemoEntitiy formDemoEntitiy = formDemoMapper.toFormDemoEntity(formDemoDto);

        //save from repository
        FormDemoEntitiy savedItem = formDemoRepository.save(formDemoEntitiy);

        //return type is dto and again convert from entity to dto
        FormDemoDto savedDto = formDemoMapper.toFormDemoDto(savedItem);
        return savedDto;
    }

    @Override
    public List<FormDemoDto> getData() {
        List<FormDemoEntitiy> formDemoEntitiyList = formDemoRepository.findAll();
        List<FormDemoDto> formDemoDtoList = formDemoMapper.toFormDemoDtoList(formDemoEntitiyList);
        return formDemoDtoList;
    }

    @Override
    public List<JobRoleDto> getJobRole() {
        List<JobRoleEntitiy> jobRoleEntitiyList = jobRoleRepository.getJobRole();
        List<JobRoleDto> JobRoleDtoList = formDemoMapper.toJobRoleDtoList(jobRoleEntitiyList);
        return JobRoleDtoList;
    }

    @Override
    public FormDemoDto updateFormDemo(long id, FormDemoDto formDemoDto) {

       Optional<FormDemoEntitiy> opformDemoEntity =  formDemoRepository.findById(id);

       if (!opformDemoEntity.isPresent()) {
           throw  new AppException("form demo entity not exists", HttpStatus.BAD_REQUEST);
       }

        //need to convert dto to entity type to save from repository
        FormDemoEntitiy newformDemoEntitiy = formDemoMapper.toFormDemoEntity(formDemoDto);

        //save from repository
        newformDemoEntitiy.setId(id);
        FormDemoEntitiy formDemoEntitiy = formDemoRepository.save(newformDemoEntitiy);

        //return type is dto and again convert from entity to dto
        FormDemoDto savedDto = formDemoMapper.toFormDemoDto(formDemoEntitiy);
        return savedDto;
    }

    @Override
    public  FormDemoDto deleteFormDemo(long id) {

        Optional<FormDemoEntitiy> opformDemoEntity =  formDemoRepository.findById(id);

        if (!opformDemoEntity.isPresent()) {
            throw  new AppException("form demo entity not exists", HttpStatus.BAD_REQUEST);
        }

         formDemoRepository.deleteById(id);

        FormDemoDto deleteDTO = formDemoMapper.toFormDemoDto(opformDemoEntity.get());

        return deleteDTO;
    }
}
