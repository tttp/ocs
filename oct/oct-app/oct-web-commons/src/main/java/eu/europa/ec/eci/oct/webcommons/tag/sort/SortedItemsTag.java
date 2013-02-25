package eu.europa.ec.eci.oct.webcommons.tag.sort;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;

/**
 * Container tag for sorted items. Please note that this tag renders only the nested {@link SortedItemTag} instances and
 * <em>no other nested content</em>!
 * 
 * @author keschma
 * 
 */
public class SortedItemsTag extends BodyTagSupport {

	private static final long serialVersionUID = 9181473625054639687L;

	private List<SortedItemTag> items;

	private final List<SortedFixedContentTag> fixedContents = new ArrayList<SortedFixedContentTag>();

	private final Set<Integer> fixedContentSlots = new HashSet<Integer>();

	// tag attributes below

	private String itemHtmlWrapper = null;

	private String itemEvenClass = null;

	public List<SortedItemTag> getItems() {
		return items;
	}

	public List<SortedFixedContentTag> getFixedContents() {
		return fixedContents;
	}

	public Set<Integer> getFixedContentSlots() {
		return fixedContentSlots;
	}

	public void setItemHtmlWrapper(String itemHtmlWrapper) {
		this.itemHtmlWrapper = itemHtmlWrapper;
	}

	public void setItemEvenClass(String itemEvenClass) {
		this.itemEvenClass = itemEvenClass;
	}

	/**
	 * Adds a supported child tag to this container.
	 * 
	 * @param tag
	 *            the child tag to add
	 */
	public void addChild(AbstractSortedItemTag tag) {
		if (tag instanceof SortedItemTag) {
			items.add((SortedItemTag)tag);
		} else if (tag instanceof SortedFixedContentTag) {
			final int currentSlotIndex = getCurrentSlotIndex();
			fixedContents.add((SortedFixedContentTag) tag);
			fixedContentSlots.add(currentSlotIndex);
		} else {
			throw new UnsupportedOperationException("Cannot add child tag: " + tag.getClass().getName());
		}
	}

	/**
	 * Returns the current child tag "slot index". This index denotes the 0-based index of the next child tag that will
	 * be added.
	 * 
	 * @return the slot index of the current
	 */
	private int getCurrentSlotIndex() {
		return items.size() + fixedContents.size();
	}

	@Override
	public int doEndTag() throws JspException {
		// sort items according to the underlying property values, if available
		final Collator coll = Collator.getInstance(LocaleUtils.getCurrentLocale((HttpServletRequest) pageContext.getRequest()));
		
		Collections.sort(items, new Comparator<SortedItemTag>() {

			@Override
			public int compare(SortedItemTag o1, SortedItemTag o2) {
				final String property1 = (o1.propertyTag != null) ? o1.propertyTag.getPropertyValue() : "";
				final String property2 = (o2.propertyTag != null) ? o2.propertyTag.getPropertyValue() : "";					
				
				return coll.compare(property1, property2);
			}

		});

		// dump all sorted and fixed items in the correct order
		// to the JSP writer
		final Iterator<SortedItemTag> sortedIt = items.iterator();
		final Iterator<SortedFixedContentTag> fixedIt = fixedContents.iterator();
		int i = 0;
		int iSorted = 0;

		while (sortedIt.hasNext() || fixedIt.hasNext()) {
			// fixed content -> just print it
			if (fixedContentSlots.contains(i)) {
				final SortedFixedContentTag item = fixedIt.next();
				try {
					item.print(getPreviousOut());
				} catch (IOException e) {
					throw new JspException(e);
				}
			}

			// item -> possibly wrap and print it
			else {
				final SortedItemTag item = sortedIt.next();
				try {
					if (itemHtmlWrapper != null) {
						getPreviousOut().append(
								buildStartTag(itemHtmlWrapper, (iSorted % 2 == 1) ? itemEvenClass : null));
						// twisted, but true, since i starts with 0!
					}
					item.print(getPreviousOut());
					if (itemHtmlWrapper != null) {
						getPreviousOut().append(buildEndTag(itemHtmlWrapper));
					}
				} catch (IOException e) {
					throw new JspException(e);
				}
				iSorted++;
			}

			i++;
		}

		return EVAL_PAGE;
	}

	/**
	 * Builds an opening tag.
	 * 
	 * @param elementName
	 *            name of the HTML element
	 * @param cssClass
	 *            an optional CSS class; if null, no class will be used
	 * @return the HTML tag
	 */
	private String buildStartTag(String elementName, String cssClass) {
		final StringBuilder builder = new StringBuilder();

		builder.append("<");
		builder.append(elementName);
		if (cssClass != null) {
			builder.append(" class=\"");
			builder.append(cssClass);
			builder.append("\"");
		}
		builder.append(">");

		return builder.toString();
	}

	@Override
	public int doStartTag() throws JspException {
		items = new ArrayList<SortedItemTag>();

		return super.doStartTag();
	}

	/**
	 * Builds a closing tag.
	 * 
	 * @param elementName
	 *            name of the HTML element
	 * @return the HTML tag
	 */
	private String buildEndTag(String elementName) {
		final StringBuilder builder = new StringBuilder();

		builder.append("</");
		builder.append(elementName);
		builder.append(">");

		return builder.toString();
	}

}
