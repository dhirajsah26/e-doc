package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Data
public class DiagnosisDTO {
    String bloodPressure , bloodGlucose , bodyWeight , bodyMassIndex , diagnosisPhoto , pName , dName;
    BigInteger id , patientId , doctorId;
}
