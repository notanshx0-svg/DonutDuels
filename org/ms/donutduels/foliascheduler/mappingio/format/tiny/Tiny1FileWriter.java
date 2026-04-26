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
/*     */ public final class Tiny1FileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public Tiny1FileWriter(Writer writer) {
/*  38 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  43 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  48 */     return flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  53 */     this.dstNames = new String[dstNamespaces.size()];
/*     */     
/*  55 */     write("v1\t");
/*  56 */     write(srcNamespace);
/*     */     
/*  58 */     for (String dstNamespace : dstNamespaces) {
/*  59 */       writeTab();
/*  60 */       write(dstNamespace);
/*     */     } 
/*     */     
/*  63 */     writeLn();
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*  68 */     switch (key) {
/*     */       case "next-intermediary-class":
/*     */       case "next-intermediary-field":
/*     */       case "next-intermediary-method":
/*  72 */         write("# INTERMEDIARY-COUNTER ");
/*     */         
/*  74 */         switch (key) {
/*     */           case "next-intermediary-class":
/*  76 */             write("class");
/*     */             break;
/*     */           case "next-intermediary-field":
/*  79 */             write("field");
/*     */             break;
/*     */           case "next-intermediary-method":
/*  82 */             write("method");
/*     */             break;
/*     */           default:
/*  85 */             throw new IllegalStateException();
/*     */         } 
/*     */         
/*  88 */         write(" ");
/*  89 */         write(value);
/*  90 */         writeLn();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*  96 */     this.classSrcName = srcName;
/*     */     
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 103 */     this.memberSrcName = srcName;
/* 104 */     this.memberSrcDesc = srcDesc;
/*     */     
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 111 */     this.memberSrcName = srcName;
/* 112 */     this.memberSrcDesc = srcDesc;
/*     */     
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/* 129 */     this.dstNames[namespace] = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 135 */     boolean found = false;
/*     */     
/* 137 */     for (String dstName : this.dstNames) {
/* 138 */       if (dstName != null) {
/* 139 */         found = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 144 */     if (!found) return true;
/*     */     
/* 146 */     switch (targetKind) {
/*     */       case CLASS:
/* 148 */         write("CLASS");
/*     */         break;
/*     */       case FIELD:
/* 151 */         write("FIELD");
/*     */         break;
/*     */       case METHOD:
/* 154 */         write("METHOD");
/*     */         break;
/*     */       default:
/* 157 */         throw new IllegalStateException("unexpected invocation for " + targetKind);
/*     */     } 
/*     */     
/* 160 */     writeTab();
/* 161 */     write(this.classSrcName);
/*     */     
/* 163 */     if (targetKind != MappedElementKind.CLASS) {
/* 164 */       writeTab();
/* 165 */       write(this.memberSrcDesc);
/* 166 */       writeTab();
/* 167 */       write(this.memberSrcName);
/*     */     } 
/*     */     
/* 170 */     for (String dstName : this.dstNames) {
/* 171 */       writeTab();
/* 172 */       if (dstName != null) write(dstName);
/*     */     
/*     */     } 
/* 175 */     writeLn();
/*     */     
/* 177 */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*     */     
/* 179 */     return (targetKind == MappedElementKind.CLASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/*     */   private void write(String str) throws IOException {
/* 188 */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeLn() throws IOException {
/* 192 */     this.writer.write(10);
/*     */   }
/*     */   
/*     */   private void writeTab() throws IOException {
/* 196 */     this.writer.write(9);
/*     */   }
/*     */   
/* 199 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_SRC_FIELD_DESC, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private final Writer writer;
/*     */   private String classSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private String[] dstNames;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\tiny\Tiny1FileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */