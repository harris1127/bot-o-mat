package com.rharris.robot.repository;

import com.rharris.robot.domain.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Integer> {
}
