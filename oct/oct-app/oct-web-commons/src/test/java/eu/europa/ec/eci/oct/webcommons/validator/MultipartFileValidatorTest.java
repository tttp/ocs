package eu.europa.ec.eci.oct.webcommons.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator.RejectReason;

public class MultipartFileValidatorTest {
	
	private MultipartFileValidator validator;
	
	@Mock
	private MessageSourceAware messageSource;
	
	@Mock
	private Errors errors;
	
	@Before
	public void onSetUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		List<String> extensionWhitelist = new ArrayList<String>();
		extensionWhitelist.add(".bar");
		extensionWhitelist.add(".beer");
		
		Map<RejectReason, String> rejectDetailsMap = new HashMap<RejectReason, String>();
		rejectDetailsMap.put(RejectReason.EMPTY_CONTENT, "code.empty.content");
		rejectDetailsMap.put(RejectReason.EMPTY_NAME, "code.empty.name");
		rejectDetailsMap.put(RejectReason.BAD_EXTENSION, "code.bad.extension");
		
		validator = new MultipartFileValidator(messageSource, "code.root",
				rejectDetailsMap, extensionWhitelist, 0);
	}
	
	@Test
	public void testSupports() {		
		assertTrue("should support MultipartFile", validator.supports(MultipartFile.class));
		assertFalse("should not support Object", validator.supports(Object.class));
	}

	@Test
	public void testValidate_EmptyContent() {
		MockMultipartFile file = new MockMultipartFile("bla", "bla.bEEr", "beer/Belgian", new byte[] {});
		
		when(messageSource.getMessage("code.empty.content")).thenReturn("EMPTY-CONTENT");
		
		validator.validate(file, errors);
		
		verify(messageSource).getMessage("code.empty.content");
		verifyNoMoreInteractions(messageSource);
		
		verify(errors).reject("code.empty.content", new Object[] {  }, "EMPTY-CONTENT");
		verifyNoMoreInteractions(errors);
	}
	
	@Test
	public void testValidate_EmptyName() {
		MockMultipartFile file = new MockMultipartFile("bla", "", "beer/Belgian", new byte[] { 1, 2, 3 });
		
		when(messageSource.getMessage("code.empty.name")).thenReturn("EMPTY-NAME");
		
		validator.validate(file, errors);
		
		verify(messageSource).getMessage("code.empty.name");
		verifyNoMoreInteractions(messageSource);
		
		verify(errors).reject("code.empty.name", new Object[] { }, "EMPTY-NAME");
		verifyNoMoreInteractions(errors);
	}
	
	@Test
	public void testValidate_BadExtension() {
		MockMultipartFile file = new MockMultipartFile("bla", "bla.txt", "beer/Belgian", new byte[] { 1, 2, 3 });
		
		when(messageSource.getMessage("code.bad.extension")).thenReturn("BAD-EXTENSION");
		
		validator.validate(file, errors);
		
		verify(messageSource).getMessage("code.bad.extension");
		verifyNoMoreInteractions(messageSource);
		
		verify(errors).reject("code.bad.extension", new Object[] {  }, "BAD-EXTENSION");
		verifyNoMoreInteractions(errors);
	}
	
	@Test
	public void testValidate_OK() {
		MockMultipartFile file = new MockMultipartFile("bla", "bla.bEEr", "beer/Belgian", new byte[] { 1, 2, 3 });
		
		validator.validate(file, errors);
		
		verifyZeroInteractions(messageSource);
		
		verifyZeroInteractions(errors);
	}
	
}
