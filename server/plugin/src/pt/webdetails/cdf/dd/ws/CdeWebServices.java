/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

public class CdeWebServices {

    public String test(String echo) {
        return "This is my echo: " + (echo == null ? "null" : echo);
    }

	public String getScript(String file, String componentId, String newHtmlObject) {
		return new ChartScript(file).getScript(componentId, newHtmlObject);
	}

    public String listCharts(String file) {
        return new ChartList(file).toJSON();
    }
}
