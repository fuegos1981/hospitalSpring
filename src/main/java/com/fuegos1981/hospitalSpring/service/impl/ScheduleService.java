package com.fuegos1981.hospitalSpring.service.impl;


import com.fuegos1981.hospitalSpring.model.Schedule;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.ScheduleRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ScheduleService implements GlobalService<Schedule> {

    private ScheduleRepository scheduleRepository;

    private ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule create(Schedule schedule) throws SQLException {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule readById(Integer id) throws SQLException{
        return scheduleRepository.findById(id).get();
    }

    @Override
    public Schedule update(Schedule schedule) throws SQLException {

        return scheduleRepository.save(schedule);
    }

    @Override
    public void delete(Schedule schedule) throws SQLException {
        scheduleRepository.delete(schedule);
    }

    @Override
    public List<Schedule> getAll(QueryRedactor qr) throws SQLException {
        return null;

    }

    public int getSize(QueryRedactor qr){
        return 0;//scheduleRepository.getSize(qr);
    }

    @Override
    public List<Schedule> getAll() throws SQLException {
        return scheduleRepository.findAll();
    }

    public int getSize(){
        return (int)scheduleRepository.count();
    }

}
