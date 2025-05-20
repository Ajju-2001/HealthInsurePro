package com.healthinsurance.healthproject.personaldetailsrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthinsurance.healthproject.personalentities.QueueTable;

public interface QueueTableRepository extends JpaRepository<QueueTable, Integer>{

//	List<QueueTable> findByIsProcessed(char c);

	List<QueueTable> findByIsProcessedOrderByQueueIdAsc(char c);


}
