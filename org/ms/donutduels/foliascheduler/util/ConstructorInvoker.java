/*    */ package org.ms.donutduels.foliascheduler.util;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public class ConstructorInvoker<T>
/*    */ {
/*    */   @NotNull
/*    */   private final Constructor<T> constructor;
/*    */   
/*    */   public ConstructorInvoker(@NotNull Constructor<T> constructor) {
/* 24 */     this.constructor = constructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Constructor<T> getConstructor() {
/* 33 */     return this.constructor;
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
/*    */   @NotNull
/*    */   public T newInstance(Object... initargs) throws IllegalArgumentException {
/*    */     try {
/* 65 */       return this.constructor.newInstance(initargs);
/* 66 */     } catch (ReflectiveOperationException e) {
/* 67 */       throw new WrappedReflectiveOperationException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliaschedule\\util\ConstructorInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */