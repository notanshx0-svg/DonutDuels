/*    */ package org.ms.donutduels.foliascheduler.bukkit;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.GlobalSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class BukkitSyncScheduler
/*    */   implements GlobalSchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   
/*    */   public BukkitSyncScheduler(@NotNull Plugin plugin) {
/* 18 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> BukkitRunnable buildBukkitRunnable(@NotNull final Function<TaskImplementation<T>, T> function, @NotNull final BukkitTask<T> taskImplementation) {
/* 25 */     return new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 28 */           taskImplementation.setCallback(function.apply(taskImplementation));
/* 29 */           taskImplementation.asFuture().complete(taskImplementation);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(@NotNull final Runnable run) {
/* 36 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 39 */           run.run();
/*    */         }
/* 41 */       }).runTask(this.plugin);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function) {
/* 46 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 47 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 48 */     taskImplementation.setScheduledTask(runnable.runTask(this.plugin));
/* 49 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay) {
/* 54 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 55 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 56 */     taskImplementation.setScheduledTask(runnable.runTaskLater(this.plugin, delay));
/* 57 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period) {
/* 62 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, true);
/* 63 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 64 */     taskImplementation.setScheduledTask(runnable.runTaskTimer(this.plugin, delay, period));
/* 65 */     return taskImplementation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancelTasks() {
/* 70 */     this.plugin.getServer().getScheduler().cancelTasks(this.plugin);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitSyncScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */