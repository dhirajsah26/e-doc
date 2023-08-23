package com.example.edoc.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Data
public class DoctorHospitalDTO {
    BigInteger id;
    String hospitalName ,city ,country,doctorName;
    Date startDate , endDate;


}
