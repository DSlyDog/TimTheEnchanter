package net.whispwriting.timtheenchanter;

import net.whispwriting.timtheenchanter.commands.EnchantCommand;
import net.whispwriting.timtheenchanter.files.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimTheEnchanter extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigFile config = new ConfigFile(this);
        config.createDefaults();
        config.get().options().copyDefaults(true);
        config.save();
        this.getCommand("enchant").setExecutor(new EnchantCommand(this));
    }

    @Override
    public void onDisable() {
        //
    }
}
