/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

public class CdeWebServices {

    public String test(String echo) {
        return "This is my echo: " + (echo == null ? "null" : echo);
    }

	public String getScript(String file, String componentId) {
		ChartList list = getChartList(file);
		return list.getScript(componentId);
	}

    public String listCharts(String file) {
        ChartList list = getChartList(file);
        return list.toJSON();
    }

<<<<<<< HEAD
    protected ChartList getChartList(String file) {
        ChartList list = new ChartList(file);
        return list;
=======
    private ChartList getChartList(String file) {
        return new ChartList(CdfTemplates.DEFAULT_TEMPLATE_DIR, file);
>>>>>>> 3d7972257c70bb8ec9df1430cc324f11be9915d4
    }

}
