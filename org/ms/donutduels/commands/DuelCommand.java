/*     */ package org.ms.donutduels.commands;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.ms.donutduels.DonutDuels;
/*     */ import org.ms.donutduels.gui.DuelGUI;
/*     */ import org.ms.donutduels.model.DuelRequest;
/*     */ import org.ms.donutduels.service.ArenaSelectionManager;
/*     */ import org.ms.donutduels.service.WorldEditService;
/*     */ import org.ms.donutduels.utils.ColorUtil;
/*     */ 
/*     */ public class DuelCommand
/*     */   implements CommandExecutor
/*     */ {
/*     */   private final DonutDuels plugin;
/*     */   private final WorldEditService worldEditService;
/*     */   private final ArenaSelectionManager arenaSelectionManager;
/*     */   
/*     */   public DuelCommand(DonutDuels plugin) {
/*  25 */     this.plugin = plugin;
/*  26 */     this.worldEditService = new WorldEditService();
/*  27 */     this.arenaSelectionManager = new ArenaSelectionManager();
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*     */     Player player;
/*  32 */     if (sender instanceof Player) { player = (Player)sender; }
/*  33 */     else { sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelOnlyPlayersMessage()));
/*  34 */       return true; }
/*     */ 
/*     */     
/*  37 */     if (args.length < 1) {
/*  38 */       sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelUsageMessage()));
/*  39 */       return true;
/*     */     } 
/*     */     
/*  42 */     String sub = args[0].toLowerCase();
/*     */ 
/*     */     
/*  45 */     if (sub.equals("pos1") || sub.equals("pos2")) {
/*  46 */       if (!sender.hasPermission("donutduels.admin")) {
/*  47 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/*  48 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  52 */       if (!this.arenaSelectionManager.hasSelectedArena(player)) {
/*  53 */         String str = this.plugin.getConfigManager().getDuelSetArenaFirstMessage();
/*  54 */         player.sendMessage(ColorUtil.color(str));
/*  55 */         player.sendActionBar(ColorUtil.color(str));
/*  56 */         return true;
/*     */       } 
/*     */       
/*  59 */       String selectedArena = this.arenaSelectionManager.getSelectedArena(player);
/*  60 */       Location loc = player.getLocation();
/*  61 */       String locStr = String.format("%s:%.2f,%.2f,%.2f", new Object[] { loc.getWorld().getName(), Double.valueOf(loc.getX()), Double.valueOf(loc.getY()), Double.valueOf(loc.getZ()) });
/*     */ 
/*     */       
/*  64 */       this.plugin.getDatabase().setPosition(selectedArena + "_" + selectedArena, loc);
/*     */ 
/*     */ 
/*     */       
/*  68 */       String message = this.plugin.getConfigManager().getDuelPositionSetMessage().replace("{position}", sub).replace("{arena}", selectedArena).replace("{location}", locStr);
/*  69 */       player.sendMessage(ColorUtil.color(message));
/*  70 */       player.playSound(player.getLocation(), this.plugin.getConfigManager().getDuelPositionSetSound(), 1.0F, 1.0F);
/*  71 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if (sub.equals("manualpos1") || sub.equals("manualpos2")) {
/*  76 */       if (!sender.hasPermission("donutduels.admin")) {
/*  77 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/*  78 */         return true;
/*     */       } 
/*     */       
/*  81 */       Location loc = player.getLocation();
/*  82 */       String locStr = String.format("%s:%.2f,%.2f,%.2f", new Object[] { loc.getWorld().getName(), Double.valueOf(loc.getX()), Double.valueOf(loc.getY()), Double.valueOf(loc.getZ()) });
/*  83 */       String posType = sub.replace("manual", "");
/*     */       
/*  85 */       this.plugin.getDatabase().setPosition(posType, loc);
/*     */ 
/*     */       
/*  88 */       String message = this.plugin.getConfigManager().getDuelManualPositionSetMessage().replace("{position}", posType).replace("{location}", locStr);
/*  89 */       player.sendMessage(ColorUtil.color(message));
/*  90 */       player.playSound(player.getLocation(), this.plugin.getConfigManager().getDuelPositionSetSound(), 1.0F, 1.0F);
/*     */ 
/*     */       
/*  93 */       if (this.plugin.getDatabase().getPosition("pos1") != null && this.plugin.getDatabase().getPosition("pos2") != null) {
/*  94 */         player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelCreateArenaPromptMessage()));
/*  95 */         player.playSound(player.getLocation(), this.plugin.getConfigManager().getDuelPositionSetSound(), 1.0F, 1.0F);
/*     */       } 
/*  97 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     if (sub.equals("select")) {
/* 102 */       if (!sender.hasPermission("donutduels.admin")) {
/* 103 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/* 104 */         return true;
/*     */       } 
/*     */       
/* 107 */       if (args.length != 2) {
/* 108 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelSelectUsageMessage()));
/* 109 */         return true;
/*     */       } 
/*     */       
/* 112 */       String arenaName = args[1];
/*     */ 
/*     */       
/* 115 */       if (!this.plugin.getDatabase().arenaExists(arenaName)) {
/*     */         
/* 117 */         String str = this.plugin.getConfigManager().getDuelArenaNotExistMessage().replace("{arena}", arenaName);
/* 118 */         player.sendMessage(ColorUtil.color(str));
/* 119 */         return true;
/*     */       } 
/*     */       
/* 122 */       this.arenaSelectionManager.setSelectedArena(player, arenaName);
/*     */       
/* 124 */       String message = this.plugin.getConfigManager().getDuelArenaSelectedMessage().replace("{arena}", arenaName);
/* 125 */       player.sendMessage(ColorUtil.color(message));
/* 126 */       player.sendActionBar(ColorUtil.color(message));
/* 127 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (sub.equals("create")) {
/* 132 */       if (!sender.hasPermission("donutduels.admin")) {
/* 133 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/* 134 */         return true;
/*     */       } 
/*     */       
/* 137 */       if (args.length != 2) {
/* 138 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelCreateUsageMessage()));
/* 139 */         return true;
/*     */       } 
/*     */       
/* 142 */       String arenaName = args[1];
/*     */ 
/*     */       
/* 145 */       if (this.plugin.getDatabase().arenaExists(arenaName)) {
/*     */         
/* 147 */         String str = this.plugin.getConfigManager().getDuelArenaAlreadyExistsMessage().replace("{arena}", arenaName);
/* 148 */         player.sendMessage(ColorUtil.color(str));
/* 149 */         player.sendActionBar(ColorUtil.color(this.plugin.getConfigManager().getDuelArenaAlreadyExistsActionBarMessage()));
/* 150 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 154 */       Location[] selection = this.worldEditService.getPlayerSelection(player);
/*     */       
/* 156 */       if (selection != null) {
/*     */         
/* 158 */         Location location1 = selection[0];
/* 159 */         Location location2 = selection[1];
/*     */         
/* 161 */         this.plugin.getDatabase().createArena(arenaName, location1, location2);
/*     */         
/* 163 */         String str1 = this.plugin.getConfigManager().getDuelArenaCreatedWorldEditMessage().replace("{arena}", arenaName);
/* 164 */         player.sendMessage(ColorUtil.color(str1));
/*     */         
/* 166 */         String str2 = this.plugin.getConfigManager().getDuelArenaCreatedActionBarMessage().replace("{arena}", arenaName);
/* 167 */         player.sendActionBar(ColorUtil.color(str2));
/*     */ 
/*     */         
/* 170 */         this.arenaSelectionManager.setSelectedArena(player, arenaName);
/* 171 */         return true;
/*     */       } 
/*     */       
/* 174 */       Location pos1 = this.plugin.getDatabase().getPosition("pos1");
/* 175 */       Location pos2 = this.plugin.getDatabase().getPosition("pos2");
/*     */       
/* 177 */       if (pos1 == null || pos2 == null) {
/* 178 */         player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNeedSelectionOrPositionsMessage()));
/* 179 */         return true;
/*     */       } 
/*     */       
/* 182 */       this.plugin.getDatabase().createArena(arenaName, pos1, pos2);
/*     */       
/* 184 */       String message = this.plugin.getConfigManager().getDuelArenaCreatedManualMessage().replace("{arena}", arenaName);
/* 185 */       player.sendMessage(ColorUtil.color(message));
/*     */       
/* 187 */       String actionBarMessage = this.plugin.getConfigManager().getDuelArenaCreatedActionBarMessage().replace("{arena}", arenaName);
/* 188 */       player.sendActionBar(ColorUtil.color(actionBarMessage));
/*     */ 
/*     */       
/* 191 */       this.arenaSelectionManager.setSelectedArena(player, arenaName);
/* 192 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (sub.equals("accept")) {
/* 198 */       if (args.length < 2) {
/* 199 */         player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelAcceptUsageMessage()));
/* 200 */         return true;
/*     */       } 
/*     */       
/* 203 */       String senderName = args[1];
/* 204 */       DuelRequest request = this.plugin.getDuelRequestManager().getPendingRequestBySender(senderName, player.getUniqueId());
/*     */       
/* 206 */       if (request == null) {
/*     */         
/* 208 */         String message = this.plugin.getConfigManager().getDuelNoPendingRequestMessage().replace("{player}", senderName);
/* 209 */         player.sendMessage(ColorUtil.color(message));
/* 210 */         return true;
/*     */       } 
/*     */       
/* 213 */       this.plugin.getDuelRequestManager().openAcceptGUI(player, request);
/* 214 */       return true;
/*     */     } 
/*     */     
/* 217 */     if (sub.equals("loadschematic") || sub.equals("loadschematics")) {
/* 218 */       if (!sender.hasPermission("donutduels.admin")) {
/* 219 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/* 220 */         return true;
/*     */       } 
/*     */       
/* 223 */       sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelLoadingSchematicsMessage()));
/*     */       
/* 225 */       this.plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> {
/*     */             this.plugin.getSchematicManager().loadAllSchematics();
/*     */             
/*     */             sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelSchematicsLoadedMessage()));
/*     */           });
/* 230 */       return true;
/*     */     } 
/*     */     
/* 233 */     if (sub.equals("schematics")) {
/* 234 */       if (!sender.hasPermission("donutduels.admin")) {
/* 235 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoPermissionMessage()));
/* 236 */         return true;
/*     */       } 
/*     */       
/* 239 */       List<String> schematicFiles = this.plugin.getSchematicManager().getAvailableSchematicFiles();
/*     */       
/* 241 */       if (schematicFiles.isEmpty()) {
/* 242 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelNoSchematicsFoundMessage()));
/*     */         
/* 244 */         String folderMessage = this.plugin.getConfigManager().getDuelSchematicsFolderInfoMessage().replace("{folder}", this.plugin.getSchematicManager().getSchematicsFolder().getAbsolutePath());
/* 245 */         sender.sendMessage(ColorUtil.color(folderMessage));
/*     */       } else {
/* 247 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelAvailableSchematicsMessage()));
/* 248 */         for (String file : schematicFiles) {
/*     */           
/* 250 */           String fileMessage = this.plugin.getConfigManager().getDuelSchematicFileEntryMessage().replace("{file}", file);
/* 251 */           sender.sendMessage(ColorUtil.color(fileMessage));
/*     */         } 
/*     */       } 
/*     */       
/* 255 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     String targetPlayerName = args[0];
/* 261 */     Player targetPlayer = this.plugin.getServer().getPlayer(targetPlayerName);
/*     */     
/* 263 */     if (targetPlayer == null || !targetPlayer.isOnline()) {
/* 264 */       player.playSound(player.getLocation(), this.plugin.getConfigManager().getDuelPlayerNotOnlineSound(), 1.0F, 1.0F);
/* 265 */       String message = this.plugin.getConfigManager().getDuelPlayerNotOnlineMessage();
/* 266 */       player.sendMessage(ColorUtil.color(message));
/* 267 */       player.sendActionBar(ColorUtil.color(message));
/* 268 */       return true;
/*     */     } 
/*     */     
/* 271 */     if (targetPlayer.equals(player)) {
/* 272 */       player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDuelCannotDuelYourselfMessage()));
/* 273 */       return true;
/*     */     } 
/*     */     
/* 276 */     DuelGUI duelGUI = new DuelGUI(this.plugin, player, targetPlayer);
/* 277 */     duelGUI.openGUI();
/* 278 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\commands\DuelCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */