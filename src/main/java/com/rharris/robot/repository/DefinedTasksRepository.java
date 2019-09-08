package com.rharris.robot.repository;

import com.rharris.robot.domain.DefinedTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefinedTasksRepository extends JpaRepository<DefinedTasks, Integer> {
}
