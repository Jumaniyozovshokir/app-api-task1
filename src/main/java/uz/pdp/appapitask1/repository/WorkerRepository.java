package uz.pdp.appapitask1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appapitask1.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
