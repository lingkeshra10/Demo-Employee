package com.lingkesh.employeeAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lingkesh.employeeAPI.entity.Employee;
import com.lingkesh.employeeAPI.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(ObjectMapper objectMapper, EmployeeService employeeService) {
        this.objectMapper = objectMapper;
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeService.findAll();
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployeeId(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);
        
        if(theEmployee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        return theEmployee;
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0);
        return employeeService.save(theEmployee);
    }

    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        return employeeService.save(theEmployee);
    }

    @PatchMapping("/employee/{employeeId}")
    public Employee patchEmployeeDetails(@PathVariable int employeeId,
                                         @RequestBody Map<String, Object> patchPayload){
        Employee tempEmployee = employeeService.findById(employeeId);

        if(tempEmployee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        if(patchPayload.containsKey("id")){
            throw new RuntimeException("Employee id not allowed in request body: " + employeeId);
        }

        Employee patchEmployee = apply(patchPayload, tempEmployee);

        return employeeService.save(patchEmployee);
    }

    private Employee apply(Map<String, Object> patchPayload, Employee tempEmployee) {
        // Convert employee object to a JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee, ObjectNode.class);

        // Convert the patchPayload map to a JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        // Merge the patch updates into the employee node
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }

    @DeleteMapping("/employee/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);

        if(theEmployee == null){
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Deleted the employee Id: " + employeeId;
    }

}
