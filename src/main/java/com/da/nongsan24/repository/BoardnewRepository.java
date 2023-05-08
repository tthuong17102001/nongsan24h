package com.da.nongsan24.repository;

import com.da.nongsan24.entities.Boardnew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardnewRepository extends JpaRepository<Boardnew,Long> {
}
