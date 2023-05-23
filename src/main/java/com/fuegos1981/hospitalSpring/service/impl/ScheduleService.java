package com.fuegos1981.hospitalSpring.service.impl;


import com.fuegos1981.hospitalSpring.dto.ScheduleDto;
import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.model.Schedule;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.ScheduleRepository;
import com.fuegos1981.hospitalSpring.service.GlobalService;
import com.fuegos1981.hospitalSpring.service.MappingUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService implements GlobalService<ScheduleDto> {

    private ScheduleRepository scheduleRepository;
    private final MappingUtils mappingUtils;

    private ScheduleService(ScheduleRepository scheduleRepository, MappingUtils mappingUtils) {
        this.scheduleRepository = scheduleRepository;
        this.mappingUtils = mappingUtils;
    }

    @Override
    public ScheduleDto create(ScheduleDto scheduleDto) throws SQLException, DBException {
        return mappingUtils.mapToScheduleDto(scheduleRepository.save(mappingUtils.mapToSchedule(scheduleDto)));
    }

    @Override
    public ScheduleDto readById(Integer id) throws SQLException{
        return mappingUtils.mapToScheduleDto(scheduleRepository.findById(id).get());
    }

    @Override
    public ScheduleDto update(ScheduleDto scheduleDto) throws SQLException, DBException {
        return mappingUtils.mapToScheduleDto(scheduleRepository.save(mappingUtils.mapToSchedule(scheduleDto)));
    }

    @Override
    public void delete(ScheduleDto scheduleDto) throws SQLException, DBException {
        scheduleRepository.delete(mappingUtils.mapToSchedule(scheduleDto));
    }

    @Override
    public List<ScheduleDto> getAll(QueryRedactor qr) throws SQLException {
        return scheduleRepository.findAll(qr, Schedule.class).stream()
                .map(mappingUtils::mapToScheduleDto)
                .collect(Collectors.toList());

    }

    public int getSize(QueryRedactor qr){
        return scheduleRepository.count(qr, Schedule.class);
    }

    @Override
    public List<ScheduleDto> getAll() throws SQLException {
        return scheduleRepository.findAll().stream()
                .map(mappingUtils::mapToScheduleDto)
                .collect(Collectors.toList());
    }

    public int getSize(){
        return (int)scheduleRepository.count();
    }

}
