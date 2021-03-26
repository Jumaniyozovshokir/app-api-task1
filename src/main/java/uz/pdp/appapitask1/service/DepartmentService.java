package uz.pdp.appapitask1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appapitask1.entity.Company;
import uz.pdp.appapitask1.entity.Department;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.payload.DepartmentDto;
import uz.pdp.appapitask1.repository.CompanyRepository;
import uz.pdp.appapitask1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    // Id ORQALI
    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    //BO'LIM QO'SHISH
    public ApiResponse addDepartment(@RequestBody DepartmentDto departmentDto) {
        boolean existsByNameAndCompany = departmentRepository.existsByNameAndCompany(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompany) {
            return new ApiResponse("Bunday Bo'lim mavjud", false);
        }

        Department department = new Department();
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Saqlandi", true);
    }

    //TAHRIRLASH
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        boolean existsByNameAndCompany = departmentRepository.existsByNameAndCompany(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompany) {
            return new ApiResponse("Bunday Bo'lim mavjud emas", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bo'lim mavjud emas", false);
        }
        Department department = new Department();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        department.setCompany(optionalCompany.get());
        return new ApiResponse("Saqlandi", true);
    }

    //DELETE
    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Bo'lim o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik !!!", false);
        }

    }

}
