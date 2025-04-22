package com.bit.backend.repositories;

import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.JobRoleEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JobRoleRepository extends JpaRepository<JobRoleEntitiy, Long> {

   @Query(nativeQuery = true, value="SELECT * FROM job_role_one")
   List<JobRoleEntitiy> getJobRole();

}
