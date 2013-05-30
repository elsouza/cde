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
	
	public JSON findMyDashboardComponents() {
		return JsonUtils.getFileAsJson(getFilePath());
	}
	
	public boolean save(String dashboardComponentsJSON) {

		try {
			
			FileWriter file = new FileWriter(getFilePath());
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

		// FIXME usuario!!
		// PentahoSystem.getUserDetailsRoleListService().getAllUsers();
	}


	private String getFilePath() {
		String filePath = "usuario_dashboard.json";
		if (!this.myDashboardPath.isEmpty()) {
			filePath = Utils.joinPath(myDashboardPath, filePath);
		}
		return filePath;
	}


}
