/*     */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.FlatMappingVisitor;
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
/*     */ public final class FlatAsRegularMappingVisitor
/*     */   implements MappingVisitor
/*     */ {
/*     */   private final FlatMappingVisitor next;
/*     */   private String srcClsName;
/*     */   private String srcMemberName;
/*     */   private String srcMemberDesc;
/*     */   private String srcMemberSubName;
/*     */   private int argIdx;
/*     */   private int lvIndex;
/*     */   private int startOpIdx;
/*     */   private int endOpIdx;
/*     */   private String[] dstNames;
/*     */   private String[] dstClassNames;
/*     */   private String[] dstMemberNames;
/*     */   private String[] dstMemberDescs;
/*     */   
/*     */   public FlatAsRegularMappingVisitor(FlatMappingVisitor out) {
/*  40 */     this.next = out;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  45 */     return this.next.getFlags();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  50 */     this.next.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitHeader() throws IOException {
/*  55 */     return this.next.visitHeader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  60 */     this.next.visitNamespaces(srcNamespace, dstNamespaces);
/*     */     
/*  62 */     int count = dstNamespaces.size();
/*  63 */     this.dstNames = new String[count];
/*  64 */     Set<MappingFlag> flags = this.next.getFlags();
/*     */     
/*  66 */     if (flags.contains(MappingFlag.NEEDS_ELEMENT_UNIQUENESS)) {
/*  67 */       this.dstClassNames = new String[count];
/*  68 */       this.dstMemberNames = new String[count];
/*     */     } else {
/*  70 */       this.dstClassNames = this.dstMemberNames = null;
/*     */     } 
/*     */     
/*  73 */     this.dstMemberDescs = (flags.contains(MappingFlag.NEEDS_DST_FIELD_DESC) || flags.contains(MappingFlag.NEEDS_DST_METHOD_DESC)) ? new String[count] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*  78 */     this.next.visitMetadata(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitContent() throws IOException {
/*  83 */     return this.next.visitContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) {
/*  88 */     this.srcClsName = srcName;
/*     */     
/*  90 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*  91 */     if (this.dstClassNames != null) Arrays.fill((Object[])this.dstClassNames, (Object)null);
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) {
/*  98 */     this.srcMemberName = srcName;
/*  99 */     this.srcMemberDesc = srcDesc;
/*     */     
/* 101 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/* 102 */     if (this.dstMemberNames != null) Arrays.fill((Object[])this.dstMemberNames, (Object)null); 
/* 103 */     if (this.dstMemberDescs != null) Arrays.fill((Object[])this.dstMemberDescs, (Object)null);
/*     */     
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) {
/* 110 */     this.srcMemberName = srcName;
/* 111 */     this.srcMemberDesc = srcDesc;
/*     */     
/* 113 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/* 114 */     if (this.dstMemberNames != null) Arrays.fill((Object[])this.dstMemberNames, (Object)null); 
/* 115 */     if (this.dstMemberDescs != null) Arrays.fill((Object[])this.dstMemberDescs, (Object)null);
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) {
/* 122 */     this.srcMemberSubName = srcName;
/* 123 */     this.argIdx = argPosition;
/* 124 */     this.lvIndex = lvIndex;
/*     */     
/* 126 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) {
/* 133 */     this.srcMemberSubName = srcName;
/* 134 */     this.argIdx = lvtRowIndex;
/* 135 */     this.lvIndex = lvIndex;
/* 136 */     this.startOpIdx = startOpIdx;
/* 137 */     this.endOpIdx = endOpIdx;
/*     */     
/* 139 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*     */     
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitEnd() throws IOException {
/* 146 */     return this.next.visitEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/* 151 */     this.dstNames[namespace] = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) {
/* 156 */     if (this.dstMemberDescs != null) this.dstMemberDescs[namespace] = desc;
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     boolean relay;
/* 163 */     switch (targetKind) {
/*     */       case CLASS:
/* 165 */         relay = this.next.visitClass(this.srcClsName, this.dstNames);
/* 166 */         if (relay && this.dstClassNames != null) System.arraycopy(this.dstNames, 0, this.dstClassNames, 0, this.dstNames.length);
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
/* 190 */         return relay;case FIELD: relay = this.next.visitField(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.dstClassNames, this.dstNames, this.dstMemberDescs); if (relay && this.dstMemberNames != null) System.arraycopy(this.dstNames, 0, this.dstMemberNames, 0, this.dstNames.length);  return relay;case METHOD: relay = this.next.visitMethod(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.dstClassNames, this.dstNames, this.dstMemberDescs); if (relay && this.dstMemberNames != null) System.arraycopy(this.dstNames, 0, this.dstMemberNames, 0, this.dstNames.length);  return relay;case METHOD_ARG: relay = this.next.visitMethodArg(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.argIdx, this.lvIndex, this.srcMemberSubName, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, this.dstNames); return relay;case METHOD_VAR: relay = this.next.visitMethodVar(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.argIdx, this.lvIndex, this.startOpIdx, this.endOpIdx, this.srcMemberSubName, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, this.dstNames); return relay;
/*     */     } 
/*     */     throw new IllegalStateException();
/*     */   }
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {
/* 195 */     switch (targetKind) {
/*     */       case CLASS:
/* 197 */         this.next.visitClassComment(this.srcClsName, this.dstClassNames, comment);
/*     */         break;
/*     */       case FIELD:
/* 200 */         this.next.visitFieldComment(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, comment);
/*     */         break;
/*     */       
/*     */       case METHOD:
/* 204 */         this.next.visitMethodComment(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, comment);
/*     */         break;
/*     */       
/*     */       case METHOD_ARG:
/* 208 */         this.next.visitMethodArgComment(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.argIdx, this.lvIndex, this.srcMemberSubName, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, this.dstNames, comment);
/*     */         break;
/*     */       
/*     */       case METHOD_VAR:
/* 212 */         this.next.visitMethodVarComment(this.srcClsName, this.srcMemberName, this.srcMemberDesc, this.argIdx, this.lvIndex, this.startOpIdx, this.endOpIdx, this.srcMemberSubName, this.dstClassNames, this.dstMemberNames, this.dstMemberDescs, this.dstNames, comment);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\FlatAsRegularMappingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */