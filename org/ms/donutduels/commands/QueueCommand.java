/*    */ package org.ms.donutduels.commands;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ import org.ms.donutduels.gui.QueueGUI;
/*    */ import org.ms.donutduels.utils.ColorUtil;
/*    */ 
/*    */ public class QueueCommand
/*    */   implements CommandExecutor {
/*    */   private final DonutDuels plugin;
/*    */   
/*    */   public QueueCommand(DonutDuels plugin) {
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 21 */     if (!(sender instanceof Player)) {
/* 22 */       sender.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getQueueOnlyPlayersMessage()));
/* 23 */       return true;
/*    */     } 
/*    */     
/* 26 */     Player player = (Player)sender;
/*    */ 
/*    */     
/* 29 */     if (this.plugin.getQueueManager().isPlayerInQueue(player)) {
/*    */       
/* 31 */       this.plugin.getQueueManager().removePlayerFromQueue(player);
/*    */ 
/*    */       
/* 34 */       QueueGUI.stopSearchForPlayer(player.getUniqueId());
/*    */ 
/*    */       
/* 37 */       player.sendMessage(ColorUtil.color(this.plugin.getConfigManager().getQueueLeftQueueChatMessage()));
/* 38 */       player.sendActionBar(ColorUtil.color(this.plugin.getConfigManager().getQueueLeftQueueActionBarMessage()));
/*    */ 
/*    */       
/* 41 */       player.sendActionBar("");
/*    */     } else {
/*    */       
/* 44 */       QueueGUI queueGUI = new QueueGUI(this.plugin, player);
/* 45 */       queueGUI.openGUI();
/*    */     } 
/*    */     
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\commands\QueueCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */