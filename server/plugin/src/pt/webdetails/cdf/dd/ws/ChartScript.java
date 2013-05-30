/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd.ws;

import net.sf.json.JSON;

import org.apache.commons.jxpath.JXPathContext;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import pt.webdetails.cdf.dd.render.RenderComponents;
import pt.webdetails.cdf.dd.util.JsonUtils;
import pt.webdetails.cdf.dd.util.Utils;

public class ChartScript {

    private final String[] fileNames;

    protected ChartScript() {
    	this.fileNames = new String[] { };
    }
    
    public ChartScript(String... fileNames) {
        this.fileNames = fileNames;
    }

    public String getScript(String componentId, String dashboardName, String newHtmlObject) {

    	for (String fileName : fileNames) {
    		if (fileName.endsWith(dashboardName + ".cdfde")) {
    			return getComponentScript(componentId, newHtmlObject, fileName);
    		}
    	}
    	return "";
    }

	private String getComponentScript(String componentId, String newHtmlObject, String fileName) {

		if (!WebServiceCommons.isUnitTest())
			fileName = Utils.joinPath(PentahoSystem.getApplicationContext().getSolutionPath(""), fileName);
		
		JSON json = JsonUtils.getFileAsJson(fileName);
		if (json == null) {
			return WebServiceCommons.JSON_FILE_DOES_NOT_EXIST;
		}
		
		try {
			return prepareScript(newHtmlObject, new RenderComponents().renderComponent(JXPathContext.newContext(json),
					"mydashboard", "render_mydashboard_" + componentId));

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
