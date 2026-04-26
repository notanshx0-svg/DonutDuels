/*    */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
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
/*    */ public final class MissingDescFilter
/*    */   extends ForwardingMappingVisitor
/*    */ {
/*    */   public MissingDescFilter(MappingVisitor next) {
/* 30 */     super(next);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 35 */     if (srcDesc == null) return false;
/*    */     
/* 37 */     return super.visitField(srcName, srcDesc);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 42 */     if (srcDesc == null) return false;
/*    */     
/* 44 */     return super.visitMethod(srcName, srcDesc);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\MissingDescFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */