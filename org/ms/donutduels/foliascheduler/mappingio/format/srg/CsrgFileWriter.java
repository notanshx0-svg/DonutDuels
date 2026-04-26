/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.srg;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingWriter;
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
/*     */ public final class CsrgFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public CsrgFileWriter(Writer writer) {
/*  37 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  42 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  47 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  57 */     this.classSrcName = srcName;
/*     */     
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*  64 */     this.memberSrcName = srcName;
/*     */     
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*  71 */     this.memberSrcName = srcName;
/*  72 */     this.methodSrcDesc = srcDesc;
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*  89 */     if (namespace != 0)
/*     */       return; 
/*  91 */     this.dstName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*  96 */     if (this.dstName != null) {
/*  97 */       write(this.classSrcName);
/*     */       
/*  99 */       if (targetKind != MappedElementKind.CLASS) {
/* 100 */         writeSpace();
/* 101 */         write(this.memberSrcName);
/*     */         
/* 103 */         if (targetKind == MappedElementKind.METHOD) {
/* 104 */           writeSpace();
/* 105 */           write(this.methodSrcDesc);
/*     */         } 
/*     */         
/* 108 */         this.memberSrcName = this.methodSrcDesc = null;
/*     */       } 
/*     */       
/* 111 */       writeSpace();
/* 112 */       write(this.dstName);
/* 113 */       writeLn();
/*     */       
/* 115 */       this.dstName = null;
/*     */     } 
/*     */     
/* 118 */     return (targetKind == MappedElementKind.CLASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 127 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeSpace() throws IOException {
/* 131 */     this.writer.write(32);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 135 */     this.writer.write(10);
/*     */   }
/*     */   
/* 138 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private final Writer writer;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String methodSrcDesc;
/*     */   private String dstName;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\CsrgFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */