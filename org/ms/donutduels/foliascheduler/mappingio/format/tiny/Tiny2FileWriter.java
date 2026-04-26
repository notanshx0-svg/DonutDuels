/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.tiny;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
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
/*     */ public final class Tiny2FileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public Tiny2FileWriter(Writer writer, boolean escapeNames) {
/*  39 */     this.writer = writer;
/*  40 */     this.escapeNames = escapeNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  45 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  50 */     return flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  55 */     this.dstNames = new String[dstNamespaces.size()];
/*     */     
/*  57 */     write("tiny\t2\t0\t");
/*  58 */     write(srcNamespace);
/*     */     
/*  60 */     for (String dstNamespace : dstNamespaces) {
/*  61 */       writeTab();
/*  62 */       write(dstNamespace);
/*     */     } 
/*     */     
/*  65 */     writeLn();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*  70 */     switch (key) {
/*     */       case "escaped-names":
/*  72 */         this.escapeNames = true;
/*  73 */         this.wroteEscapedNamesProperty = true;
/*     */         break;
/*     */       
/*     */       case "migrationmap:order":
/*     */         return;
/*     */     } 
/*  79 */     writeTab();
/*  80 */     write(key);
/*     */     
/*  82 */     if (value != null) {
/*  83 */       writeTab();
/*  84 */       write(value);
/*     */     } 
/*     */     
/*  87 */     writeLn();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitContent() throws IOException {
/*  92 */     if (this.escapeNames && !this.wroteEscapedNamesProperty) {
/*  93 */       write("\t");
/*  94 */       write("escaped-names");
/*  95 */       writeLn();
/*     */     } 
/*     */     
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/* 103 */     write("c\t");
/* 104 */     writeName(srcName);
/*     */     
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 111 */     write("\tf\t");
/* 112 */     writeName(srcDesc);
/* 113 */     writeTab();
/* 114 */     writeName(srcName);
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 121 */     write("\tm\t");
/* 122 */     writeName(srcDesc);
/* 123 */     writeTab();
/* 124 */     writeName(srcName);
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/* 131 */     write("\t\tp\t");
/* 132 */     write(lvIndex);
/* 133 */     writeTab();
/* 134 */     if (srcName != null) writeName(srcName);
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/* 141 */     write("\t\tv\t");
/* 142 */     write(lvIndex);
/* 143 */     writeTab();
/* 144 */     write(startOpIdx);
/* 145 */     writeTab();
/* 146 */     write(Math.max(lvtRowIndex, -1));
/* 147 */     writeTab();
/* 148 */     if (srcName != null) writeName(srcName);
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/* 155 */     this.dstNames[namespace] = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 160 */     for (String dstName : this.dstNames) {
/* 161 */       writeTab();
/* 162 */       if (dstName != null) writeName(dstName);
/*     */     
/*     */     } 
/* 165 */     writeLn();
/*     */     
/* 167 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*     */     
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {
/* 174 */     writeTabs(targetKind.level);
/* 175 */     write("\tc\t");
/* 176 */     writeEscaped(comment);
/* 177 */     writeLn();
/*     */   }
/*     */   
/*     */   private void write(String str) throws IOException {
/* 181 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void write(int i) throws IOException {
/* 185 */     write(Integer.toString(i));
/*     */   }
/*     */   
/*     */   private void writeEscaped(String str) throws IOException {
/* 189 */     Tiny2Util.writeEscaped(str, this.writer);
/*     */   }
/*     */   
/*     */   private void writeName(String str) throws IOException {
/* 193 */     if (this.escapeNames) {
/* 194 */       writeEscaped(str);
/*     */     } else {
/* 196 */       write(str);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 201 */     this.writer.write(10);
/*     */   }
/*     */   
/*     */   private void writeTab() throws IOException {
/* 205 */     this.writer.write(9);
/*     */   }
/*     */   
/*     */   private void writeTabs(int count) throws IOException {
/* 209 */     for (int i = 0; i < count; i++) {
/* 210 */       this.writer.write(9);
/*     */     }
/*     */   }
/*     */   
/* 214 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_HEADER_METADATA, MappingFlag.NEEDS_METADATA_UNIQUENESS, MappingFlag.NEEDS_ELEMENT_UNIQUENESS, MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private final Writer writer;
/*     */   private boolean escapeNames;
/*     */   private boolean wroteEscapedNamesProperty;
/*     */   private String[] dstNames;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\tiny\Tiny2FileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */