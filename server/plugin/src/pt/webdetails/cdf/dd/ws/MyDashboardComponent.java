package pt.webdetails.cdf.dd.ws;

public class MyDashboardComponent {

	private String idComponent;

	private String originalDashboard;
	
	private String htmlObject;

	public MyDashboardComponent(String originalDashboard, String idComponent, String htmlObject) {
		this.idComponent = idComponent;
		this.originalDashboard = originalDashboard;
		this.htmlObject = htmlObject;
	}

	public String getOriginalDashboard() {
		return originalDashboard;
	}
	
	public String getIdComponent() {
		return idComponent;
	}
	
	public String getHtmlObject() {
		return htmlObject;
	}

}
