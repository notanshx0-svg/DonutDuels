/*    */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ public final class MappingNsRenamer
/*    */   extends ForwardingMappingVisitor
/*    */ {
/*    */   private final Map<String, String> nameMap;
/*    */   
/*    */   public MappingNsRenamer(MappingVisitor next, Map<String, String> nameMap) {
/* 36 */     super(next);
/*    */     
/* 38 */     Objects.requireNonNull(nameMap, "null name map");
/*    */     
/* 40 */     this.nameMap = nameMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/* 45 */     String newSrcNamespace = this.nameMap.getOrDefault(srcNamespace, srcNamespace);
/* 46 */     List<String> newDstNamespaces = new ArrayList<>(dstNamespaces.size());
/*    */     
/* 48 */     for (String ns : dstNamespaces) {
/* 49 */       newDstNamespaces.add(this.nameMap.getOrDefault(ns, ns));
/*    */     }
/*    */     
/* 52 */     super.visitNamespaces(newSrcNamespace, newDstNamespaces);
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\MappingNsRenamer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */