package com.fuegos1981.hospitalSpring.repository.elements;

import com.fuegos1981.hospitalSpring.model.Schedule;
import com.fuegos1981.hospitalSpring.repository.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, CustomRepository<Schedule> {

}
