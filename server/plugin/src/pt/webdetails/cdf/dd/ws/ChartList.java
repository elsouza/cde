/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pt.webdetails.cdf.dd.DashboardDesignerContentGenerator;
import pt.webdetails.cdf.dd.render.RenderComponents;


public class ChartList {

    private static final String JSON_FILE_DOES_NOT_EXIST = "{\"error\": \"file does not exist\"}";

    private static Log logger = LogFactory.getLog(DashboardDesignerContentGenerator.class);

    private final String fileName;

    public ChartList(String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings("rawtypes")
    public String toJSON() {
        JSON json = getFileAsJson();
        if (json == null) {
            return JSON_FILE_DOES_NOT_EXIST;
        }

        StringBuilder builder = new StringBuilder("[");
        Iterator it = JXPathContext.newContext(json).iterate("layout/components/rows[@parent='CHARTS']");

        while (it.hasNext()) {
            JSONObject row = (JSONObject) it.next();

            String[] idaAndTile = getIdAndTitle(row);

            appendIdAndTitle(builder, idaAndTile);
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


    private JSON getFileAsJson() {
        InputStream fileInputStream = getFileInputStream();
        if (fileInputStream == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(fileInputStream);
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }

        return JSONSerializer.toJSON(builder.toString());
    }

    private InputStream getFileInputStream() {
        if (StringUtils.isBlank(this.fileName)) {
            return null;
        }

        File file = new File(this.fileName);

        try {
            return file.exists() ? new FileInputStream(file) : null;
        } catch (FileNotFoundException e) {
            logger.warn("File not found: " + this.fileName);
            return null;
        }
    }

	public String getScript(String componentId) {
		try {
	        JSON json = getFileAsJson();
	        if (json == null) {
	            return JSON_FILE_DOES_NOT_EXIST;
	        }
			return new RenderComponents().render(JXPathContext.newContext(json), "bla"); //TODO wtf esse parametro?
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
