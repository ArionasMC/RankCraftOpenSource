package me.amc.rankcraft.config;

import me.amc.rankcraft.MainCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class ConfigUpdater {

     private YamlConfiguration outConfig;
     private File outFile;
     private InputStream inStream;
     private InputStreamReader inStreamReader;

     public ConfigUpdater() {
          outFile = new File(MainCore.instance.getDataFolder()+"/config.yml");
          outConfig = YamlConfiguration.loadConfiguration(outFile);
     }

     public void update() throws IOException {
          Set<String> keys = outConfig.getKeys(true);
          HashMap<String, Object> map = new HashMap<>();
          for(String key : keys) {
               map.put(key, outConfig.get(key));
               //System.out.println(key+" of class "+map.get(key).getClass()+" :: "+map.get(key).toString());
          }
          inStream = MainCore.instance.getClass().getResourceAsStream("/config.yml");
          inStreamReader = new InputStreamReader(inStream);

          FileWriter fw = new FileWriter(outFile, false);
          BufferedReader br = new BufferedReader(inStreamReader);
          String line;
          while((line = br.readLine()) != null)
               fw.write(line+'\n');
          br.close();
          fw.close();

          outConfig = YamlConfiguration.loadConfiguration(outFile);
          for(String key : map.keySet())
               if (outConfig.contains(key))
                    outConfig.set(key, map.get(key));
          outConfig.save(outFile);

     }

}
