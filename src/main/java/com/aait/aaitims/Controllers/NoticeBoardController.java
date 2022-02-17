package com.aait.aaitims.Controllers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aait.aaitims.Entity.NoticeBoard;
import com.aait.aaitims.Services.NoticeBoardService;

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
public class NoticeBoardController {
	
	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private NoticeBoardService noticeboardService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = {"/noticeForm"})
	public String addNoticeBoardPage() {
		return "noticeForm";
	}
	
	// @GetMapping(value = { "/" })
	// public String viewNoticeBoardPage() {
	// 	return "noticeViewUser";
	// }
	
	// @GetMapping(value = { "/noticeView" })
	// public String viewNoticeBoardAdminPage() {
	// 	return "noticeView";
	// }

	// @GetMapping(value = { "/advisorViewUser" })
	// public String viewUserAdvisorPage() {
	// 	return "advisorViewUser";
	// }
	@GetMapping("/noticeViewUser")
	String showNotice(Model map) {
		List<NoticeBoard> notice = noticeboardService.getAllActiveNotices();
		map.addAttribute("notice", notice);
		return "noticeViewUser";
	}
	
	@GetMapping("/noticeView")
	String showAdminNotice(Model map) {
		List<NoticeBoard> notice = noticeboardService.getAllActiveNotices();
		map.addAttribute("notice", notice);
		return "noticeView";
	}
	
	@GetMapping(value = { "/Portal" })
	public String viewPortalPage() {
		return "PortalView";
	}


	@PostMapping("/notice/saveNoticeDetails")
	public @ResponseBody ResponseEntity<?> createNoticeBoard( 
			Model model,
			HttpServletRequest request,
			final @RequestParam("image") MultipartFile file,
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("content") String content) {
	
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
			NoticeBoard notice = new NoticeBoard();

  			notice.setTitle(title);
           	notice.setDescription(description);
			notice.setContent(content);
			notice.setImage(image);
			
			noticeboardService.saveNoticeBoard(notice);

			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/notice/display/{id}")
	@ResponseBody
	void showImage(
		@PathVariable("id") Long id,
		 HttpServletResponse response,
		  Optional<NoticeBoard> notice)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		notice = noticeboardService.getNoticeById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(notice.get().getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/notice/noticeDetails")
	String showProductDetails(@RequestParam("id") Long id, Optional<NoticeBoard> notice, Model model) {
		try {
			log.info("Id :: " + id);
			if (id != 0) {
				notice = noticeboardService.getNoticeById(id);
			
				log.info("products :: " + notice);
				if (notice.isPresent()) {
					model.addAttribute("id", notice.get().getId());
					model.addAttribute("description", notice.get().getDescription());
					model.addAttribute("title", notice.get().getTitle());
					model.addAttribute("content", notice.get().getContent());
					return "noticeDetails";
				}
				return "redirect:/";
			}
		return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}	
	}

	@GetMapping("/deleteNotice/{id}")
	public String deleteAdvisor(@PathVariable(value = "id") long id) {
		this.noticeboardService.deleteNoticeById(id);
		return "redirect:/noticeView";
	}

	@GetMapping("updateNotice/{id}")
	public String showFormForUpdate(@PathVariable("id") long id, Model model) {
		// get course from the service
		Optional<NoticeBoard> advisor = noticeboardService.getNoticeById(id);
		// set course as a model attribute to pre-populate the form
		model.addAttribute("advisor", advisor);
		return "updateNotice";
	}















}	

