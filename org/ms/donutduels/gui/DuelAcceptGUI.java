/*     */ package org.ms.donutduels.gui;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.model.DuelRequest;
/*     */ import org.ms.donutduels.stats.PlayerStatsManager;
/*     */ import org.ms.donutduels.utils.ColorUtil;
/*     */ 
/*     */ public class DuelAcceptGUI
/*     */   implements Listener {
/*     */   private final DonutDuels plugin;
/*     */   private final Player target;
/*     */   private final DuelRequest request;
/*     */   private final Inventory inventory;
/*  32 */   private static final Map<UUID, Long> clickCooldowns = new HashMap<>();
/*     */   private static final long CLICK_COOLDOWN_MS = 80L;
/*     */   
/*     */   public DuelAcceptGUI(DonutDuels plugin, Player target, DuelRequest request) {
/*  36 */     this.plugin = plugin;
/*  37 */     this.target = target;
/*  38 */     this.request = request;
/*     */ 
/*     */     
/*  41 */     String senderNameSmall = convertToSmallLetters(request.getSenderName());
/*  42 */     this.inventory = Bukkit.createInventory(null, 27, ColorUtil.color(senderNameSmall + " ᴡᴀɴᴛѕ ʏᴏᴜ ᴛᴏ ᴅᴜᴇʟ"));
/*     */ 
/*     */     
/*  45 */     plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
/*     */     
/*  47 */     setupGUI();
/*     */   }
/*     */ 
/*     */   
/*     */   private String convertToSmallLetters(String input) {
/*  52 */     return input.toLowerCase()
/*  53 */       .replace('a', 'ᴀ').replace('b', 'ʙ').replace('c', 'ᴄ').replace('d', 'ᴅ')
/*  54 */       .replace('e', 'ᴇ').replace('f', 'ꜰ').replace('g', 'ɢ').replace('h', 'ʜ')
/*  55 */       .replace('i', 'ɪ').replace('j', 'ᴊ').replace('k', 'ᴋ').replace('l', 'ʟ')
/*  56 */       .replace('m', 'ᴍ').replace('n', 'ɴ').replace('o', 'ᴏ').replace('p', 'ᴘ')
/*  57 */       .replace('q', 'ǫ').replace('r', 'ʀ').replace('s', 'ѕ').replace('t', 'ᴛ')
/*  58 */       .replace('u', 'ᴜ').replace('v', 'ᴠ').replace('w', 'ᴡ').replace('x', 'x')
/*  59 */       .replace('y', 'ʏ').replace('z', 'ᴢ');
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupGUI() {
/*  64 */     ItemStack cancelButton = new ItemStack(Material.RED_STAINED_GLASS_PANE);
/*  65 */     ItemMeta cancelMeta = cancelButton.getItemMeta();
/*  66 */     cancelMeta.setDisplayName(ColorUtil.color("#FF0000ᴄᴀɴᴄᴇʟ"));
/*  67 */     cancelMeta.setLore(Arrays.asList(new String[] { ColorUtil.color("&fClick to decline") }));
/*  68 */     cancelButton.setItemMeta(cancelMeta);
/*  69 */     this.inventory.setItem(10, cancelButton);
/*     */ 
/*     */     
/*  72 */     ItemStack arenaButton = new ItemStack(Material.GRASS_BLOCK);
/*  73 */     ItemMeta arenaMeta = arenaButton.getItemMeta();
/*  74 */     arenaMeta.setDisplayName(ColorUtil.color("#00FF6Cᴀʀᴇɴᴀ"));
/*  75 */     arenaMeta.setLore(Arrays.asList(new String[] { ColorUtil.color("&7" + this.request.getArena()) }));
/*  76 */     arenaButton.setItemMeta(arenaMeta);
/*  77 */     this.inventory.setItem(12, arenaButton);
/*     */ 
/*     */     
/*  80 */     ItemStack timeButton = new ItemStack(Material.CLOCK, Math.min(this.request.getDurationMinutes(), 64));
/*  81 */     ItemMeta timeMeta = timeButton.getItemMeta();
/*  82 */     timeMeta.setDisplayName(ColorUtil.color("#00FF6Cᴛɪᴍᴇ"));
/*  83 */     timeMeta.setLore(Arrays.asList(new String[] { ColorUtil.color("&7(" + this.request.getDurationMinutes() + "m)") }));
/*  84 */     timeButton.setItemMeta(timeMeta);
/*  85 */     this.inventory.setItem(13, timeButton);
/*     */ 
/*     */     
/*  88 */     Player sender = this.plugin.getServer().getPlayer(this.request.getSenderId());
/*  89 */     ItemStack pingButton = new ItemStack(Material.CREEPER_BANNER_PATTERN);
/*  90 */     ItemMeta pingMeta = pingButton.getItemMeta();
/*  91 */     pingMeta.setDisplayName(ColorUtil.color("#00FF6Cᴘɪɴɢ"));
/*  92 */     int ping = (sender != null) ? sender.getPing() : 0;
/*  93 */     pingMeta.setLore(Arrays.asList(new String[] { ColorUtil.color("&7Ping (&b" + ping + "ms&7)") }));
/*  94 */     pingButton.setItemMeta(pingMeta);
/*  95 */     this.inventory.setItem(14, pingButton);
/*     */ 
/*     */     
/*  98 */     ItemStack acceptButton = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
/*  99 */     ItemMeta acceptMeta = acceptButton.getItemMeta();
/* 100 */     acceptMeta.setDisplayName(ColorUtil.color("#1BFF00ᴀᴄᴄᴇᴘᴛ"));
/* 101 */     acceptMeta.setLore(Arrays.asList(new String[] { ColorUtil.color("&fClick to accept duel") }));
/* 102 */     acceptButton.setItemMeta(acceptMeta);
/* 103 */     this.inventory.setItem(16, acceptButton);
/*     */   }
/*     */   
/*     */   private boolean isOnCooldown(Player player) {
/* 107 */     UUID playerId = player.getUniqueId();
/* 108 */     Long lastClick = clickCooldowns.get(playerId);
/*     */     
/* 110 */     if (lastClick == null) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     return (System.currentTimeMillis() - lastClick.longValue() < 80L);
/*     */   }
/*     */   
/*     */   private void setCooldown(Player player) {
/* 118 */     clickCooldowns.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */   
/*     */   public void openGUI() {
/* 122 */     this.target.openInventory(this.inventory);
/*     */   }
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/*     */     Player player;
/* 127 */     if (!event.getInventory().equals(this.inventory)) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     event.setCancelled(true);
/*     */     
/* 133 */     HumanEntity humanEntity = event.getWhoClicked(); if (humanEntity instanceof Player) { player = (Player)humanEntity; }
/*     */     else
/*     */     { return; }
/*     */ 
/*     */     
/* 138 */     if (!player.equals(this.target)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 143 */     if (isOnCooldown(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 148 */     setCooldown(player);
/*     */     
/* 150 */     int slot = event.getSlot();
/*     */     
/* 152 */     if (slot == 10) {
/* 153 */       player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
/* 154 */       player.sendMessage(ColorUtil.color("&cYou declined the duel request from " + this.request.getSenderName()));
/* 155 */       this.plugin.getDuelRequestManager().removeRequest(this.request);
/* 156 */       player.closeInventory();
/* 157 */     } else if (slot == 16) {
/* 158 */       player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
/*     */ 
/*     */       
/* 161 */       if (!this.plugin.getDatabase().hasArenas()) {
/* 162 */         player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
/* 163 */         player.sendMessage(ColorUtil.color("&cNo arenas available."));
/* 164 */         player.sendActionBar(ColorUtil.color("&cNo arenas available."));
/* 165 */         player.closeInventory();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 170 */       Player sender = this.plugin.getServer().getPlayer(this.request.getSenderId());
/* 171 */       if (sender != null && sender.isOnline()) {
/*     */ 
/*     */         
/* 174 */         this.plugin.getDuelManager().startDuel(sender, player, this.request.getArena());
/*     */ 
/*     */         
/* 177 */         startDuelEffects(sender, player);
/* 178 */         teleportPlayersToArena(sender, player, this.request.getArena());
/*     */       } 
/*     */ 
/*     */       
/* 182 */       this.plugin.getDuelRequestManager().removeRequest(this.request);
/* 183 */       player.closeInventory();
/* 184 */     } else if (slot == 14) {
/* 185 */       player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startDuelEffects(Player player1, Player player2) {
/* 194 */     player1.playSound(player1.getLocation(), Sound.AMBIENT_CAVE, 0.5F, 1.0F);
/* 195 */     player2.playSound(player2.getLocation(), Sound.AMBIENT_CAVE, 0.5F, 1.0F);
/*     */ 
/*     */     
/* 198 */     String titleText = ColorUtil.color("#FF0000ᴄᴀѕᴜᴀʟ ᴅᴜᴇʟ");
/* 199 */     String subtitleText = ColorUtil.color("&fFight players & steal their loot");
/*     */     
/* 201 */     player1.sendTitle(titleText, subtitleText, 10, 70, 20);
/* 202 */     player2.sendTitle(titleText, subtitleText, 10, 70, 20);
/*     */ 
/*     */     
/* 205 */     String reportMessage = ColorUtil.color("&7Report cheaters by using &f/report (name) <reason>");
/* 206 */     player1.sendMessage(reportMessage);
/* 207 */     player2.sendMessage(reportMessage);
/*     */ 
/*     */     
/* 210 */     PlayerStatsManager statsManager = this.plugin.getPlayerStatsManager();
/* 211 */     String player1Winrate = statsManager.getFormattedWinrate(player1.getUniqueId());
/* 212 */     String player2Winrate = statsManager.getFormattedWinrate(player2.getUniqueId());
/*     */ 
/*     */     
/* 215 */     String actionBar1 = ColorUtil.color("&7Your opponent &b" + player2.getName() + " &7has a &b" + player2Winrate + " &7winrate. Good luck!");
/* 216 */     player1.sendActionBar(actionBar1);
/*     */ 
/*     */     
/* 219 */     String actionBar2 = ColorUtil.color("&7Your opponent &b" + player1.getName() + " &7has a &b" + player1Winrate + " &7winrate. Good luck!");
/* 220 */     player2.sendActionBar(actionBar2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void teleportPlayersToArena(Player player1, Player player2, String arenaName) {
/* 225 */     Location pos1 = this.plugin.getDatabase().getPosition(arenaName + "_pos1");
/* 226 */     Location pos2 = this.plugin.getDatabase().getPosition(arenaName + "_pos2");
/*     */     
/* 228 */     if (pos1 != null && pos2 != null) {
/*     */       
/* 230 */       player1.teleport(pos1);
/* 231 */       player2.teleport(pos2);
/*     */ 
/*     */       
/* 234 */       player1.playSound(player1.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
/* 235 */       player2.playSound(player2.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
/*     */     } else {
/*     */       
/* 238 */       player1.sendMessage(ColorUtil.color("&cArena positions not properly configured!"));
/* 239 */       player2.sendMessage(ColorUtil.color("&cArena positions not properly configured!"));
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryDrag(InventoryDragEvent event) {
/* 245 */     if (event.getInventory().equals(this.inventory)) {
/* 246 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 252 */     clickCooldowns.remove(this.target.getUniqueId());
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\gui\DuelAcceptGUI.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */