/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.simple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public final class RecafSimpleFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public RecafSimpleFileWriter(Writer writer) {
/*  35 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  40 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  45 */     return flags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {}
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  54 */     this.classSrcName = srcName;
/*     */     
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, String srcDesc) throws IOException {
/*  61 */     this.memberSrcName = srcName;
/*  62 */     this.memberSrcDesc = srcDesc;
/*     */     
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, String srcDesc) throws IOException {
/*  69 */     this.memberSrcName = srcName;
/*  70 */     this.memberSrcDesc = srcDesc;
/*     */     
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, String srcName) throws IOException {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, String srcName) throws IOException {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*  87 */     if (namespace != 0)
/*  88 */       return;  this.dstName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*  93 */     if (this.dstName == null) return true; 
/*  94 */     write(this.classSrcName);
/*     */     
/*  96 */     if (targetKind != MappedElementKind.CLASS) {
/*  97 */       if (this.memberSrcName == null) throw new IllegalArgumentException("member source name cannot be null!"); 
/*  98 */       this.writer.write(46);
/*  99 */       write(this.memberSrcName);
/*     */       
/* 101 */       if (this.memberSrcDesc != null) {
/* 102 */         if (targetKind == MappedElementKind.FIELD) writeSpace(); 
/* 103 */         write(this.memberSrcDesc);
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     writeSpace();
/* 108 */     write(this.dstName);
/* 109 */     writeLn();
/* 110 */     this.dstName = null;
/*     */     
/* 112 */     return (targetKind == MappedElementKind.CLASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 121 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 125 */     this.writer.write(10);
/*     */   }
/*     */   
/*     */   private void writeSpace() throws IOException {
/* 129 */     this.writer.write(32);
/*     */   }
/*     */   
/* 132 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private final Writer writer;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private String dstName;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\simple\RecafSimpleFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */