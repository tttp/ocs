package eu.europa.ec.eci.oct.webcommons.tag.sort;

/**
 * Defines a single sorted item. This tag must be nested within a {@link SortedItemsTag} instance. The nested content is
 * dumped as-is to the JSP writer.
 * 
 * @author keschma
 * 
 */
public class SortedItemTag extends AbstractSortedItemTag implements Cloneable {

	private static final long serialVersionUID = -8668036080872084309L;

	SortedItemPropertyTag propertyTag;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		SortedItemTag tag = (SortedItemTag) super.clone();
		tag.propertyTag = (SortedItemPropertyTag) this.propertyTag.clone();

		return tag;
	}
}
