package com.bit.backend.services;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.ItemRegDTO;
import com.bit.backend.dtos.JobRoleDto;

import java.util.List;

public interface FormDemoServiceI {

    //this is interface and its body is in FormDemoService Class
    //ask why need intarface and class
    FormDemoDto addFormDemoEntity(FormDemoDto formDemoDto);
    List<FormDemoDto> getData();
    List<JobRoleDto> getJobRole();
    FormDemoDto updateFormDemo(long id, FormDemoDto formDemoDto);

    FormDemoDto deleteFormDemo(long id);

}
