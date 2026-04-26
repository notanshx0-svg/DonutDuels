/*     */ package org.ms.donutduels.gui;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Bukkit;
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
/*     */ import org.ms.donutduels.config.QueueGUIConfig;
/*     */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*     */ import org.ms.donutduels.utils.ColorUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueueGUI
/*     */   implements Listener
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private final Player player;
/*     */   private final Inventory inventory;
/*     */   private final QueueGUIConfig config;
/*  33 */   private static final Map<UUID, Long> clickCooldowns = new HashMap<>();
/*     */ 
/*     */   
/*  36 */   private static final Map<UUID, TaskImplementation<Void>> activeSearches = new HashMap<>();
/*     */ 
/*     */   
/*     */   public QueueGUI(DonutDuels plugin, Player player) {
/*  40 */     this.plugin = plugin;
/*  41 */     this.player = player;
/*  42 */     this.config = plugin.getQueueGUIConfig();
/*  43 */     this.inventory = Bukkit.createInventory(null, this.config.getGUISize(), ColorUtil.color(this.config.getGUITitle()));
/*     */     
/*  45 */     setupGUI();
/*  46 */     plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupGUI() {
/*  53 */     ItemStack cancelButton = new ItemStack(this.config.getCancelButtonMaterial());
/*  54 */     ItemMeta cancelMeta = cancelButton.getItemMeta();
/*  55 */     if (cancelMeta != null) {
/*  56 */       cancelMeta.setDisplayName(String.valueOf(ChatColor.of(this.config.getCancelButtonName().startsWith("#") ? 
/*  57 */               this.config.getCancelButtonName().substring(0, 7) : "#FF0000")) + String.valueOf(ChatColor.of(this.config.getCancelButtonName().startsWith("#") ? this.config.getCancelButtonName().substring(0, 7) : "#FF0000")));
/*     */       
/*  59 */       cancelMeta.setLore(this.config.getCancelButtonLore().stream()
/*  60 */           .map(ColorUtil::color)
/*  61 */           .toList());
/*  62 */       cancelButton.setItemMeta(cancelMeta);
/*     */     } 
/*  64 */     this.inventory.setItem(this.config.getCancelButtonSlot(), cancelButton);
/*     */ 
/*     */     
/*  67 */     updateWaitTimeButton();
/*     */ 
/*     */     
/*  70 */     updateStatisticsButton();
/*     */ 
/*     */     
/*  73 */     updateRegionButton();
/*     */ 
/*     */     
/*  76 */     ItemStack searchButton = new ItemStack(this.config.getSearchButtonMaterial());
/*  77 */     ItemMeta searchMeta = searchButton.getItemMeta();
/*  78 */     if (searchMeta != null) {
/*  79 */       searchMeta.setDisplayName(String.valueOf(ChatColor.of(this.config.getSearchButtonName().startsWith("#") ? 
/*  80 */               this.config.getSearchButtonName().substring(0, 7) : "#1BFF00")) + String.valueOf(ChatColor.of(this.config.getSearchButtonName().startsWith("#") ? this.config.getSearchButtonName().substring(0, 7) : "#1BFF00")));
/*     */       
/*  82 */       searchMeta.setLore(this.config.getSearchButtonLore().stream()
/*  83 */           .map(ColorUtil::color)
/*  84 */           .toList());
/*  85 */       searchButton.setItemMeta(searchMeta);
/*     */     } 
/*  87 */     this.inventory.setItem(this.config.getSearchButtonSlot(), searchButton);
/*     */   }
/*     */   
/*     */   private void updateWaitTimeButton() {
/*  91 */     ItemStack compass = new ItemStack(this.config.getWaitTimeButtonMaterial());
/*  92 */     ItemMeta compassMeta = compass.getItemMeta();
/*  93 */     if (compassMeta != null) {
/*  94 */       compassMeta.setDisplayName(String.valueOf(ChatColor.of(this.config.getWaitTimeButtonName().startsWith("#") ? 
/*  95 */               this.config.getWaitTimeButtonName().substring(0, 7) : "#1DFF98")) + String.valueOf(ChatColor.of(this.config.getWaitTimeButtonName().startsWith("#") ? this.config.getWaitTimeButtonName().substring(0, 7) : "#1DFF98")));
/*     */ 
/*     */       
/*  98 */       String waitTime = this.plugin.getQueueManager().getEstimatedWaitTime();
/*  99 */       int queueCount = this.plugin.getQueueManager().getQueueSize();
/*     */       
/* 101 */       compassMeta.setLore(Arrays.asList(new String[] {
/* 102 */               ColorUtil.color(this.config.getWaitTimeEstimatedFormat().replace("{time}", waitTime)), 
/* 103 */               ColorUtil.color(this.config.getWaitTimeQueuedFormat().replace("{count}", String.valueOf(queueCount)))
/*     */             }));
/* 105 */       compass.setItemMeta(compassMeta);
/*     */     } 
/* 107 */     this.inventory.setItem(this.config.getWaitTimeButtonSlot(), compass);
/*     */   }
/*     */   
/*     */   private void updateStatisticsButton() {
/* 111 */     ItemStack statsButton = new ItemStack(this.config.getStatisticsButtonMaterial());
/* 112 */     ItemMeta statsMeta = statsButton.getItemMeta();
/* 113 */     if (statsMeta != null) {
/* 114 */       statsMeta.setDisplayName(String.valueOf(ChatColor.of(this.config.getStatisticsButtonName().startsWith("#") ? 
/* 115 */               this.config.getStatisticsButtonName().substring(0, 7) : "#1DFF98")) + String.valueOf(ChatColor.of(this.config.getStatisticsButtonName().startsWith("#") ? this.config.getStatisticsButtonName().substring(0, 7) : "#1DFF98")));
/*     */ 
/*     */       
/* 118 */       UUID playerId = this.player.getUniqueId();
/* 119 */       int wins = this.plugin.getDatabase().getWins(playerId);
/* 120 */       int losses = this.plugin.getDatabase().getLosses(playerId);
/* 121 */       int streak = 0;
/*     */       
/* 123 */       statsMeta.setLore(Arrays.asList(new String[] {
/* 124 */               ColorUtil.color(this.config.getStatisticsWinsFormat().replace("{wins}", String.valueOf(wins))), 
/* 125 */               ColorUtil.color(this.config.getStatisticsLossesFormat().replace("{losses}", String.valueOf(losses))), 
/* 126 */               ColorUtil.color(this.config.getStatisticsStreakFormat().replace("{streak}", String.valueOf(streak)))
/*     */             }));
/* 128 */       statsButton.setItemMeta(statsMeta);
/*     */     } 
/* 130 */     this.inventory.setItem(this.config.getStatisticsButtonSlot(), statsButton);
/*     */   }
/*     */   
/*     */   private void updateRegionButton() {
/* 134 */     ItemStack regionButton = new ItemStack(this.config.getRegionButtonMaterial());
/* 135 */     ItemMeta regionMeta = regionButton.getItemMeta();
/* 136 */     if (regionMeta != null) {
/* 137 */       regionMeta.setDisplayName(String.valueOf(ChatColor.of(this.config.getRegionButtonName().startsWith("#") ? 
/* 138 */               this.config.getRegionButtonName().substring(0, 7) : "#1DFF98")) + String.valueOf(ChatColor.of(this.config.getRegionButtonName().startsWith("#") ? this.config.getRegionButtonName().substring(0, 7) : "#1DFF98")));
/*     */ 
/*     */ 
/*     */       
/* 142 */       int ping = this.player.getPing();
/*     */       
/* 144 */       regionMeta.setLore(Arrays.asList(new String[] {
/* 145 */               ColorUtil.color(this.config.getRegionPingFormat().replace("{ping}", String.valueOf(ping)))
/*     */             }));
/* 147 */       regionButton.setItemMeta(regionMeta);
/*     */     } 
/* 149 */     this.inventory.setItem(this.config.getRegionButtonSlot(), regionButton);
/*     */   }
/*     */   
/*     */   public void openGUI() {
/* 153 */     this.player.openInventory(this.inventory);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/* 158 */     if (!event.getInventory().equals(this.inventory)) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     event.setCancelled(true);
/*     */     
/* 164 */     if (!(event.getWhoClicked() instanceof Player)) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     Player clickedPlayer = (Player)event.getWhoClicked();
/*     */ 
/*     */     
/* 171 */     UUID playerId = clickedPlayer.getUniqueId();
/* 172 */     long currentTime = System.currentTimeMillis();
/* 173 */     Long lastClick = clickCooldowns.get(playerId);
/*     */     
/* 175 */     if (lastClick != null && currentTime - lastClick.longValue() < this.config.getClickCooldown()) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     clickCooldowns.put(playerId, Long.valueOf(currentTime));
/*     */     
/* 181 */     int slot = event.getSlot();
/*     */ 
/*     */     
/* 184 */     if (slot == this.config.getCancelButtonSlot()) {
/* 185 */       clickedPlayer.playSound(clickedPlayer.getLocation(), this.config.getButtonClickSound(), this.config
/* 186 */           .getSoundVolume(), this.config.getSoundPitch());
/*     */ 
/*     */       
/* 189 */       TaskImplementation<Void> activeSearch = activeSearches.remove(playerId);
/* 190 */       if (activeSearch != null) {
/* 191 */         activeSearch.cancel();
/* 192 */         clickedPlayer.sendActionBar("");
/*     */       } 
/*     */ 
/*     */       
/* 196 */       clickedPlayer.stopSound(this.config.getSearchSound());
/*     */       
/* 198 */       clickedPlayer.closeInventory();
/* 199 */     } else if (slot == this.config.getWaitTimeButtonSlot()) {
/* 200 */       clickedPlayer.playSound(clickedPlayer.getLocation(), this.config.getButtonClickSound(), this.config
/* 201 */           .getSoundVolume(), this.config.getSoundPitch());
/*     */ 
/*     */       
/* 204 */       updateWaitTimeButton();
/* 205 */     } else if (slot == this.config.getStatisticsButtonSlot()) {
/* 206 */       clickedPlayer.playSound(clickedPlayer.getLocation(), this.config.getButtonClickSound(), this.config
/* 207 */           .getSoundVolume(), this.config.getSoundPitch());
/*     */ 
/*     */       
/* 210 */       updateStatisticsButton();
/* 211 */     } else if (slot == this.config.getRegionButtonSlot()) {
/* 212 */       clickedPlayer.playSound(clickedPlayer.getLocation(), this.config.getButtonClickSound(), this.config
/* 213 */           .getSoundVolume(), this.config.getSoundPitch());
/*     */ 
/*     */       
/* 216 */       updateRegionButton();
/* 217 */     } else if (slot == this.config.getSearchButtonSlot()) {
/* 218 */       clickedPlayer.playSound(clickedPlayer.getLocation(), this.config.getSearchSound(), this.config
/* 219 */           .getSoundVolume(), this.config.getSoundPitch());
/*     */ 
/*     */       
/* 222 */       clickedPlayer.closeInventory();
/*     */ 
/*     */       
/* 225 */       startQueueSearch(clickedPlayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startQueueSearch(Player player) {
/* 230 */     UUID playerId = player.getUniqueId();
/*     */ 
/*     */     
/* 233 */     if (this.plugin.getQueueManager().isPlayerInQueue(player)) {
/* 234 */       this.plugin.getQueueManager().removePlayerFromQueue(player);
/*     */ 
/*     */       
/* 237 */       TaskImplementation<Void> taskImplementation = activeSearches.remove(playerId);
/* 238 */       if (taskImplementation != null) {
/* 239 */         taskImplementation.cancel();
/*     */       }
/*     */ 
/*     */       
/* 243 */       player.stopSound(this.config.getSearchSound());
/*     */ 
/*     */       
/* 246 */       String leaveMessage = ColorUtil.color(this.plugin.getConfigManager().getQueueLeftQueueChatMessage());
/* 247 */       player.sendMessage(leaveMessage);
/* 248 */       player.sendActionBar(ColorUtil.color(this.plugin.getConfigManager().getQueueLeftQueueActionBarMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 253 */     TaskImplementation<Void> existingSearch = activeSearches.get(playerId);
/* 254 */     if (existingSearch != null) {
/* 255 */       existingSearch.cancel();
/*     */     }
/*     */ 
/*     */     
/* 259 */     String waitTime = this.plugin.getQueueManager().getEstimatedWaitTime();
/*     */ 
/*     */     
/* 262 */     TaskImplementation<Void> searchTask = this.plugin.getSchedulerService().runGlobalRepeating(task -> { if (!player.isOnline()) { task.cancel(); activeSearches.remove(playerId); return; }  String actionBarMessage = ColorUtil.color(this.config.getSearchingActionBarFormat().replace("{time}", waitTime)); player.sendActionBar(actionBarMessage); }1L, this.config
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 272 */         .getActionBarUpdateInterval());
/*     */     
/* 274 */     activeSearches.put(playerId, searchTask);
/*     */ 
/*     */     
/* 277 */     this.plugin.getQueueManager().addPlayerToQueue(player);
/* 278 */     String joinMessage = ColorUtil.color(this.config.getJoinedQueueMessage());
/* 279 */     player.sendMessage(joinMessage);
/* 280 */     player.sendActionBar(joinMessage);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryDrag(InventoryDragEvent event) {
/* 285 */     if (event.getInventory().equals(this.inventory)) {
/* 286 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 291 */     UUID playerId = this.player.getUniqueId();
/* 292 */     clickCooldowns.remove(playerId);
/*     */ 
/*     */     
/* 295 */     TaskImplementation<Void> activeSearch = activeSearches.remove(playerId);
/* 296 */     if (activeSearch != null) {
/* 297 */       activeSearch.cancel();
/*     */     }
/*     */ 
/*     */     
/* 301 */     this.player.stopSound(this.config.getSearchSound());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopSearchForPlayer(UUID playerId) {
/* 307 */     TaskImplementation<Void> activeSearch = activeSearches.remove(playerId);
/* 308 */     if (activeSearch != null)
/* 309 */       activeSearch.cancel(); 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\gui\QueueGUI.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */