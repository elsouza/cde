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

    private final String fileName;

    public ChartList(String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings("rawtypes")
    public String toJSON() {
        JSON json = JsonUtils.getFileAsJson(this.fileName);
        if (json == null) {
            return WebServiceCommons.JSON_FILE_DOES_NOT_EXIST;
        }

        StringBuilder builder = new StringBuilder("[");
        Iterator it = JXPathContext.newContext(json).iterate("components/rows[@parent='CHARTS']");
        
        while (it.hasNext()) {
            JSONObject row = (JSONObject) it.next();
            appendIdAndTitle(builder, getIdAndTitle(row));
        }
        builder.append("]");

        return builder.toString();
    }

    private void appendIdAndTitle(StringBuilder builder, String[] idaAndTile) {
        if (idaAndTile[0] != null && idaAndTile[1] != null) {
            if (builder.length() > 1) {
                builder.append(", ");
            }

            builder.append(String.format("{\"id\":\"%s\", \"title\":\"%s\"}", idaAndTile[0], idaAndTile[1]));
        }
    }

    @SuppressWarnings("rawtypes")
    private String[] getIdAndTitle(JSONObject row) {
        String[] idaAndTile = new String[2];

        Iterator itObj = row.getJSONArray("properties").iterator();
        while (itObj.hasNext()) {
            JSONObject props = (JSONObject) itObj.next();

            if ("Id".equalsIgnoreCase(props.getString("type"))) {
                idaAndTile[0] = props.getString("value");
            }

            if ("title".equalsIgnoreCase(props.getString("name"))) {
                idaAndTile[1] = props.getString("value");
            }
        }
        return idaAndTile;
    }
}
