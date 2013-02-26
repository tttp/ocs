package eu.europa.ec.eci.oct.utils.validator;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.apache.log4j.Logger;

public abstract class AbstractValidator {

	private static final Logger logger = Logger.getLogger(AbstractValidator.class);

	private static final String TOLERATED_CHARS = "[\\.\\-\\/\\\\\\;\\:\\,\\_\\s+]";

	public boolean validateNotEmpty(String value) {
		return value != null && !"".equals(value);
	}

	public abstract boolean validateDataType(String value);

	public abstract boolean validateSize(String value, int minSize, int maxSize);

	public abstract boolean validateRange(String value, String minValue, String maxValue);

	public boolean validateRegularExpression(String value, String regexp) {
		if (value == null) {
			return false;
		}
		return value.replaceAll(AbstractValidator.TOLERATED_CHARS, "").toLowerCase().matches(regexp);
	}

	@SuppressWarnings("unchecked")
	public boolean validateJavaExpression(String javaExpression, JAXELStructure bean) {
		boolean result = false;
		final String VARIABLE_BEAN_NAME = "bean";
		final String VARIABLE_PATTERN = "pattern";
		try {
			Expression expression = ExpressionFactory.createExpression(javaExpression);
			JexlContext context = JexlHelper.createContext();
			context.getVars().put(VARIABLE_BEAN_NAME, bean);
			context.getVars().put(VARIABLE_PATTERN, AbstractValidator.TOLERATED_CHARS);

			Object resultObject = expression.evaluate(context);
			if (!(resultObject instanceof Boolean)) {
				result = false;
			} else {
				result = (Boolean) resultObject;
			}
		} catch (Exception e) {
			logger.error("Exception encountered while parsing jaxel expression! ", e);
			result = false;
		}

		return result;
	}
}
