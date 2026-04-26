/*    */ package org.ms.donutduels.foliascheduler.reflectionremapper;
/*    */ 
/*    */ import org.checkerframework.checker.nullness.qual.NonNull;
/*    */ import org.checkerframework.framework.qual.DefaultQualifier;
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
/*    */ @DefaultQualifier(NonNull.class)
/*    */ final class NoopReflectionRemapper
/*    */   implements ReflectionRemapper
/*    */ {
/* 25 */   static NoopReflectionRemapper INSTANCE = new NoopReflectionRemapper();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String remapClassName(String className) {
/* 32 */     return className;
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapFieldName(Class<?> holdingClass, String fieldName) {
/* 37 */     return fieldName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapMethodName(Class<?> holdingClass, String methodName, Class<?>... paramTypes) {
/* 42 */     return methodName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapClassOrArrayName(String name) {
/* 47 */     return name;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\NoopReflectionRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */