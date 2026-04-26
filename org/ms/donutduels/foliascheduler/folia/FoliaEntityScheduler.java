/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.ms.donutduels.foliascheduler.EntitySchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class FoliaEntityScheduler implements EntitySchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   @NotNull
/*    */   private final EntityScheduler entityScheduler;
/*    */   
/*    */   @Internal
/*    */   public FoliaEntityScheduler(@NotNull Plugin plugin, @NotNull Entity entity) {
/* 24 */     this.plugin = plugin;
/* 25 */     this.entityScheduler = entity.getScheduler();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> Consumer<ScheduledTask> buildFoliaConsumer(@NotNull FoliaTask<T> taskImplementation, @NotNull Function<TaskImplementation<T>, T> callbackFunction) {
/* 32 */     return scheduledTask -> {
/*    */         taskImplementation.setScheduledTask(scheduledTask);
/*    */         taskImplementation.setCallback(callbackFunction.apply(taskImplementation));
/*    */         taskImplementation.asFuture().complete(taskImplementation);
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@NotNull Runnable run, @Nullable Runnable retired, long delay) {
/* 41 */     return this.entityScheduler.execute(this.plugin, run, retired, delay);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired) {
/* 46 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 47 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 48 */     ScheduledTask scheduledTask = this.entityScheduler.run(this.plugin, foliaConsumer, retired);
/*    */ 
/*    */     
/* 51 */     if (scheduledTask == null) {
/* 52 */       return null;
/*    */     }
/* 54 */     taskImplementation.setScheduledTask(scheduledTask);
/* 55 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired, long delay) {
/* 60 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 61 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 62 */     ScheduledTask scheduledTask = this.entityScheduler.runDelayed(this.plugin, foliaConsumer, retired, delay);
/*    */ 
/*    */     
/* 65 */     if (scheduledTask == null) {
/* 66 */       return null;
/*    */     }
/* 68 */     taskImplementation.setScheduledTask(scheduledTask);
/* 69 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, @Nullable Runnable retired, long delay, long period) {
/* 74 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 75 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 76 */     ScheduledTask scheduledTask = this.entityScheduler.runAtFixedRate(this.plugin, foliaConsumer, retired, delay, period);
/*    */ 
/*    */     
/* 79 */     if (scheduledTask == null) {
/* 80 */       return null;
/*    */     }
/* 82 */     taskImplementation.setScheduledTask(scheduledTask);
/* 83 */     return taskImplementation;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaEntityScheduler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */