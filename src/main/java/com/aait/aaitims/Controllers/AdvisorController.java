
package com.aait.aaitims.Controllers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aait.aaitims.Entity.Advisor;
import com.aait.aaitims.Services.AdvisorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdvisorController {
	
	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private AdvisorService advisorService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	
	@GetMapping(value = { "/addform" })
	public String addPrctPage() {
		return "index";
	}

	@PostMapping("/image/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct( 
			Model model,
			HttpServletRequest request,
			final @RequestParam("image") MultipartFile file,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("bio") String bio,
			@RequestParam("contact") String contact,
			@RequestParam("address") String address,
			@RequestParam("email") String email) {

          
			
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] image = file.getBytes();
			Advisor advisor = new Advisor();

  			advisor.setFirstname(firstname);
            advisor.setLastname(lastname);
            advisor.setFileName(fileName);
            advisor.setFilePath(filePath);
            advisor.setEmail(email);
            advisor.setBio(bio);
            advisor.setContact(contact);
            advisor.setAddress(address);
			advisor.setImage(image);
			
			advisorService.saveAdvisor(advisor);

			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	







	@GetMapping("/image/display/{id}")
	@ResponseBody
	void showImage(
		@PathVariable("id") Long id,
		 HttpServletResponse response,
		  Optional<Advisor> advisor)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		advisor = advisorService.getAdvisorById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(advisor.get().getImage());
		response.getOutputStream().close();
	}

	// @GetMapping("/image/imageDetails")
	// String showProductDetails(@RequestParam("id") Long id, Optional<ImageGallery> imageGallery, Model model) {
	// 	try {
	// 		log.info("Id :: " + id);
	// 		if (id != 0) {
	// 			advisor = advisorService.getImageById(id);
			
	// 			log.info("products :: " + imageGallery);
	// 			if (imageGallery.isPresent()) {
	// 				model.addAttribute("id", imageGallery.get().getId());
	// 				model.addAttribute("description", imageGallery.get().getDescription());
	// 				model.addAttribute("name", imageGallery.get().getName());
	// 				model.addAttribute("price", imageGallery.get().getPrice());
	// 				return "imagedetails";
	// 			}
	// 			return "redirect:/home";
	// 		}
	// 	return "redirect:/home";
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		return "redirect:/home";
	// 	}	
	// }

	@GetMapping("/images")
	public String show(Model map) {
		List<Advisor> advisor = advisorService.getAllActiveAdvisors();
		map.addAttribute("advisor", advisor);
		return "images";
	}
	
	@GetMapping("/advisorViewUser")
	public String showUser(Model map) {
		List<Advisor> advisor = advisorService.getAllActiveAdvisors();
		map.addAttribute("advisor", advisor);
		return "advisorViewUser";
	}
	

	@GetMapping("/deleteAdvisor/{id}")
	public String deleteAdvisor(@PathVariable(value = "id") long id) {

		this.advisorService.deleteAdvisorById(id);
		return "redirect:/images";
	}
	
	// @GetMapping("/updateAdvisor/{id}")
	// public String updateAdvisor(@PathVariable(value = "id") long id) {

	// 	return "updateAdv";
	// }

	@GetMapping("updateAdvisor/{id}")
	public String showFormForUpdate(@PathVariable("id") long id, Model model) {
		// get course from the service
		Optional<Advisor> advisor = advisorService.getAdvisorById(id);
		// set course as a model attribute to pre-populate the form
		model.addAttribute("advisor", advisor);
		return "updateAdv";
	}
	
	@PostMapping("/image/updateImageDetails")
	public String updateProduct(
			@PathVariable("id") long id,
			Advisor advisor,

			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("bio") String bio,
			@RequestParam("contact") String contact,
			@RequestParam("address") String address,
			@RequestParam("email") String email) {
		try {
		
			// Advisor advisor = new Advisor();

			advisor.setFirstname(firstname);
			advisor.setLastname(lastname);
			advisor.setEmail(email);
			advisor.setBio(bio);
			advisor.setContact(contact);
			advisor.setAddress(address);
			advisorService.updateAdvisor(advisor);

			return "redirect:/images";
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return "redirect:/updateAdv";
		}
	}

}	

