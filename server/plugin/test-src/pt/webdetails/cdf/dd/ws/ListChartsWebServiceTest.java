/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import junit.framework.Assert;

import org.junit.Test;


public class ListChartsWebServiceTest {

    private final String testDirPath = getClass().getClassLoader().getResource("").getPath();

    @Test
    public void listOneChart() {
        ChartList ws = new ChartList(this.testDirPath + "test-one-chart.cdfde");
        Assert.assertEquals("[{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\", \"dashboard\" : \"test-one-chart\"}]", ws.toJSON());
    }

    @Test
    public void listTwoCharts() {
        ChartList ws = new ChartList(this.testDirPath + "test-two-charts.cdfde");
        Assert.assertEquals("[{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\", \"dashboard\" : \"test-two-charts\"}, "
        		+ "{\"id\":\"socialBar\", \"title\":\"Social Bar Chart\", \"dashboard\" : \"test-two-charts\"}]", ws.toJSON());
    }

    @Test 
    public void listThreeChartsFromTwoFiles() {
    	ChartList ws = new ChartList(this.testDirPath + "test-one-chart.cdfde", this.testDirPath + "test-two-charts.cdfde");
    	
    	Assert.assertEquals(
    			"[{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\", \"dashboard\" : \"test-one-chart\"}, " +
    			 "{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\", \"dashboard\" : \"test-two-charts\"}, " +
    			 "{\"id\":\"socialBar\", \"title\":\"Social Bar Chart\", \"dashboard\" : \"test-two-charts\"}]", ws.toJSON());
    }
    
    @Test
    public void listComplete() {
        ChartList ws = new ChartList(this.testDirPath + "test-complete.cdfde");
        
		Assert.assertEquals(
				"[{\"id\":\"mapa_empresasUF\", \"title\":\"null\", \"dashboard\" : \"test-complete\"}," +
				" {\"id\":\"mapa_softwaresUF\", \"title\":\"null\", \"dashboard\" : \"test-complete\"}," +
				" {\"id\":\"graficoBarra_faturamentoEmpresas\", \"title\":\"\", \"dashboard\" : \"test-complete\"}," +
				" {\"id\":\"graficoBarra_funcionariosEmpresas\", \"title\":\"\", \"dashboard\" : \"test-complete\"}," +
				" {\"id\":\"graficoBarra_statusProcesso\", \"title\":\"\", \"dashboard\" : \"test-complete\"}]",
				ws.toJSON());
    }

    
    @Test
    public void listNocharts() {
        ChartList ws = new ChartList(this.testDirPath + "test-no-charts.cdfde");
        Assert.assertEquals("[]", ws.toJSON());
    }

    @Test
    public void listFileDoesNotExist() {
        ChartList ws = new ChartList(this.testDirPath + "no-existent-file.cdfde");
        Assert.assertEquals("{\"error\": \"file does not exist\"}", ws.toJSON());
    }

    @Test
    public void listWithEmptyFileName() {
        ChartList ws = new ChartList("");
        Assert.assertEquals("{\"error\": \"file does not exist\"}", ws.toJSON());
    }

    @Test
    public void listWithNullFileName() {
    	
    	try {
    		String[] array = null;
    		new ChartList(array);
    		Assert.fail("should throw IllegalArgumentException");
    	} catch (Exception ex) {
    		Assert.assertTrue(ex instanceof IllegalArgumentException);
    	}
    	
    }
}
