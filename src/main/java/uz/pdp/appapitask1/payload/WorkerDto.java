package uz.pdp.appapitask1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    @NotNull(message = "Worker bo`sh bo`lmasin")
    private String name;

    @NotNull(message = "PhoneNumber bo`sh bo`lmasin")
    private String phoneNumber;

    @NotNull(message = "Address bo`sh bo`lmasin")
    private Integer addressId;

    @NotNull(message = "Department bo`sh bo`lmasin")
    private Integer departmentId;

}
