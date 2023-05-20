package com.fuegos1981.hospitalSpring.repository;

public enum MainQuery {
    GET_ALL_PATIENTS ("from Patient el"),
    GET_ALL_DOCTORS("from Doctor el" +
            " left join fetch el.category category"),// +
            //" left join (select Count(distinct patient_id) as count_patients,doctor_id  from schedule group by doctor_id)as sch on sch.doctor_id= doctor.id"),
    GET_ALL_SCHEDULES("from Schedule"),
    GET_ALL_APPOINTMENTS("from Appointment"),
    GET_ALL_CATEGORIES("from Category"),
    GET_ALL_DIAGNOSISES("from Diagnosis"),
    GET_SIZE_PATIENT ("select count(id) from Patient"),
    GET_SIZE_DOCTOR ("select count(id) from Doctor"),
    GET_SIZE_SCHEDULE ("select count(id) from Schedule"),
    GET_SIZE_APPOINTMENT ("select count(id) from Appointment"),
    GET_SIZE_CATEGORY ("select count(id) from Category"),
    GET_SIZE_DIAGNOSIS ("select count(id) from Diagnosis"),

    ;


    private  final String query;

    public String getQuery() {
        return query;
    }

    MainQuery(String query) {
        this.query = query;
    }
}
