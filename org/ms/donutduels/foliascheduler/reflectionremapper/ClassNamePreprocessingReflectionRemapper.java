/*    */ package org.ms.donutduels.foliascheduler.reflectionremapper;
/*    */ 
/*    */ import java.util.function.UnaryOperator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @DefaultQualifier(NonNull.class)
/*    */ final class ClassNamePreprocessingReflectionRemapper
/*    */   implements ReflectionRemapper
/*    */ {
/*    */   private final ReflectionRemapper delegate;
/*    */   private final UnaryOperator<String> processor;
/*    */   
/*    */   ClassNamePreprocessingReflectionRemapper(ReflectionRemapper delegate, UnaryOperator<String> processor) {
/* 33 */     this.delegate = delegate;
/* 34 */     this.processor = processor;
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapClassName(String className) {
/* 39 */     return this.delegate.remapClassName(this.processor.apply(className));
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapFieldName(Class<?> holdingClass, String fieldName) {
/* 44 */     return this.delegate.remapFieldName(holdingClass, fieldName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String remapMethodName(Class<?> holdingClass, String methodName, Class<?>... paramTypes) {
/* 49 */     return this.delegate.remapMethodName(holdingClass, methodName, paramTypes);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\ClassNamePreprocessingReflectionRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */