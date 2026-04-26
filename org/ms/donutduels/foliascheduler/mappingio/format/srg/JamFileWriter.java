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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JamFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public JamFileWriter(Writer writer) {
/* 206 */     this.classOnlyPass = true;
/*     */ 
/*     */ 
/*     */     
/* 210 */     this.argSrcPosition = -1;
/*     */     this.writer = writer;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*     */     this.writer.close();
/*     */   }
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*     */     return flags;
/*     */   }
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {}
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*     */     this.classSrcName = srcName;
/*     */     this.classDstName = null;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     this.memberSrcName = srcName;
/*     */     this.memberSrcDesc = srcDesc;
/*     */     this.memberDstName = null;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     this.memberSrcName = srcName;
/*     */     this.memberSrcDesc = srcDesc;
/*     */     this.memberDstName = null;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*     */     this.argSrcPosition = argPosition;
/*     */     this.argDstName = null;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*     */     if (namespace != 0)
/*     */       return; 
/*     */     switch (targetKind) {
/*     */       case CLASS:
/*     */         this.classDstName = name;
/*     */         return;
/*     */       case FIELD:
/*     */       case METHOD:
/*     */         this.memberDstName = name;
/*     */         return;
/*     */       case METHOD_ARG:
/*     */         this.argDstName = name;
/*     */         return;
/*     */     } 
/*     */     throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */   }
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     boolean isClass = (targetKind == MappedElementKind.CLASS);
/*     */     boolean isMethod = false;
/*     */     boolean isArg = false;
/*     */     if (isClass) {
/*     */       if (!this.classOnlyPass || this.classDstName == null)
/*     */         return true; 
/*     */       write("CL ");
/*     */     } else {
/*     */       if (targetKind != MappedElementKind.FIELD)
/*     */         if (!(isMethod = (targetKind == MappedElementKind.METHOD)))
/*     */           if (!(isArg = (targetKind == MappedElementKind.METHOD_ARG)))
/*     */             throw new IllegalStateException("unexpected invocation for " + targetKind);   
/*     */       if (this.classOnlyPass || this.memberSrcDesc == null)
/*     */         return false; 
/*     */       if (!isArg && this.memberDstName == null)
/*     */         return isMethod; 
/*     */       if (isMethod) {
/*     */         write("MD ");
/*     */       } else if (!isArg) {
/*     */         write("FD ");
/*     */       } else {
/*     */         if (this.argSrcPosition == -1 || this.argDstName == null)
/*     */           return false; 
/*     */         write("MP ");
/*     */       } 
/*     */     } 
/*     */     write(this.classSrcName);
/*     */     writeSpace();
/*     */     if (isClass) {
/*     */       write(this.classDstName);
/*     */     } else {
/*     */       write(this.memberSrcName);
/*     */       writeSpace();
/*     */       write(this.memberSrcDesc);
/*     */       writeSpace();
/*     */       if (!isArg) {
/*     */         write(this.memberDstName);
/*     */       } else {
/*     */         write(Integer.toString(this.argSrcPosition));
/*     */         writeSpace();
/*     */         write(this.argDstName);
/*     */       } 
/*     */     } 
/*     */     writeLn();
/*     */     return (isClass || isMethod);
/*     */   }
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */   
/*     */   public boolean visitEnd() throws IOException {
/*     */     if (this.classOnlyPass) {
/*     */       this.classOnlyPass = false;
/*     */       return false;
/*     */     } 
/*     */     this.classOnlyPass = true;
/*     */     return super.visitEnd();
/*     */   }
/*     */   
/*     */   private void write(String str) throws IOException {
/*     */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeSpace() throws IOException {
/*     */     this.writer.write(32);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/*     */     this.writer.write(10);
/*     */   }
/*     */   
/*     */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC, MappingFlag.NEEDS_MULTIPLE_PASSES);
/*     */   private final Writer writer;
/*     */   private boolean classOnlyPass;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private int argSrcPosition;
/*     */   private String classDstName;
/*     */   private String memberDstName;
/*     */   private String argDstName;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\JamFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */