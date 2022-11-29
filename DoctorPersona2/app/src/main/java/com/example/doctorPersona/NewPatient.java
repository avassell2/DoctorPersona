package com.example.doctorPersona;


public class NewPatient {

    private String Name;
    private String Prescription;
    private String DOB;


    // No-argument constructor is required to support conversion of Firestore document to POJO
    public NewPatient() {}

    // All-argument constructor is required to support conversion of Firestore document to POJO
    public NewPatient(String Name, String Prescription, String date) {
        this.Name = Name;
        this.Prescription = Prescription;
        this.DOB = DOB;


    }

    public String getName() {
        return Name;
    }

    public String getPrescription() {
        return Prescription;
    }

    public String getdate() {
        return DOB;
    }


    public void setName(String Name){this.Name = Name;
    }

    public void setPrescription(String Prescription) {
        this.Prescription = Prescription;
    }

    public void setdate(String DOB) {
        this.DOB = DOB;
    }










}
