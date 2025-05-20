package com.healthinsurance.healthproject.personaldetailservice;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.healthinsurance.healthproject.personaldto.PersonalDetailsDTO;
import com.healthinsurance.healthproject.personalentities.PersonalDetails;
import com.healthinsurance.healthproject.praposallisting.PraposalListing;

import jakarta.servlet.http.HttpServletRequest;


public interface PersonalDetailsService {
	//Save the data in database
	PersonalDetails savePersonalDetails(PersonalDetailsDTO personalDetailsdto);    //access dto class for add update
	//Update the data in database
	PersonalDetails updatePersonalDetails(Integer id, PersonalDetailsDTO personalDetailsdto);
	//Fetch all the data in database
//	List<PersonalDetails> getAllPersonalDetails();
	List<PersonalDetails> getAllPersonalDetails(HttpServletRequest request);
	//Fetch the data by using id
//	PersonalDetails getPersonalDetailsById(Integer id);
	PersonalDetailsDTO getPersonalDetailsById(Integer personalId);
	//Delete the data by using id
	void deletePersonalDetailsById(Integer id); 
	//Delete all the data
	void deleteAllPersonalDetails();
	//Fetch pagination listing 
	List<Map<String, Object>> getPersonalDetails(PraposalListing praposalListing); 
	//Fetch total count method
	public Integer getTotalCount(); 
	//Sample Export Data
	public String exportPersonalSampleData() throws IOException;
	//Import Excel Data
//	List<PersonalDetails> importPersonalDetailsFromExcel(MultipartFile file,Map<String, Integer> recordCounts) throws IOException;
	//Export Excel Data
	public String exportPersonalData() throws IOException; 
	//Export PDF Data
	public String exportPersonalDataToPdf() throws Exception;
	//fetchAllProducts
	List<Map<String, Object>> fetchAllProducts() throws Exception;
	//QueTable data
//	public QueueTable processExcelQueue() throws Exception;
	List<PersonalDetails> importScheduleDetailsFromExcel(MultipartFile file, Map<String, Integer> recordCount)
			throws IOException;
	void scheduleQueueProcessing();
	
	
	
}
