package com.bit.backend.repositories;

import com.bit.backend.entities.GRNEntitiy;
import com.bit.backend.entities.ItemRegEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GRNRepository extends JpaRepository<GRNEntitiy, Long> {


}
