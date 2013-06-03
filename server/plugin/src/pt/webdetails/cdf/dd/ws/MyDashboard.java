package pt.webdetails.cdf.dd.ws;

import java.io.FileWriter;
import java.io.IOException;

import org.pentaho.platform.engine.core.system.PentahoSystem;

import net.sf.json.JSON;
import pt.webdetails.cdf.dd.util.JsonUtils;
import pt.webdetails.cdf.dd.util.Utils;

public class MyDashboard {

	private String myDashboardPath = ""; 
	
	public MyDashboard(String myDashboardPath) {
		if (!WebServiceCommons.isUnitTest())
			this.myDashboardPath = Utils.joinPath(PentahoSystem.getApplicationContext().getSolutionPath(""), myDashboardPath);
	}
	
	public JSON findMyDashboardComponents(String user) {
		return JsonUtils.getFileAsJson(getFilePath(user));
	}
	
	public boolean save(String user, String dashboardComponentsJSON) {

		try {
			FileWriter file = new FileWriter(getFilePath(user));
			try {
				file.write(dashboardComponentsJSON);
				file.flush();
			} finally {
				file.close();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}


	private String getFilePath(String user) {
		String filePath = user + "_dashboard.json";
		if (!this.myDashboardPath.isEmpty()) {
			filePath = Utils.joinPath(myDashboardPath, filePath);
		}
		return filePath;
	}


}
