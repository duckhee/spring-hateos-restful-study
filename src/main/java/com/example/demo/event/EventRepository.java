package com.example.demo.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EventRepository extends JpaRepository<EventDomain, Long>{

}
