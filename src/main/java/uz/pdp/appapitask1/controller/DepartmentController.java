package uz.pdp.appapitask1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapitask1.entity.Address;
import uz.pdp.appapitask1.entity.Department;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.payload.DepartmentDto;
import uz.pdp.appapitask1.service.AddressService;
import uz.pdp.appapitask1.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/api/department")
    public ResponseEntity<List<Department>> getDepartments() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }


    @GetMapping("/api/department/{id}")
    public HttpEntity<Department> getDepartment(@PathVariable Integer id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }



    @PostMapping("/api/department")
    public HttpEntity<ApiResponse> save(@Valid  @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);

    }

    // tahrirlash
    @PutMapping("/api/department/{id}")
    public ApiResponse editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return apiResponse;
    }

    //O`CHIRISH ID ORQALI
    @DeleteMapping("/api/department/{id}")
    public ApiResponse deleteDepartment(@PathVariable Integer id) {
        return departmentService.deleteDepartment(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
