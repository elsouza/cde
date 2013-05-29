package pt.webdetails.cdf.dd.ws;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSON;
import pt.webdetails.cdf.dd.util.JsonUtils;

public class MyDashboard {

	public JSON findMyDashboardComponents() {
		return JsonUtils.getFileAsJson("usuario_dashboard.json");
	}
	
	public void save(String myDashboardPath, List<MyDashboardComponent> dashboardComponents) {

		try {

			// FIXME pegar o nome do usuario
			FileWriter file = new FileWriter("usuario_dashboard.json");
			try {
				file.write(JsonUtils.buildMyDashboardJSON(dashboardComponents).toString());
				file.flush();
			} finally {
				file.close();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// PentahoSystem.getApplicationContext().
		// PentahoSystem.getApplicationContext().getSolutionPath(PLUGIN_PATH);
		// TODO se funcionar esse pentahoSystem get Bla, usar isso nos outros
		// PentahoSystem.getUserDetailsRoleListService().getAllUsers();
	}


}
