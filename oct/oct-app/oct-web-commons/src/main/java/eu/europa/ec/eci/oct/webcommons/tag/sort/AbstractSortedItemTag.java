package eu.europa.ec.eci.oct.webcommons.tag.sort;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Parent class for all children tags of the {@link SortedItemsTag}. The nested content is dumped as-is to the JSP
 * writer.
 * 
 * @author keschma
 * 
 */
public abstract class AbstractSortedItemTag extends BodyTagSupport implements Cloneable {

	private static final long serialVersionUID = 1985233775938471724L;

	private String fullContent;

	/**
	 * Writes the content of this tag into the given Writer. This method can be called at any time after the body has
	 * been processed.
	 * 
	 * @param writer
	 *            the writer to use
	 * @throws IOException
	 *             on IO failures
	 */
	public void print(Writer writer) throws IOException {
		writer.append(fullContent);
	}

	@Override
	public int doAfterBody() throws JspException {
		fullContent = getBodyContent().getString();
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		final SortedItemsTag itemsTag = (SortedItemsTag) findAncestorWithClass(this, SortedItemsTag.class);
		if (itemsTag == null) {
			throw new JspTagException(this.getClass().getName() + " must be nested withing an instance of "
					+ SortedItemsTag.class.getName());
		}

		try {
			itemsTag.addChild((AbstractSortedItemTag) this.clone());
		} catch (CloneNotSupportedException e) {
		}
		return EVAL_PAGE;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractSortedItemTag tag = (AbstractSortedItemTag) super.clone();
		tag.setBodyContent(this.getBodyContent());
		tag.setId(this.getId());
		tag.setPageContext(this.pageContext);
		tag.setParent(this.getParent());

		return tag;
	}

}
