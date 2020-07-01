/**
 * 
 */
package com.div.fh.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.div.fh.response.ResponseMessage;

import ch.qos.logback.classic.Logger;

/**
 * @author pks
 *
 */
@RestController
public class FileHandlerController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(FileHandlerController.class);
	  
	
	@Value("${file.absolute.path}")
	String filePath;
	
	/*
	 * TODO Refractor file handling implementation to FileService 
	 *
		@Autowired
		FileSErvice fileService;
	*/
	
	
	@PostMapping("/fileupload")
	public ResponseEntity<ResponseMessage> fileUpload(@RequestParam String fileName,@RequestParam MultipartFile file ) {
		if(StringUtils.isEmpty(fileName) || file == null) {
			logger.debug("Missing filename {} or file itself",fileName);
			//TODO externalize messages to file
			//TODO validation implementation
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ResponseMessage("File name or file content is missing"));
		}else {
			Path targetLocation = Paths.get(filePath+fileName);
            try {
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				logger.error("Error in creating or replacing file ",  e);
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
			}
            	
		}
		return ResponseEntity.ok().body(new ResponseMessage("Successfuly created or updated file"));
		
	}
	
	
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
		Path file = Paths.get(filePath+filename);
		try {
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isFile()) {
				logger.debug("File {} exists, returning file",filename);
				return ResponseEntity.ok().body(resource);
			}
		} catch (MalformedURLException e) {
			logger.error("Internal error reading the file" ,e);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

}
