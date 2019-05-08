package net.whispwriting.timtheenchanter;

import net.whispwriting.timtheenchanter.commands.EnchantCommand;
import net.whispwriting.timtheenchanter.commands.EnchantCommandOldVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TimTheEnchanter extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName();
            int vNumIndex = version.indexOf("_");
            String vSub = version.substring(vNumIndex + 1, vNumIndex + 3);
            System.out.println(version);
            System.out.println(vNumIndex);
            System.out.println(vSub);
            int versionNum = Integer.parseInt(vSub);
            if (versionNum >= 13) {
                this.getCommand("enchant").setExecutor(new EnchantCommand());
            } else {
                this.getCommand("enchant").setExecutor(new EnchantCommandOldVersion());
            }
        }catch (NumberFormatException e){
            String version = Bukkit.getServer().getClass().getPackage().getName();
            int vNumIndex = version.indexOf("_");
            String vSub = version.substring(vNumIndex + 1, vNumIndex + 2);
            System.out.println(version);
            System.out.println(vNumIndex);
            System.out.println(vSub);
            int versionNum = Integer.parseInt(vSub);
            if (versionNum >= 13) {
                this.getCommand("enchant").setExecutor(new EnchantCommand());
            } else {
                this.getCommand("enchant").setExecutor(new EnchantCommandOldVersion());
            }
        }

    }

    @Override
    public void onDisable() {
        //
    }
}
