/*     */ package org.ms.donutduels.gui;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.config.DuelGUIConfig;
/*     */ import org.ms.donutduels.utils.ColorUtil;
/*     */ 
/*     */ public class DuelGUI implements Listener {
/*     */   private final DonutDuels plugin;
/*     */   private final DuelGUIConfig config;
/*     */   private final Player sender;
/*     */   private final Player target;
/*     */   private final Inventory inventory;
/*     */   private int duelTimeMinutes;
/*     */   private List<String> availableArenas;
/*  34 */   private int selectedArenaIndex = 0;
/*     */ 
/*     */   
/*  37 */   private static final Map<UUID, Long> clickCooldowns = new HashMap<>();
/*     */   
/*     */   public DuelGUI(DonutDuels plugin, Player sender, Player target) {
/*  40 */     this.plugin = plugin;
/*  41 */     this.config = plugin.getDuelGUIConfig();
/*  42 */     this.sender = sender;
/*  43 */     this.target = target;
/*  44 */     this.duelTimeMinutes = this.config.getDefaultTimeMinutes();
/*     */     
/*  46 */     String title = this.config.getGUITitle().replace("{target}", target.getName());
/*  47 */     this.inventory = Bukkit.createInventory(null, this.config.getGUISize(), ColorUtil.color(title));
/*  48 */     this.availableArenas = plugin.getDatabase().getAllArenaNames();
/*     */ 
/*     */     
/*  51 */     plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
/*     */     
/*  53 */     setupGUI();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupGUI() {
/*  58 */     ItemStack cancelButton = new ItemStack(this.config.getCancelButtonMaterial());
/*  59 */     ItemMeta cancelMeta = cancelButton.getItemMeta();
/*  60 */     if (cancelMeta != null) {
/*  61 */       cancelMeta.setDisplayName(ColorUtil.color(this.config.getCancelButtonName()));
/*  62 */       cancelMeta.setLore((List)this.config.getCancelButtonLore().stream()
/*  63 */           .map(ColorUtil::color)
/*  64 */           .collect(Collectors.toList()));
/*  65 */       cancelButton.setItemMeta(cancelMeta);
/*     */     } 
/*  67 */     this.inventory.setItem(this.config.getCancelButtonSlot(), cancelButton);
/*     */ 
/*     */     
/*  70 */     updateArenaButton();
/*     */ 
/*     */     
/*  73 */     updateTimeButton();
/*     */ 
/*     */     
/*  76 */     updatePingButton();
/*     */ 
/*     */     
/*  79 */     ItemStack confirmButton = new ItemStack(this.config.getConfirmButtonMaterial());
/*  80 */     ItemMeta confirmMeta = confirmButton.getItemMeta();
/*  81 */     if (confirmMeta != null) {
/*  82 */       confirmMeta.setDisplayName(ColorUtil.color(this.config.getConfirmButtonName()));
/*  83 */       confirmMeta.setLore((List)this.config.getConfirmButtonLore().stream()
/*  84 */           .map(ColorUtil::color)
/*  85 */           .collect(Collectors.toList()));
/*  86 */       confirmButton.setItemMeta(confirmMeta);
/*     */     } 
/*  88 */     this.inventory.setItem(this.config.getConfirmButtonSlot(), confirmButton);
/*     */   }
/*     */   
/*     */   private void updateArenaButton() {
/*  92 */     if (this.availableArenas.isEmpty()) {
/*     */       
/*  94 */       ItemStack noArenaButton = new ItemStack(this.config.getNoArenaMaterial());
/*  95 */       ItemMeta noArenaMeta = noArenaButton.getItemMeta();
/*  96 */       if (noArenaMeta != null) {
/*  97 */         noArenaMeta.setDisplayName(ColorUtil.color(this.config.getArenaButtonName()));
/*  98 */         noArenaMeta.setLore((List)this.config.getNoArenaLore().stream()
/*  99 */             .map(ColorUtil::color)
/* 100 */             .collect(Collectors.toList()));
/* 101 */         noArenaButton.setItemMeta(noArenaMeta);
/*     */       } 
/* 103 */       this.inventory.setItem(this.config.getArenaButtonSlot(), noArenaButton);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 108 */     String currentArena = this.availableArenas.get(this.selectedArenaIndex);
/*     */ 
/*     */     
/* 111 */     Material[] arenaMaterials = this.config.getArenaMaterials();
/* 112 */     Material arenaMaterial = arenaMaterials[this.selectedArenaIndex % arenaMaterials.length];
/*     */     
/* 114 */     ItemStack arenaButton = new ItemStack(arenaMaterial);
/* 115 */     ItemMeta arenaMeta = arenaButton.getItemMeta();
/* 116 */     if (arenaMeta != null) {
/* 117 */       arenaMeta.setDisplayName(ColorUtil.color(this.config.getArenaButtonName()));
/* 118 */       arenaMeta.setLore((List)this.config.getArenaButtonLore().stream()
/* 119 */           .map(lore -> ColorUtil.color(lore.replace("{arena}", currentArena)))
/* 120 */           .collect(Collectors.toList()));
/* 121 */       arenaButton.setItemMeta(arenaMeta);
/*     */     } 
/* 123 */     this.inventory.setItem(this.config.getArenaButtonSlot(), arenaButton);
/*     */   }
/*     */   
/*     */   private void updateTimeButton() {
/* 127 */     ItemStack timeButton = new ItemStack(this.config.getTimeButtonMaterial(), Math.min(this.duelTimeMinutes, 64));
/* 128 */     ItemMeta timeMeta = timeButton.getItemMeta();
/* 129 */     if (timeMeta != null) {
/* 130 */       timeMeta.setDisplayName(ColorUtil.color(this.config.getTimeButtonName()));
/* 131 */       timeMeta.setLore((List)this.config.getTimeButtonLore().stream()
/* 132 */           .map(lore -> ColorUtil.color(lore.replace("{time}", String.valueOf(this.duelTimeMinutes))))
/* 133 */           .collect(Collectors.toList()));
/* 134 */       timeButton.setItemMeta(timeMeta);
/*     */     } 
/* 136 */     this.inventory.setItem(this.config.getTimeButtonSlot(), timeButton);
/*     */   }
/*     */   
/*     */   private void updatePingButton() {
/* 140 */     ItemStack pingButton = new ItemStack(this.config.getPingButtonMaterial());
/* 141 */     ItemMeta pingMeta = pingButton.getItemMeta();
/* 142 */     if (pingMeta != null) {
/* 143 */       pingMeta.setDisplayName(ColorUtil.color(this.config.getPingButtonName()));
/* 144 */       int ping = this.target.getPing();
/* 145 */       pingMeta.setLore((List)this.config.getPingButtonLore().stream()
/* 146 */           .map(lore -> ColorUtil.color(lore.replace("{ping}", String.valueOf(ping))))
/* 147 */           .collect(Collectors.toList()));
/* 148 */       pingButton.setItemMeta(pingMeta);
/*     */     } 
/* 150 */     this.inventory.setItem(this.config.getPingButtonSlot(), pingButton);
/*     */   }
/*     */   
/*     */   private boolean isOnCooldown(Player player) {
/* 154 */     UUID playerId = player.getUniqueId();
/* 155 */     Long lastClick = clickCooldowns.get(playerId);
/*     */     
/* 157 */     if (lastClick == null) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     return (System.currentTimeMillis() - lastClick.longValue() < this.config.getClickCooldownMs());
/*     */   }
/*     */   
/*     */   private void setCooldown(Player player) {
/* 165 */     clickCooldowns.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
/*     */   }
/*     */   
/*     */   private void playButtonSound(Player player) {
/*     */     try {
/* 170 */       Sound sound = Sound.valueOf(this.config.getButtonClickSound());
/* 171 */       player.playSound(player.getLocation(), sound, this.config.getSoundVolume(), this.config.getSoundPitch());
/* 172 */     } catch (IllegalArgumentException e) {
/* 173 */       player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playErrorSound(Player player) {
/*     */     try {
/* 179 */       Sound sound = Sound.valueOf(this.config.getErrorSound());
/* 180 */       player.playSound(player.getLocation(), sound, this.config.getSoundVolume(), this.config.getSoundPitch());
/* 181 */     } catch (IllegalArgumentException e) {
/* 182 */       player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openGUI() {
/* 187 */     this.sender.openInventory(this.inventory);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event) {
/*     */     Player player;
/* 193 */     if (!event.getInventory().equals(this.inventory)) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     event.setCancelled(true);
/*     */     
/* 199 */     HumanEntity humanEntity = event.getWhoClicked(); if (humanEntity instanceof Player) { player = (Player)humanEntity; }
/*     */     else
/*     */     { return; }
/*     */ 
/*     */     
/* 204 */     if (!player.equals(this.sender)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 209 */     if (isOnCooldown(player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 214 */     setCooldown(player);
/*     */     
/* 216 */     int slot = event.getSlot();
/* 217 */     ClickType clickType = event.getClick();
/*     */     
/* 219 */     if (slot == this.config.getCancelButtonSlot()) {
/* 220 */       playButtonSound(player);
/* 221 */       player.closeInventory();
/* 222 */     } else if (slot == this.config.getArenaButtonSlot()) {
/* 223 */       if (this.availableArenas.isEmpty()) {
/* 224 */         playErrorSound(player);
/*     */         
/*     */         return;
/*     */       } 
/* 228 */       playButtonSound(player);
/*     */       
/* 230 */       if (clickType == ClickType.LEFT) {
/*     */         
/* 232 */         this.selectedArenaIndex = (this.selectedArenaIndex + 1) % this.availableArenas.size();
/* 233 */         updateArenaButton();
/* 234 */       } else if (clickType == ClickType.RIGHT) {
/*     */         
/* 236 */         this.selectedArenaIndex = (this.selectedArenaIndex - 1 + this.availableArenas.size()) % this.availableArenas.size();
/* 237 */         updateArenaButton();
/*     */       } 
/* 239 */     } else if (slot == this.config.getTimeButtonSlot()) {
/* 240 */       playButtonSound(player);
/*     */       
/* 242 */       if (clickType == ClickType.LEFT) {
/*     */         
/* 244 */         if (this.duelTimeMinutes < this.config.getMaxTimeMinutes()) {
/* 245 */           this.duelTimeMinutes++;
/* 246 */           updateTimeButton();
/*     */         } 
/* 248 */       } else if (clickType == ClickType.RIGHT) {
/*     */         
/* 250 */         if (this.duelTimeMinutes > this.config.getMinTimeMinutes()) {
/* 251 */           this.duelTimeMinutes--;
/* 252 */           updateTimeButton();
/*     */         } 
/*     */       } 
/* 255 */     } else if (slot == this.config.getPingButtonSlot()) {
/* 256 */       playButtonSound(player);
/* 257 */       updatePingButton();
/* 258 */     } else if (slot == this.config.getConfirmButtonSlot()) {
/* 259 */       playButtonSound(player);
/*     */ 
/*     */       
/* 262 */       if (!this.plugin.getDatabase().hasArenas() || this.availableArenas.isEmpty()) {
/* 263 */         playErrorSound(player);
/* 264 */         player.sendMessage(ColorUtil.color("&cNo arenas available."));
/* 265 */         player.sendActionBar(ColorUtil.color("&cNo arenas available."));
/* 266 */         player.closeInventory();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 271 */       String selectedArena = this.availableArenas.get(this.selectedArenaIndex);
/*     */ 
/*     */       
/* 274 */       this.plugin.getDuelRequestManager().sendDuelRequest(this.sender, this.target, selectedArena, this.duelTimeMinutes);
/* 275 */       player.closeInventory();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryDrag(InventoryDragEvent event) {
/* 281 */     if (event.getInventory().equals(this.inventory)) {
/* 282 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDuelTimeMinutes() {
/* 287 */     return this.duelTimeMinutes;
/*     */   }
/*     */   
/*     */   public String getSelectedArena() {
/* 291 */     if (this.availableArenas.isEmpty()) {
/* 292 */       return null;
/*     */     }
/* 294 */     return this.availableArenas.get(this.selectedArenaIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 299 */     clickCooldowns.remove(this.sender.getUniqueId());
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\gui\DuelGUI.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */