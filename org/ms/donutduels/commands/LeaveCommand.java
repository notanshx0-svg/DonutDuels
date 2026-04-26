/*    */ package org.ms.donutduels.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ import org.ms.donutduels.utils.ColorUtil;
/*    */ 
/*    */ public class LeaveCommand implements CommandExecutor {
/*    */   private final DonutDuels plugin;
/*    */   
/*    */   public LeaveCommand(DonutDuels plugin) {
/* 14 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*    */     Player player;
/* 19 */     if (sender instanceof Player) { player = (Player)sender; }
/* 20 */     else { sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getLeaveOnlyPlayersMessage()));
/* 21 */       return true; }
/*    */ 
/*    */     
/* 24 */     if (!this.plugin.getDuelManager().isInDuel(player) && !this.plugin.getDuelManager().isSpectator(player)) {
/* 25 */       player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getLeaveNotInDuelMessage()));
/* 26 */       return true;
/*    */     } 
/*    */     
/* 29 */     this.plugin.getDuelManager().leaveDuel(player);
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\commands\LeaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */