package net.whispwriting.timtheenchanter.files;

import net.whispwriting.timtheenchanter.TimTheEnchanter;

public class ConfigFile extends AbstractFile {

    public ConfigFile(TimTheEnchanter pl) {
        super(pl, "config.yml", "");
    }

    public void createDefaults(){
        config.addDefault("fancy-text", true);
        config.addDefault("enchant-all-gives-unbreakable", true);
    }
}
