package uz.pdp.appapitask1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appapitask1.entity.Worker;
import uz.pdp.appapitask1.payload.ApiResponse;
import uz.pdp.appapitask1.payload.WorkerDto;
import uz.pdp.appapitask1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;


    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }



    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalCustomer = workerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }


    public ApiResponse addWorker(@RequestBody WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Bunday Ishchi mavjud", false);
        }

        Worker worker = new Worker();
        worker.setPhoneNumber(workerDto.getPhoneNumber());
//        worker.setAddress(workerDto.getAddressId());
//        worker.setDepartment(workerDto.getDepartmentId());
        workerRepository.save(worker);
        return new ApiResponse("Ishchi saqlandi", true);
    }



    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Bunday telefon raqamli ishchi mavjud",false);
        }
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Bunday ishchi mavjud emas", false);
        }
        Worker worker = optionalWorker.get();
//        worker.setAddress(workerDto.getAddress());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setName(workerDto.getName());
//        worker.setDepartment(workerDto.getDepartmentId());
        return new ApiResponse("Ishchi saqlandi", true);
    }


    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Ishchi o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik !!!", false);
        }

    }
}
