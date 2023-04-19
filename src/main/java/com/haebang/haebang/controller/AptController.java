package com.haebang.haebang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("api/apt")
public class AptController {
    @GetMapping("list")
    public ResponseEntity getAptItemList(){
        return null;
    }

    @PostMapping()
    public ResponseEntity createAptItem(){
        return null;
    }

    @PutMapping()
    public ResponseEntity editAptItem(){
        return null;
    }

    @DeleteMapping()
    public ResponseEntity deleteApt(){
        return null;
    }

}
