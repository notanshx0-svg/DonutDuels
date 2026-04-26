/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.jobf;
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
/*     */ public final class JobfFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public JobfFileWriter(Writer writer) {
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
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  56 */     this.classSrcName = srcName;
/*  57 */     this.dstName = null;
/*     */     
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*  64 */     this.memberSrcName = srcName;
/*  65 */     this.memberSrcDesc = srcDesc;
/*  66 */     this.dstName = null;
/*     */     
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*  73 */     this.memberSrcName = srcName;
/*  74 */     this.memberSrcDesc = srcDesc;
/*  75 */     this.dstName = null;
/*     */     
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*  92 */     if (namespace != 0)
/*     */       return; 
/*  94 */     this.dstName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*  99 */     boolean isClass = (targetKind == MappedElementKind.CLASS);
/* 100 */     boolean isField = false;
/*     */     
/* 102 */     if (this.dstName == null) return isClass;
/*     */     
/* 104 */     if (isClass) {
/* 105 */       int srcNameLastSep = this.classSrcName.lastIndexOf('/');
/* 106 */       int dstNameLastSep = this.dstName.lastIndexOf('/');
/* 107 */       String srcPkg = this.classSrcName.substring(0, srcNameLastSep + 1);
/* 108 */       String dstPkg = this.dstName.substring(0, dstNameLastSep + 1);
/*     */       
/* 110 */       this.classSrcName = this.classSrcName.replace('/', '.');
/* 111 */       if (!srcPkg.equals(dstPkg)) return true;
/*     */       
/* 113 */       this.dstName = this.dstName.substring(dstNameLastSep + 1);
/* 114 */       write("c ");
/* 115 */     } else if ((isField = (targetKind == MappedElementKind.FIELD)) || targetKind == MappedElementKind.METHOD) {
/*     */       
/* 117 */       if (this.memberSrcDesc == null) return false; 
/* 118 */       write(isField ? "f " : "m ");
/*     */     } else {
/* 120 */       throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */     } 
/*     */     
/* 123 */     write(this.classSrcName);
/*     */     
/* 125 */     if (!isClass) {
/* 126 */       write(".");
/* 127 */       write(this.memberSrcName);
/*     */       
/* 129 */       if (isField) write(":"); 
/* 130 */       write(this.memberSrcDesc);
/*     */     } 
/*     */     
/* 133 */     write(" = ");
/* 134 */     write(this.dstName);
/*     */     
/* 136 */     writeLn();
/*     */     
/* 138 */     return isClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 147 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 151 */     this.writer.write(10);
/*     */   }
/*     */   
/* 154 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private final Writer writer;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private String dstName;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\jobf\JobfFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */