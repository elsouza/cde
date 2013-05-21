package pt.webdetails.cdf.dd.ws;

import java.io.FileInputStream;
import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.jxpath.JXPathContext;

import pt.webdetails.cdf.dd.render.RenderComponents;
import pt.webdetails.cdf.dd.util.JsonUtils;


public class CdeWebServices {

    public String test(String echo) {
        return "This is my echo: " + (echo == null ? "null" : echo);
    }

	public String getScriptForComponentId(String dashboardName, String componentId) {
		
		try {
			FileInputStream streamDashboard = new FileInputStream(dashboardName);
			final JSONObject json = (JSONObject) JsonUtils.readJsonFromInputStream(streamDashboard);
			final JXPathContext doc = JXPathContext.newContext(json);
			
			return new RenderComponents().render(doc, ""); //TODO oq é o ALIAS?
			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}
}
