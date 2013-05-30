package pt.webdetails.cdf.dd.ws;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class SaveMyDashboardTest {

	@Test
	public void testSaveDashboard() {
		
		System.setProperty("mock.path", "true");
		String myDashboardPath = "";

		String expected = "[{\"originalDashboard\":\"A\",\"idComponent\":\"b\",\"htmlObject\":\"C\",\"componentTitle\":\"titulo\"}," +
				"{\"originalDashboard\":\"AAAA\",\"idComponent\":\"VVVb\",\"htmlObject\":\"saDFC\",\"componentTitle\":\"titulo\"}]";

		new MyDashboard(myDashboardPath).save(expected);
		
		File f = new File("usuario_dashboard.json");
		try {
			Assert.assertTrue("File not created", f.exists());
			Assert.assertEquals(expected, conteudoJSON());
		} finally {
			f.delete();
		}
	}

	private String conteudoJSON() {
		return new MyDashboard("").findMyDashboardComponents().toString();
	}
	
}
