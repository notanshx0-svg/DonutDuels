/*    */ package org.ms.donutduels.foliascheduler.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodInvoker
/*    */ {
/*    */   @NotNull
/*    */   private final Method method;
/*    */   
/*    */   public MethodInvoker(@NotNull Method method) {
/* 23 */     this.method = method;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Method getMethod() {
/* 32 */     return this.method;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Object invoke(@Nullable Object obj, Object... args) throws IllegalArgumentException {
/*    */     try {
/* 69 */       return this.method.invoke(obj, args);
/* 70 */     } catch (ReflectiveOperationException e) {
/* 71 */       throw new WrappedReflectiveOperationException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\MethodInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */