package com.aait.aaitims.Services;
import java.util.List;
import java.util.Optional;

import com.aait.aaitims.Entity.Advisor;
import com.aait.aaitims.Repositories.AdvisorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AdvisorService {

	@Autowired
	private AdvisorRepository advisorRepository;
	
	public void saveAdvisor(Advisor advisor) {
		advisorRepository.save(advisor);	
	}

	public List<Advisor> getAllActiveAdvisors() {
		return advisorRepository.findAll();
	}

	public Optional<Advisor> getAdvisorById(Long id) {
		return advisorRepository.findById(id);
	}
	
	public void deleteAdvisorById(long id) {
		this.advisorRepository.deleteById(id);

	}
	
	public void updateAdvisor(Advisor advisor) {
		this.advisorRepository.save(advisor);

	}

}

