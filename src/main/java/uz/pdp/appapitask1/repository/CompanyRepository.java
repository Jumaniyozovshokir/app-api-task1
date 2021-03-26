package uz.pdp.appapitask1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appapitask1.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByDirectorName(String directorName);
}
