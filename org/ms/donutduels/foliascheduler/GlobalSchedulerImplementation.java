/*     */ package org.ms.donutduels.foliascheduler;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface GlobalSchedulerImplementation
/*     */ {
/*     */   @NotNull
/*     */   default TaskImplementation<Void> run(@NotNull Consumer<TaskImplementation<Void>> consumer) {
/*  36 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  40 */     return run(wrapperFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> run(@NotNull Runnable runnable) {
/*  50 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/*  54 */     return run(wrapperFunction);
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
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay) {
/*  81 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  85 */     return runDelayed(wrapperFunction, delay);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Runnable runnable, long delay) {
/*  99 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 103 */     return runDelayed(wrapperFunction, delay);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay, long period) {
/* 131 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/* 135 */     return runAtFixedRate(wrapperFunction, delay, period);
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
/*     */   @NotNull
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Runnable runnable, long delay, long period) {
/* 151 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 155 */     return runAtFixedRate(wrapperFunction, delay, period);
/*     */   }
/*     */   
/*     */   void execute(@NotNull Runnable paramRunnable);
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> paramFunction);
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> paramFunction, long paramLong);
/*     */   
/*     */   @NotNull
/*     */   <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> paramFunction, long paramLong1, long paramLong2);
/*     */   
/*     */   void cancelTasks();
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\GlobalSchedulerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */