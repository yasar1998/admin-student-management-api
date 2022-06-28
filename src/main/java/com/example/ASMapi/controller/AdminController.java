package com.example.ASMapi.controller;

import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/delete/student/{id}")
    public ResponseEntity<IdResponse> deleteUser(@PathVariable Long id){
        return new ResponseEntity(adminService.deleteRecord(id), HttpStatus.OK);
    }

}
