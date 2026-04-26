/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.srg;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TsrgFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public TsrgFileWriter(Writer writer, boolean tsrg2) {
/* 244 */     this.lvIndex = -1;
/*     */     this.writer = writer;
/*     */     this.tsrg2 = tsrg2;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*     */     this.writer.close();
/*     */   }
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*     */     return this.tsrg2 ? tsrg2Flags : tsrgFlags;
/*     */   }
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*     */     this.dstNames = new String[dstNamespaces.size()];
/*     */     if (this.tsrg2) {
/*     */       write("tsrg2 ");
/*     */       write(srcNamespace);
/*     */       for (String dstNamespace : dstNamespaces) {
/*     */         writeSpace();
/*     */         write(dstNamespace);
/*     */       } 
/*     */       writeLn();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {}
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*     */     this.clsSrcName = srcName;
/*     */     this.hasAnyDstNames = false;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     this.memberSrcName = srcName;
/*     */     this.memberSrcDesc = srcDesc;
/*     */     this.hasAnyDstNames = false;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/*     */     this.memberSrcName = srcName;
/*     */     this.memberSrcDesc = srcDesc;
/*     */     this.hasAnyDstNames = false;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/*     */     if (!this.tsrg2)
/*     */       return false; 
/*     */     this.lvIndex = lvIndex;
/*     */     this.argSrcName = srcName;
/*     */     this.hasAnyDstNames = false;
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/*     */     if (!this.tsrg2 && namespace != 0)
/*     */       return; 
/*     */     this.dstNames[namespace] = name;
/*     */     this.hasAnyDstNames |= (name != null) ? 1 : 0;
/*     */   }
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     if (this.classContentVisitPending && targetKind != MappedElementKind.CLASS && this.hasAnyDstNames) {
/*     */       String[] memberOrArgDstNames = (String[])this.dstNames.clone();
/*     */       Arrays.fill((Object[])this.dstNames, this.clsSrcName);
/*     */       visitElementContent(MappedElementKind.CLASS);
/*     */       this.classContentVisitPending = false;
/*     */       this.dstNames = memberOrArgDstNames;
/*     */     } 
/*     */     if (this.methodContentVisitPending && targetKind == MappedElementKind.METHOD_ARG && this.hasAnyDstNames) {
/*     */       String[] argDstNames = (String[])this.dstNames.clone();
/*     */       Arrays.fill((Object[])this.dstNames, this.memberSrcName);
/*     */       visitElementContent(MappedElementKind.METHOD);
/*     */       this.methodContentVisitPending = false;
/*     */       this.dstNames = argDstNames;
/*     */     } 
/*     */     String srcName = null;
/*     */     switch (targetKind) {
/*     */       case CLASS:
/*     */         if (!this.hasAnyDstNames) {
/*     */           this.classContentVisitPending = true;
/*     */           return true;
/*     */         } 
/*     */         srcName = this.clsSrcName;
/*     */         break;
/*     */       case FIELD:
/*     */       case METHOD:
/*     */         if (!this.hasAnyDstNames) {
/*     */           if (targetKind == MappedElementKind.METHOD) {
/*     */             this.methodContentVisitPending = true;
/*     */             return this.tsrg2;
/*     */           } 
/*     */           return false;
/*     */         } 
/*     */         srcName = this.memberSrcName;
/*     */         writeTab();
/*     */         break;
/*     */       case METHOD_ARG:
/*     */         assert this.tsrg2;
/*     */         if (!this.hasAnyDstNames)
/*     */           return false; 
/*     */         srcName = this.argSrcName;
/*     */         writeTab();
/*     */         writeTab();
/*     */         write(Integer.toString(this.lvIndex));
/*     */         writeSpace();
/*     */       case METHOD_VAR:
/*     */         assert this.tsrg2;
/*     */         break;
/*     */     } 
/*     */     write(srcName);
/*     */     if (targetKind == MappedElementKind.METHOD || (targetKind == MappedElementKind.FIELD && this.tsrg2)) {
/*     */       writeSpace();
/*     */       write(this.memberSrcDesc);
/*     */     } 
/*     */     int dstNsCount = this.tsrg2 ? this.dstNames.length : 1;
/*     */     for (int i = 0; i < dstNsCount; i++) {
/*     */       String dstName = this.dstNames[i];
/*     */       writeSpace();
/*     */       write((dstName != null) ? dstName : srcName);
/*     */     } 
/*     */     writeLn();
/*     */     Arrays.fill((Object[])this.dstNames, (Object)null);
/*     */     return (targetKind == MappedElementKind.CLASS || (this.tsrg2 && targetKind == MappedElementKind.METHOD));
/*     */   }
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */   
/*     */   private void write(String str) throws IOException {
/*     */     this.writer.write(str);
/*     */   }
/*     */   
/*     */   private void writeTab() throws IOException {
/*     */     this.writer.write(9);
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
/*     */   private static final Set<MappingFlag> tsrgFlags = EnumSet.of(MappingFlag.NEEDS_ELEMENT_UNIQUENESS, MappingFlag.NEEDS_SRC_METHOD_DESC);
/*     */   private static final Set<MappingFlag> tsrg2Flags = EnumSet.copyOf(tsrgFlags);
/*     */   private final Writer writer;
/*     */   private final boolean tsrg2;
/*     */   private String clsSrcName;
/*     */   private String memberSrcName;
/*     */   private String memberSrcDesc;
/*     */   private String argSrcName;
/*     */   private String[] dstNames;
/*     */   private boolean hasAnyDstNames;
/*     */   private int lvIndex;
/*     */   private boolean classContentVisitPending;
/*     */   private boolean methodContentVisitPending;
/*     */   
/*     */   static {
/*     */     tsrg2Flags.add(MappingFlag.NEEDS_SRC_FIELD_DESC);
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\srg\TsrgFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */