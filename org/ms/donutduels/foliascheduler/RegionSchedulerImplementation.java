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
/*     */ public interface RegionSchedulerImplementation
/*     */ {
/*     */   @NotNull
/*     */   default TaskImplementation<Void> run(@NotNull Consumer<TaskImplementation<Void>> consumer) {
/*  35 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  39 */     return run(wrapperFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   default TaskImplementation<Void> run(@NotNull Runnable runnable) {
/*  49 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/*  53 */     return run(wrapperFunction);
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
/*  80 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  84 */     return runDelayed(wrapperFunction, delay);
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
/*  98 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 102 */     return runDelayed(wrapperFunction, delay);
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
/* 130 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/* 134 */     return runAtFixedRate(wrapperFunction, delay, period);
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
/* 150 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 154 */     return runAtFixedRate(wrapperFunction, delay, period);
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
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\RegionSchedulerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */