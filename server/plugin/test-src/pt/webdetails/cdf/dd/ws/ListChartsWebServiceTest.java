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
        Assert.assertEquals("[{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\"}]", ws.toJSON());
    }

    @Test
    public void listTwoCharts() {
        ChartList ws = new ChartList(this.testDirPath + "test-two-charts.cdfde");
        Assert.assertEquals("[{\"id\":\"socialPie\", \"title\":\"Social Pie Chart\"}, "
        		+ "{\"id\":\"socialBar\", \"title\":\"Social Bar Chart\"}]", ws.toJSON());
    }

    @Test
    public void listComplete() {
        ChartList ws = new ChartList(this.testDirPath + "test-complete.cdfde");
		Assert.assertEquals("[{\"id\":\"socialPie\", \"title\":\"Torta Social\"}," +
				" {\"id\":\"socialBar\", \"title\":\"Barra social\"}, {\"id\":\"socialLine\", \"title\":\"Linha social\"}]",
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
        ChartList ws = new ChartList(null);
        Assert.assertEquals("{\"error\": \"file does not exist\"}", ws.toJSON());
    }
}
