package pt.webdetails.cdf.dd.ws;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSON;
import pt.webdetails.cdf.dd.util.JsonUtils;

public class MyDashboard {

	public JSON findMyDashboardComponents() {
		return JsonUtils.getFileAsJson("usuario_dashboard.json");
	}
	
	public boolean save(String myDashboardPath, String dashboardComponentsJSON) {

		try {
			// FIXME pegar o nome do usuario
			FileWriter file = new FileWriter("usuario_dashboard.json");
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
		// PentahoSystem.getApplicationContext().
		// PentahoSystem.getApplicationContext().getSolutionPath(PLUGIN_PATH);
		// TODO se funcionar esse pentahoSystem get Bla, usar isso nos outros
		// PentahoSystem.getUserDetailsRoleListService().getAllUsers();
	}


}
