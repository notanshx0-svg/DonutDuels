/*    */ package org.ms.donutduels.foliascheduler.bukkit;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.RegionSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ public class BukkitRegionScheduler
/*    */   implements RegionSchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   
/*    */   public BukkitRegionScheduler(@NotNull Plugin plugin) {
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> BukkitRunnable buildBukkitRunnable(@NotNull final Function<TaskImplementation<T>, T> function, @NotNull final BukkitTask<T> taskImplementation) {
/* 23 */     return new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 26 */           taskImplementation.setCallback(function.apply(taskImplementation));
/* 27 */           taskImplementation.asFuture().complete(taskImplementation);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(@NotNull final Runnable run) {
/* 35 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 38 */           run.run();
/*    */         }
/* 40 */       }).runTask(this.plugin);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function) {
/* 45 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 46 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 47 */     taskImplementation.setScheduledTask(runnable.runTask(this.plugin));
/* 48 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay) {
/* 53 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 54 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 55 */     taskImplementation.setScheduledTask(runnable.runTaskLater(this.plugin, delay));
/* 56 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period) {
/* 61 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, true);
/* 62 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 63 */     taskImplementation.setScheduledTask(runnable.runTaskTimer(this.plugin, delay, period));
/* 64 */     return taskImplementation;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitRegionScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */