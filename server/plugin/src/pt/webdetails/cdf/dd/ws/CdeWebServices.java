/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import pt.webdetails.cdf.dd.CdfTemplates;

public class CdeWebServices {

    public String test(String echo) {
        return "This is my echo: " + (echo == null ? "null" : echo);
    }

    public String listCharts(String file) {
        ChartList list = getChartList(file);
        return list.toJSON();
    }

    protected ChartList getChartList(String file) {
        ChartList list = new ChartList(CdfTemplates.DEFAULT_TEMPLATE_DIR, file);
        return list;
    }
}
