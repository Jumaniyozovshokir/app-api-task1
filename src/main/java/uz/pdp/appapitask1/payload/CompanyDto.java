package uz.pdp.appapitask1.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    @NotNull(message = "Company name bo`sh bo`lmasligi kerak")
    private String corpName;

    @NotNull(message = "Director name bo`sh bo`lmasligi kerak")
    private String directorName;

    @NotNull(message = "Address id bo`sh bo`lmasligi kerak")
    private Integer addressId;
}

