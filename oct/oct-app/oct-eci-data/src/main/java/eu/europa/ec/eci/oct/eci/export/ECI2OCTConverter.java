package eu.europa.ec.eci.oct.eci.export;

import java.util.List;

import eu.europa.ec.eci.export.InitiativeType;
import eu.europa.ec.eci.export.LanguageType;
import eu.europa.ec.eci.export.OrganiserRoleType;
import eu.europa.ec.eci.export.OrganiserType;
import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;

public class ECI2OCTConverter {
	
	private LanguageProvider lp;
	
	public ECI2OCTConverter(LanguageProvider lp){
		this.lp = lp;
	}
	

	public SystemPreferences convertInitiative(InitiativeType i) throws ECIDataException{
		
		SystemPreferences prefs = new SystemPreferences();
		if(i.getRegistrationDate()==null) throw new ECIDataException("Registration date not provided");
		prefs.setRegistrationDate(i.getRegistrationDate().toGregorianCalendar().getTime());
		prefs.setRegistrationNumber("" + i.getRegistrationNumber());
		if(i.getUrl()==null) throw new ECIDataException("EC registry url not provided");
		prefs.setCommissionRegisterUrl(i.getUrl()); 
		
		return prefs;
	}
	
	public InitiativeDescription convertLanguageVersions(InitiativeType i, List<InitiativeDescription> descs) throws ECIDataException{
		InitiativeDescription defaultDesc = null;
		boolean defaultFound = false;
		for (LanguageType l : i.getLanguages().getLanguage()) {
			InitiativeDescription desc = new InitiativeDescription();
			boolean isDefault = convertLanguageVersion(l, desc);
			if(isDefault && defaultFound){
				throw new ECIDataException("Only one linguistic version can be marked as original");
			}
			if(isDefault){
				defaultDesc = desc;
				defaultFound = true;
			} else {			
				descs.add(desc);
			}
		}
		if(defaultDesc==null){
			throw new ECIDataException("None of language versions marked as a default");
		}
		
		return defaultDesc;
	}
	
	public Contact convertOrganisers(InitiativeType i) throws ECIDataException{
		Contact c = new Contact();
		StringBuffer orgBuff = new StringBuffer();
		StringBuffer conBuff = new StringBuffer();
		StringBuffer emailBuff = new StringBuffer();
		for(OrganiserType organiser : i.getOrganisers().getOrganiser()){
			String organiserName = organiser.getFamilyName() + " " + organiser.getFirstName();
			if(OrganiserRoleType.MEMBER.equals(organiser.getRole())){
				if(orgBuff.length()>0){
					orgBuff.append(", ");
				}
				orgBuff.append(organiserName);
			} else if(OrganiserRoleType.REPRESENTATIVE.equals(organiser.getRole())
					||OrganiserRoleType.SUBSTITUTE.equals(organiser.getRole())){
				if(orgBuff.length()>0){
					orgBuff.append(", ");
				}
				orgBuff.append(organiserName);

				if(conBuff.length()>0){
					conBuff.append(", ");					
				}
				conBuff.append(organiserName);
				if(organiser.getEmail()!=null && organiser.getEmail().trim().length()>0 && !organiser.getEmail().trim().equalsIgnoreCase("null")){					
					if(emailBuff.length()>0){
						emailBuff.append(", ");
					}
					emailBuff.append(organiser.getEmail());					
				}
				
			}
		}
		c.setOrganizers(orgBuff.toString());
		c.setName(conBuff.toString());
		c.setEmail(emailBuff.toString());
		
		return c;
	}
	
	public boolean convertLanguageVersion(LanguageType l, InitiativeDescription id) throws ECIDataException{		
		
		id.setTitle(l.getTitle());
		id.setSubjectMatter(l.getSubject());
		id.setObjectives(l.getDescription());
		id.setLanguage(lp.getLanguageByCode(l.getCode()));
		id.setUrl(l.getSite());
		
		return l.isOriginal();
	}
	
	
	
}
