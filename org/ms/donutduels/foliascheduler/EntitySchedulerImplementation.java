/*     */ package org.ms.donutduels.foliascheduler;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public interface EntitySchedulerImplementation
/*     */ {
/*     */   default boolean execute(@NotNull Runnable run) {
/*  33 */     return execute(run, null, 1L);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> run(@NotNull Consumer<TaskImplementation<Void>> consumer, @Nullable Runnable retired) {
/*  54 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/*  58 */     return run(wrapperFunction, retired);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TaskImplementation<Void> run(@NotNull Runnable runnable, @Nullable Runnable retired) {
/*  69 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/*  73 */     return run(wrapperFunction, retired);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> function) {
/*  84 */     return run(function, (Runnable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TaskImplementation<Void> run(@NotNull Consumer<TaskImplementation<Void>> consumer) {
/*  95 */     return run(consumer, (Runnable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TaskImplementation<Void> run(@NotNull Runnable runnable) {
/* 106 */     return run(runnable, (Runnable)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Consumer<TaskImplementation<Void>> consumer, @Nullable Runnable retired, long delay) {
/* 138 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/* 142 */     return runDelayed(wrapperFunction, retired, delay);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Runnable runnable, @Nullable Runnable retired, long delay) {
/* 159 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 163 */     return runDelayed(wrapperFunction, retired, delay);
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
/*     */   @Nullable
/*     */   default <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> function, long delay) {
/* 178 */     return runDelayed(function, (Runnable)null, delay);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay) {
/* 193 */     return runDelayed(consumer, (Runnable)null, delay);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runDelayed(@NotNull Runnable runnable, long delay) {
/* 208 */     return runDelayed(runnable, (Runnable)null, delay);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Consumer<TaskImplementation<Void>> consumer, @Nullable Runnable retired, long delay, long period) {
/* 244 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         consumer.accept(task);
/*     */         return null;
/*     */       };
/* 248 */     return runAtFixedRate(wrapperFunction, retired, delay, period);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Runnable runnable, @Nullable Runnable retired, long delay, long period) {
/* 267 */     Function<TaskImplementation<Void>, Void> wrapperFunction = task -> {
/*     */         runnable.run();
/*     */         return null;
/*     */       };
/* 271 */     return runAtFixedRate(wrapperFunction, retired, delay, period);
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
/*     */   @Nullable
/*     */   default <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> function, long delay, long period) {
/* 288 */     return runAtFixedRate(function, (Runnable)null, delay, period);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Consumer<TaskImplementation<Void>> consumer, long delay, long period) {
/* 305 */     return runAtFixedRate(consumer, (Runnable)null, delay, period);
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
/*     */   @Nullable
/*     */   default TaskImplementation<Void> runAtFixedRate(@NotNull Runnable runnable, long delay, long period) {
/* 322 */     return runAtFixedRate(runnable, (Runnable)null, delay, period);
/*     */   }
/*     */   
/*     */   boolean execute(@NotNull Runnable paramRunnable1, @Nullable Runnable paramRunnable2, long paramLong);
/*     */   
/*     */   @Nullable
/*     */   <T> TaskImplementation<T> run(@NotNull Function<TaskImplementation<T>, T> paramFunction, @Nullable Runnable paramRunnable);
/*     */   
/*     */   @Nullable
/*     */   <T> TaskImplementation<T> runDelayed(@NotNull Function<TaskImplementation<T>, T> paramFunction, @Nullable Runnable paramRunnable, long paramLong);
/*     */   
/*     */   @Nullable
/*     */   <T> TaskImplementation<T> runAtFixedRate(@NotNull Function<TaskImplementation<T>, T> paramFunction, @Nullable Runnable paramRunnable, long paramLong1, long paramLong2);
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\EntitySchedulerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */