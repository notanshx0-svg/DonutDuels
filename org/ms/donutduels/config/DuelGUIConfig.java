/*     */ package org.ms.donutduels.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ 
/*     */ 
/*     */ public class DuelGUIConfig
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private FileConfiguration config;
/*     */   private File configFile;
/*     */   
/*     */   public DuelGUIConfig(DonutDuels plugin) {
/*  21 */     this.plugin = plugin;
/*  22 */     loadConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadConfig() {
/*  27 */     File guisFolder = new File(this.plugin.getDataFolder(), "guis");
/*  28 */     if (!guisFolder.exists()) {
/*  29 */       guisFolder.mkdirs();
/*     */     }
/*     */     
/*  32 */     this.configFile = new File(guisFolder, "duelsguiconfig.yml");
/*     */ 
/*     */     
/*  35 */     if (!this.configFile.exists()) {
/*  36 */       createDefaultConfig();
/*     */     }
/*     */     
/*  39 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
/*     */   }
/*     */ 
/*     */   
/*     */   private void createDefaultConfig() {
/*     */     try {
/*  45 */       InputStream defaultConfig = this.plugin.getResource("guis/duelsguiconfig.yml");
/*  46 */       if (defaultConfig != null) {
/*  47 */         Files.copy(defaultConfig, this.configFile.toPath(), new java.nio.file.CopyOption[0]);
/*  48 */         defaultConfig.close();
/*     */       } else {
/*     */         
/*  51 */         this.configFile.createNewFile();
/*  52 */         YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
/*     */ 
/*     */         
/*  55 */         yamlConfiguration.set("gui.title", "ᴄʀᴇᴀᴛᴇ ᴅᴜᴇʟ - {target}");
/*  56 */         yamlConfiguration.set("gui.size", Integer.valueOf(27));
/*     */ 
/*     */         
/*  59 */         yamlConfiguration.set("buttons.cancel.slot", Integer.valueOf(10));
/*  60 */         yamlConfiguration.set("buttons.cancel.material", "RED_STAINED_GLASS_PANE");
/*  61 */         yamlConfiguration.set("buttons.cancel.name", "#FF0000ᴄᴀɴᴄᴇʟ");
/*  62 */         yamlConfiguration.set("buttons.cancel.lore", List.of("&fClick to cancel"));
/*     */ 
/*     */         
/*  65 */         yamlConfiguration.set("buttons.arena.slot", Integer.valueOf(12));
/*  66 */         yamlConfiguration.set("buttons.arena.name", "#00FF6Cᴀʀᴇɴᴀ");
/*  67 */         yamlConfiguration.set("buttons.arena.lore", List.of("&fClick to change map", "&7({arena})"));
/*  68 */         yamlConfiguration.set("buttons.arena.no_arena_material", "BARRIER");
/*  69 */         yamlConfiguration.set("buttons.arena.no_arena_lore", List.of("&fClick to change map", "&7(No arenas available)"));
/*     */ 
/*     */         
/*  72 */         yamlConfiguration.set("buttons.time.slot", Integer.valueOf(13));
/*  73 */         yamlConfiguration.set("buttons.time.material", "CLOCK");
/*  74 */         yamlConfiguration.set("buttons.time.name", "#00FF6Cᴛɪᴍᴇ");
/*  75 */         yamlConfiguration.set("buttons.time.lore", List.of("&7({time}m)"));
/*  76 */         yamlConfiguration.set("buttons.time.default_minutes", Integer.valueOf(5));
/*  77 */         yamlConfiguration.set("buttons.time.min_minutes", Integer.valueOf(1));
/*  78 */         yamlConfiguration.set("buttons.time.max_minutes", Integer.valueOf(30));
/*     */ 
/*     */         
/*  81 */         yamlConfiguration.set("buttons.ping.slot", Integer.valueOf(14));
/*  82 */         yamlConfiguration.set("buttons.ping.material", "CREEPER_BANNER_PATTERN");
/*  83 */         yamlConfiguration.set("buttons.ping.name", "#00FF6Cᴘɪɴɢ");
/*  84 */         yamlConfiguration.set("buttons.ping.lore", List.of("&7Ping (&b{ping}ms&7)"));
/*     */ 
/*     */         
/*  87 */         yamlConfiguration.set("buttons.confirm.slot", Integer.valueOf(16));
/*  88 */         yamlConfiguration.set("buttons.confirm.material", "LIME_STAINED_GLASS_PANE");
/*  89 */         yamlConfiguration.set("buttons.confirm.name", "#1BFF00ѕᴇɴᴅ");
/*  90 */         yamlConfiguration.set("buttons.confirm.lore", List.of("&fClick to send request"));
/*     */ 
/*     */         
/*  93 */         yamlConfiguration.set("arena_materials", List.of("SAND", "GRASS_BLOCK", "NETHERITE_BLOCK", "STONE", "COBBLESTONE", "DIAMOND_BLOCK", "EMERALD_BLOCK", "GOLD_BLOCK", "IRON_BLOCK", "REDSTONE_BLOCK"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  99 */         yamlConfiguration.set("sounds.button_click", "UI_BUTTON_CLICK");
/* 100 */         yamlConfiguration.set("sounds.error", "ENTITY_VILLAGER_NO");
/* 101 */         yamlConfiguration.set("sounds.volume", Double.valueOf(1.0D));
/* 102 */         yamlConfiguration.set("sounds.pitch", Double.valueOf(1.0D));
/*     */ 
/*     */         
/* 105 */         yamlConfiguration.set("cooldown.click_cooldown_ms", Integer.valueOf(90));
/*     */         
/* 107 */         yamlConfiguration.save(this.configFile);
/*     */       } 
/* 109 */     } catch (IOException e) {
/* 110 */       this.plugin.getLogger().severe("Could not create duelsguiconfig.yml: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadConfig() {
/* 115 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGUITitle() {
/* 120 */     return this.config.getString("gui.title", "ᴄʀᴇᴀᴛᴇ ᴅᴜᴇʟ - {target}");
/*     */   }
/*     */   
/*     */   public int getGUISize() {
/* 124 */     return this.config.getInt("gui.size", 27);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCancelButtonSlot() {
/* 129 */     return this.config.getInt("buttons.cancel.slot", 10);
/*     */   }
/*     */   
/*     */   public Material getCancelButtonMaterial() {
/* 133 */     String materialName = this.config.getString("buttons.cancel.material", "RED_STAINED_GLASS_PANE");
/*     */     try {
/* 135 */       return Material.valueOf(materialName);
/* 136 */     } catch (IllegalArgumentException e) {
/* 137 */       return Material.RED_STAINED_GLASS_PANE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getCancelButtonName() {
/* 142 */     return this.config.getString("buttons.cancel.name", "#FF0000ᴄᴀɴᴄᴇʟ");
/*     */   }
/*     */   
/*     */   public List<String> getCancelButtonLore() {
/* 146 */     return this.config.getStringList("buttons.cancel.lore");
/*     */   }
/*     */   
/*     */   public int getArenaButtonSlot() {
/* 150 */     return this.config.getInt("buttons.arena.slot", 12);
/*     */   }
/*     */   
/*     */   public String getArenaButtonName() {
/* 154 */     return this.config.getString("buttons.arena.name", "#00FF6Cᴀʀᴇɴᴀ");
/*     */   }
/*     */   
/*     */   public List<String> getArenaButtonLore() {
/* 158 */     return this.config.getStringList("buttons.arena.lore");
/*     */   }
/*     */   
/*     */   public Material getNoArenaMaterial() {
/* 162 */     String materialName = this.config.getString("buttons.arena.no_arena_material", "BARRIER");
/*     */     try {
/* 164 */       return Material.valueOf(materialName);
/* 165 */     } catch (IllegalArgumentException e) {
/* 166 */       return Material.BARRIER;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getNoArenaLore() {
/* 171 */     return this.config.getStringList("buttons.arena.no_arena_lore");
/*     */   }
/*     */   
/*     */   public int getTimeButtonSlot() {
/* 175 */     return this.config.getInt("buttons.time.slot", 13);
/*     */   }
/*     */   
/*     */   public Material getTimeButtonMaterial() {
/* 179 */     String materialName = this.config.getString("buttons.time.material", "CLOCK");
/*     */     try {
/* 181 */       return Material.valueOf(materialName);
/* 182 */     } catch (IllegalArgumentException e) {
/* 183 */       return Material.CLOCK;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getTimeButtonName() {
/* 188 */     return this.config.getString("buttons.time.name", "#00FF6Cᴛɪᴍᴇ");
/*     */   }
/*     */   
/*     */   public List<String> getTimeButtonLore() {
/* 192 */     return this.config.getStringList("buttons.time.lore");
/*     */   }
/*     */   
/*     */   public int getDefaultTimeMinutes() {
/* 196 */     return this.config.getInt("buttons.time.default_minutes", 5);
/*     */   }
/*     */   
/*     */   public int getMinTimeMinutes() {
/* 200 */     return this.config.getInt("buttons.time.min_minutes", 1);
/*     */   }
/*     */   
/*     */   public int getMaxTimeMinutes() {
/* 204 */     return this.config.getInt("buttons.time.max_minutes", 30);
/*     */   }
/*     */   
/*     */   public int getPingButtonSlot() {
/* 208 */     return this.config.getInt("buttons.ping.slot", 14);
/*     */   }
/*     */   
/*     */   public Material getPingButtonMaterial() {
/* 212 */     String materialName = this.config.getString("buttons.ping.material", "CREEPER_BANNER_PATTERN");
/*     */     try {
/* 214 */       return Material.valueOf(materialName);
/* 215 */     } catch (IllegalArgumentException e) {
/* 216 */       return Material.CREEPER_BANNER_PATTERN;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPingButtonName() {
/* 221 */     return this.config.getString("buttons.ping.name", "#00FF6Cᴘɪɴɢ");
/*     */   }
/*     */   
/*     */   public List<String> getPingButtonLore() {
/* 225 */     return this.config.getStringList("buttons.ping.lore");
/*     */   }
/*     */   
/*     */   public int getConfirmButtonSlot() {
/* 229 */     return this.config.getInt("buttons.confirm.slot", 16);
/*     */   }
/*     */   
/*     */   public Material getConfirmButtonMaterial() {
/* 233 */     String materialName = this.config.getString("buttons.confirm.material", "LIME_STAINED_GLASS_PANE");
/*     */     try {
/* 235 */       return Material.valueOf(materialName);
/* 236 */     } catch (IllegalArgumentException e) {
/* 237 */       return Material.LIME_STAINED_GLASS_PANE;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getConfirmButtonName() {
/* 242 */     return this.config.getString("buttons.confirm.name", "#1BFF00ѕᴇɴᴅ");
/*     */   }
/*     */   
/*     */   public List<String> getConfirmButtonLore() {
/* 246 */     return this.config.getStringList("buttons.confirm.lore");
/*     */   }
/*     */ 
/*     */   
/*     */   public Material[] getArenaMaterials() {
/* 251 */     List<String> materialNames = this.config.getStringList("arena_materials");
/* 252 */     if (materialNames.isEmpty()) {
/* 253 */       return new Material[] { Material.SAND, Material.GRASS_BLOCK, Material.NETHERITE_BLOCK, Material.STONE, Material.COBBLESTONE, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.REDSTONE_BLOCK };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     return (Material[])materialNames.stream()
/* 262 */       .map(name -> {
/*     */           try {
/*     */             return Material.valueOf(name);
/* 265 */           } catch (IllegalArgumentException e) {
/*     */             
/*     */             return Material.STONE;
/*     */           } 
/* 269 */         }).toArray(x$0 -> new Material[x$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getButtonClickSound() {
/* 274 */     return this.config.getString("sounds.button_click", "UI_BUTTON_CLICK");
/*     */   }
/*     */   
/*     */   public String getErrorSound() {
/* 278 */     return this.config.getString("sounds.error", "ENTITY_VILLAGER_NO");
/*     */   }
/*     */   
/*     */   public float getSoundVolume() {
/* 282 */     return (float)this.config.getDouble("sounds.volume", 1.0D);
/*     */   }
/*     */   
/*     */   public float getSoundPitch() {
/* 286 */     return (float)this.config.getDouble("sounds.pitch", 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getClickCooldownMs() {
/* 291 */     return this.config.getLong("cooldown.click_cooldown_ms", 90L);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\config\DuelGUIConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */