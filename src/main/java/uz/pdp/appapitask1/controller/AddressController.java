package uz.pdp.appapitask1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appapitask1.entity.Address;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

   @GetMapping("/api/address")
   public ResponseEntity<List<Address>> getAddresses() {
       List<Address> addresses = addressService.getAddresses();
       return ResponseEntity.ok(addresses);
   }


    @GetMapping("/api/address/{id}")
    public HttpEntity<Address> getAddresses(@PathVariable Integer id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }



    @PostMapping("/api/address")
    public HttpEntity<ApiResponse> save(@Valid  @RequestBody Address address){
        ApiResponse apiResponse = addressService.addAddress(address);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);

    }

    // tahrirlash
    @PutMapping("/api/address/{id}")
    public ApiResponse editAddress(@PathVariable Integer id, @Valid @RequestBody Address address) {
        ApiResponse apiResponse = addressService.editAddress(id, address);
        return apiResponse;
    }

    //O`CHIRISH ID ORQALI
    @DeleteMapping("/api/address/{id}")
    public ApiResponse deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return apiResponse;
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
