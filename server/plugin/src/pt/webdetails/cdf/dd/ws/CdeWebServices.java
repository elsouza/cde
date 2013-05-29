/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import net.sf.json.JSON;


public class CdeWebServices {

	public String getScript(String componentId, String dashboardName, String newHtmlObject, String... files) {
		return new ChartScript(files).getScript(componentId, dashboardName, newHtmlObject);
	}

    public String listCharts(String... files) {
        return new ChartList(files).toJSON();
    }

    public boolean saveMyDashboard(String myDashboardPath, String dashboardComponents) {
    	return new MyDashboard().save(myDashboardPath, dashboardComponents);
    }

    public String findMyDashboardComponents() {
    	JSON json = new MyDashboard().findMyDashboardComponents();
    	if (json == null)
    		return "";
    	
    	return json.toString();
    }
}
