package pt.webdetails.cdf.dd.ws;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class SaveMyDashboardTest {

	@Test
	public void testSaveDashboard() {
		
		String myDashboardPath = null;

		List<MyDashboardComponent> dashboardComponents = new ArrayList<MyDashboardComponent>();
		MyDashboardComponent dc = new MyDashboardComponent("A", "b", "C");
		MyDashboardComponent dc2 = new MyDashboardComponent("AAAA", "VVVb", "saDFC");
		dashboardComponents.add(dc);
		dashboardComponents.add(dc2);
		
		new MyDashboard().save(myDashboardPath, dashboardComponents);
		
		File f = new File("usuario_dashboard.json");
		try {
			Assert.assertTrue("File not created", f.exists());
			Assert.assertEquals(
					"[{\"originalDashboard\":\"A\",\"idComponent\":\"b\",\"htmlObject\":\"C\"}," +
				  	 "{\"originalDashboard\":\"AAAA\",\"idComponent\":\"VVVb\",\"htmlObject\":\"saDFC\"}]",
					conteudoJSON());
		
		} finally {
			f.delete();
		}
	}

	private String conteudoJSON() {
		return new MyDashboard().findMyDashboardComponents().toString();
	}
}
