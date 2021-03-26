package uz.pdp.appapitask1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appapitask1.entity.Company;
import uz.pdp.appapitask1.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByName(String name);
    boolean existsByNameAndCompany(String name, Integer companyId);
}
