/*    */ package org.ms.donutduels.foliascheduler.bukkit;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.AsyncSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class BukkitAsyncScheduler
/*    */   implements AsyncSchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   
/*    */   public BukkitAsyncScheduler(@NotNull Plugin plugin) {
/* 19 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> BukkitRunnable buildBukkitRunnable(@NotNull final Function<TaskImplementation<T>, T> function, @NotNull final BukkitTask<T> taskImplementation) {
/* 26 */     return new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 29 */           taskImplementation.setCallback(function.apply(taskImplementation));
/* 30 */           taskImplementation.asFuture().complete(taskImplementation);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runNow(@NotNull Function<TaskImplementation<T>, T> function) {
/* 37 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 38 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 39 */     taskImplementation.setScheduledTask(runnable.runTaskAsynchronously(this.plugin));
/* 40 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay, @NotNull TimeUnit unit) {
/* 45 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 46 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 47 */     taskImplementation.setScheduledTask(runnable.runTaskLaterAsynchronously(this.plugin, unit.toSeconds(delay) * 20L));
/* 48 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period, @NotNull TimeUnit unit) {
/* 53 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, true);
/* 54 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 55 */     taskImplementation.setScheduledTask(runnable.runTaskTimerAsynchronously(this.plugin, unit.toSeconds(delay) * 20L, unit.toSeconds(period) * 20L));
/* 56 */     return taskImplementation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancelTasks() {
/* 61 */     this.plugin.getServer().getScheduler().cancelTasks(this.plugin);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitAsyncScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */