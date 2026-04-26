/*    */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
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
/*    */ public final class MappingDstNsReorder
/*    */   extends ForwardingMappingVisitor
/*    */ {
/*    */   private final List<String> newDstNs;
/*    */   private int[] nsMap;
/*    */   
/*    */   public MappingDstNsReorder(MappingVisitor next, List<String> newDstNs) {
/* 37 */     super(next);
/*    */     
/* 39 */     Objects.requireNonNull(newDstNs, "null newDstNs list");
/*    */     
/* 41 */     this.newDstNs = newDstNs;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MappingDstNsReorder(MappingVisitor next, String... newDstNs) {
/* 50 */     this(next, Arrays.asList(newDstNs));
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/* 55 */     this.nsMap = new int[dstNamespaces.size()];
/*    */     
/* 57 */     for (int i = 0; i < dstNamespaces.size(); i++) {
/* 58 */       this.nsMap[i] = this.newDstNs.indexOf(dstNamespaces.get(i));
/*    */     }
/*    */     
/* 61 */     super.visitNamespaces(srcNamespace, this.newDstNs);
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
/* 66 */     namespace = this.nsMap[namespace];
/*    */     
/* 68 */     if (namespace >= 0) {
/* 69 */       super.visitDstName(targetKind, namespace, name);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {
/* 75 */     namespace = this.nsMap[namespace];
/*    */     
/* 77 */     if (namespace >= 0)
/* 78 */       super.visitDstDesc(targetKind, namespace, desc); 
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\MappingDstNsReorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */