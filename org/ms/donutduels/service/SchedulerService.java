/*     */ package org.ms.donutduels.service;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.ms.donutduels.foliascheduler.FoliaCompatibility;
/*     */ import org.ms.donutduels.foliascheduler.ServerImplementation;
/*     */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchedulerService
/*     */ {
/*     */   private final ServerImplementation scheduler;
/*     */   private final Plugin plugin;
/*     */   
/*     */   public SchedulerService(Plugin plugin) {
/*  22 */     this.plugin = plugin;
/*  23 */     this.scheduler = (new FoliaCompatibility(plugin)).getServerImplementation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerImplementation getScheduler() {
/*  31 */     return this.scheduler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runTaskAsynchronously(Runnable task) {
/*  40 */     return this.scheduler.async().runNow(t -> {
/*     */           task.run();
/*     */           return null;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runTask(Runnable task) {
/*  52 */     return this.scheduler.global().run(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runTaskLater(Runnable task, long delayTicks) {
/*  62 */     return this.scheduler.global().runDelayed(t -> task.run(), delayTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runTaskTimer(Runnable task, long delayTicks, long periodTicks) {
/*  75 */     return this.scheduler.global().runAtFixedRate(t -> task.run(), delayTicks, periodTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runGlobalRepeating(Consumer<TaskImplementation<Void>> task, long delayTicks, long periodTicks) {
/*  88 */     return this.scheduler.global().runAtFixedRate(task, delayTicks, periodTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runTaskTimerAsynchronously(Runnable task, long delayTicks, long periodTicks) {
/*  99 */     return this.scheduler.async().runAtFixedRate(t -> task.run(), delayTicks, periodTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runEntityTask(Entity entity, Runnable task) {
/* 111 */     return this.scheduler.entity(entity).run(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runEntity(Entity entity, Consumer<TaskImplementation<Void>> task) {
/* 121 */     return this.scheduler.entity(entity).run(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runEntityTaskLater(Entity entity, Runnable task, long delayTicks) {
/* 132 */     return this.scheduler.entity(entity).runDelayed(t -> task.run(), delayTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> runLocationTask(Location location, Runnable task) {
/* 146 */     return runTask(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskImplementation<Void> createCountdown(Player player, int seconds, Consumer<Integer> onTick, Runnable onComplete, Runnable onCancel) {
/* 159 */     int[] remaining = { seconds };
/* 160 */     boolean[] cancelled = { false };
/*     */     
/* 162 */     return this.scheduler.entity((Entity)player).runAtFixedRate(task -> { if (cancelled[0]) { onCancel.run(); task.cancel(); return; }  if (remaining[0] <= 0) { onComplete.run(); task.cancel(); return; }  onTick.accept(Integer.valueOf(remaining[0])); remaining[0] = remaining[0] - 1; }0L, 20L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(Entity entity) {
/* 186 */     return this.scheduler.isOwnedByCurrentRegion(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(Location location) {
/* 195 */     return this.scheduler.isOwnedByCurrentRegion(location);
/*     */   }
/*     */   
/*     */   public void shutdown() {}
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\service\SchedulerService.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */