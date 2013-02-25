package eu.europa.ec.eci.oct.admin.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.springframework.validation.Errors;

import eu.europa.ec.eci.oct.admin.model.SystemPreferencesBean;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.utils.CalendarHelper;
import eu.europa.ec.eci.oct.utils.StringUtils;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.validator.BaseValidator;

public class SystemPreferencesValidator extends BaseValidator {

	private boolean manual;

	public SystemPreferencesValidator(MessageSourceAware messageSource, boolean manual) {
		super(messageSource);
		this.manual = manual;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SystemPreferencesBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SystemPreferencesBean bean = (SystemPreferencesBean) target;
		
		// emptiness
		validateRequired(bean.getTitle(), getMessageSource().getMessage("oct.s4.title") , errors, null);
		validateRequired(bean.getLanguage(), getMessageSource().getMessage("oct.s11.reg.language") , errors, null);		
		
		if (!manual) {
			validateRequired(bean.getSubjectMatter(), getMessageSource().getMessage("oct.s4.subject") , errors, null);
			validateRequired(bean.getObjectives(), getMessageSource().getMessage("oct.s4.desc") , errors, null);
			validateRequired(bean.getRegistrationNumber(), getMessageSource().getMessage("oct.s4.reg.nr") , errors, null);
			validateRequired(bean.getRegistrationDate(), getMessageSource().getMessage("oct.s4.reg.date") , errors, null);
			validateRequired(bean.getRegisterUrl(), getMessageSource().getMessage("oct.s4.initiative.url") , errors, null);
			validateRequired(bean.getOrganizers(), getMessageSource().getMessage("oct.s4.organisers") , errors, null);
			validateRequired(bean.getContactPerson(), getMessageSource().getMessage("oct.s4.contacts") , errors, null);
			validateRequired(bean.getContactEmail(), getMessageSource().getMessage("oct.s4.email") , errors, null);			
		}
				
		// initiative data
		validateLength(bean.getTitle(), getMessageSource().getMessage("oct.s4.title"), errors, 1000, null);
		validateLength(bean.getSubjectMatter(), getMessageSource().getMessage("oct.s4.subject"), errors, 2000, null);
		validateLength(bean.getObjectives(), getMessageSource().getMessage("oct.s4.desc"), errors, 4000, null);
		validateLength(bean.getWebsite(), getMessageSource().getMessage("oct.s4.website"), errors, 100, null);
		
		// contact data
		validateLength(bean.getOrganizers(), getMessageSource().getMessage("oct.s4.organisers"), errors, 2000, null);
		validateLength(bean.getContactPerson(), getMessageSource().getMessage("oct.s4.contacts"), errors, 2000, null);
		validateLength(bean.getContactEmail(), getMessageSource().getMessage("oct.s4.email"), errors, 2000, null);
		
		// registration info
		validateLength(bean.getRegistrationNumber(), getMessageSource().getMessage("oct.s4.reg.nr"), errors, 64, null);
		validateLength(bean.getRegisterUrl(), getMessageSource().getMessage("oct.s4.initiative.url"), errors, 500, null);
		
		validateUrl(bean.getRegisterUrl(), getMessageSource().getMessage("oct.s4.initiative.url"), errors, null);
		validateUrl(bean.getWebsite(), getMessageSource().getMessage("oct.s4.website"), errors, null);
		
		if(bean.getRegistrationDate()!=null){
			Date regDate = CalendarHelper.removeTime(bean.getRegistrationDate());
			Date todayDate = CalendarHelper.removeTime(new Date());
			if(!regDate.before(todayDate) && todayDate.before(regDate)){
				errors.reject("oct.s4.date.future", "Registration date must be before current date");
			}
		}
		
		if (!manual) {
			for (InitiativeDescription desc : bean.getLanguageVersions()) {
				validateRequired(desc.getTitle(), getMessageSource().getMessage("oct.s4.title"), errors, desc.getLanguage());
				validateRequired(desc.getSubjectMatter(), getMessageSource().getMessage("oct.s4.subject"), errors, desc.getLanguage());
				validateRequired(desc.getObjectives(), getMessageSource().getMessage("oct.s4.desc"), errors, desc.getLanguage());
				
				validateLength(desc.getTitle(), getMessageSource().getMessage("oct.s4.title"), errors, 1000,
						desc.getLanguage());
				validateLength(desc.getSubjectMatter(), getMessageSource().getMessage("oct.s4.subject"), errors, 2000,
						desc.getLanguage());
				validateLength(desc.getObjectives(), getMessageSource().getMessage("oct.s4.desc"), errors, 4000,
						desc.getLanguage());
				validateLength(desc.getUrl(), getMessageSource().getMessage("oct.s4.website"), errors, 100,
						desc.getLanguage());
				
				validateUrl(desc.getUrl(), getMessageSource().getMessage("oct.s4.website"), errors, desc.getLanguage());
			}
		}
		
		
	}
	
	private void validateUrl(String url, String field, Errors errors, Language lang) {
		
		if (!StringUtils.isEmpty(url)) {
			try {
				new URL(url);
			} catch (MalformedURLException e) {
				errors.reject("oct.s4.url.incorrect", //TODO consider adding specific message
						lang==null ? new Object[] { field } : new Object[] { field + " (" + getMessageSource().getMessage(lang.getName()) +")"},						
						"The URL "	+ field	+ " is not syntatically correct");
			}
		}
	}
	
	private void validateLength(String fieldValue, String fieldName, Errors errors, int maxLength, Language lang) {
		
		if(!StringUtils.isEmpty(fieldValue) && fieldValue.length() > maxLength ){
			errors.reject(
					"oct.admin.error.field.toolong", //TODO consider adding specific message
					lang==null ? new Object[]{fieldName, maxLength} : new Object[]{fieldName + " (" +  getMessageSource().getMessage(lang.getName()) +")", maxLength },
					"The field "+ fieldName + " cannot exceed " + maxLength + " characters");			
		}
	}
	
	private void validateRequired(Object fieldValue, String fieldName, Errors errors, Language lang) {
		
		if(fieldValue==null || StringUtils.isEmpty(fieldValue.toString())){
			errors.reject(
					"oct.admin.error.empty.field", //TODO consider adding specific message
					lang==null ? new Object[]{fieldName} : new Object[]{fieldName + " (" + getMessageSource().getMessage(lang.getName()) +" )"},
					"The field " + fieldName + " cannot be empty");			
		}
	}

}
