package com.example.ASMapi.controller;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/delete/student/{id}")
    public ResponseEntity<IdResponse> deleteStudentUser(@PathVariable Long id){
        return new ResponseEntity(adminService.deleteStudentRecord(id), HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<AppUserDto>> getAllStudents(){
        return ResponseEntity.ok().body(adminService.getAllStudentRecords());
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        return ResponseEntity.ok().body(adminService.getAllRecords());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<AppUserDto> getStudentUser(@PathVariable Long id){
        return ResponseEntity.ok().body(adminService.getStudentRecordById(id));
    }
}
