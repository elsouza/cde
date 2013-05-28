/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import java.util.Iterator;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.jxpath.JXPathContext;

import pt.webdetails.cdf.dd.util.JsonUtils;


public class ChartList {

    private final String[] fileNames;

    public ChartList(String... fileNames) {
    	
    	if (fileNames == null)
    		throw new IllegalArgumentException("The fileNames array can not be null");
    	
        this.fileNames = fileNames;
    }

    @SuppressWarnings("rawtypes")
    public String toJSON() {
    	
    	StringBuilder builder = new StringBuilder("");
    	builder.append("[");

    	for (String fileName : this.fileNames) {

	        JSON json = JsonUtils.getFileAsJson(fileName);
	        if (json == null)
	            return WebServiceCommons.JSON_FILE_DOES_NOT_EXIST;
	
	        String dashboardName = getDashboardName(fileName);
	        Iterator it = JXPathContext.newContext(json).iterate("components/rows[@parent='CHARTS']");

	        while (it.hasNext()) {
	            buildOutputJSON(builder, getIdAndTitleFromJSON((JSONObject) it.next()), dashboardName);
	        }
    	}

    	builder.append("]");
        return builder.toString();
    }

    private void buildOutputJSON(StringBuilder builder,
    		String[] jsonComponentIdAndTitle, String dashboardName) {

        if (jsonComponentIdAndTitle[0] != null && jsonComponentIdAndTitle[1] != null) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            
            builder.append(String.format("{\"id\":\"%s\", \"title\":\"%s\", \"dashboard\" : \"%s\"}",
            		jsonComponentIdAndTitle[0], jsonComponentIdAndTitle[1], dashboardName));
        }
    }

	private String getDashboardName(String dashboardName) {
		int begin = dashboardName.lastIndexOf("/");
		int end = dashboardName.lastIndexOf(".cdfde");
		return dashboardName.substring(begin + 1, end);
	}

    @SuppressWarnings("rawtypes")
    private String[] getIdAndTitleFromJSON(JSONObject row) {
        String[] componentIdAndTitle = new String[2];

        Iterator itObj = row.getJSONArray("properties").iterator();
        while (itObj.hasNext()) {
            JSONObject props = (JSONObject) itObj.next();

            if ("Id".equalsIgnoreCase(props.getString("type"))) {
                componentIdAndTitle[0] = props.getString("value");
            }

            if ("title".equalsIgnoreCase(props.getString("name"))) {
                componentIdAndTitle[1] = props.getString("value");
            }
        }
        return componentIdAndTitle;
    }
}
