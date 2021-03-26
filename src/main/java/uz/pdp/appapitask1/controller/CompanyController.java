package uz.pdp.appapitask1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapitask1.entity.Address;
import uz.pdp.appapitask1.entity.Company;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.payload.CompanyDto;
import uz.pdp.appapitask1.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    @Autowired
    CompanyService companyService;


    //BARCHA COMPANYLAR
    @GetMapping("/api/company")
    public ResponseEntity<List<Company>> getCompany() {
        List<Company> companyList = companyService.getCompany();
        return ResponseEntity.ok(companyList);
    }


    //ID ORQALI BITTA COMPANY
    @GetMapping("/api/company/{id}")
    public HttpEntity<Company> getCompany(@PathVariable Integer id) {
        Company companyById = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyById);
    }



    //COMPANY QO`SHISH
    @PostMapping("/api/company")
    public HttpEntity<ApiResponse> save(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);

    }

    // tahrirlash
    @PutMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    //O`CHIRISH ID ORQALI
    @DeleteMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id) {
        ApiResponse apiResponse = companyService.deleteCompany(id);

        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
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
