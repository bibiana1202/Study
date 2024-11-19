package org.hyejung.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:/dev/upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("------------------------------------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			File saveFile= new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				// transferTo 의 파라미터로는 java.io.File의 객체를 지정하면 되기 때문에 업로드 되는 원래 파일의 이름으로 저장된다.
				multipartFile.transferTo(saveFile); // 업로드되는 파일을 저장하는 방법
			}
			catch(Exception e) {
				log.error(e.getMessage());
			} // end catch
		
		} // end for
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("update ajax post.........");
		
		String uploadFolder = "C:/dev/upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-------------------------------------");
			log.info("Upload File Nmae : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			log.info("only file name: " + uploadFileName);
			
			File saveFile = new File(uploadFolder, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);
			}catch(Exception e ) {
				log.error(e.getMessage());
			} // end catch
		} // end for
	}
}
