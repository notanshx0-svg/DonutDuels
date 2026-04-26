/*    */ package org.ms.donutduels.foliascheduler.bukkit;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.ms.donutduels.foliascheduler.EntitySchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class BukkitEntityScheduler implements EntitySchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   @NotNull
/*    */   private final Entity entity;
/*    */   
/*    */   @Internal
/*    */   public BukkitEntityScheduler(@NotNull Plugin plugin, @NotNull Entity entity) {
/* 22 */     this.plugin = plugin;
/* 23 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> BukkitRunnable buildBukkitRunnable(@NotNull final Function<TaskImplementation<T>, T> function, @NotNull final BukkitTask<T> taskImplementation) {
/* 30 */     return new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 33 */           taskImplementation.setCallback(function.apply(taskImplementation));
/* 34 */           taskImplementation.asFuture().complete(taskImplementation);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   private boolean isRetired(@Nullable Runnable retired) {
/* 40 */     if (!this.entity.isValid()) {
/* 41 */       if (retired != null)
/* 42 */         retired.run(); 
/* 43 */       return true;
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@NotNull final Runnable run, @Nullable final Runnable retired, long delay) {
/* 50 */     if (isRetired(retired)) {
/* 51 */       return false;
/*    */     }
/* 53 */     (new BukkitRunnable()
/*    */       {
/*    */         public void run() {
/* 56 */           if (BukkitEntityScheduler.this.isRetired(retired))
/*    */             return; 
/* 58 */           run.run();
/*    */         }
/* 60 */       }).runTaskLater(this.plugin, delay);
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired) {
/* 66 */     if (isRetired(retired)) {
/* 67 */       return null;
/*    */     }
/* 69 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 70 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 71 */     taskImplementation.setScheduledTask(runnable.runTask(this.plugin));
/* 72 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired, long delay) {
/* 77 */     if (isRetired(retired)) {
/* 78 */       return null;
/*    */     }
/* 80 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, false);
/* 81 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 82 */     taskImplementation.setScheduledTask(runnable.runTaskLater(this.plugin, delay));
/* 83 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired, long delay, long period) {
/* 88 */     if (isRetired(retired)) {
/* 89 */       return null;
/*    */     }
/* 91 */     BukkitTask<T> taskImplementation = new BukkitTask<>(this.plugin, true);
/* 92 */     BukkitRunnable runnable = buildBukkitRunnable(function, taskImplementation);
/* 93 */     taskImplementation.setScheduledTask(runnable.runTaskTimer(this.plugin, delay, period));
/* 94 */     return taskImplementation;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\bukkit\BukkitEntityScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */