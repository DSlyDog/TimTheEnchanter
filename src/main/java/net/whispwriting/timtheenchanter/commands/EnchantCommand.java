package net.whispwriting.timtheenchanter.commands;

import net.whispwriting.timtheenchanter.TimTheEnchanter;
import net.whispwriting.timtheenchanter.files.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantCommand implements CommandExecutor {

    private TimTheEnchanter plugin;
    private boolean fancyText;
    private ConfigFile config;

    public EnchantCommand(TimTheEnchanter plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use that command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("TimTheEnchanter.enchant")){
            player.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
            return true;
        }

        config = new ConfigFile(plugin);
        fancyText = config.get().getBoolean("fancy-text");

        if (args.length >= 1 && args[0].equals("all")){
            if (!player.hasPermission("TimTheEnchanter.enchant.all")){
                player.sendMessage(ChatColor.YELLOW + "[Tim] You lack the strength to wield such power!");
                return true;
            }
            ItemStack item = player.getItemInHand();
            if (player.hasPermission("TimTheEnchanter.enchant.unsafe")){
                enchantAll(true, item, args, player);
            }else{
                enchantAll(false, item, args, player);
            }
            player.sendMessage(ChatColor.YELLOW + "[Tim] " + getQuote());
        }else{
            if (args.length == 1 && args[0].equalsIgnoreCase("unbreakable")){
                ItemStack item = player.getItemInHand();
                ItemMeta meta = item.getItemMeta();
                meta.spigot().setUnbreakable(true);
                item.setItemMeta(meta);
                player.sendMessage(ChatColor.YELLOW + "[Tim] " + getQuote());
                return true;
            }
            if (args.length != 2){
                player.sendMessage(ChatColor.YELLOW + "[Tim] You must only tell me the enchantment and level.");
                return true;
            }
            ItemStack item = player.getItemInHand();
            try {
                int applyLvl = Integer.parseInt(args[1]);
                if (fancyText){
                    ItemMeta meta = item.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    List<String> lore = new ArrayList<>();
                    Map<Enchantment, Integer> enchantments = item.getEnchantments();
                    for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()){
                        String name = buildName(getEnchantmentLabel(enchantment.getKey().getName()));
                        String level = romanNumeral(Integer.toString(enchantment.getValue()));
                        if (!name.equals(buildName(args[0])))
                            lore.add(ChatColor.GRAY + name + " " + level);
                    }
                    Enchantment enchantment = Enchantment.getByName(getEnchantmentName(args[0]));
                    if (enchantment == null){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] That is not an enchantment I can do.");
                        return true;
                    }
                    String name = buildName(args[0]);
                    String level = romanNumeral(args[1]);
                    if (player.hasPermission("TimTheEnchanter.enchant.unsafe")){
                        meta.addEnchant(enchantment, applyLvl, true);
                        lore.add(ChatColor.GRAY + name + " " + level);
                    }else{
                        if (applyLvl > enchantment.getMaxLevel()){
                            meta.addEnchant(enchantment, enchantment.getMaxLevel(), false);
                            lore.add(ChatColor.GRAY + name + " " + romanNumeral(Integer.toString(enchantment.getMaxLevel())));
                        }else{
                            meta.addEnchant(enchantment, applyLvl, false);
                            lore.add(ChatColor.GRAY + name + " " + level);
                        }
                    }
                    try {
                        meta.getLore().clear();
                    }catch(NullPointerException e){
                        //
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }else{
                    Enchantment enchantment = Enchantment.getByName(getEnchantmentName(args[0]));
                    if (enchantment == null){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] That is not an enchantment I can do.");
                        return true;
                    }
                    if (player.hasPermission("TimTheEnchanter.enchant.unsafe")){
                        item.addUnsafeEnchantment(enchantment, applyLvl);
                    }else{
                        if (applyLvl > enchantment.getMaxLevel()){
                            item.addEnchantment(enchantment, enchantment.getMaxLevel());
                        }else{
                            item.addEnchantment(enchantment, applyLvl);
                        }
                    }
                }
                player.sendMessage(ChatColor.YELLOW + "[Tim] " + getQuote());
            }catch(NumberFormatException e){
                player.sendMessage(ChatColor.YELLOW + "[Tim] You power level must be a number.");
                return true;
            }
        }
        return true;
    }

    private void enchantAll(boolean unsafe, ItemStack item, String[] args, Player player){
        boolean addUnbreakable = config.get().getBoolean("enchant-all-gives-unbreakable");
        if (unsafe){
            if (fancyText) {
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                List<String> lore = new ArrayList<>();
                String level = "10000";
                int applyLvl = 10000;
                if (args.length == 2) {
                    level = romanNumeral(args[1]);
                    try {
                        applyLvl = Integer.parseInt(args[1]);
                    }catch(NumberFormatException e){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] You power level must be a number.");
                        return;
                    }
                }
                for (Enchantment enchantment : Enchantment.values()) {
                    String name = buildName(getEnchantmentLabel(enchantment.getName()));
                    meta.addEnchant(enchantment, applyLvl, true);
                    lore.add(ChatColor.GRAY + name + " " + level);
                }
                try {
                    meta.getLore().clear();
                }catch(NullPointerException e){
                    //
                }
                if (addUnbreakable)
                    meta.spigot().setUnbreakable(true);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }else{
                int applyLvl = 10000;
                if (args.length == 2) {
                    try {
                        applyLvl = Integer.parseInt(args[1]);
                    }catch(NumberFormatException e){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] You power level must be a number.");
                        return;
                    }
                }
                for (Enchantment enchantment : Enchantment.values()) {
                    item.addUnsafeEnchantment(enchantment, applyLvl);
                }
                if (addUnbreakable) {
                    ItemMeta meta = item.getItemMeta();
                    meta.spigot().setUnbreakable(true);
                    item.setItemMeta(meta);
                }
            }
        }else{
            if (fancyText){
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                List<String> lore = new ArrayList<>();
                int applyLvl = 5;
                if (args.length == 2) {
                    try {
                        applyLvl = Integer.parseInt(args[1]);
                    }catch(NumberFormatException e){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] You power level must be a number.");
                        return;
                    }
                }
                for (Enchantment enchantment : Enchantment.values()) {
                    String name = buildName(getEnchantmentLabel(enchantment.getName()));
                    if (applyLvl > enchantment.getMaxLevel())
                        meta.addEnchant(enchantment, enchantment.getMaxLevel(), false);
                    else{
                        meta.addEnchant(enchantment, applyLvl, false);
                    }
                    lore.add(ChatColor.GRAY + name + " " + romanNumeral(Integer.toString(applyLvl)));
                }
                try {
                    meta.getLore().clear();
                }catch(NullPointerException e){
                    //
                }
                if (addUnbreakable)
                    meta.spigot().setUnbreakable(true);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }else{
                int applyLvl = 5;
                if (args.length == 2) {
                    try {
                        applyLvl = Integer.parseInt(args[1]);
                    }catch(NumberFormatException e){
                        player.sendMessage(ChatColor.YELLOW + "[Tim] You power level must be a number.");
                        return;
                    }
                }
                for (Enchantment enchantment : Enchantment.values()) {
                    if (applyLvl > enchantment.getMaxLevel())
                        item.addEnchantment(enchantment, enchantment.getMaxLevel());
                    else{
                        item.addEnchantment(enchantment, applyLvl);
                    }
                }
                if (addUnbreakable) {
                    ItemMeta meta = item.getItemMeta();
                    meta.spigot().setUnbreakable(true);
                    item.setItemMeta(meta);
                }
            }
        }
    }

    private String getQuote(){
        ArrayList<String> timQuotes = new ArrayList<String>();
        timQuotes.add("I... am an enchanter. ");
        timQuotes.add("There are some who call me... 'Tim'? ");
        timQuotes.add("Yes, I can help you find the Holy Grail. ");
        timQuotes.add("To the north there lies a cave-- the cave of Caerbannog-- wherein, carved in mystic runes upon the very living rock, the last words of Olfin Bedwere of Rheged... ");
        timQuotes.add("Follow. But! Follow only if ye be men of valour, for the entrance to this cave is guarded by a creature so foul, so cruel that no man yet has fought with it and lived!");
        timQuotes.add("So, brave knights, if you do doubt your courage or your strength, come no further, for death awaits you all with nasty, big, pointy teeth. ");
        timQuotes.add("Behold the cave of Caerbannog! ");
        timQuotes.add("That's the most foul, cruel, and bad-tempered rodent you ever set eyes on! Look, that rabbit's got a vicious streak a mile wide! It's a killer!");
        timQuotes.add("I warned you, but did you listen to me? Oh, no, you knew it all, didn't you? Oh, it's just a harmless little bunny, isn't it? Well, it's always the same. I always tell them-- ");
        Random random = new Random();
        int index = random.nextInt(timQuotes.size());
        String quote = timQuotes.get(index);
        return quote;
    }

    public String romanNumeral(String level){
        String rn = level;
        switch (level){
            case "1":
                rn = "I";
                break;
            case "2":
                rn = "II";
                break;
            case "3":
                rn = "III";
                break;
            case "4":
                rn = "IV";
                break;
            case "5":
                rn = "V";
                break;
            case "6":
                rn = "VI";
                break;
            case "7":
                rn = "VII";
                break;
            case "8":
                rn = "VIII";
                break;
            case "9":
                rn = "IX";
                break;
            case "10":
                rn = "X";
                break;
        }
        return rn;
    }

    private String buildName(String enchNameLow){
        String enchName = "";
        enchNameLow = enchNameLow.replace("_", " ");
        if (enchNameLow.contains(" ")) {
            List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
            for (int i = 0; i < enchStrList.size(); i++) {
                String unsubbed = enchStrList.get(i);
                String sub = unsubbed.substring(0, 1);
                sub = sub.toUpperCase();
                String sub2 = unsubbed.substring(1);
                String finalSub = sub+sub2;
                enchName += finalSub + " ";
                if (enchName.contains(" Of ")){
                    int index = enchName.indexOf('O');
                    char l = enchName.charAt(index);
                    String letter = Character.toString(l);
                    letter = letter.toLowerCase();
                    l = letter.charAt(0);
                    enchName = enchName.replace('O', l);
                }
            }
        }else{
            String unsubbed = enchNameLow;
            String sub = unsubbed.substring(0, 1);
            sub = sub.toUpperCase();
            String sub2 = unsubbed.substring(1);
            String finalSub = sub+sub2;
            enchName += finalSub + " ";
            if (enchName.contains(" Of ")){
                int index = enchName.indexOf('O');
                char l = enchName.charAt(index);
                String letter = Character.toString(l);
                letter = letter.toLowerCase();
                l = letter.charAt(0);
                enchName = enchName.replace('O', l);
            }

        }
        return enchName;
    }

    public String getEnchantmentName(String name){
        switch (name) {
            case "protection":
                return "PROTECTION_ENVIRONMENTAL";
            case "fire_protection":
                return "PROTECTION_FIRE";
            case "feather_falling":
                return "PROTECTION_FALL";
            case "blast_protection":
                return "PROTECTION_EXPLOSIONS";
            case "projectile_protection":
                return "PROTECTION_PROJECTILE";
            case "respiration":
                return "OXYGEN";
            case "aqua_affinity":
                return "WATER_WORKER";
            case "thorns":
                return "THORNS";
            case "depth_strider":
                return "DEPTH_STRIDER";
            case "frost_walker":
                return "FROST_WALKER";
            case "binding_curse":
                return "BINDING_CURSE";
            case "sharpness":
                return "DAMAGE_ALL";
            case "smite":
                return "DAMAGE_UNDEAD";
            case "bane_of_arthropods":
                return "DAMAGE_ARTHROPODS";
            case "knockback":
                return "KNOCKBACK";
            case "fire_aspect":
                return "FIRE_ASPECT";
            case "looting":
                return "LOOT_BONUS_MOBS";
            case "sweeping_edge":
                return "SWEEPING_EDGE";
            case "efficiency":
                return "DIG_SPEED";
            case "silk_touch":
                return "SILK_TOUCH";
            case "unbeaking":
                return "DURABILITY";
            case "fortune":
                return "LOOT_BONUS_BLOCKS";
            case "power":
                return "ARROW_DAMAGE";
            case "punch":
                return "ARROW_KNOCKBACK";
            case "flame":
                return "ARROW_FIRE";
            case "infinity":
                return "ARROW_INFINITE";
            case "luck":
                return "LUCK";
            case "lure":
                return "LURE";
            case "mending":
                return "MENDING";
            case "vanishing_curse":
                return "VANISHING_CURSE";
        }
        return "not found";
    }

    public String getEnchantmentLabel(String name){
        switch (name) {
            case "PROTECTION_ENVIRONMENTAL":
                return "protection";
            case "PROTECTION_FIRE":
                return "fire_protection";
            case "PROTECTION_FALL":
                return "feather_falling";
            case "PROTECTION_EXPLOSIONS":
                return "blast_protection";
            case "PROTECTION_PROJECTILE":
                return "projectile_protection";
            case "OXYGEN":
                return "respiration";
            case "WATER_WORKER":
                return "aqua_affinity";
            case "THORNS":
                return "thorns";
            case "DEPTH_STRIDER":
                return "depth_strider";
            case "FROST_WALKER":
                return "frost_walker";
            case "BINDING_CURSE":
                return "binding_curse";
            case "DAMAGE_ALL":
                return "sharpness";
            case "DAMAGE_UNDEAD":
                return "smite";
            case "DAMAGE_ARTHROPODS":
                return "bane_of_arthropods";
            case "KNOCKBACK":
                return "knockback";
            case "FIRE_ASPECT":
                return "fire_aspect";
            case "LOOT_BONUS_MOBS":
                return "looting";
            case "SWEEPING_EDGE":
                return "sweeping_edge";
            case "DIG_SPEED":
                return "efficiency";
            case "SILK_TOUCH":
                return "silk_touch";
            case "DURABILITY":
                return "unbeaking";
            case "LOOT_BONUS_BLOCKS":
                return "fortune";
            case "ARROW_DAMAGE":
                return "power";
            case "ARROW_KNOCKBACK":
                return "punch";
            case "ARROW_FIRE":
                return "flame";
            case "ARROW_INFINITE":
                return "infinity";
            case "LUCK":
                return "luck";
            case "LURE":
                return "lure";
            case "MENDING":
                return "mending";
            case "VANISHING_CURSE":
                return "vanishing_curse";
        }
        return "not found";
    }
}