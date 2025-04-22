package com.bit.backend.controllers;

 import com.bit.backend.dtos.GRNDTO;
 import com.bit.backend.dtos.ItemRegDTO;
 import com.bit.backend.exceptions.AppException;
import com.bit.backend.services.GRNServiceI;
import com.bit.backend.services.ItemRegServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@RestController
public class GRNControlloer {

    //FormDemoServiceI type variable
     private final GRNServiceI grnServiceI;
     private final ItemRegServiceI itemRegServiceI;

     //Dependency Injection (constructor injection)- Call constructor - assign value to the variable
    public GRNControlloer(GRNServiceI grnServiceI, ItemRegServiceI itemRegServiceI) {
        this.grnServiceI = grnServiceI;
        this.itemRegServiceI = itemRegServiceI;
    }

    //first create DTO and Entitiy classes with constructors and getter setter
     //when front end request coming like -  /form-demo in url -  addForm method with ResponseEntity return type is  called
     //@RequestBody annotation = response coming from frontend ( JSON type) convert to formDemoDto type object
    @PostMapping("/grn")
    public ResponseEntity<GRNDTO> addForm(@RequestBody GRNDTO grndto){

        GRNDTO grndtoresponse = grnServiceI.addGRNEntity(grndto);
         return ResponseEntity.created(URI.create("/grn"+grndtoresponse.getGrnno())).body(grndtoresponse);
//       return ResponseEntity.ok().body(formDemoDtoResponse);
    }

    @GetMapping("/allgrn")
    public ResponseEntity<List<GRNDTO>> getGRNDTOs(){
        try{
            List<GRNDTO> grnDtoList = grnServiceI.getData();
            return ResponseEntity.ok().body(grnDtoList);
        } catch (Exception e) {
            throw new AppException(" Get mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/grn")
    public ResponseEntity<List<GRNDTO>> getGRNs(){
         try{
             List<GRNDTO> grnDtoList = grnServiceI.getData();
             return ResponseEntity.ok().body(grnDtoList);
         } catch (Exception e) {
             throw new AppException(" Get mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
//
    @GetMapping("/grn/get-item")
    public ResponseEntity<List<ItemRegDTO>> getItems(){
         List<ItemRegDTO> itemDtoList = itemRegServiceI.getData();
        return ResponseEntity.ok().body(itemDtoList);
    }
//
@PutMapping("/outeredit/{id}")
public ResponseEntity<GRNDTO> updateOuterGRN(@PathVariable long id, @RequestBody GRNDTO grnDTO){
    GRNDTO responsegrndto = grnServiceI.updateOuterGRN(id,grnDTO);
    return ResponseEntity.ok(responsegrndto);
}
//
//
    @DeleteMapping("/deleteOuter/{id}")
    public ResponseEntity<GRNDTO> deleteOuterGRN(@PathVariable long id){
        GRNDTO grndto = grnServiceI.deleteOuterGRN(id);
        return ResponseEntity.ok(grndto);
    }



}
