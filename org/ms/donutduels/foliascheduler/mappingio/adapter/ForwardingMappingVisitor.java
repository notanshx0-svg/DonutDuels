/*     */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
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
/*     */ public abstract class ForwardingMappingVisitor
/*     */   implements MappingVisitor
/*     */ {
/*     */   protected final MappingVisitor next;
/*     */   
/*     */   protected ForwardingMappingVisitor(MappingVisitor next) {
/*  37 */     Objects.requireNonNull(next, "null next");
/*     */     
/*  39 */     this.next = next;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  44 */     return this.next.getFlags();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  49 */     this.next.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitHeader() throws IOException {
/*  54 */     return this.next.visitHeader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  59 */     this.next.visitNamespaces(srcNamespace, dstNamespaces);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*  64 */     this.next.visitMetadata(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitContent() throws IOException {
/*  69 */     return this.next.visitContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  74 */     return this.next.visitClass(srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*  79 */     return this.next.visitField(srcName, srcDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*  84 */     return this.next.visitMethod(srcName, srcDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*  89 */     return this.next.visitMethodArg(argPosition, lvIndex, srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*  94 */     return this.next.visitMethodVar(lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitEnd() throws IOException {
/*  99 */     return this.next.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
/* 104 */     this.next.visitDstName(targetKind, namespace, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {
/* 109 */     this.next.visitDstDesc(targetKind, namespace, desc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 114 */     return this.next.visitElementContent(targetKind);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {
/* 119 */     this.next.visitComment(targetKind, comment);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\ForwardingMappingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */