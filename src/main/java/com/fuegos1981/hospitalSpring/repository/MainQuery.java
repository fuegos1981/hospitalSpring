package com.fuegos1981.hospitalSpring.repository;

public enum MainQuery {
    GET_ALL_PATIENTS ("select patient.id, last_name,first_name, birthday, email," +
            "gender.name as gender_name from patient join gender on patient.gender_id=gender.id"),
    GET_ALL_DOCTORS("select doctor.id, last_name,first_name, login, password, roles.name as role_name, category_id, category.name as category_name, count_patients from doctor " +
            "join category on category.id=doctor.category_id " +
            "join roles on roles.id=doctor.role_id "+
            " left join (select Count(distinct patient_id) as count_patients,doctor_id  from schedule group by doctor_id)as sch on sch.doctor_id= doctor.id"),
    GET_ALL_SCHEDULE("select * from schedule"),
    GET_ALL_APPOINTMENTS("select * from appointment"),
    GET_ALL_CATEGORIES("select * from category"),
    GET_ALL_DIAGNOSISES("select * from diagnosis");

    private  final String query;

    public String getQuery() {
        return query;
    }

    MainQuery(String query) {
        this.query = query;
    }
}
