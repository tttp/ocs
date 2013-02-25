package eu.europa.ec.eci.oct.webcommons.tag.sort;

/**
 * Defines a static block of output. This tag must be nested within a {@link SortedItemsTag} instance. The nested
 * content is dumped as-is to the JSP writer.
 * 
 * @author keschma
 * 
 */
public class SortedFixedContentTag extends AbstractSortedItemTag implements Cloneable {

	private static final long serialVersionUID = 7311793440686166751L;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
