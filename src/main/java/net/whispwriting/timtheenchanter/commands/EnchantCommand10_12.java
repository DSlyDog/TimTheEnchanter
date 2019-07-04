package net.whispwriting.timtheenchanter.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.*;

public class EnchantCommand10_12 implements CommandExecutor {

    private boolean safeEnch = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("TimTheEnchanter.enchant")){
                if (args.length == 0){
                    sender.sendMessage(ChatColor.YELLOW + "/enchant <enchantment:all> <level>");
                    return true;
                }
                ItemStack item =  player.getInventory().getItemInMainHand();
                String quote = getQuote();
                if (item.getType() == Material.AIR){
                    sender.sendMessage(ChatColor.YELLOW+"[TIm] What!?");
                    return true;
                }
                if (args[0].equalsIgnoreCase("all")){
                    if (player.hasPermission("TimTheEnchanter.enchant.all")) {
                        if (args.length == 2) {
                            if (args[1].equals("safe")) {
                                safeEnch = true;
                                enchAll(player, item);
                                safeEnch = false;
                            }else {
                                enchAll(player, item, args[1]);
                            }
                        } else {
                            enchAll(player, item);
                        }
                    }else{
                        player.sendMessage(ChatColor.YELLOW + "[Tim] I am unable to do that for you.");
                    }
                    return true;
                }
                if (args.length == 2){
                    //try {
                    int level = Integer.parseInt(args[1]);
                    if (player.hasPermission("TimTheEnchanter.enchant.unsafe")) {
                        if (level > 1000) {
                            sender.sendMessage(ChatColor.RED + "Enchantment level may not exceed 1000.");
                            return true;
                        }else{
                            String enchantment = getEnchantmentName(args[0]);
                            if (enchantment.equals("not found")){
                                player.sendMessage(ChatColor.YELLOW + "[Tim] Sorry, " + args[0] + " is not in my spellbook.");
                                return true;
                            }
                            item.addUnsafeEnchantment(Enchantment.getByName(enchantment), level);
                            ItemMeta meta = item.getItemMeta();
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            try {
                                List<String> lore = meta.getLore();
                                String enchNameLow = args[0].replace("_", " ");
                                enchNameLow = enchNameLow.toLowerCase();
                                if (enchNameLow.equals("binding curse")){
                                    enchNameLow = "curse of binding";
                                }
                                if (enchNameLow.equals("vanishing curse")){
                                    enchNameLow = "curse of vanishing";
                                }
                                if (enchNameLow.equals("sweeping")){
                                    enchNameLow = "sweeping edge";
                                }
                                String enchName = "";
                                if (enchNameLow.contains(" ")) {
                                    List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                                    for (int i = 0; i < enchStrList.size(); i++) {
                                        String unsubbed = enchStrList.get(i);
                                        String sub = unsubbed.substring(0, 1);
                                        sub = sub.toUpperCase();
                                        String sub2 = unsubbed.substring(1, unsubbed.length());
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
                                    String sub2 = unsubbed.substring(1, unsubbed.length());
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
                                for (int i=0; i<lore.size(); i++){
                                    if (lore.get(i).contains(enchName)){
                                        if (enchName.contains("Curse")){
                                            lore.set(i, ChatColor.RED + enchName);
                                        }else {
                                            lore.set(i, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level+""));
                                        }
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                        player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
                                        return true;
                                    }
                                }
                                if (enchName.contains("Curse")){
                                    lore.add(ChatColor.RED + enchName);
                                }else {
                                    lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level+""));
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }catch (NullPointerException e) {
                                List<String> lore = new ArrayList<String>();
                                String enchNameLow = args[0].replace("_", " ");
                                enchNameLow = enchNameLow.toLowerCase();
                                if (enchNameLow.equals("binding curse")){
                                    enchNameLow = "curse of binding";
                                }
                                if (enchNameLow.equals("vanishing curse")){
                                    enchNameLow = "curse of vanishing";
                                }
                                if (enchNameLow.equals("sweeping")){
                                    enchNameLow = "sweeping edge";
                                }
                                String enchName = "";
                                if (enchNameLow.contains(" ")) {
                                    List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                                    for (int i = 0; i < enchStrList.size(); i++) {
                                        String unsubbed = enchStrList.get(i);
                                        String sub = unsubbed.substring(0, 1);
                                        sub = sub.toUpperCase();
                                        String sub2 = unsubbed.substring(1, unsubbed.length());
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
                                    String sub2 = unsubbed.substring(1, unsubbed.length());
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
                                for (int i=0; i<lore.size(); i++){
                                    if (lore.get(i).contains(enchName)){
                                        if (enchName.contains("Curse")){
                                            lore.set(i, ChatColor.RED + enchName);
                                        }else {
                                            lore.set(i, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level+""));
                                        }
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                        player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
                                        return true;
                                    }
                                }
                                if (enchName.contains("Curse")){
                                    lore.add(ChatColor.RED + enchName);
                                }else {
                                    lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level+""));
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                            player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
                        }
                    }else{
                        String enchantment = getEnchantmentName(args[0]);
                        if (level > Enchantment.getByName(enchantment).getMaxLevel()){
                            sender.sendMessage(ChatColor.YELLOW + "[Tim] That is beyond my abilities.");
                            return true;
                        }
                        item.addEnchantment(Enchantment.getByName(enchantment), level);
                        player.sendMessage(ChatColor.YELLOW + "[Tim] "+quote);
                        return true;
                    }
                    /*}catch(IllegalArgumentException e){
                        sender.sendMessage(ChatColor.YELLOW+"[TIm] What!?");
                    }catch(InputMismatchException f){
                        sender.sendMessage(ChatColor.YELLOW+"[TIm] What!?");
                    }*/
                }else{
                    player.sendMessage(ChatColor.YELLOW + "[Tim] Do they listen to me? NO!");
                }
            }else{
                player.sendMessage(ChatColor.DARK_RED + "You do not have permission for this command.");
                return true;
            }
        }else{
            sender.sendMessage("Only players can execute this command.");
        }
        return true;
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
    private void enchAll(Player player, ItemStack item){
        ArrayList<Enchantment> enchants = new ArrayList<Enchantment>();
        Enchantment[] e = Enchantment.values();
        Enchantment ench;
        String quote = getQuote();
        for (int i=0; i<e.length; i++) {
            ench = (Enchantment) Array.get(e, i);
            enchants.add(ench);
        }
        if (player.hasPermission("TimTheEnchanter.enchant.unsafe") && !safeEnch){
            for (int i=0; i<enchants.size(); i++) {
                item.addUnsafeEnchantment(enchants.get(i), 1000);
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                try {
                    List<String> lore = meta.getLore();
                    String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                    enchNameLow = enchNameLow.replace("minecraft:", "");
                    if (enchNameLow.equals("binding curse")){
                        enchNameLow = "curse of binding";
                    }
                    if (enchNameLow.equals("vanishing curse")){
                        enchNameLow = "curse of vanishing";
                    }
                    if (enchNameLow.equals("sweeping")){
                        enchNameLow = "sweeping edge";
                    }

                    String enchName = "";
                    if (enchNameLow.contains(" ")) {
                        List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                        for (int j = 0; j < enchStrList.size(); j++) {
                            String unsubbed = enchStrList.get(j);
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
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
                    } else {
                        String unsubbed = setEnchantmentName(enchants.get(i).getName());
                        unsubbed = unsubbed.replace("minecraft:", "");
                        String sub = unsubbed.substring(0, 1);
                        sub = sub.toUpperCase();
                        String sub2 = unsubbed.substring(1, unsubbed.length());
                        String finalSub = sub + sub2;
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
                    for (int k = 0; k < lore.size(); k++) {
                        if (lore.get(k).contains(enchName)) {
                            if (enchName.contains("Curse")){
                                lore.set(k, ChatColor.RED + enchName);
                            }else {
                                lore.set(k, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                    if (enchName.contains("Curse")){
                        lore.add(ChatColor.RED + enchName);
                    }else {
                        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } catch (NullPointerException f) {
                    List<String> lore = new ArrayList<String>();
                    String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                    enchNameLow = enchNameLow.replace("minecraft:", "");
                    if (enchNameLow.equals("binding curse")){
                        enchNameLow = "curse of binding";
                    }
                    if (enchNameLow.equals("vanishing curse")){
                        enchNameLow = "curse of vanishing";
                    }
                    if (enchNameLow.equals("sweeping")){
                        enchNameLow = "sweeping edge";
                    }
                    String enchName = "";
                    if (enchNameLow.contains(" ")) {
                        List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                        for (int l = 0; l < enchStrList.size(); l++) {
                            String unsubbed = enchStrList.get(l);
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
                            enchName += finalSub + " ";
                            if (enchName.contains(" Of ")){
                                int index = enchName.indexOf('O');
                                char le = enchName.charAt(index);
                                String letter = Character.toString(le);
                                letter = letter.toLowerCase();
                                le = letter.charAt(0);
                                enchName = enchName.replace('O', le);
                            }
                        }
                    } else {
                        String unsubbed = setEnchantmentName(enchants.get(i).getName());
                        unsubbed = unsubbed.replace("minecraft:", "");
                        String sub = unsubbed.substring(0, 1);
                        sub = sub.toUpperCase();
                        String sub2 = unsubbed.substring(1, unsubbed.length());
                        String finalSub = sub + sub2;
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
                    for (int m = 0; m < lore.size(); m++) {
                        if (lore.get(m).contains(enchName)) {
                            if (enchName.contains("Curse")){
                                lore.set(m, ChatColor.RED + enchName);
                            }else {
                                lore.set(m, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                    if (enchName.contains("Curse")){
                        lore.add(ChatColor.RED + enchName);
                    }else {
                        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
            player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
        }else{
            for (int i=0; i<enchants.size(); i++){
                try {
                    item.addEnchantment(enchants.get(i), enchants.get(i).getMaxLevel());
                    ItemMeta meta = item.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    try {
                        List<String> lore = meta.getLore();
                        String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                        enchNameLow = enchNameLow.replace("minecraft:", "");
                        if (enchNameLow.equals("binding curse")){
                            enchNameLow = "curse of binding";
                        }
                        if (enchNameLow.equals("vanishing curse")){
                            enchNameLow = "curse of vanishing";
                        }
                        if (enchNameLow.equals("sweeping")){
                            enchNameLow = "sweeping edge";
                        }

                        String enchName = "";
                        if (enchNameLow.contains(" ")) {
                            List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                            for (int j = 0; j < enchStrList.size(); j++) {
                                String unsubbed = enchStrList.get(j);
                                String sub = unsubbed.substring(0, 1);
                                sub = sub.toUpperCase();
                                String sub2 = unsubbed.substring(1, unsubbed.length());
                                String finalSub = sub + sub2;
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
                        } else {
                            String unsubbed = setEnchantmentName(enchants.get(i).getName());
                            unsubbed = unsubbed.replace("minecraft:", "");
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
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
                        for (int k = 0; k < lore.size(); k++) {
                            if (lore.get(k).contains(enchName)) {
                                if (enchName.contains("Curse")){
                                    lore.set(k, ChatColor.RED + enchName);
                                }else {
                                    lore.set(k, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                        }
                        if (enchName.contains("Curse")){
                            lore.add(ChatColor.RED + enchName);
                        }else {
                            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    } catch (NullPointerException f) {
                        List<String> lore = meta.getLore();
                        String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                        enchNameLow = enchNameLow.replace("minecraft:", "");
                        if (enchNameLow.equals("binding curse")){
                            enchNameLow = "curse of binding";
                        }
                        if (enchNameLow.equals("vanishing curse")){
                            enchNameLow = "curse of vanishing";
                        }
                        if (enchNameLow.equals("sweeping")){
                            enchNameLow = "sweeping edge";
                        }
                        String enchName = "";
                        if (enchNameLow.contains(" ")) {
                            List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                            for (int l = 0; l < enchStrList.size(); l++) {
                                String unsubbed = enchStrList.get(l);
                                String sub = unsubbed.substring(0, 1);
                                sub = sub.toUpperCase();
                                String sub2 = unsubbed.substring(1, unsubbed.length());
                                String finalSub = sub + sub2;
                                enchName += finalSub + " ";
                                if (enchName.contains(" Of ")){
                                    int index = enchName.indexOf('O');
                                    char le = enchName.charAt(index);
                                    String letter = Character.toString(le);
                                    letter = letter.toLowerCase();
                                    le = letter.charAt(0);
                                    enchName = enchName.replace('O', le);
                                }
                            }
                        } else {
                            String unsubbed = setEnchantmentName(enchants.get(i).getName());
                            unsubbed = unsubbed.replace("minecraft:", "");
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
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
                        for (int m = 0; m < lore.size(); m++) {
                            if (lore.get(m).contains(enchName)) {
                                if (enchName.contains("Curse")){
                                    lore.set(m, ChatColor.RED + enchName);
                                }else {
                                    lore.set(m, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                        }
                        if (enchName.contains("Curse")){
                            lore.add(ChatColor.RED + enchName);
                        }else {
                            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + 1000);
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    }
                }catch (Exception f){
                    //
                }
            }
            player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
        }
    }

    private void enchAll(Player player, ItemStack item, String level) {
        ArrayList<Enchantment> enchants = new ArrayList<Enchantment>();
        Enchantment[] e = Enchantment.values();
        Enchantment ench;
        String quote = getQuote();
        for (int i = 0; i < e.length; i++) {
            ench = (Enchantment) Array.get(e, i);
            enchants.add(ench);
        }
        if (player.hasPermission("TimTheEnchanter.enchant.unsafe")) {
            int levelInt;
            try{
                levelInt = Integer.parseInt(level);
            }catch(InputMismatchException ime){
                player.sendMessage(ChatColor.YELLOW+"[TIm] What!?");
                return;
            }
            for (int i = 0; i < enchants.size(); i++) {
                item.addUnsafeEnchantment(enchants.get(i), levelInt);
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                try {
                    List<String> lore = meta.getLore();
                    String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                    enchNameLow = enchNameLow.replace("minecraft:", "");
                    if (enchNameLow.equals("binding curse")) {
                        enchNameLow = "curse of binding";
                    }
                    if (enchNameLow.equals("vanishing curse")) {
                        enchNameLow = "curse of vanishing";
                    }
                    if (enchNameLow.equals("sweeping")) {
                        enchNameLow = "sweeping edge";
                    }
                    String enchName = "";
                    if (enchNameLow.contains(" ")) {
                        List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                        for (int j = 0; j < enchStrList.size(); j++) {
                            String unsubbed = enchStrList.get(j);
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
                            enchName += finalSub + " ";
                            if (enchName.contains(" Of ")) {
                                int index = enchName.indexOf('O');
                                char l = enchName.charAt(index);
                                String letter = Character.toString(l);
                                letter = letter.toLowerCase();
                                l = letter.charAt(0);
                                enchName = enchName.replace('O', l);
                            }
                        }
                    } else {
                        String unsubbed = setEnchantmentName(enchants.get(i).getName());
                        unsubbed = unsubbed.replace("minecraft:", "");
                        String sub = unsubbed.substring(0, 1);
                        sub = sub.toUpperCase();
                        String sub2 = unsubbed.substring(1, unsubbed.length());
                        String finalSub = sub + sub2;
                        enchName += finalSub + " ";
                        if (enchName.contains(" Of ")) {
                            int index = enchName.indexOf('O');
                            char l = enchName.charAt(index);
                            String letter = Character.toString(l);
                            letter = letter.toLowerCase();
                            l = letter.charAt(0);
                            enchName = enchName.replace('O', l);
                        }
                    }
                    for (int k = 0; k < lore.size(); k++) {
                        if (lore.get(k).contains(enchName)) {
                            if (enchName.contains("Curse")) {
                                lore.set(k, ChatColor.RED + enchName);
                            } else {
                                lore.set(k, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                    if (enchName.contains("Curse")) {
                        lore.add(ChatColor.RED + enchName);
                    } else {
                        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } catch (NullPointerException f) {
                    List<String> lore = meta.getLore();
                    String enchNameLow = enchants.get(i).getName();
                    enchNameLow = setEnchantmentName(enchNameLow);
                    enchNameLow = enchNameLow.replace("minecraft:", "");
                    if (enchNameLow.equals("binding curse")) {
                        enchNameLow = "curse of binding";
                    }
                    if (enchNameLow.equals("vanishing curse")) {
                        enchNameLow = "curse of vanishing";
                    }
                    if (enchNameLow.equals("sweeping")) {
                        enchNameLow = "sweeping edge";
                    }
                    String enchName = "";
                    if (enchNameLow.contains(" ")) {
                        List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                        for (int l = 0; l < enchStrList.size(); l++) {
                            String unsubbed = enchStrList.get(l);
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
                            enchName += finalSub + " ";
                            if (enchName.contains(" Of ")) {
                                int index = enchName.indexOf('O');
                                char le = enchName.charAt(index);
                                String letter = Character.toString(le);
                                letter = letter.toLowerCase();
                                le = letter.charAt(0);
                                enchName = enchName.replace('O', le);
                            }
                        }
                    } else {
                        String unsubbed = setEnchantmentName(enchants.get(i).toString());
                        unsubbed = unsubbed.replace("minecraft:", "");
                        String sub = unsubbed.substring(0, 1);
                        sub = sub.toUpperCase();
                        String sub2 = unsubbed.substring(1, unsubbed.length());
                        String finalSub = sub + sub2;
                        enchName += finalSub + " ";
                        if (enchName.contains(" Of ")) {
                            int index = enchName.indexOf('O');
                            char l = enchName.charAt(index);
                            String letter = Character.toString(l);
                            letter = letter.toLowerCase();
                            l = letter.charAt(0);
                            enchName = enchName.replace('O', l);
                        }
                    }
                    for (int m = 0; m < lore.size(); m++) {
                        if (lore.get(m).contains(enchName)) {
                            if (enchName.contains("Curse")) {
                                lore.set(m, ChatColor.RED + enchName);
                            } else {
                                lore.set(m, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                    if (enchName.contains("Curse")) {
                        lore.add(ChatColor.RED + enchName);
                    } else {
                        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
            }
            player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
        } else {
            for (int i = 0; i < enchants.size(); i++) {
                try {
                    int levelInt = Integer.parseInt(level);
                    Enchantment enchs = enchants.get(i);
                    if (levelInt > enchs.getMaxLevel()){
                        levelInt = enchs.getMaxLevel();
                    }
                    item.addEnchantment(enchants.get(i), levelInt);
                    ItemMeta meta = item.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    try {
                        List<String> lore = meta.getLore();
                        String enchNameLow = setEnchantmentName(enchants.get(i).getName());
                        enchNameLow = enchNameLow.replace("minecraft:", "");
                        if (enchNameLow.equals("binding curse")) {
                            enchNameLow = "curse of binding";
                        }
                        if (enchNameLow.equals("vanishing curse")) {
                            enchNameLow = "curse of vanishing";
                        }
                        if (enchNameLow.equals("sweeping")) {
                            enchNameLow = "sweeping edge";
                        }
                        String enchName = "";
                        if (enchNameLow.contains(" ")) {
                            List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                            for (int j = 0; j < enchStrList.size(); j++) {
                                String unsubbed = enchStrList.get(j);
                                String sub = unsubbed.substring(0, 1);
                                sub = sub.toUpperCase();
                                String sub2 = unsubbed.substring(1, unsubbed.length());
                                String finalSub = sub + sub2;
                                enchName += finalSub + " ";
                                if (enchName.contains(" Of ")) {
                                    int index = enchName.indexOf('O');
                                    char l = enchName.charAt(index);
                                    String letter = Character.toString(l);
                                    letter = letter.toLowerCase();
                                    l = letter.charAt(0);
                                    enchName = enchName.replace('O', l);
                                }
                            }
                        } else {
                            String unsubbed = setEnchantmentName(enchants.get(i).getName());
                            unsubbed = unsubbed.replace("minecraft:", "");
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
                            enchName += finalSub + " ";
                            if (enchName.contains(" Of ")) {
                                int index = enchName.indexOf('O');
                                char l = enchName.charAt(index);
                                String letter = Character.toString(l);
                                letter = letter.toLowerCase();
                                l = letter.charAt(0);
                                enchName = enchName.replace('O', l);
                            }
                        }
                        for (int k = 0; k < lore.size(); k++) {
                            if (lore.get(k).contains(enchName)) {
                                if (enchName.contains("Curse")) {
                                    lore.set(k, ChatColor.RED + enchName);
                                } else {
                                    lore.set(k, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                        }
                        if (enchName.contains("Curse")) {
                            lore.add(ChatColor.RED + enchName);
                        } else {
                            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    } catch (NullPointerException f) {
                        List<String> lore = meta.getLore();
                        String enchNameLow = enchants.get(i).getName();
                        enchNameLow = setEnchantmentName(enchNameLow);
                        enchNameLow = enchNameLow.replace("minecraft:", "");
                        if (enchNameLow.equals("binding curse")) {
                            enchNameLow = "curse of binding";
                        }
                        if (enchNameLow.equals("vanishing curse")) {
                            enchNameLow = "curse of vanishing";
                        }
                        if (enchNameLow.equals("sweeping")) {
                            enchNameLow = "sweeping edge";
                        }
                        String enchName = "";
                        if (enchNameLow.contains(" ")) {
                            List<String> enchStrList = Arrays.asList(enchNameLow.split(" "));
                            for (int l = 0; l < enchStrList.size(); l++) {
                                String unsubbed = enchStrList.get(l);
                                String sub = unsubbed.substring(0, 1);
                                sub = sub.toUpperCase();
                                String sub2 = unsubbed.substring(1, unsubbed.length());
                                String finalSub = sub + sub2;
                                enchName += finalSub + " ";
                                if (enchName.contains(" Of ")) {
                                    int index = enchName.indexOf('O');
                                    char le = enchName.charAt(index);
                                    String letter = Character.toString(le);
                                    letter = letter.toLowerCase();
                                    le = letter.charAt(0);
                                    enchName = enchName.replace('O', le);
                                }
                            }
                        } else {
                            String unsubbed = setEnchantmentName(enchants.get(i).toString());
                            unsubbed = unsubbed.replace("minecraft:", "");
                            String sub = unsubbed.substring(0, 1);
                            sub = sub.toUpperCase();
                            String sub2 = unsubbed.substring(1, unsubbed.length());
                            String finalSub = sub + sub2;
                            enchName += finalSub + " ";
                            if (enchName.contains(" Of ")) {
                                int index = enchName.indexOf('O');
                                char l = enchName.charAt(index);
                                String letter = Character.toString(l);
                                letter = letter.toLowerCase();
                                l = letter.charAt(0);
                                enchName = enchName.replace('O', l);
                            }
                        }
                        for (int m = 0; m < lore.size(); m++) {
                            if (lore.get(m).contains(enchName)) {
                                if (enchName.contains("Curse")) {
                                    lore.set(m, ChatColor.RED + enchName);
                                } else {
                                    lore.set(m, ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                        }
                        if (enchName.contains("Curse")) {
                            lore.add(ChatColor.RED + enchName);
                        } else {
                            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + enchName + romanNumeral(level));
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    }
                    player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
                }catch(InputMismatchException f) {
                    player.sendMessage(ChatColor.YELLOW+"[TIm] What!?");
                }catch(IllegalArgumentException g){
                    //
                }
            }
            player.sendMessage(ChatColor.YELLOW + "[Tim] " + quote);
        }
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

    public String getEnchantmentName(String s){
        String name = s;
        switch (s) {
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

    public String setEnchantmentName(String s){
        String name = s;
        switch (s) {
            case "PROTECTION_ENVIRONMENTAL":
                name = "protection";
                break;
            case "PROTECTION_FIRE":
                name = "fire protection";
                break;
            case "PROTECTION_FALL":
                name = "feather falling";
                break;
            case "PROTECTION_EXPLOSIONS":
                name = "blast protection";
                break;
            case "PROTECTION_PROJECTILE":
                name = "projectile protection";
                break;
            case "OXYGEN":
                name = "respiration";
                break;
            case "WATER_WORKER":
                name = "aqua affinity";
                break;
            case "THORNS":
                name = "thorns";
                break;
            case "DEPTH_STRIDER":
                name = "depth strider";
                break;
            case "FROST_WALKER":
                name = "frost walker";
                break;
            case "BINDING_CURSE":
                name = "binding curse";
                break;
            case "DAMAGE_ALL":
                name = "sharpness";
                break;
            case "DAMAGE_UNDEAD":
                name = "smite";
                break;
            case "DAMAGE_ARTHROPODS":
                name = "bane of anrthropods";
                break;
            case "KNOCKBACK":
                name = "knockback";
                break;
            case "FIRE_ASPECT":
                name = "fire aspect";
                break;
            case "LOOT_BONUS_MOBS":
                name = "looting";
                break;
            case "SWEEPING_EDGE":
                name = "sweeping edge";
                break;
            case "DIG_SPEED":
                name = "efficiency";
                break;
            case "SILK_TOUCH":
                name = "silk touch";
                break;
            case "DURABILITY":
                name = "unbreaking";
                break;
            case "LOOT_BONUS_BLOCKS":
                name = "fortune";
                break;
            case "ARROW_DAMAGE":
                name = "power";
                break;
            case "ARROW_KNOCKBACK":
                name = "punch";
                break;
            case "ARROW_FIRE":
                name = "flame";
                break;
            case "ARROW_INFINITE":
                name = "infinity";
                break;
            case "LUCK":
                name = "luck";
                break;
            case "LURE":
                name = "lure";
                break;
            case "MENDING":
                name = "mending";
                break;
            case "VANISHING_CURSE":
                name = "vanishing curse";
                break;
        }
        return name;
    }

}


