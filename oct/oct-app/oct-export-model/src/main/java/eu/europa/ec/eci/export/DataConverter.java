package eu.europa.ec.eci.export;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import eu.europa.ec.eci.export.model.GroupType;
import eu.europa.ec.eci.export.model.InformationGroupListType;
import eu.europa.ec.eci.export.model.InitiativeDataType;
import eu.europa.ec.eci.export.model.PropertyListType;
import eu.europa.ec.eci.export.model.PropertyType;
import eu.europa.ec.eci.export.model.SignatoryType;
import eu.europa.ec.eci.export.model.SignatureListType;
import eu.europa.ec.eci.export.model.SignatureType;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.utils.CalendarHelper;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;
import eu.europa.ec.eci.oct.vo.export.InitiativeDetails;

public class DataConverter {

	private static DataConverter instance;

	private DataConverter() {

	}

	public static DataConverter getInstance() {
		if (instance == null) {
			instance = new DataConverter();
		}

		return instance;
	}

	public SupportForm attachSignatures(SupportForm originalForm, Collection<ExportSignatureBean> signatures) {
		SignatureListType signaturesList = new SignatureListType();

		for (ExportSignatureBean signatureBean : signatures) {
			SignatureType signature = new SignatureType();
			signature.setSubmissionDate(CalendarHelper.getInstance().convertDate(signatureBean.getDate()));
			signature.setSignatureIdentifier(signatureBean.getUuid());

			SignatoryType signatory = new SignatoryType();

			InformationGroupListType groupList = new InformationGroupListType();

			for (Entry<String, Map<String, String>> groupEntry : signatureBean.getGroups().entrySet()) {
				GroupType group = new GroupType();
				group.setName(groupEntry.getKey());

				PropertyListType propertyList = new PropertyListType();

				Map<String, String> properties = groupEntry.getValue();
				for (Entry<String, String> propertyEntry : properties.entrySet()) {
					PropertyType property = new PropertyType();
					property.setKey(propertyEntry.getKey());
					property.setValue(propertyEntry.getValue());

					propertyList.getProperty().add(property);
				}

				group.setProperties(propertyList);

				groupList.getGroup().add(group);
			}
			signatory.setGroups(groupList);

			signature.setSignatoryInfo(signatory);

			signaturesList.getSignature().add(signature);
		}

		originalForm.setSignatures(signaturesList);
		return originalForm;
	}

	public InitiativeDataType convertInitiativeDetails(InitiativeDetails initiativeDetails) {
		InitiativeDataType initiativeData = new InitiativeDataType();
		initiativeData.setContactPersonsList(initiativeDetails.getContactPersonsList());
		initiativeData.setMainObjectives(initiativeDetails.getMainObjectives());
		initiativeData.setOrganizerList(initiativeDetails.getOrganizerList());
		initiativeData.setRegistrationDate(CalendarHelper.getInstance().convertDate(
				initiativeDetails.getRegistrationDate()));
		initiativeData.setRegistrationNumber(initiativeDetails.getRegistrationNumber());
		initiativeData.setSubjectMatter(initiativeDetails.getSubjectMatter());
		initiativeData.setTitle(initiativeDetails.getTitle());
		initiativeData.setUrl(initiativeDetails.getUrl());
		initiativeData.setUrlOnCommissionRegister(initiativeDetails.getUrlOnCommissionRegister());

		return initiativeData;
	}
}
