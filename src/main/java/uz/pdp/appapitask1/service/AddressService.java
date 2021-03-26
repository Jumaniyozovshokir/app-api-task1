package uz.pdp.appapitask1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appapitask1.entity.Address;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    //BARCHA ADDRESS LARNI QAYTARAMIZ

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    //Id ORQALI BITTA ADDRESS
    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    //ADDRESS QO`SHISH
    public ApiResponse addAddress(@RequestBody Address address) {
        boolean existsByStreetAndHome = addressRepository.existsByStreetAndHome(address.getStreet(), address.getHome());
        if (existsByStreetAndHome) {
            return new ApiResponse("Bunday address mavjud", false);
        }

        Address address1 = new Address();
        address1.setHome(address.getHome());
        address1.setStreet(address.getStreet());
        addressRepository.save(address1);
        return new ApiResponse("Address saqlandi", true);
    }

    //ADDRESS TAHRIRLASH
    public ApiResponse editAddress(Integer id, Address address) {
        boolean existsByStreetAndHome = addressRepository.existsByStreetAndHome(address.getStreet(), address.getHome());
        if (existsByStreetAndHome) {
            return new ApiResponse("Bunday address mavjud", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday address mavjud emas", false);
        }
        Address address1 = optionalAddress.get();
        address1.setStreet(address.getStreet());
        address1.setHome(address.getHome());
        return new ApiResponse("Address saqlandi", true);
    }

    //DELETE
    public ApiResponse deleteAddress(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address o`chirildi", true);
        }catch (Exception e) {
            return new ApiResponse("Xatolik !!!", false);
        }

    }
}
