/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.webdetails.cdf.dd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author pedro
 */
public class JsonUtils {

  private static Log logger = LogFactory.getLog(JsonUtils.class);

  public static JSON readJsonFromInputStream(final InputStream input) throws IOException {
    
    String contents = StringUtils.trim(IOUtils.toString(input, "UTF-8"));
    return (JSON) JSONSerializer.toJSON(contents);
  }

  public static void buildJsonResult(final OutputStream out, final Boolean sucess, final Object result) {

    final JSONObject jsonResult = new JSONObject();

    jsonResult.put("status", sucess.toString());
    if (result != null) {
      jsonResult.put("result", result);
    }

    PrintWriter pw = null;
    try {
      pw = new PrintWriter(out);
      pw.print(jsonResult.toString(2));
      pw.flush();
    } finally{
      IOUtils.closeQuietly(pw);
    }
  }

  public static String toJsString(String text)
  {
	  String content;
	  if(text == null)
	  {
		  content = "";
	  }
	  else
	  {
		  content = text.replaceAll("\"", "\\\\\"")  // replaceBy: |\\"|
                        .replaceAll("\n", "\\\\n" )  // replaceBy: |\\n|
                        .replaceAll("\r", "\\\\r" ); // replaceBy: |\\r|
	  }

	  return "\"" + content + "\"";
  }
  
  public static JSON getFileAsJson(String path) {
      InputStream fileInputStream = getFileInputStream(path);
      if (fileInputStream == null) {
          return null;
      }

      StringBuilder builder = new StringBuilder();
      Scanner scanner = new Scanner(fileInputStream);
      while (scanner.hasNextLine()) {
          builder.append(scanner.nextLine());
      }
      
      return JSONSerializer.toJSON(builder.toString());
  }

  private static InputStream getFileInputStream(String path) {
      if (StringUtils.isBlank(path)) {
          return null;
      }

      File file = new File(path);

      try {
          return file.exists() ? new FileInputStream(file) : null;
      } catch (FileNotFoundException e) {
          logger.warn("File not found: " + path);
          return null;
      }
  }

}
