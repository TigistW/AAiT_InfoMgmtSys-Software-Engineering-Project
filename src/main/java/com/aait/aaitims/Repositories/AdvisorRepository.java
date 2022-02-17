package com.aait.aaitims.Repositories;


import com.aait.aaitims.Entity.Advisor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, Long>{

}

