package eu.europa.ec.eci.oct.webcommons.tag.sort;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Marks a sortable property of a {@link SortedItemTag}. The nested content is dumped as-is to the JSP writer.
 * 
 * @author keschma
 * 
 */
public class SortedItemPropertyTag extends BodyTagSupport implements Cloneable {

	private static final long serialVersionUID = 308612695394892006L;

	private static final Pattern HTML_PATTERN = Pattern.compile("<[^>]*>");

	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	private String propertyValue = "";

	public String getPropertyValue() {
		return propertyValue;
	}

	@Override
	public int doAfterBody() throws JspException {
		final String bodyContent = getBodyContent().getString();
		propertyValue = extractPropertyValue(bodyContent);
		try {
			getPreviousOut().print(bodyContent);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		final SortedItemTag itemTag = (SortedItemTag) findAncestorWithClass(this, SortedItemTag.class);
		if (itemTag == null) {
			throw new JspTagException(this.getClass().getName() + " must be nested withing an instance of "
					+ SortedItemTag.class.getName());
		}

		try {
			itemTag.propertyTag = (SortedItemPropertyTag) this.clone();
		} catch (CloneNotSupportedException e) {
		}
		return EVAL_PAGE;
	}

	/**
	 * Transforms a rendered JSP content (that is, some HTML usually) into a property value suitable for sorting. This
	 * may include whatever string cleanup options are considered appropriate: trimming, case conversions, etc.
	 * 
	 * @param renderedContent
	 *            the raw, rendered JSP content
	 * @return the property value extracted from the content
	 */
	private String extractPropertyValue(String renderedContent) {
		String propertyValue = renderedContent;

		// strip all HTML tags
		final Matcher htmlMatcher = HTML_PATTERN.matcher(propertyValue);
		propertyValue = htmlMatcher.replaceAll(" ");

		// transform all duplicate whitespace into single space
		final Matcher whitespaceMatcher = WHITESPACE_PATTERN.matcher(propertyValue);
		propertyValue = whitespaceMatcher.replaceAll(" ");

		// basic string cleanup
		propertyValue = propertyValue.trim();

		return propertyValue;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
