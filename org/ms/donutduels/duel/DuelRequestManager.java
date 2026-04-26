/*    */ package org.ms.donutduels.duel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import net.md_5.bungee.api.chat.BaseComponent;
/*    */ import net.md_5.bungee.api.chat.ClickEvent;
/*    */ import net.md_5.bungee.api.chat.TextComponent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.ms.donutduels.DonutDuels;
/*    */ import org.ms.donutduels.gui.DuelAcceptGUI;
/*    */ import org.ms.donutduels.model.DuelRequest;
/*    */ import org.ms.donutduels.utils.ColorUtil;
/*    */ 
/*    */ public class DuelRequestManager {
/* 18 */   private final Map<UUID, List<DuelRequest>> pendingRequests = new ConcurrentHashMap<>(); private final DonutDuels plugin;
/*    */   
/*    */   public DuelRequestManager(DonutDuels plugin) {
/* 21 */     this.plugin = plugin;
/*    */ 
/*    */     
/* 24 */     plugin.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)plugin, this::cleanupExpiredRequests, 200L, 200L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendDuelRequest(Player sender, Player target, String arena, int durationMinutes) {
/* 29 */     DuelRequest request = new DuelRequest(sender, target, arena, durationMinutes);
/*    */ 
/*    */     
/* 32 */     ((List<DuelRequest>)this.pendingRequests.computeIfAbsent(target.getUniqueId(), k -> new ArrayList())).add(request);
/*    */ 
/*    */     
/* 35 */     sender.sendMessage(ColorUtil.color("&7You sent a duel to &b" + target.getName()));
/* 36 */     sender.sendActionBar(ColorUtil.color("&7You sent a duel to &b" + target.getName()));
/*    */ 
/*    */     
/* 39 */     sendTargetNotification(target, request);
/*    */   }
/*    */ 
/*    */   
/*    */   private void sendTargetNotification(Player target, DuelRequest request) {
/* 44 */     TextComponent message = new TextComponent(ColorUtil.color("&b" + request.getSenderName() + " &7invited you to duel on " + request.getArena()));
/*    */ 
/*    */     
/* 47 */     TextComponent lineBreak = new TextComponent("\n");
/* 48 */     message.addExtra((BaseComponent)lineBreak);
/*    */     
/* 50 */     TextComponent clickToAccept = new TextComponent(ColorUtil.color("&b[CLICK TO ACCEPT]"));
/* 51 */     clickToAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + request.getSenderName()));
/*    */     
/* 53 */     message.addExtra((BaseComponent)clickToAccept);
/*    */ 
/*    */     
/* 56 */     TextComponent orType = new TextComponent(ColorUtil.color(" &7or type &b/duel accept " + request.getSenderName()));
/* 57 */     message.addExtra((BaseComponent)orType);
/*    */     
/* 59 */     target.spigot().sendMessage((BaseComponent)message);
/*    */ 
/*    */     
/* 62 */     target.sendActionBar(ColorUtil.color("&b[CLICK TO ACCEPT] &7or type &b/duel accept " + request.getSenderName()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DuelRequest getPendingRequestBySender(String senderName, UUID targetId) {
/* 69 */     List<DuelRequest> requests = this.pendingRequests.get(targetId);
/* 70 */     if (requests == null) return null;
/*    */     
/* 72 */     return requests.stream()
/* 73 */       .filter(request -> (request.getSenderName().equalsIgnoreCase(senderName) && !request.isExpired()))
/* 74 */       .findFirst()
/* 75 */       .orElse(null);
/*    */   }
/*    */   
/*    */   public void openAcceptGUI(Player target, DuelRequest request) {
/* 79 */     DuelAcceptGUI acceptGUI = new DuelAcceptGUI(this.plugin, target, request);
/* 80 */     acceptGUI.openGUI();
/*    */   }
/*    */   
/*    */   public void removeRequest(DuelRequest request) {
/* 84 */     List<DuelRequest> requests = this.pendingRequests.get(request.getTargetId());
/* 85 */     if (requests != null) {
/* 86 */       requests.remove(request);
/* 87 */       if (requests.isEmpty()) {
/* 88 */         this.pendingRequests.remove(request.getTargetId());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void cleanupExpiredRequests() {
/* 94 */     this.pendingRequests.values().forEach(requests -> requests.removeIf(DuelRequest::isExpired));
/*    */ 
/*    */ 
/*    */     
/* 98 */     this.pendingRequests.entrySet().removeIf(entry -> ((List)entry.getValue()).isEmpty());
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\duel\DuelRequestManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */