package pt.webdetails.cdf.dd.ws;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class SaveMyDashboardTest {

	@Test
	public void testSaveDashboard() {
		
		System.setProperty("mock.path", "true");
		String myDashboardPath = "";

		String expected = "{\"numberOfLines\":2,\"myComponents\":[{\"originalDashboard\":\"PoliticSys_dashboard\"," +
				"\"idComponent\":\"graficoBarra_faturamentoEmpresas\",\"htmlObject\":\"conteudo_22\"," +
				"\"componentTitle\":\"Barra de faturamento das empresas lorem upsum mussimis\"}," +
				"{\"originalDashboard\":\"PoliticSys_dashboard\",\"idComponent\":\"graficoBarra_faturamentoEmpresas\"," +
				"\"htmlObject\":\"conteudo_11\",\"componentTitle\":\"Barra de faturamento das empresas lorem upsum mussimis\"}]}";
		

		new MyDashboard(myDashboardPath).save("usuario", expected);
		
		File f = new File("usuario_dashboard.json");
		try {
			Assert.assertTrue("File not created", f.exists());
			Assert.assertEquals(expected, conteudoJSON());
		} finally {
			f.delete();
		}
	}

	private String conteudoJSON() {
		return new MyDashboard("").findMyDashboardComponents("usuario").toString();
	}
	
}
