/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ChartScriptTest extends ChartScript {

	public ChartScriptTest() { }
	
    @Before
    public void setUp() {
    	System.setProperty("mock.path", "true");
    }

	@SuppressWarnings("unused")
	private ChartScriptTest(String fileName) {
		super(fileName);
	}
	
	@Test
	public void testRemoveScriptTag() {
		String script = "<script language=\"javascript\" type=\"text/javascript\">Dashboards.init();</script>";
		Assert.assertEquals("Dashboards.init();", super.removeTagScript(script));
	}
	
	@Test
	public void testExtractComponents() {
		String script = "Dashboards.addComponents([render_texto_titulo,render_graficoBarra_faturamentoEmpresas,render_graficoBarra_funcionariosEmpresas]);";
		Assert.assertEquals("render_texto_titulo,render_graficoBarra_faturamentoEmpresas,render_graficoBarra_funcionariosEmpresas", super.extractComponentName(script));
	}

	@Test
	public void testAddUpdateAll() {
		String script = "Dashboards.addComponents([render_texto_titulo,render_graficoBarra_faturamentoEmpresas,render_graficoBarra_funcionariosEmpresas]);";
		Assert.assertEquals(script + " Dashboards.updateAll([render_texto_titulo,render_graficoBarra_faturamentoEmpresas,render_graficoBarra_funcionariosEmpresas]);",
				super.addUpdateAll(script));
	}
	
	@Test
	public void testAddUpdateAll2() {
		String script = "var render_table = { 	type: \"Table\", 	name: \"render_table\", 	priority: \"5\", 	htmlObject: \"table\", 	listeners: ['territory'], 	parameters: [[\"territory\",\"territory\"]], 	chartDefinition: { 		dataAccessId: \"table\", path: \"/SyncBR3/exemploCickAction/example_01.cda\", 		colHeaders: [], 		colTypes: [], 		colFormats: [], 		colWidths: [], 		colSortable: [], 		colSearchable: [], 		paginate: true, 		paginateServerside: false, 		paginationType: \"two_button\", 		filter: true, 		info: true, 		sort: true, 		sortBy: [], 		lengthChange: true, 		tableStyle: \"themeroller\" 	}, 	executeAtStart: true, 	extraOptions: [], 	expandParameters: [], 	expandOnClick: false  };  var territory = \"All Markets\"; Dashboards.setParameterViewMode('territory','unused');Dashboards.addComponents([render_pie,render_table]);";
		Assert.assertEquals(script + " Dashboards.updateAll([render_pie,render_table]);",
				super.addUpdateAll(script));
	}
	
	@Test
	public void testReplaceHTMLObject() {
		String script = "var render_graficoBarra_funcionariosEmpresas = {type: \"cccBarChart\",	name: \"render_graficoBarra_funcionariosEmpresas\",	htmlObject: \"numeroFuncEmpresas_obj\", listeners: []";		
		Assert.assertEquals("var render_graficoBarra_funcionariosEmpresas = {type: \"cccBarChart\",	name: \"render_graficoBarra_funcionariosEmpresas\",	htmlObject: \"NovoHTML\",  listeners: []",
				super.replaceHTMLObject("NovoHTML", script));
	}
	
}
