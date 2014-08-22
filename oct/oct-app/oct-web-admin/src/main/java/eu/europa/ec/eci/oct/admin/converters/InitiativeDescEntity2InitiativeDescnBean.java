package eu.europa.ec.eci.oct.admin.converters;

import eu.europa.ec.eci.oct.admin.model.InitiativeDescriptionBean;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;

public class InitiativeDescEntity2InitiativeDescnBean {

	public static InitiativeDescription convert(InitiativeDescriptionBean bean) {
		InitiativeDescription result = new InitiativeDescription();
		if (bean.getId() != null) {
			result.setId(bean.getId());
		}
		result.setLanguage(bean.getLanguage());
		result.setObjectives(bean.getObjectives());
		result.setSubjectMatter(bean.getSubjectMatter());
		result.setTitle(bean.getTitle());
		result.setUrl(bean.getUrl());

		return result;
	}

	public static InitiativeDescriptionBean convert(InitiativeDescription entity) {
		InitiativeDescriptionBean result = new InitiativeDescriptionBean();
		if (entity.getId() != null) {
			result.setId(entity.getId());
		}
		result.setLanguage(entity.getLanguage());
		result.setObjectives(entity.getObjectives());
		result.setSubjectMatter(entity.getSubjectMatter());
		result.setTitle(entity.getTitle());
		result.setUrl(entity.getUrl());

		return result;
	}

	public static void copy(InitiativeDescriptionBean source, InitiativeDescription destination) {
		destination.setObjectives(source.getObjectives());
		destination.setSubjectMatter(source.getSubjectMatter());
		destination.setTitle(source.getTitle());

	}
}
