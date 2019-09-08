package com.rharris.robot.repository;

import com.rharris.robot.domain.RobotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotTypeRepository extends JpaRepository<RobotType, Integer> {

    public RobotType getRobotTypeByName(String name);
}
