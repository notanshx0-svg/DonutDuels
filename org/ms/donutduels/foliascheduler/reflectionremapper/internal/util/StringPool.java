/*    */ package org.ms.donutduels.foliascheduler.reflectionremapper.internal.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
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
/*    */ @DefaultQualifier(NonNull.class)
/*    */ public final class StringPool
/*    */ {
/*    */   private final Map<String, String> pool;
/*    */   
/*    */   public StringPool(Map<String, String> backingMap) {
/* 31 */     this.pool = backingMap;
/*    */   }
/*    */   
/*    */   public StringPool() {
/* 35 */     this(new HashMap<>());
/*    */   }
/*    */   
/*    */   public String string(String string) {
/* 39 */     return this.pool.computeIfAbsent(string, (Function)Function.identity());
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\reflectionremapper\interna\\util\StringPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */