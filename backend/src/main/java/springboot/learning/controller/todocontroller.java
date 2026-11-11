package springboot.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.learning.models.tododata;
import springboot.learning.service.todoservice;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class todocontroller {

    @Autowired
   private todoservice todoservice;

    @GetMapping
    ResponseEntity<List<tododata>> getalltodo(){
        return new ResponseEntity<List<tododata>>(todoservice.getalltodo(),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    ResponseEntity<tododata> todobyid(@PathVariable Long id){
        try {
            tododata todobyid = todoservice.gettodobyid(id);
            return new ResponseEntity<>(todobyid,HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>((HttpHeaders) null,HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/create")
    ResponseEntity<tododata> createtodo(@RequestBody tododata data){
      return   new ResponseEntity<>(todoservice.createtodo(data), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<tododata> updatetodo(@RequestBody tododata data){
        return   new ResponseEntity<>(todoservice.updatetodo(data), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deletebyid(@PathVariable Long id){
        todoservice.deletebyid(id);
    }








}
