package pt.webdetails.cdf.dd.ws;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class SaveMyDashboardTest {

	@Test
	public void testSaveDashboard() {
		
		String myDashboardPath = null;

		String expected = "[{\"originalDashboard\":\"A\",\"idComponent\":\"b\",\"htmlObject\":\"C\"}," +
				"{\"originalDashboard\":\"AAAA\",\"idComponent\":\"VVVb\",\"htmlObject\":\"saDFC\"}]";

		new MyDashboard().save(myDashboardPath, expected);
		
		File f = new File("usuario_dashboard.json");
		try {
			Assert.assertTrue("File not created", f.exists());
			Assert.assertEquals(expected, conteudoJSON());
		} finally {
			f.delete();
		}
	}

	private String conteudoJSON() {
		return new MyDashboard().findMyDashboardComponents().toString();
	}
	
}
