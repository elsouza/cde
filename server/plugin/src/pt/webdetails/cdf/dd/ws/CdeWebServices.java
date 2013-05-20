package pt.webdetails.cdf.dd.ws;


public class CdeWebServices {

    public String test(String echo) {
        return "This is my echo: " + (echo == null ? "null" : echo);
    }
}
