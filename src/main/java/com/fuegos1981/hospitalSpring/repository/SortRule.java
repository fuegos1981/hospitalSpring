package com.fuegos1981.hospitalSpring.repository;

public enum SortRule {
    NAME_ASC (" order by el.lastName asc, el.firstName asc"),
    NAME_DESC(" order by el.lastName desc, el.firstName desc"),
    CATEGORY_ASC(" order by category.name, el.lastName, el.firstName"),
    CATEGORY_DESC(" order by category.name desc, el.lastName, el.firstName"),
    COUNT_PATIENT_ASC(" order by count_patients, el.lastName, el.firstName"),
    COUNT_PATIENT_DESC(" order by count_patients desc, el.lastName, el.firstName"),
    BIRTHDAY_ASC(" order by birthday,el.lastName,el.firstName"),
    BIRTHDAY_DESC(" order by birthday desc,el.lastName,el.firstName"),
    DATE_CREATE_DESC(" order by date_create desc"),
    NAME_SIMPLE_ASC (" order by name"),
    VISIT_TIME_DESC(" order by visit_time desc");


    private  final String query;

    public String getQuery() {
        return query;
    }

    SortRule(String query) {
        this.query = query;
    }
}
