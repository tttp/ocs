package eu.europa.ec.eci.oct.business.api;

import java.util.List;

import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;

/**
 * Exporter service represents the entry point to the business layer for the export process.
 * 
 * @author chiridl
 * 
 */
public interface ExporterService {

	/**
	 * Starts the actual export for the given export parameters. See {@link ExportParametersBean} for further details.
	 * 
	 * @param parametersList
	 *            - the list containing export parameter beans
	 */
	public void export(List<ExportParametersBean> parametersList);
}
