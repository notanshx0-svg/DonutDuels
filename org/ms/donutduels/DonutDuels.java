/*     */ package org.ms.donutduels;
/*     */ 
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.ms.donutduels.commands.DonutDuelsCommand;
/*     */ import org.ms.donutduels.commands.DuelCommand;
/*     */ import org.ms.donutduels.commands.LeaveCommand;
/*     */ import org.ms.donutduels.commands.QueueCommand;
/*     */ import org.ms.donutduels.config.ConfigManager;
/*     */ import org.ms.donutduels.config.DuelGUIConfig;
/*     */ import org.ms.donutduels.config.QueueGUIConfig;
/*     */ import org.ms.donutduels.database.Database;
/*     */ import org.ms.donutduels.database.MySQLDatabase;
/*     */ import org.ms.donutduels.database.SQLiteDatabase;
/*     */ import org.ms.donutduels.duel.DuelManager;
/*     */ import org.ms.donutduels.duel.DuelRequestManager;
/*     */ import org.ms.donutduels.migration.MigrationManager;
/*     */ import org.ms.donutduels.queue.QueueManager;
/*     */ import org.ms.donutduels.service.ArenaSelectionManager;
/*     */ import org.ms.donutduels.service.MusicManager;
/*     */ import org.ms.donutduels.service.SchedulerService;
/*     */ import org.ms.donutduels.service.SchematicManager;
/*     */ import org.ms.donutduels.service.WorldEditService;
/*     */ import org.ms.donutduels.stats.PlayerStatsManager;
/*     */ 
/*     */ public class DonutDuels extends JavaPlugin {
/*     */   private Database database;
/*     */   private ConfigManager configManager;
/*     */   private MigrationManager migrationManager;
/*     */   private DuelRequestManager duelRequestManager;
/*     */   private QueueManager queueManager;
/*     */   private WorldEditService worldEditService;
/*     */   private ArenaSelectionManager arenaSelectionManager;
/*     */   private DuelManager duelManager;
/*     */   private PlayerStatsManager playerStatsManager;
/*     */   private SchematicManager schematicManager;
/*     */   private MusicManager musicManager;
/*     */   private DuelGUIConfig duelGUIConfig;
/*     */   private QueueGUIConfig queueGUIConfig;
/*     */   private SchedulerService schedulerService;
/*     */   
/*     */   public void onEnable() {
/*  45 */     this.schedulerService = new SchedulerService((Plugin)this);
/*     */     
/*  47 */     this.configManager = new ConfigManager(this);
/*  48 */     this.migrationManager = new MigrationManager(this);
/*  49 */     initDatabase();
/*     */     
/*  51 */     this.duelRequestManager = new DuelRequestManager(this);
/*  52 */     this.queueManager = new QueueManager(this);
/*  53 */     this.worldEditService = new WorldEditService();
/*  54 */     this.arenaSelectionManager = new ArenaSelectionManager();
/*  55 */     this.playerStatsManager = new PlayerStatsManager(this);
/*  56 */     this.schematicManager = new SchematicManager(this);
/*  57 */     this.musicManager = new MusicManager(this);
/*  58 */     this.queueGUIConfig = new QueueGUIConfig(this);
/*  59 */     this.duelGUIConfig = new DuelGUIConfig(this);
/*     */ 
/*     */     
/*  62 */     this.duelManager = new DuelManager(this);
/*     */ 
/*     */     
/*  65 */     getCommand("leave").setExecutor((CommandExecutor)new LeaveCommand(this));
/*  66 */     getCommand("duel").setExecutor((CommandExecutor)new DuelCommand(this));
/*  67 */     getCommand("queue").setExecutor((CommandExecutor)new QueueCommand(this));
/*  68 */     getCommand("donutduels").setExecutor((CommandExecutor)new DonutDuelsCommand(this));
/*  69 */     getCommand("donutduels").setTabCompleter((TabCompleter)new DonutDuelsCommand(this));
/*     */ 
/*     */     
/*  72 */     this.schedulerService.runTaskLater(() -> this.schematicManager.loadAllSchematics(), 20L);
/*     */ 
/*     */ 
/*     */     
/*  76 */     getLogger().info("DonutDuels has been enabled with FAWE integration, Schematic support, and Folia compatibility!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  82 */     if (this.musicManager != null) {
/*  83 */       this.musicManager.stopAllMusic();
/*     */     }
/*     */     
/*  86 */     if (this.database != null) {
/*  87 */       this.database.close();
/*     */     }
/*  89 */     getLogger().info("DonutDuels has been disabled!");
/*     */   }
/*     */   
/*     */   public void reloadPlugin() {
/*  93 */     this.configManager.reloadConfig();
/*  94 */     if (this.database != null) {
/*  95 */       this.database.close();
/*     */     }
/*  97 */     initDatabase();
/*     */     
/*  99 */     this.queueGUIConfig.reloadConfig();
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.schematicManager.loadAllSchematics();
/*     */   }
/*     */   
/*     */   private void initDatabase() {
/* 107 */     String dbType = this.configManager.getDatabaseType();
/* 108 */     if (dbType.equals("mysql")) {
/* 109 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 115 */         .database = (Database)new MySQLDatabase(this, this.configManager.getMySQLHost(), this.configManager.getMySQLPort(), this.configManager.getMySQLDatabase(), this.configManager.getMySQLUser(), this.configManager.getMySQLPassword());
/*     */     } else {
/*     */       
/* 118 */       this.database = (Database)new SQLiteDatabase(this);
/*     */     } 
/* 120 */     this.database.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Database getDatabase() {
/* 125 */     return this.database;
/*     */   }
/*     */   
/*     */   public ConfigManager getConfigManager() {
/* 129 */     return this.configManager;
/*     */   }
/*     */   
/*     */   public MigrationManager getMigrationManager() {
/* 133 */     return this.migrationManager;
/*     */   }
/*     */   
/*     */   public DuelRequestManager getDuelRequestManager() {
/* 137 */     return this.duelRequestManager;
/*     */   }
/*     */   
/*     */   public WorldEditService getWorldEditService() {
/* 141 */     return this.worldEditService;
/*     */   }
/*     */   
/*     */   public ArenaSelectionManager getArenaSelectionManager() {
/* 145 */     return this.arenaSelectionManager;
/*     */   }
/*     */   
/*     */   public PlayerStatsManager getPlayerStatsManager() {
/* 149 */     return this.playerStatsManager;
/*     */   }
/*     */   
/*     */   public DuelManager getDuelManager() {
/* 153 */     return this.duelManager;
/*     */   }
/*     */   
/*     */   public SchematicManager getSchematicManager() {
/* 157 */     return this.schematicManager;
/*     */   }
/*     */   
/*     */   public MusicManager getMusicManager() {
/* 161 */     return this.musicManager;
/*     */   }
/*     */   
/*     */   public QueueManager getQueueManager() {
/* 165 */     return this.queueManager;
/*     */   }
/*     */   
/*     */   public DuelGUIConfig getDuelGUIConfig() {
/* 169 */     return this.duelGUIConfig;
/*     */   }
/*     */   
/*     */   public QueueGUIConfig getQueueGUIConfig() {
/* 173 */     return this.queueGUIConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public SchedulerService getSchedulerService() {
/* 178 */     return this.schedulerService;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\DonutDuels.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */