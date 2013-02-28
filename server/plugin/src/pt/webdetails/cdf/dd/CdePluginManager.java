/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cdf.dd;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.pentaho.platform.util.xml.dom4j.XmlDom4JHelper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.pentaho.platform.api.engine.IFileFilter;
import org.pentaho.platform.api.engine.ISolutionFile;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import org.pentaho.platform.repository.solution.filebased.FileSolutionFile;
import pt.webdetails.cdf.dd.CdeSettings;
import pt.webdetails.cdf.dd.DashboardDesignerContentGenerator;
import pt.webdetails.cdf.dd.render.datasources.CdaDatasource;
import pt.webdetails.cdf.dd.render.properties.PropertyManager;
import pt.webdetails.cdf.dd.util.Utils;
import pt.webdetails.cpf.PluginSettings;
import pt.webdetails.cpf.repository.RepositoryAccess;
import pt.webdetails.cpf.repository.RepositoryAccess.FileAccess;
import sun.management.FileSystem;

/**
 *
 * @author pdpi
 */
public class CdePluginManager extends PluginSettings
{

  protected static Log logger = LogFactory.getLog(CdePluginManager.class);
  private static final String PLUGIN_DIR = Utils.joinPath("system", DashboardDesignerContentGenerator.PLUGIN_NAME);
  private static CdePluginManager _engine;
  private String basePath = PentahoSystem.getApplicationContext().getSolutionPath("");
  private ArrayList<String> compatiblePlugins = new ArrayList<String>();
  //private ArrayList<ISolutionFile> settings = new ArrayList<ISolutionFile>();
  private HashMap<String,ISolutionFile> settings = new HashMap<String, ISolutionFile>();

  
  /**
   * Verificar o que é isto!
   */
  private static final String PACKAGEHEADER = "pt.webdetails.cdf.dd.render.components.";

  protected CdePluginManager()
  {    
  }

  public static synchronized CdePluginManager getInstance()
  {
    if (_engine == null)
    {
      _engine = new CdePluginManager();
      _engine.findCDECompatible();
    }

    return _engine;
  }

  private List<File> listAllFiles(File dir, FilenameFilter filter)
  {
    ArrayList<File> results = new ArrayList<File>();

    File[] files = dir.listFiles();
    if (files != null)
    {
      for (File file : files)
      {
        if (file.isDirectory())
        {
          results.addAll(listAllFiles(file, filter));
        }
        else if (filter.accept(dir, file.getName()))
        {
          results.add(file);
        }
      }
    }
    return results;
  }

  @Override
  public String getPluginName()
  {
    return getPluginNameTemp();
  }

  /**
   * CDE-Compatible plugins search
   * This will search for "cde-compatible" tag on settings.xml file
   * A HashMap<nameKey,pluginSettingsXml> will be filled with the information gathered
   */
  public void findCDECompatible()
  {


    ISolutionFile systemDir = RepositoryAccess.getRepository().getSolutionFile("system", FileAccess.NONE);

    ISolutionFile[] systemFolders = systemDir.listFiles(new IFileFilter()
    {

      public boolean accept(ISolutionFile file)
      {
        return file.isDirectory();
      }
    });

    ArrayList<ISolutionFile> settingsFiles = new ArrayList<ISolutionFile>();

    for (ISolutionFile sysFolder : systemFolders)
    {
      
      /*
       * sett variable stores the files which names are "settings.xml"
       */
      ISolutionFile[] sett = sysFolder.listFiles(new IFileFilter()
      {

        public boolean accept(ISolutionFile file)
        {
          return file.getFileName().equals("settings.xml");
        }
      });
      
      /*
       * A little iteration to transfer the settings files from a temporary vessel into another for further filtering
       */
      for (ISolutionFile s : sett)
      {
        settingsFiles.add(s);
      }
    }



    
    for (ISolutionFile file : settingsFiles)
    {
      
      String pluginName = file.retrieveParent().getFileName();
      setPlugin(pluginName);
      
      List<String> compat = getTagValue("cde-compatible");
      List<String> description = getTagValue("description");
      if (compat.size() > 0)
      {
        for(String s : compat){
          if(s.equalsIgnoreCase("true")){
            addSettingToCollection(pluginName,file);
          }
          
        }
        String desc = new String();
        for(String s : description){
          desc += s;
        }
          logger.info("CDE-Compatible Plugin found:"+pluginName);
          logger.info("'"+pluginName.toUpperCase()+"' description: "+desc);
      }
    }
  }
  
  private ArrayList<String> getCompatiblePluginsNameList(){
    return this.compatiblePlugins;
  }
  
  /**
   * This adds the settings.xml of the nameKey plugin
   * @param nameKey plugin name
   * @param file settings.xml file reference
   */
  private void addSettingToCollection(String nameKey, ISolutionFile file){
    this.settings.put(nameKey,file); //this map comes in handy to do aditional checks on the settings.xml file
    this.compatiblePlugins.add(nameKey); //this is to quickly show the detected plugins
  }
  
  /**
   * This function transforms the CDE-Compatible plugins
   * informations into a JSON then adds the JSONObjects created
   * into an array to be sent
   * @return returns a JSONArray  in String format 
   */
  public String pluginsToJSON(){
    
    JSONArray pluginsArray = new JSONArray();
    
    for(String name : getCompatiblePluginsNameList()){
      
      JSONObject pluginObject = new JSONObject();
      setPlugin(name);
      
      pluginObject.put("title", getTagValue("title"));
      pluginObject.put("description", getTagValue("description"));
      pluginObject.put("url",getTagValue("url"));
      pluginObject.put("jsPath", getTagValue("jsPath"));
      pluginObject.put("pluginId", getPluginName());
      
      pluginsArray.add(pluginObject);
    }
    
    logger.info("Feeding client with CDE-Compatible plugin list");
    return pluginsArray.toString();
  }
  
  private ISolutionFile getSettingsFromPlugin(String name){
      return settings.get(name);
  }
  
  
  /**
   * This class will store all the plugin settings inside the settings.xml
   */
  /*
  class CdePlugin {
    
    CdePlugin(){
      
    }
    
  }*/
  
  
}
