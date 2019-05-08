1.14 NOTICE​
I have started testing on 1.14, and it appears to work mostly correctly. BUT if you are enchanting a crossbow in any way, do not set the level of Quick Charge beyond 5 or the crossbow WILL NOT CHARGE! I will be writing in code to fix this issue for the 1.14 update.
Tim The Enchanter is BACK!
​
Hello all! My name is Whisp, or Zippitey2. What you are looking at here is my very first published plugin. I'm sure many of you know of the enchantment plugin TimTheEnchanter. It was one of my favorites. But it's been out of development for some time, so I took it upon myself to recreate the plugin for 1.13 and on.

Using The Plugin​
I'm sure many of you will agree that knowing how to use a plugin is important, so that's what this section is for. It's a pretty simple plugin though. For all enchanting commands, the item must be in your main hand to enchant it.

Basic Enchanting:
To add a single enchantment type /enchant along with the enchantment name and level, ie: /enchantment sharpness 5.

/enchantment <name> <level>

With the unsafe enchantment permission (seen further down) users can set the enchantment to a maximum of 1000, and they can apply an enchantment to the item that normally the enchantment can't be applied to..

Enchant All:
What this command does depends on a user's permissions. To use the command, simply type /enchant all. If the user only has the base enchant permission it will apply every enchant that can be applied to an item at its max level. So for example, using this on a sword with only the general permission will enchant a sword with only enchants a sword can normally use. This means you won't be seeing Infinity on it.

If the user has the unsafe enchant permission, running this command will apply every single existing enchantment at a level of 1000 to the item. (So yes, you'll have Water Walking on your sword.)

/enchant all

It is now possible to choose a level. However, you can still just type the above command to enchant all at max level.

/enchant all <level>



Permissions​
Permissions are definitely important for using the plugin. You can't use it if your access is denied.

TimTheEnchanter.enchant
This is the general enchant permission. Give this to a user and they will be limited to only enchantments that can be applied to an item and only at that enchantment's maximum level.

TimTheEnchanter.enchant.unsafe
This will allow users to add enchantments to any item at any level. (Yes, that means you can put sharpness 1000 on a feather). However, the user must also have the general enchant permission (TimTheEnchanter.enchant) for this to be of any use.

TimTheEnchanter.enchant.all
This will allow users to use /enchant all. Users that do not have this, but have the unsafe enchanting permission will still be able to add single enchantments beyond the maximum level.

TimTheEnchanter.*
This is the wildcard permission. Add this to a user or group and they will have access to every command in the plugin.


Bug Reporting, Feedback, and Suggestions​
If there is a bug, please let me know. The sooner I know, the sooner I'll fix it. If you want to suggest an addition to the plugin, I'd also like to know. More ideas means more features means more fun. Or perhaps you'd like to leave some feedback--something constructive and genuine. Something like this, perhaps: "Bruh, why? Ur plugin's bad. Go fix the stuffs." Then I'll know what you guys think of my work and how much I'm wasting my life at this keyboard.
So, I suppose you want a way to actually give me such information. I am working on a website to house my plugin (and eventually mod projects) and a support forums, but it's not finished yet. So for now, reach me via email, Discord, or Google Hangouts. My handles are as follows:
email: whisp@whispwriting.net
Discord: WhispTheFox#5879
Google Hangouts: zippitey2@gmail.com (If you email me here I will never see it as I no longer use this email for email communications...too much spam. I will see it if you send me a message on Hangouts though.






To Dos
(well, hopefully anyway.)​
- Add the ability to apply only the enchantments available to the item in hand item for users with the unsafe enchantment (not started)
- Add the ability to enchant all without curse enchantments (not started)
- Update to support 1.14 (Started testing on Spigot 1.14 beta builds)
- Have enchantments display more nicely, "Sharpness 1000" instead of "Sharpness enchantment.level.1000" (Done)
- Add in a check for plugin updates (not started)
- Make it possible to choose a level when using /enchant all (Done)
- Make an xp requirement system for enchanting (not started) 
