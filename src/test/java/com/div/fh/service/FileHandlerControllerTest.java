/**
 * 
 */
package com.div.fh.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.div.fh.api.FileHandlerController;
import com.div.fh.response.ResponseMessage;

/**
 * @author pks
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class FileHandlerControllerTest {
	

	String fileName = "test.txt";
	
	@Mock
	MultipartFile file;


	@InjectMocks
	FileHandlerController fileHandler;
	
	@Test
	public void testEmptyFileName() {
		fileName = "";
		ResponseEntity<ResponseMessage> response = fileHandler.fileUpload(fileName, file);
		assertThat(response.getStatusCode().equals(HttpStatus.PRECONDITION_FAILED));
	}

	@Test
	public void testNoFile() {
		file = null;
		ResponseEntity<ResponseMessage> response = fileHandler.fileUpload(fileName, file);
		assertThat(response.getStatusCode().equals(HttpStatus.PRECONDITION_FAILED));
	}
	
}
