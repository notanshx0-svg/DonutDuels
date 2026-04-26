/*    */ package org.ms.donutduels.foliascheduler.folia;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.ms.donutduels.foliascheduler.AsyncSchedulerImplementation;
/*    */ import org.ms.donutduels.foliascheduler.TaskImplementation;
/*    */ 
/*    */ @Internal
/*    */ public class FoliaAsyncScheduler implements AsyncSchedulerImplementation {
/*    */   @NotNull
/*    */   private final Plugin plugin;
/*    */   @NotNull
/*    */   private final AsyncScheduler asyncScheduler;
/*    */   
/*    */   @Internal
/*    */   public FoliaAsyncScheduler(@NotNull Plugin plugin) {
/* 23 */     this.plugin = plugin;
/* 24 */     this.asyncScheduler = plugin.getServer().getAsyncScheduler();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private <T> Consumer<ScheduledTask> buildFoliaConsumer(@NotNull FoliaTask<T> taskImplementation, @NotNull Function<TaskImplementation<T>, T> callbackFunction) {
/* 31 */     return scheduledTask -> {
/*    */         taskImplementation.setScheduledTask(scheduledTask);
/*    */         taskImplementation.setCallback(callbackFunction.apply(taskImplementation));
/*    */         taskImplementation.asFuture().complete(taskImplementation);
/*    */       };
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runNow(@NotNull Function<TaskImplementation<T>, T> function) {
/* 40 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 41 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 42 */     ScheduledTask scheduledTask = this.asyncScheduler.runNow(this.plugin, foliaConsumer);
/* 43 */     taskImplementation.setScheduledTask(scheduledTask);
/* 44 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay, @NotNull TimeUnit unit) {
/* 49 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 50 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 51 */     ScheduledTask scheduledTask = this.asyncScheduler.runDelayed(this.plugin, foliaConsumer, delay, unit);
/* 52 */     taskImplementation.setScheduledTask(scheduledTask);
/* 53 */     return taskImplementation;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period, @NotNull TimeUnit unit) {
/* 58 */     FoliaTask<T> taskImplementation = new FoliaTask<>();
/* 59 */     Consumer<ScheduledTask> foliaConsumer = buildFoliaConsumer(taskImplementation, function);
/* 60 */     ScheduledTask scheduledTask = this.asyncScheduler.runAtFixedRate(this.plugin, foliaConsumer, delay, period, unit);
/* 61 */     taskImplementation.setScheduledTask(scheduledTask);
/* 62 */     return taskImplementation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancelTasks() {
/* 67 */     this.asyncScheduler.cancelTasks(this.plugin);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\folia\FoliaAsyncScheduler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */