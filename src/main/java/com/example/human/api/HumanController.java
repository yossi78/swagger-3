package com.example.human.api;

import com.example.human.dto.Human;
import com.example.human.services.HumanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping(value = "/v1/humans")
@Tag(name="Tag - Human-Controller", description="Tag - Human-Controller-Description")
public class HumanController {


    private static final Logger logger = LoggerFactory.getLogger(HumanController.class);
    private HumanService humanService;


    @Autowired
    public HumanController(HumanService humanService){
        this.humanService=humanService;
    }


    @GetMapping(value = "/health")
    @Operation(summary = "Operation - Health Check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity healthCheck() {
        try {
            return new ResponseEntity("Human Service Health is OK", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    @Operation(summary = "Operation - Add Human")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "ApiResponse - Added successfully the human",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Human.class)) }),
        @ApiResponse(responseCode = "400", description = "ApiResponse - You send a Bad Request",
            content = @Content)
    })
    public ResponseEntity addHuman(@Parameter(description = "human",required = true)
        @RequestBody Human human) {
        try {
            humanService.addHuman(human);
            logger.info("Human has been added successfully");
            return new ResponseEntity(human,HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Failed to add new Human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping
    @Operation(summary = "Operation - get All Humans")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAllHumans() {
        try {
            List<Human> humans = humanService.getAllHumans();
            if (humans==null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(humans);
        }catch (Exception e){
            logger.error("Failed to find all humans",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Operation - Update Human")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateHuman(@Parameter(description = "id",required = true) @PathVariable Long id ,
        @Parameter(description = "human",required = true)@RequestBody Human human) {
        try {
            logger.info("Update Human : "+ human);
            Boolean isUpdated = humanService.updateHuman(human,id);
            if(!isUpdated){
                logger.warn("The human not exists and can not be updated");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to update human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Operation - Delete Human")
    public ResponseEntity deleteHuman(@Parameter(description = "id",required = true)
    @PathVariable Long id) {
        try {
            logger.info("Delete Human by Id: "+ id);
            Boolean isRemoved = humanService.removeHuman(id);
            if(!isRemoved){
                logger.warn("The human not exists and can not be deleted");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to delete human",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
