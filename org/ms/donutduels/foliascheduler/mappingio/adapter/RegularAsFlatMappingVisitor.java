/*     */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public final class RegularAsFlatMappingVisitor
/*     */   implements FlatMappingVisitor
/*     */ {
/*     */   private final MappingVisitor next;
/*     */   private boolean relayDstFieldDescs;
/*     */   private boolean relayDstMethodDescs;
/*     */   private String lastClass;
/*     */   private boolean relayLastClass;
/*     */   private String lastMemberName;
/*     */   private String lastMemberDesc;
/*     */   private boolean lastMemberIsField;
/*     */   private boolean relayLastMember;
/*     */   private int lastArgPosition;
/*     */   private int lastLvIndex;
/*     */   private int lastStartOpIdx;
/*     */   private boolean lastMethodSubIsArg;
/*     */   private boolean relayLastMethodSub;
/*     */   
/*     */   public RegularAsFlatMappingVisitor(MappingVisitor next) {
/*  35 */     this.next = next;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  40 */     return this.next.getFlags();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  45 */     this.lastClass = null;
/*  46 */     this.lastMemberName = this.lastMemberDesc = null;
/*  47 */     this.lastArgPosition = this.lastLvIndex = this.lastStartOpIdx = -1;
/*     */     
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
/*     */     
/*  61 */     Set<MappingFlag> flags = this.next.getFlags();
/*  62 */     this.relayDstFieldDescs = flags.contains(MappingFlag.NEEDS_DST_FIELD_DESC);
/*  63 */     this.relayDstMethodDescs = flags.contains(MappingFlag.NEEDS_DST_METHOD_DESC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*  68 */     this.next.visitMetadata(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitContent() throws IOException {
/*  73 */     return this.next.visitContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName, String[] dstNames) throws IOException {
/*  78 */     return visitClass(srcName, dstNames, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName, String dstName) throws IOException {
/*  83 */     return visitClass(srcName, null, dstName);
/*     */   }
/*     */   
/*     */   private boolean visitClass(String srcName, @Nullable String[] dstNames, @Nullable String dstName) throws IOException {
/*  87 */     if (!srcName.equals(this.lastClass)) {
/*  88 */       this.lastClass = srcName;
/*  89 */       this.lastMemberName = this.lastMemberDesc = null;
/*  90 */       this.lastArgPosition = this.lastLvIndex = this.lastStartOpIdx = -1;
/*  91 */       this.relayLastClass = (this.next.visitClass(srcName) && visitDstNames(MappedElementKind.CLASS, dstNames, dstName));
/*     */     } 
/*     */     
/*  94 */     return this.relayLastClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassComment(String srcName, @Nullable String[] dstNames, String comment) throws IOException {
/*  99 */     if (!visitClass(srcName, dstNames, null))
/* 100 */       return;  this.next.visitComment(MappedElementKind.CLASS, comment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitClassComment(String srcName, @Nullable String dstName, String comment) throws IOException {
/* 105 */     if (!visitClass(srcName, null, dstName))
/* 106 */       return;  this.next.visitComment(MappedElementKind.CLASS, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, String[] dstNames, @Nullable String[] dstDescs) throws IOException {
/* 112 */     return visitField(srcClsName, srcName, srcDesc, dstClsNames, dstNames, dstDescs, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, String dstName, @Nullable String dstDesc) throws IOException {
/* 118 */     return visitField(srcClsName, srcName, srcDesc, null, null, null, dstClsName, dstName, dstDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean visitField(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, @Nullable String[] dstNames, @Nullable String[] dstDescs, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc) throws IOException {
/* 124 */     if (!visitClass(srcClsName, dstClsNames, dstClsName)) return false;
/*     */     
/* 126 */     if (!this.lastMemberIsField || !srcName.equals(this.lastMemberName) || (srcDesc != null && !srcDesc.equals(this.lastMemberDesc))) {
/* 127 */       this.lastMemberName = srcName;
/* 128 */       this.lastMemberDesc = srcDesc;
/* 129 */       this.lastMemberIsField = true;
/* 130 */       this.lastArgPosition = this.lastLvIndex = this.lastStartOpIdx = -1;
/* 131 */       this.relayLastMember = (this.next.visitField(srcName, srcDesc) && visitDstNamesDescs(MappedElementKind.FIELD, dstNames, dstDescs, dstName, dstDesc));
/*     */     } 
/*     */     
/* 134 */     return this.relayLastMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, @Nullable String[] dstNames, @Nullable String[] dstDescs, String comment) throws IOException {
/* 141 */     if (!visitField(srcClsName, srcName, srcDesc, dstClsNames, dstNames, dstDescs, null, null, null))
/* 142 */       return;  this.next.visitComment(MappedElementKind.FIELD, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc, String comment) throws IOException {
/* 149 */     if (!visitField(srcClsName, srcName, srcDesc, null, null, null, dstClsName, dstName, dstDesc))
/* 150 */       return;  this.next.visitComment(MappedElementKind.FIELD, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, String[] dstNames, @Nullable String[] dstDescs) throws IOException {
/* 156 */     return visitMethod(srcClsName, srcName, srcDesc, dstClsNames, dstNames, dstDescs, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, String dstName, @Nullable String dstDesc) throws IOException {
/* 162 */     return visitMethod(srcClsName, srcName, srcDesc, null, null, null, dstClsName, dstName, dstDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean visitMethod(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, @Nullable String[] dstNames, @Nullable String[] dstDescs, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc) throws IOException {
/* 168 */     if (!visitClass(srcClsName, dstClsNames, dstClsName)) return false;
/*     */     
/* 170 */     if (this.lastMemberIsField || !srcName.equals(this.lastMemberName) || (srcDesc != null && !srcDesc.equals(this.lastMemberDesc))) {
/* 171 */       this.lastMemberName = srcName;
/* 172 */       this.lastMemberDesc = srcDesc;
/* 173 */       this.lastMemberIsField = false;
/* 174 */       this.lastArgPosition = this.lastLvIndex = this.lastStartOpIdx = -1;
/* 175 */       this.relayLastMember = (this.next.visitMethod(srcName, srcDesc) && visitDstNamesDescs(MappedElementKind.METHOD, dstNames, dstDescs, dstName, dstDesc));
/*     */     } 
/*     */     
/* 178 */     return this.relayLastMember;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String[] dstClsNames, @Nullable String[] dstNames, @Nullable String[] dstDescs, String comment) throws IOException {
/* 185 */     if (!visitMethod(srcClsName, srcName, srcDesc, dstClsNames, dstNames, dstDescs, null, null, null))
/* 186 */       return;  this.next.visitComment(MappedElementKind.METHOD, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodComment(String srcClsName, String srcName, @Nullable String srcDesc, @Nullable String dstClsName, @Nullable String dstName, @Nullable String dstDesc, String comment) throws IOException {
/* 193 */     if (!visitMethod(srcClsName, srcName, srcDesc, null, null, null, dstClsName, dstName, dstDesc))
/* 194 */       return;  this.next.visitComment(MappedElementKind.METHOD, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcArgName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, String[] dstArgNames) throws IOException {
/* 202 */     return visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcArgName, dstClsNames, dstMethodNames, dstMethodDescs, dstArgNames, null, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcArgName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, String dstArgName) throws IOException {
/* 211 */     return visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcArgName, null, null, null, null, dstClsName, dstMethodName, dstMethodDesc, dstArgName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean visitMethodArg(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, @Nullable String[] dstNames, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstName) throws IOException {
/* 221 */     if (!visitMethod(srcClsName, srcMethodName, srcMethodDesc, dstClsNames, dstMethodNames, dstMethodDescs, dstClsName, dstMethodName, dstMethodDesc)) return false;
/*     */     
/* 223 */     if (!this.lastMethodSubIsArg || argPosition != this.lastArgPosition || lvIndex != this.lastLvIndex) {
/* 224 */       this.lastArgPosition = argPosition;
/* 225 */       this.lastLvIndex = lvIndex;
/* 226 */       this.lastMethodSubIsArg = true;
/* 227 */       this.relayLastMethodSub = (this.next.visitMethodArg(argPosition, lvIndex, srcName) && visitDstNames(MappedElementKind.METHOD_ARG, dstNames, dstName));
/*     */     } 
/*     */     
/* 230 */     return this.relayLastMethodSub;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodArgComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcArgName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, @Nullable String[] dstArgNames, String comment) throws IOException {
/* 238 */     if (!visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcArgName, dstClsNames, dstMethodNames, dstMethodDescs, dstArgNames, null, null, null, null)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 243 */     this.next.visitComment(MappedElementKind.METHOD_ARG, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodArgComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int argPosition, int lvIndex, @Nullable String srcArgName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstArgName, String comment) throws IOException {
/* 251 */     if (!visitMethodArg(srcClsName, srcMethodName, srcMethodDesc, argPosition, lvIndex, srcArgName, null, null, null, null, dstClsName, dstMethodName, dstMethodDesc, dstArgName)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 256 */     this.next.visitComment(MappedElementKind.METHOD_ARG, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcVarName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, String[] dstVarNames) throws IOException {
/* 264 */     return visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcVarName, dstClsNames, dstMethodNames, dstMethodDescs, dstVarNames, null, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcVarName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, String dstVarName) throws IOException {
/* 273 */     return visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcVarName, null, null, null, null, dstClsName, dstMethodName, dstMethodDesc, dstVarName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean visitMethodVar(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, @Nullable String[] dstNames, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstName) throws IOException {
/* 281 */     if (!visitMethod(srcClsName, srcMethodName, srcMethodDesc, dstClsNames, dstMethodNames, dstMethodDescs, dstClsName, dstMethodName, dstMethodDesc)) return false;
/*     */     
/* 283 */     if (this.lastMethodSubIsArg || lvtRowIndex != this.lastArgPosition || lvIndex != this.lastLvIndex || startOpIdx != this.lastStartOpIdx) {
/* 284 */       this.lastArgPosition = lvtRowIndex;
/* 285 */       this.lastLvIndex = lvIndex;
/* 286 */       this.lastStartOpIdx = startOpIdx;
/* 287 */       this.lastMethodSubIsArg = false;
/* 288 */       this.relayLastMethodSub = (this.next.visitMethodVar(lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName) && visitDstNames(MappedElementKind.METHOD_VAR, dstNames, dstName));
/*     */     } 
/*     */     
/* 291 */     return this.relayLastMethodSub;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodVarComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcVarName, @Nullable String[] dstClsNames, @Nullable String[] dstMethodNames, @Nullable String[] dstMethodDescs, @Nullable String[] dstVarNames, String comment) throws IOException {
/* 299 */     if (!visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcVarName, dstClsNames, dstMethodNames, dstMethodDescs, dstVarNames, null, null, null, null)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 304 */     this.next.visitComment(MappedElementKind.METHOD_VAR, comment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodVarComment(String srcClsName, String srcMethodName, @Nullable String srcMethodDesc, int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcVarName, @Nullable String dstClsName, @Nullable String dstMethodName, @Nullable String dstMethodDesc, @Nullable String dstVarName, String comment) throws IOException {
/* 312 */     if (!visitMethodVar(srcClsName, srcMethodName, srcMethodDesc, lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcVarName, null, null, null, null, dstClsName, dstMethodName, dstMethodDesc, dstVarName)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 317 */     this.next.visitComment(MappedElementKind.METHOD_VAR, comment);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitEnd() throws IOException {
/* 322 */     return this.next.visitEnd();
/*     */   }
/*     */   
/*     */   private boolean visitDstNames(MappedElementKind targetKind, @Nullable String[] dstNames, @Nullable String dstName) throws IOException {
/* 326 */     if (dstNames != null) {
/* 327 */       for (int i = 0; i < dstNames.length; i++) {
/* 328 */         String name = dstNames[i];
/* 329 */         if (name != null) this.next.visitDstName(targetKind, i, name); 
/*     */       } 
/* 331 */     } else if (dstName != null) {
/* 332 */       this.next.visitDstName(targetKind, 0, dstName);
/*     */     } 
/*     */     
/* 335 */     return this.next.visitElementContent(targetKind);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean visitDstNamesDescs(MappedElementKind targetKind, @Nullable String[] dstNames, @Nullable String[] dstDescs, @Nullable String dstName, @Nullable String dstDesc) throws IOException {
/* 340 */     boolean relayMemberDesc = ((targetKind == MappedElementKind.FIELD && this.relayDstFieldDescs) || (targetKind != MappedElementKind.FIELD && this.relayDstMethodDescs));
/*     */ 
/*     */     
/* 343 */     if (dstNames != null || dstDescs != null) {
/* 344 */       if (dstNames != null) {
/* 345 */         for (int i = 0; i < dstNames.length; i++) {
/* 346 */           String name = dstNames[i];
/* 347 */           if (name != null) this.next.visitDstName(targetKind, i, name);
/*     */         
/*     */         } 
/*     */       }
/* 351 */       if (dstDescs != null && relayMemberDesc)
/* 352 */         for (int i = 0; i < dstDescs.length; i++) {
/* 353 */           String desc = dstDescs[i];
/* 354 */           if (desc != null) this.next.visitDstDesc(targetKind, i, desc);
/*     */         
/*     */         }  
/*     */     } else {
/* 358 */       if (dstName != null) this.next.visitDstName(targetKind, 0, dstName); 
/* 359 */       if (dstDesc != null && relayMemberDesc) this.next.visitDstDesc(targetKind, 0, dstDesc);
/*     */     
/*     */     } 
/* 362 */     return this.next.visitElementContent(targetKind);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\RegularAsFlatMappingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */