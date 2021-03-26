package uz.pdp.appapitask1.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appapitask1.entity.Address;
import uz.pdp.appapitask1.entity.Company;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.payload.CompanyDto;
import uz.pdp.appapitask1.repository.AddressRepository;
import uz.pdp.appapitask1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    //BARCHA COMPANY LARNI QAYTARAMIZ

    public List<Company> getCompany() {
        return companyRepository.findAll();
    }

    //Id ORQALI BITTA COMPANY
    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    //COMPANY QO`SHISH
    public ApiResponse addCompany(@RequestBody CompanyDto companyDto) {
        boolean existsByDirectorName = companyRepository.existsByDirectorName(companyDto.getDirectorName());
        if (existsByDirectorName) {
            return new ApiResponse("Bunday Company mavjud", false);
        }

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Saqlandi",true);

    }

    //COMPANY TAHRIRLASH
    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        boolean existsByDirectorName = companyRepository.existsByDirectorName(companyDto.getDirectorName());
        if (existsByDirectorName) {
            return new ApiResponse("Bunday company mavjud", false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Bunday company mavjud emas", false);
        }
        Company company  = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Saqlandi",true);
    }

    //DELETE
    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company o`chirildi", true);
        }catch (Exception e) {
            return new ApiResponse("Xatolik !!!", false);
        }

    }

}
