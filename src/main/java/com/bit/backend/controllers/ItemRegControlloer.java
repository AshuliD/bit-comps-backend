package com.bit.backend.controllers;

import com.bit.backend.dtos.FormDemoDto;
import com.bit.backend.dtos.GRNaddedDTO;
import com.bit.backend.dtos.ItemRegDTO;
import com.bit.backend.dtos.StockDTO;
import com.bit.backend.exceptions.AppException;
import com.bit.backend.services.ItemRegServiceI;
import com.bit.backend.services.StockServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
public class ItemRegControlloer {

    //FormDemoServiceI type variable
     private final ItemRegServiceI itemRegServiceI;
     private final StockServiceI stockServiceI;

     //Dependency Injection (constructor injection)- Call constructor - assign value to the variable
    public ItemRegControlloer(ItemRegServiceI itemRegServiceI, StockServiceI stockServiceI) {
        this.itemRegServiceI = itemRegServiceI;
        this.stockServiceI = stockServiceI;
    }

    //first create DTO and Entitiy classes with constructors and getter setter
     //when front end request coming like -  /form-demo in url -  addForm method with ResponseEntity return type is  called
     //@RequestBody annotation = response coming from frontend ( JSON type) convert to formDemoDto type object
    @PostMapping("/item-reg")
    public ResponseEntity<ItemRegDTO> addForm(@RequestBody ItemRegDTO itemRegDTO){

        ItemRegDTO itemRegDtoResponse = itemRegServiceI.addItemRegEntity(itemRegDTO);
         return ResponseEntity.created(URI.create("/form-demo"+itemRegDtoResponse.getId())).body(itemRegDtoResponse);
//       return ResponseEntity.ok().body(formDemoDtoResponse);
    }


    @GetMapping("/getQty/{itemID}")
    public ResponseEntity<StockDTO> getQty(@PathVariable long itemID){
        try{

            StockDTO stockDTO = stockServiceI.getQty(itemID);
//            if (stockDTO == null) {
//                // Return 0 if no quantity found
//            }

            return ResponseEntity.ok().body(stockDTO);
        } catch (Exception e) {
            throw new AppException(" Get mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/stock")
    public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO stockDTO){

        StockDTO stockaddedresponse = stockServiceI.addStock(stockDTO);
        return ResponseEntity.created(URI.create("/stock"+stockaddedresponse.getId())).body(stockaddedresponse);
    }

    @GetMapping("/item")
    public ResponseEntity<List<ItemRegDTO>> getItems(){
         try{
             List<ItemRegDTO> itemRegDTOList = itemRegServiceI.getData();
             return ResponseEntity.ok().body(itemRegDTOList);
         } catch (Exception e) {
             throw new AppException(" Get mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
//
//    @GetMapping("/form-demo/get-job-role")
//    public ResponseEntity<List<JobRoleDto>> getJobRole(){
//         List<JobRoleDto> JobRoleDtoList = formDemoServiceI.getJobRole();
//        return ResponseEntity.ok().body(JobRoleDtoList);
//    }
//
    //stock update
@PutMapping("/stockupdate")
public ResponseEntity<List<StockDTO>> updateStock(@RequestBody List<GRNaddedDTO> grNaddedDTOS) {
    try {
            List<StockDTO> stockDTOlist = stockServiceI.updateStock(grNaddedDTOS);
            return ResponseEntity.ok().body(stockDTOlist);
        } catch (Exception e) {
        throw new AppException(" PUT mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    //stock update
    @PutMapping("/stockupdateEdit")
    public ResponseEntity<StockDTO> updateStockEdit(@RequestBody GRNaddedDTO grNaddedDTOS) {
        try {
            StockDTO stockDTOlist = stockServiceI.updateStockEdit(grNaddedDTOS);
            return ResponseEntity.ok().body(stockDTOlist);
        } catch (Exception e) {
            throw new AppException(" PUT mapping Failed " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//
//
//    @DeleteMapping("/form-demo/{id}")
//    public ResponseEntity<FormDemoDto> deleteFormDemo(@PathVariable long id){
//        FormDemoDto formDemoDto = formDemoServiceI.deleteFormDemo(id);
//        return ResponseEntity.ok(formDemoDto);
//    }

}
