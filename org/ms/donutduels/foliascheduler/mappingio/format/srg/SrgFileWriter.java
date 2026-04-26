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
/*     */ 
/*     */ public final class SrgFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public SrgFileWriter(Writer writer, boolean xsrg) {
/*  38 */     this.writer = writer;
/*  39 */     this.xsrg = xsrg;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  44 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  49 */     return this.xsrg ? xsrgFlags : srgFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {}
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  58 */     this.classSrcName = srcName;
/*  59 */     this.classDstName = null;
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*  66 */     this.memberSrcName = srcName;
/*  67 */     this.memberSrcDesc = srcDesc;
/*  68 */     this.memberDstName = null;
/*  69 */     this.memberDstDesc = null;
/*     */     
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*  76 */     this.memberSrcName = srcName;
/*  77 */     this.memberSrcDesc = srcDesc;
/*  78 */     this.memberDstName = null;
/*  79 */     this.memberDstDesc = null;
/*     */     
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*  96 */     if (namespace != 0)
/*     */       return; 
/*  98 */     switch (targetKind) {
/*     */       case CLASS:
/* 100 */         this.classDstName = name;
/*     */         return;
/*     */       case FIELD:
/*     */       case METHOD:
/* 104 */         this.memberDstName = name;
/*     */         return;
/*     */     } 
/* 107 */     throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {
/* 113 */     if (namespace != 0)
/*     */       return; 
/* 115 */     this.memberDstDesc = desc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 120 */     switch (targetKind) {
/*     */       case CLASS:
/* 122 */         if (this.classDstName == null) return true; 
/* 123 */         write("CL: ");
/*     */         break;
/*     */       case FIELD:
/* 126 */         if (this.memberSrcDesc == null || this.memberDstName == null || (this.xsrg && this.memberDstDesc == null)) return false; 
/* 127 */         write("FD: ");
/*     */         break;
/*     */       case METHOD:
/* 130 */         if (this.memberSrcDesc == null || this.memberDstName == null || this.memberDstDesc == null) return false; 
/* 131 */         write("MD: ");
/*     */         break;
/*     */       default:
/* 134 */         throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */     } 
/*     */     
/* 137 */     write(this.classSrcName);
/*     */     
/* 139 */     if (targetKind != MappedElementKind.CLASS) {
/* 140 */       write("/");
/* 141 */       write(this.memberSrcName);
/*     */       
/* 143 */       if (targetKind == MappedElementKind.METHOD || this.xsrg) {
/* 144 */         write(" ");
/* 145 */         write(this.memberSrcDesc);
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     write(" ");
/* 150 */     if (this.classDstName == null) this.classDstName = this.classSrcName; 
/* 151 */     write(this.classDstName);
/*     */     
/* 153 */     if (targetKind != MappedElementKind.CLASS) {
/* 154 */       write("/");
/* 155 */       write(this.memberDstName);
/*     */       
/* 157 */       if (targetKind == MappedElementKind.METHOD || this.xsrg) {
/* 158 */         write(" ");
/* 159 */         write(this.memberDstDesc);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     writeLn();
/*     */     
/* 165 */     return (targetKind == MappedElementKind.CLASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 174 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 178 */     this.writer.write(10);
/*     */   }
/*     */   
/* 181 */   private static final Set<MappingFlag> srgFlags = EnumSet.of(MappingFlag.NEEDS_SRC_METHOD_DESC, MappingFlag.NEEDS_DST_METHOD_DESC);
/*     */ 
/*     */ 
/*     */   
/* 185 */   private static final Set<MappingFlag> xsrgFlags = EnumSet.copyOf(srgFlags); static {
/* 186 */     xsrgFlags.add(MappingFlag.NEEDS_SRC_FIELD_DESC);
/* 187 */     xsrgFlags.add(MappingFlag.NEEDS_DST_FIELD_DESC);
/*     */   }
/*     */   
/*     */   private final Writer writer;
/*     */   private final boolean xsrg;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private String classDstName;
/*     */   private String memberDstName;
/*     */   private String memberDstDesc;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\SrgFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */