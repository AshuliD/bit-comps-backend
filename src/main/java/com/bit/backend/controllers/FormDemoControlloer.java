package com.bit.backend.controllers;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.JobRoleDto;
import com.bit.backend.exceptions.AppException;
import com.bit.backend.services.FormDemoServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
public class FormDemoControlloer {

    //FormDemoServiceI type variable
     private final FormDemoServiceI formDemoServiceI;

     //Dependency Injection (constructor injection)- Call constructor - assign value to the variable
     public FormDemoControlloer(FormDemoServiceI formDemoServiceI) {
         this.formDemoServiceI = formDemoServiceI;
     }


    //first create DTO and Entitiy classes with constructors and getter setter
     //when front end request coming like -  /form-demo in url -  addForm method with ResponseEntity return type is  called
     //@RequestBody annotation = response coming from frontend ( JSON type) convert to formDemoDto type object
    @PostMapping("/form-demo")
    public ResponseEntity<FormDemoDto> addForm(@RequestBody FormDemoDto formDemoDto){

        FormDemoDto formDemoDtoResponse = formDemoServiceI.addFormDemoEntity(formDemoDto);
         return ResponseEntity.created(URI.create("/form-demo"+formDemoDtoResponse.getFirstName())).body(formDemoDtoResponse);
//       return ResponseEntity.ok().body(formDemoDtoResponse);
    }

    @GetMapping("/form-demo")
    public ResponseEntity<List<FormDemoDto>> getFormDemo(){
         try{
             List<FormDemoDto> formDemoDtoList = formDemoServiceI.getData();
             return ResponseEntity.ok().body(formDemoDtoList);
         } catch (Exception e) {
             throw new AppException(" Get mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    @GetMapping("/form-demo/get-job-role")
    public ResponseEntity<List<JobRoleDto>> getJobRole(){
         List<JobRoleDto> JobRoleDtoList = formDemoServiceI.getJobRole();
        return ResponseEntity.ok().body(JobRoleDtoList);
    }

    @PutMapping("/form-demo/{id}")
    public ResponseEntity<FormDemoDto> updateFormDemo(@PathVariable long id, @RequestBody FormDemoDto formDemoDto){
          FormDemoDto responseFormDemoDto = formDemoServiceI.updateFormDemo(id,formDemoDto);
          return ResponseEntity.ok(responseFormDemoDto);
    }


    @DeleteMapping("/form-demo/{id}")
    public ResponseEntity<FormDemoDto> deleteFormDemo(@PathVariable long id){
        FormDemoDto formDemoDto = formDemoServiceI.deleteFormDemo(id);
        return ResponseEntity.ok(formDemoDto);
    }

}
