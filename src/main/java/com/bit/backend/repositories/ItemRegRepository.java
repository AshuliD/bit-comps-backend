package com.bit.backend.repositories;

import com.bit.backend.entities.FormDemoEntitiy;
import com.bit.backend.entities.ItemRegEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRegRepository extends JpaRepository<ItemRegEntitiy, Long> {


}
