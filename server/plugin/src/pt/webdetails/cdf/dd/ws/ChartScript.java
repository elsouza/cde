/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import net.sf.json.JSON;

import org.apache.commons.jxpath.JXPathContext;

import pt.webdetails.cdf.dd.render.RenderComponents;
import pt.webdetails.cdf.dd.util.JsonUtils;

public class ChartScript {

    private final String fileName;

    public ChartScript(String fileName) {
        this.fileName = fileName;
    }

    public String getScript(String componentId) {
        try {
            JSON json = JsonUtils.getFileAsJson(fileName);
            if (json == null) {
                return WebServiceCommons.JSON_FILE_DOES_NOT_EXIST;
            }
            
            String alias = "mydashboard";
            
			return new RenderComponents().renderComponent(
					JXPathContext.newContext(json), alias,
					"render_" + alias + "_" + componentId); 
			
			// FIXME ta trazendo mais coisas do que deveira
			
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
