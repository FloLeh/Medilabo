package com.openclassrooms.servicenotes.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notes")
@AllArgsConstructor
public class MedicalNote {

    @Id
    private String id;

    private Long patientId;

    private String patient;

    private String note;

}
