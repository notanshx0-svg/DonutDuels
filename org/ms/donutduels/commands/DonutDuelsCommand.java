/*    */ package org.ms.donutduels.commands;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabCompleter;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ import org.ms.donutduels.utils.ColorUtil;
/*    */ 
/*    */ 
/*    */ public class DonutDuelsCommand
/*    */   implements CommandExecutor, TabCompleter
/*    */ {
/*    */   private final DonutDuels plugin;
/*    */   
/*    */   public DonutDuelsCommand(DonutDuels plugin) {
/* 22 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 27 */     if (!sender.hasPermission("donutduels.admin")) {
/* 28 */       sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDonutDuelsNoPermissionMessage()));
/* 29 */       return true;
/*    */     } 
/*    */     
/* 32 */     if (args.length < 1) {
/*    */       
/* 34 */       String message = this.plugin.getConfigManager().getDonutDuelsUsageMessage().replace("{label}", label);
/* 35 */       sender.sendMessage(ColorUtil.color(message));
/* 36 */       return true;
/*    */     } 
/*    */     
/* 39 */     String sub = args[0].toLowerCase();
/* 40 */     if (sub.equals("reload")) {
/* 41 */       long startTime = System.currentTimeMillis();
/* 42 */       this.plugin.reloadPlugin();
/* 43 */       long duration = System.currentTimeMillis() - startTime;
/*    */       
/* 45 */       String message = this.plugin.getConfigManager().getDonutDuelsReloadedMessage().replace("{duration}", String.valueOf(duration));
/* 46 */       sender.sendMessage(ColorUtil.color(message));
/* 47 */       if (sender instanceof Player) { Player player = (Player)sender;
/* 48 */         player.playSound(player.getLocation(), this.plugin.getConfigManager().getDonutDuelsReloadSuccessSound(), 1.0F, 1.0F); }
/*    */       
/* 50 */       return true;
/* 51 */     }  if (sub.equals("migrate")) {
/* 52 */       if (args.length != 2 || !args[1].equalsIgnoreCase("from old donutduels")) {
/*    */         
/* 54 */         String message = this.plugin.getConfigManager().getDonutDuelsMigrateUsageMessage().replace("{label}", label);
/* 55 */         sender.sendMessage(ColorUtil.color(message));
/* 56 */         return true;
/*    */       } 
/* 58 */       long startTime = System.currentTimeMillis();
/* 59 */       boolean success = this.plugin.getMigrationManager().migrateArenas();
/* 60 */       long duration = System.currentTimeMillis() - startTime;
/* 61 */       if (success) {
/* 62 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDonutDuelsMigrationSuccessMessage()));
/* 63 */         if (sender instanceof Player) { Player player = (Player)sender;
/* 64 */           player.playSound(player.getLocation(), this.plugin.getConfigManager().getDonutDuelsMigrationSuccessSound(), 1.0F, 1.0F); }
/*    */       
/*    */       } else {
/* 67 */         sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDonutDuelsMigrationErrorMessage()));
/*    */       } 
/* 69 */       return true;
/*    */     } 
/*    */     
/* 72 */     sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getDonutDuelsInvalidSubcommandMessage()));
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
/* 78 */     if (!sender.hasPermission("donutduels.admin")) {
/* 79 */       return Collections.emptyList();
/*    */     }
/*    */     
/* 82 */     if (args.length == 1)
/* 83 */       return (List<String>)Arrays.<String>asList(new String[] { "reload", "migrate" }).stream()
/* 84 */         .filter(s -> s.startsWith(args[0].toLowerCase()))
/* 85 */         .collect(Collectors.toList()); 
/* 86 */     if (args.length == 2 && args[0].equalsIgnoreCase("migrate")) {
/* 87 */       return Collections.singletonList("from old donutduels");
/*    */     }
/* 89 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\commands\DonutDuelsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */