/*     */ package org.ms.donutduels.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class QueueGUIConfig
/*     */ {
/*     */   private final JavaPlugin plugin;
/*  19 */   private FileConfiguration config = null;
/*  20 */   private File configFile = null;
/*  21 */   private final String fileName = "queueguiconfig.yml";
/*     */   
/*     */   public QueueGUIConfig(JavaPlugin plugin) {
/*  24 */     this.plugin = plugin;
/*  25 */     saveDefaultConfig();
/*     */   }
/*     */   
/*     */   public void reloadConfig() {
/*  29 */     if (this.configFile == null) {
/*  30 */       this.configFile = new File(String.valueOf(this.plugin.getDataFolder()) + String.valueOf(this.plugin.getDataFolder()) + "guis", "queueguiconfig.yml");
/*     */     }
/*  32 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
/*     */ 
/*     */     
/*  35 */     InputStream defaultStream = this.plugin.getResource("guis/queueguiconfig.yml");
/*  36 */     if (defaultStream != null) {
/*  37 */       YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
/*  38 */       this.config.setDefaults((Configuration)defaultConfig);
/*     */     } 
/*     */   }
/*     */   
/*     */   public FileConfiguration getConfig() {
/*  43 */     if (this.config == null) {
/*  44 */       reloadConfig();
/*     */     }
/*  46 */     return this.config;
/*     */   }
/*     */   
/*     */   public void saveConfig() {
/*  50 */     if (this.config == null || this.configFile == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  54 */       getConfig().save(this.configFile);
/*  55 */     } catch (IOException ex) {
/*  56 */       this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + String.valueOf(this.configFile), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveDefaultConfig() {
/*  61 */     if (this.configFile == null) {
/*  62 */       this.configFile = new File(String.valueOf(this.plugin.getDataFolder()) + String.valueOf(this.plugin.getDataFolder()) + "guis", "queueguiconfig.yml");
/*     */     }
/*  64 */     if (!this.configFile.exists()) {
/*  65 */       this.plugin.saveResource("guis/queueguiconfig.yml", false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGUITitle() {
/*  71 */     return getConfig().getString("gui.title", "ѕᴇʟᴇᴄᴛ ʀᴇɢɪᴏɴѕ & ᴄᴏɴꜰɪʀᴍ");
/*     */   }
/*     */   
/*     */   public int getGUISize() {
/*  75 */     return getConfig().getInt("gui.size", 27);
/*     */   }
/*     */   
/*     */   public long getClickCooldown() {
/*  79 */     return getConfig().getLong("gui.click-cooldown-ms", 90L);
/*     */   }
/*     */   
/*     */   public long getActionBarUpdateInterval() {
/*  83 */     return getConfig().getLong("gui.actionbar-update-interval-ticks", 20L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCancelButtonSlot() {
/*  88 */     return getConfig().getInt("buttons.cancel.slot", 10);
/*     */   }
/*     */   
/*     */   public Material getCancelButtonMaterial() {
/*  92 */     String materialName = getConfig().getString("buttons.cancel.material", "RED_STAINED_GLASS_PANE");
/*     */     try {
/*  94 */       return Material.valueOf(materialName);
/*  95 */     } catch (IllegalArgumentException e) {
/*  96 */       return Material.RED_STAINED_GLASS_PANE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getCancelButtonName() {
/* 101 */     return getConfig().getString("buttons.cancel.name", "#FF0000ᴄᴀɴᴄᴇʟ");
/*     */   }
/*     */   
/*     */   public List<String> getCancelButtonLore() {
/* 105 */     return getConfig().getStringList("buttons.cancel.lore");
/*     */   }
/*     */   
/*     */   public int getWaitTimeButtonSlot() {
/* 109 */     return getConfig().getInt("buttons.wait-time.slot", 12);
/*     */   }
/*     */   
/*     */   public Material getWaitTimeButtonMaterial() {
/* 113 */     String materialName = getConfig().getString("buttons.wait-time.material", "COMPASS");
/*     */     try {
/* 115 */       return Material.valueOf(materialName);
/* 116 */     } catch (IllegalArgumentException e) {
/* 117 */       return Material.COMPASS;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getWaitTimeButtonName() {
/* 122 */     return getConfig().getString("buttons.wait-time.name", "#1DFF98ᴡᴀɪᴛ ᴛɪᴍᴇ");
/*     */   }
/*     */   
/*     */   public String getWaitTimeEstimatedFormat() {
/* 126 */     return getConfig().getString("buttons.wait-time.estimated-format", "&7Estimated Wait: {time}");
/*     */   }
/*     */   
/*     */   public String getWaitTimeQueuedFormat() {
/* 130 */     return getConfig().getString("buttons.wait-time.queued-format", "&7Currently queued: {count}");
/*     */   }
/*     */   
/*     */   public int getStatisticsButtonSlot() {
/* 134 */     return getConfig().getInt("buttons.statistics.slot", 13);
/*     */   }
/*     */   
/*     */   public Material getStatisticsButtonMaterial() {
/* 138 */     String materialName = getConfig().getString("buttons.statistics.material", "GRAY_DYE");
/*     */     try {
/* 140 */       return Material.valueOf(materialName);
/* 141 */     } catch (IllegalArgumentException e) {
/* 142 */       return Material.GRAY_DYE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStatisticsButtonName() {
/* 147 */     return getConfig().getString("buttons.statistics.name", "#1DFF98ѕᴛᴀᴛɪѕᴛɪᴄѕ");
/*     */   }
/*     */   
/*     */   public String getStatisticsWinsFormat() {
/* 151 */     return getConfig().getString("buttons.statistics.wins-format", "&7Wins: {wins}");
/*     */   }
/*     */   
/*     */   public String getStatisticsLossesFormat() {
/* 155 */     return getConfig().getString("buttons.statistics.losses-format", "&7Losses: {losses}");
/*     */   }
/*     */   
/*     */   public String getStatisticsStreakFormat() {
/* 159 */     return getConfig().getString("buttons.statistics.streak-format", "&7Streak: {streak}");
/*     */   }
/*     */   
/*     */   public int getRegionButtonSlot() {
/* 163 */     return getConfig().getInt("buttons.region.slot", 14);
/*     */   }
/*     */   
/*     */   public Material getRegionButtonMaterial() {
/* 167 */     String materialName = getConfig().getString("buttons.region.material", "FEATHER");
/*     */     try {
/* 169 */       return Material.valueOf(materialName);
/* 170 */     } catch (IllegalArgumentException e) {
/* 171 */       return Material.FEATHER;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getRegionButtonName() {
/* 176 */     return getConfig().getString("buttons.region.name", "#1DFF98ʀᴇɢɪᴏɴ");
/*     */   }
/*     */   
/*     */   public String getRegionPingFormat() {
/* 180 */     return getConfig().getString("buttons.region.ping-format", "&7Ping (&b{ping}ms&7)");
/*     */   }
/*     */   
/*     */   public int getSearchButtonSlot() {
/* 184 */     return getConfig().getInt("buttons.search.slot", 16);
/*     */   }
/*     */   
/*     */   public Material getSearchButtonMaterial() {
/* 188 */     String materialName = getConfig().getString("buttons.search.material", "LIME_STAINED_GLASS_PANE");
/*     */     try {
/* 190 */       return Material.valueOf(materialName);
/* 191 */     } catch (IllegalArgumentException e) {
/* 192 */       return Material.LIME_STAINED_GLASS_PANE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSearchButtonName() {
/* 197 */     return getConfig().getString("buttons.search.name", "#1BFF00ѕᴇᴀʀᴄʜ");
/*     */   }
/*     */   
/*     */   public List<String> getSearchButtonLore() {
/* 201 */     return getConfig().getStringList("buttons.search.lore");
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getButtonClickSound() {
/* 206 */     String soundName = getConfig().getString("sounds.button-click", "UI_BUTTON_CLICK");
/*     */     try {
/* 208 */       return Sound.valueOf(soundName);
/* 209 */     } catch (IllegalArgumentException e) {
/* 210 */       return Sound.UI_BUTTON_CLICK;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sound getSearchSound() {
/* 215 */     String soundName = getConfig().getString("sounds.search", "MUSIC_DISC_MELLOHI");
/*     */     try {
/* 217 */       return Sound.valueOf(soundName);
/* 218 */     } catch (IllegalArgumentException e) {
/* 219 */       return Sound.MUSIC_DISC_MELLOHI;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getSoundVolume() {
/* 224 */     return (float)getConfig().getDouble("sounds.volume", 1.0D);
/*     */   }
/*     */   
/*     */   public float getSoundPitch() {
/* 228 */     return (float)getConfig().getDouble("sounds.pitch", 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getJoinedQueueMessage() {
/* 233 */     return getConfig().getString("messages.joined-queue", "&7You joined the casual queue");
/*     */   }
/*     */   
/*     */   public String getSearchingActionBarFormat() {
/* 237 */     return getConfig().getString("messages.searching-actionbar-format", "&7Searching for a Casual Duel... Estimated Time: #FF0000< {time} >");
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\config\QueueGUIConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */