package uz.pdp.appapitask1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class DepartmentDto {

    @NotNull(message = "Department bo`sh bo`lmasligi kerak")
    private String name;

    @NotNull(message = "Company bo`sh bo`lmasligi kerak")
    private Integer companyId;
}
