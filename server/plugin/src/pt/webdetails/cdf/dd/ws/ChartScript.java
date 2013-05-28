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

    protected ChartScript() {
    	this.fileName = "";
    }
    
    public ChartScript(String fileName) {
        this.fileName = fileName;
    }

    public String getScript(String componentId, String newHtmlObject) {
        try {

            JSON json = JsonUtils.getFileAsJson(fileName);
            if (json == null) {
                return WebServiceCommons.JSON_FILE_DOES_NOT_EXIST;
            }
            
            String alias = "mydashboard";
			return prepareScript(newHtmlObject,
					new RenderComponents().renderComponent(JXPathContext.newContext
							(json), alias, "render_"+ alias + "_" + componentId));
			
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	private String prepareScript(String newHtmlObject, String javascript) {
		javascript = replaceHTMLObject(newHtmlObject, javascript);
		javascript = removeTagScript(javascript);
		javascript = addUpdateAll(javascript);
		return javascript;
	}

	protected String addUpdateAll(String javascript) {
		return javascript + " Dashboards.updateAll([" + extractComponentName(javascript) + "]);";
	}

	protected String replaceHTMLObject(String newHtmlObject, String javascript) {
		return javascript.replaceAll("htmlObject:(.*)+,", "htmlObject: \"" + newHtmlObject + "\", ");
	}

	protected String extractComponentName(String javascript) {
		int i = javascript.lastIndexOf("([");
		int f = javascript.lastIndexOf("])");
		return javascript.substring(i + 2, f);
	}

	protected String removeTagScript(String script) {
		return script.replace("<script language=\"javascript\" type=\"text/javascript\">", "").replace("</script>", "");
	}
}
