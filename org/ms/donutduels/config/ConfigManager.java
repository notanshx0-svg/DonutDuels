/*     */ package org.ms.donutduels.config;
/*     */ 
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ 
/*     */ public class ConfigManager
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private FileConfiguration config;
/*     */   
/*     */   public ConfigManager(DonutDuels plugin) {
/*  13 */     this.plugin = plugin;
/*  14 */     this.config = plugin.getConfig();
/*  15 */     plugin.saveDefaultConfig();
/*     */   }
/*     */   
/*     */   public void reloadConfig() {
/*  19 */     this.plugin.reloadConfig();
/*  20 */     this.config = this.plugin.getConfig();
/*     */   }
/*     */   
/*     */   public String getDatabaseType() {
/*  24 */     return this.config.getString("database.type", "sqlite").toLowerCase();
/*     */   }
/*     */   
/*     */   public String getMySQLHost() {
/*  28 */     return this.config.getString("database.mysql.host", "localhost");
/*     */   }
/*     */   
/*     */   public int getMySQLPort() {
/*  32 */     return this.config.getInt("database.mysql.port", 3306);
/*     */   }
/*     */   
/*     */   public String getMySQLDatabase() {
/*  36 */     return this.config.getString("database.mysql.database", "donutduels");
/*     */   }
/*     */   
/*     */   public String getMySQLUser() {
/*  40 */     return this.config.getString("database.mysql.user", "root");
/*     */   }
/*     */   
/*     */   public String getMySQLPassword() {
/*  44 */     return this.config.getString("database.mysql.password", "");
/*     */   }
/*     */   
/*     */   public String getOldDatabaseType() {
/*  48 */     return this.config.getString("migration.old-database.type", "sqlite").toLowerCase();
/*     */   }
/*     */   
/*     */   public String getOldMySQLHost() {
/*  52 */     return this.config.getString("migration.old-database.host", "localhost");
/*     */   }
/*     */   
/*     */   public int getOldMySQLPort() {
/*  56 */     return this.config.getInt("migration.old-database.port", 3306);
/*     */   }
/*     */   
/*     */   public String getOldMySQLDatabase() {
/*  60 */     return this.config.getString("migration.old-database.database", "donutduels");
/*     */   }
/*     */   
/*     */   public String getOldMySQLUser() {
/*  64 */     return this.config.getString("migration.old-database.username", "root");
/*     */   }
/*     */   
/*     */   public String getOldMySQLPassword() {
/*  68 */     return this.config.getString("migration.old-database.password", "");
/*     */   }
/*     */   
/*     */   public String getOldSQLitePath() {
/*  72 */     return this.config.getString("migration.old-database.sqlite-path", "plugins/DonutDuels/donutduels.db");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLeaveOnlyPlayersMessage() {
/*  77 */     return this.config.getString("messages.leave.only-players", "&cOnly players can use this command.");
/*     */   }
/*     */   
/*     */   public String getLeaveNotInDuelMessage() {
/*  81 */     return this.config.getString("messages.leave.not-in-duel", "&cYou are not in a duel or spectating.");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQueueOnlyPlayersMessage() {
/*  86 */     return this.config.getString("messages.queue.only-players", "&cThis command can only be used by players.");
/*     */   }
/*     */   
/*     */   public String getQueueLeftQueueChatMessage() {
/*  90 */     return this.config.getString("messages.queue.left-queue-chat", "&cYou left the casual queue");
/*     */   }
/*     */   
/*     */   public String getQueueLeftQueueActionBarMessage() {
/*  94 */     return this.config.getString("messages.queue.left-queue-actionbar", "&cYou left the casual queue");
/*     */   }
/*     */   
/*     */   public String getQueueJoinedQueueMessage() {
/*  98 */     return this.config.getString("messages.queue.joined-queue", "&7You joined the casual queue");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDuelOnlyPlayersMessage() {
/* 103 */     return this.config.getString("messages.duel.only-players", "&cThis command can only be used by players.");
/*     */   }
/*     */   
/*     */   public String getDuelUsageMessage() {
/* 107 */     return this.config.getString("messages.duel.usage", "&cUsage: /duel <pos1|pos2|manualpos1|manualpos2|create|accept|select|player> [name]");
/*     */   }
/*     */   
/*     */   public String getDuelNoPermissionMessage() {
/* 111 */     return this.config.getString("messages.duel.no-permission", "&cYou do not have permission to use this command.");
/*     */   }
/*     */   
/*     */   public String getDuelSetArenaFirstMessage() {
/* 115 */     return this.config.getString("messages.duel.set-arena-first", "&cPlease set an arena first");
/*     */   }
/*     */   
/*     */   public String getDuelPositionSetMessage() {
/* 119 */     return this.config.getString("messages.duel.position-set", "&b&lDUELS &8>> &7Set &b{position} &7for arena &b{arena} &7at &7{location}");
/*     */   }
/*     */   
/*     */   public String getDuelManualPositionSetMessage() {
/* 123 */     return this.config.getString("messages.duel.manual-position-set", "&b&lDUELS &8>> &7Set &b{position} &7at &7{location}");
/*     */   }
/*     */   
/*     */   public String getDuelCreateArenaPromptMessage() {
/* 127 */     return this.config.getString("messages.duel.create-arena-prompt", "&b&lDUELS &8>> &7Do /duel create <name>");
/*     */   }
/*     */   
/*     */   public String getDuelSelectUsageMessage() {
/* 131 */     return this.config.getString("messages.duel.select-usage", "&cUsage: /duel select <arena_name>");
/*     */   }
/*     */   
/*     */   public String getDuelArenaNotExistMessage() {
/* 135 */     return this.config.getString("messages.duel.arena-not-exist", "&cArena '{arena}' does not exist.");
/*     */   }
/*     */   
/*     */   public String getDuelArenaSelectedMessage() {
/* 139 */     return this.config.getString("messages.duel.arena-selected", "&b&lDUELS &8>> &7Selected arena &b{arena}");
/*     */   }
/*     */   
/*     */   public String getDuelCreateUsageMessage() {
/* 143 */     return this.config.getString("messages.duel.create-usage", "&cUsage: /duel create <name>");
/*     */   }
/*     */   
/*     */   public String getDuelArenaAlreadyExistsMessage() {
/* 147 */     return this.config.getString("messages.duel.arena-already-exists", "&cArena '{arena}' already exists. Choose a different name.");
/*     */   }
/*     */   
/*     */   public String getDuelArenaAlreadyExistsActionBarMessage() {
/* 151 */     return this.config.getString("messages.duel.arena-already-exists-actionbar", "&cArena already exists!");
/*     */   }
/*     */   
/*     */   public String getDuelArenaCreatedWorldEditMessage() {
/* 155 */     return this.config.getString("messages.duel.arena-created-worldedit", "&b&lDUELS &8>> &7Arena &b{arena} &7created successfully using WorldEdit selection.");
/*     */   }
/*     */   
/*     */   public String getDuelArenaCreatedManualMessage() {
/* 159 */     return this.config.getString("messages.duel.arena-created-manual", "&b&lDUELS &8>> &7Arena &b{arena} &7created successfully using manual positions.");
/*     */   }
/*     */   
/*     */   public String getDuelArenaCreatedActionBarMessage() {
/* 163 */     return this.config.getString("messages.duel.arena-created-actionbar", "&b&lDUELS &8>> &7Arena &b{arena} &7created successfully.");
/*     */   }
/*     */   
/*     */   public String getDuelNeedSelectionOrPositionsMessage() {
/* 167 */     return this.config.getString("messages.duel.need-selection-or-positions", "&cYou must either have a WorldEdit selection or set both manualpos1 and manualpos2 first.");
/*     */   }
/*     */   
/*     */   public String getDuelAcceptUsageMessage() {
/* 171 */     return this.config.getString("messages.duel.accept-usage", "&cUsage: /duel accept <player>");
/*     */   }
/*     */   
/*     */   public String getDuelNoPendingRequestMessage() {
/* 175 */     return this.config.getString("messages.duel.no-pending-request", "&cNo pending duel request from {player}");
/*     */   }
/*     */   
/*     */   public String getDuelLoadingSchematicsMessage() {
/* 179 */     return this.config.getString("messages.duel.loading-schematics", "&b&lDUELS &8>> &7Loading schematics from folder...");
/*     */   }
/*     */   
/*     */   public String getDuelSchematicsLoadedMessage() {
/* 183 */     return this.config.getString("messages.duel.schematics-loaded", "&b&lDUELS &8>> &aSchematic loading completed!");
/*     */   }
/*     */   
/*     */   public String getDuelNoSchematicsFoundMessage() {
/* 187 */     return this.config.getString("messages.duel.no-schematics-found", "&b&lDUELS &8>> &7No schematic files found in the schematics folder.");
/*     */   }
/*     */   
/*     */   public String getDuelSchematicsFolderInfoMessage() {
/* 191 */     return this.config.getString("messages.duel.schematics-folder-info", "&7Place .schem or .schematic files in: &e{folder}");
/*     */   }
/*     */   
/*     */   public String getDuelAvailableSchematicsMessage() {
/* 195 */     return this.config.getString("messages.duel.available-schematics", "&b&lDUELS &8>> &7Available schematic files:");
/*     */   }
/*     */   
/*     */   public String getDuelSchematicFileEntryMessage() {
/* 199 */     return this.config.getString("messages.duel.schematic-file-entry", "&8- &b{file}");
/*     */   }
/*     */   
/*     */   public String getDuelPlayerNotOnlineMessage() {
/* 203 */     return this.config.getString("messages.duel.player-not-online", "&cThis user is not online.");
/*     */   }
/*     */   
/*     */   public String getDuelCannotDuelYourselfMessage() {
/* 207 */     return this.config.getString("messages.duel.cannot-duel-yourself", "&cYou cannot duel yourself.");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDonutDuelsNoPermissionMessage() {
/* 212 */     return this.config.getString("messages.donutduels.no-permission", "&cYou do not have permission to use this command.");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsUsageMessage() {
/* 216 */     return this.config.getString("messages.donutduels.usage", "&cUsage: /{label} <reload|migrate>");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsReloadedMessage() {
/* 220 */     return this.config.getString("messages.donutduels.reloaded", "&b&lDUELS &8>> &7Reloaded at &a{duration}ms");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsMigrateUsageMessage() {
/* 224 */     return this.config.getString("messages.donutduels.migrate-usage", "&cUsage: /{label} migrate \"from old donutduels\"");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsMigrationSuccessMessage() {
/* 228 */     return this.config.getString("messages.donutduels.migration-success", "&b&lDUELS &8>>#1BFF00ᴍɪɢʀᴀᴛɪᴏɴ ѕᴜᴄᴇѕѕꜰᴜʟʀ.");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsMigrationErrorMessage() {
/* 232 */     return this.config.getString("messages.donutduels.migration-error", "&b&lDUELS &8>>#FF0000ᴍɪɢʀᴀᴛɪᴏɴ ᴇʀʀᴏʀ, ᴘʟᴇᴀѕᴇ ᴄʜᴇᴄᴋ ᴄᴏɴѕᴏʟᴇ");
/*     */   }
/*     */   
/*     */   public String getDonutDuelsInvalidSubcommandMessage() {
/* 236 */     return this.config.getString("messages.donutduels.invalid-subcommand", "&cInvalid subcommand. Use reload or migrate.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getDuelPositionSetSound() {
/* 241 */     String soundName = this.config.getString("sounds.duel.position-set", "ENTITY_EXPERIENCE_ORB_PICKUP");
/*     */     try {
/* 243 */       return Sound.valueOf(soundName);
/* 244 */     } catch (IllegalArgumentException e) {
/* 245 */       return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sound getDuelPlayerNotOnlineSound() {
/* 250 */     String soundName = this.config.getString("sounds.duel.player-not-online", "ENTITY_VILLAGER_NO");
/*     */     try {
/* 252 */       return Sound.valueOf(soundName);
/* 253 */     } catch (IllegalArgumentException e) {
/* 254 */       return Sound.ENTITY_VILLAGER_NO;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sound getDonutDuelsReloadSuccessSound() {
/* 259 */     String soundName = this.config.getString("sounds.donutduels.reload-success", "ENTITY_PLAYER_LEVELUP");
/*     */     try {
/* 261 */       return Sound.valueOf(soundName);
/* 262 */     } catch (IllegalArgumentException e) {
/* 263 */       return Sound.ENTITY_PLAYER_LEVELUP;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Sound getDonutDuelsMigrationSuccessSound() {
/* 268 */     String soundName = this.config.getString("sounds.donutduels.migration-success", "ENTITY_VILLAGER_YES");
/*     */     try {
/* 270 */       return Sound.valueOf(soundName);
/* 271 */     } catch (IllegalArgumentException e) {
/* 272 */       return Sound.ENTITY_VILLAGER_YES;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\config\ConfigManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */