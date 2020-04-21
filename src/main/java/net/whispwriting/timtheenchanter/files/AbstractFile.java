package net.whispwriting.timtheenchanter.files;

import net.whispwriting.timtheenchanter.TimTheEnchanter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    protected TimTheEnchanter plugin;
    private File file;
    protected FileConfiguration config;

    public AbstractFile(TimTheEnchanter pl, String filename, String d){
        plugin = pl;
        File dir = new File(pl.getDataFolder() + d);
        if (!dir.exists()){
            dir.mkdirs();
        }
        file = new File(dir, filename);
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void save(){
        try{
            config.save(file);
        }catch(IOException e){
            System.out.println("Could not save file");
        }
    }
    public FileConfiguration get(){
        return config;
    }

    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void delete() {
        file.delete();
    }
}

