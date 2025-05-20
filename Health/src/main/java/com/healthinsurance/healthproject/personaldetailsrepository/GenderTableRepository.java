package com.healthinsurance.healthproject.personaldetailsrepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthinsurance.healthproject.personalentities.GenderTable;

public interface GenderTableRepository extends JpaRepository<GenderTable, Integer>{
	Optional<GenderTable> findByGenderType(String genderType);
}
