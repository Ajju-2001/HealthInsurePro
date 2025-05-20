package com.healthinsurance.healthproject.personaldetailsrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthinsurance.healthproject.personalentities.ResponseExcelTable;
@Repository
public interface ResponseExcelRepository extends JpaRepository<ResponseExcelTable,Integer>{
 
}
