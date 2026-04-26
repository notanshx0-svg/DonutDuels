/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.enigma;
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
/*     */ abstract class EnigmaWriterBase
/*     */   implements MappingWriter
/*     */ {
/*     */   EnigmaWriterBase(Writer writer) throws IOException {
/* 221 */     this.lastWrittenClass = "";
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
/*     */   public boolean visitHeader() throws IOException {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) {}
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*     */     this.srcClassName = srcName;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     writeIndent(0);
/*     */     this.writer.write("FIELD ");
/*     */     this.writer.write(srcName);
/*     */     this.desc = srcDesc;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     writeIndent(0);
/*     */     this.writer.write("METHOD ");
/*     */     this.writer.write(srcName);
/*     */     this.desc = srcDesc;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*     */     writeIndent(1);
/*     */     this.writer.write("ARG ");
/*     */     this.writer.write(Integer.toString(lvIndex));
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
/*     */     if (namespace != 0)
/*     */       return; 
/*     */     if (targetKind == MappedElementKind.CLASS) {
/*     */       this.dstName = name;
/*     */     } else {
/*     */       this.writer.write(32);
/*     */       this.writer.write(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract boolean visitElementContent(MappedElementKind paramMappedElementKind) throws IOException;
/*     */   
/*     */   protected static int getNextOuterEnd(String name, int startPos) {
/*     */     int pos;
/*     */     while ((pos = name.indexOf('$', startPos + 1)) > 0) {
/*     */       if (name.charAt(pos - 1) != '/')
/*     */         return pos; 
/*     */       startPos = pos + 1;
/*     */     } 
/*     */     return -1;
/*     */   }
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {
/*     */     int start = 0;
/*     */     while (start < comment.length()) {
/*     */       int pos = comment.indexOf('\n', start);
/*     */       int end = (pos >= 0) ? pos : comment.length();
/*     */       writeIndent(targetKind.level);
/*     */       this.writer.write("COMMENT");
/*     */       if (end > start) {
/*     */         this.writer.write(32);
/*     */         for (int i = start; i < end; i++) {
/*     */           char c = comment.charAt(i);
/*     */           int idx = "\\\n\r\000\t".indexOf(c);
/*     */           if (idx >= 0) {
/*     */             if (i > start)
/*     */               this.writer.write(comment, start, i - start); 
/*     */             this.writer.write(92);
/*     */             this.writer.write("\\nr0t".charAt(idx));
/*     */             start = i + 1;
/*     */           } 
/*     */         } 
/*     */         if (start < end)
/*     */           this.writer.write(comment, start, end - start); 
/*     */       } 
/*     */       this.writer.write(10);
/*     */       start = end + 1;
/*     */       if (pos < 0)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeMismatchedOrMissingClasses() throws IOException {
/*     */     this.indent = 0;
/*     */     int srcStart = 0;
/*     */     do {
/*     */       int srcEnd = getNextOuterEnd(this.srcClassName, srcStart);
/*     */       if (srcEnd < 0)
/*     */         srcEnd = this.srcClassName.length(); 
/*     */       int srcLen = srcEnd - srcStart;
/*     */       if (!this.lastWrittenClass.regionMatches(srcStart, this.srcClassName, srcStart, srcLen) || (srcEnd < this.lastWrittenClass.length() && this.lastWrittenClass.charAt(srcEnd) != '$')) {
/*     */         writeIndent(0);
/*     */         this.writer.write("CLASS ");
/*     */         this.writer.write(this.srcClassName, srcStart, srcLen);
/*     */         if (this.dstName != null) {
/*     */           int dstStart = 0;
/*     */           for (int i = 0; i < this.indent; i++) {
/*     */             dstStart = getNextOuterEnd(this.dstName, dstStart);
/*     */             if (dstStart < 0)
/*     */               break; 
/*     */             dstStart++;
/*     */           } 
/*     */           if (dstStart >= 0) {
/*     */             int dstEnd = getNextOuterEnd(this.dstName, dstStart);
/*     */             if (dstEnd < 0)
/*     */               dstEnd = this.dstName.length(); 
/*     */             int dstLen = dstEnd - dstStart;
/*     */             if (dstLen != srcLen || !this.srcClassName.regionMatches(srcStart, this.dstName, dstStart, srcLen)) {
/*     */               this.writer.write(32);
/*     */               this.writer.write(this.dstName, dstStart, dstLen);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         this.writer.write(10);
/*     */       } 
/*     */       this.indent++;
/*     */       srcStart = srcEnd + 1;
/*     */     } while (srcStart < this.srcClassName.length());
/*     */     this.lastWrittenClass = this.srcClassName;
/*     */     this.dstName = null;
/*     */   }
/*     */   
/*     */   protected void writeIndent(int extra) throws IOException {
/*     */     for (int i = 0; i < this.indent + extra; i++)
/*     */       this.writer.write(9); 
/*     */   }
/*     */   
/*     */   protected static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_ELEMENT_UNIQUENESS, MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   protected static final String toEscape = "\\\n\r\000\t";
/*     */   protected static final String escaped = "\\nr0t";
/*     */   protected Writer writer;
/*     */   protected int indent;
/*     */   protected String srcClassName;
/*     */   protected String currentClass;
/*     */   protected String lastWrittenClass;
/*     */   protected String dstName;
/*     */   protected String[] dstNames;
/*     */   protected String desc;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\enigma\EnigmaWriterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */