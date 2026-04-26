/*     */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingFlag;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingUtil;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappingVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OuterClassNamePropagator
/*     */   extends ForwardingMappingVisitor
/*     */ {
/*     */   private static final int collectClassesPass = 1;
/*     */   private static final int fixOuterClassesPass = 2;
/*     */   private static final int firstEmitPass = 3;
/*     */   private final Map<String, String[]> dstNamesBySrcName;
/*     */   private final Set<String> modifiedClasses;
/*     */   private int pass;
/*     */   private int dstNsCount;
/*     */   private String srcName;
/*     */   private boolean[] visitedDstName;
/*     */   private Map<String, String>[] dstNameBySrcNameByNamespace;
/*     */   
/*     */   public OuterClassNamePropagator(MappingVisitor next) {
/*  49 */     super(next);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     this.dstNamesBySrcName = (Map)new HashMap<>();
/* 200 */     this.modifiedClasses = new HashSet<>();
/* 201 */     this.pass = 1;
/* 202 */     this.dstNsCount = -1;
/*     */   }
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*     */     Set<MappingFlag> ret = EnumSet.noneOf(MappingFlag.class);
/*     */     ret.addAll(this.next.getFlags());
/*     */     ret.add(MappingFlag.NEEDS_MULTIPLE_PASSES);
/*     */     return ret;
/*     */   }
/*     */   
/*     */   public boolean visitHeader() throws IOException {
/*     */     if (this.pass < 3)
/*     */       return true; 
/*     */     return super.visitHeader();
/*     */   }
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*     */     this.dstNsCount = dstNamespaces.size();
/*     */     if (this.pass == 1) {
/*     */       this.visitedDstName = new boolean[this.dstNsCount];
/*     */       this.dstNameBySrcNameByNamespace = (Map<String, String>[])new HashMap[this.dstNsCount];
/*     */     } else if (this.pass >= 3) {
/*     */       super.visitNamespaces(srcNamespace, dstNamespaces);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*     */     if (this.pass < 3)
/*     */       return; 
/*     */     super.visitMetadata(key, value);
/*     */   }
/*     */   
/*     */   public boolean visitContent() throws IOException {
/*     */     if (this.pass < 3)
/*     */       return true; 
/*     */     return super.visitContent();
/*     */   }
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/*     */     this.srcName = srcName;
/*     */     if (this.pass == 1) {
/*     */       this.dstNamesBySrcName.putIfAbsent(srcName, new String[this.dstNsCount]);
/*     */     } else if (this.pass >= 3) {
/*     */       super.visitClass(srcName);
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) throws IOException {
/*     */     if (this.pass == 1) {
/*     */       if (targetKind != MappedElementKind.CLASS)
/*     */         return; 
/*     */       ((String[])this.dstNamesBySrcName.get(this.srcName))[namespace] = name;
/*     */     } else if (this.pass >= 3) {
/*     */       if (targetKind == MappedElementKind.CLASS) {
/*     */         this.visitedDstName[namespace] = true;
/*     */         name = ((String[])this.dstNamesBySrcName.get(this.srcName))[namespace];
/*     */       } 
/*     */       super.visitDstName(targetKind, namespace, name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visitDstDesc(MappedElementKind targetKind, int namespace, String desc) throws IOException {
/*     */     if (this.pass < 3)
/*     */       return; 
/*     */     if (this.modifiedClasses.contains(this.srcName)) {
/*     */       Map<String, String> nsDstNameBySrcName = this.dstNameBySrcNameByNamespace[namespace];
/*     */       if (nsDstNameBySrcName == null)
/*     */         this.dstNameBySrcNameByNamespace[namespace] = nsDstNameBySrcName = (Map<String, String>)this.dstNamesBySrcName.entrySet().stream().filter(entry -> (((String[])entry.getValue())[namespace] != null)).collect(HashMap::new, (map, entry) -> map.put((String)entry.getKey(), ((String[])entry.getValue())[namespace]), HashMap::putAll); 
/*     */       desc = MappingUtil.mapDesc(desc, nsDstNameBySrcName);
/*     */     } 
/*     */     super.visitDstDesc(targetKind, namespace, desc);
/*     */   }
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     if (targetKind == MappedElementKind.CLASS && this.pass > 1) {
/*     */       String[] dstNames = this.dstNamesBySrcName.get(this.srcName);
/*     */       for (int ns = 0; ns < dstNames.length; ns++) {
/*     */         String dstName = dstNames[ns];
/*     */         if (this.pass == 2) {
/*     */           if (dstName == null) {
/*     */             String[] parts = this.srcName.split(Pattern.quote("$"));
/*     */             for (int pos = parts.length - 2; pos >= 0; pos--) {
/*     */               String outerSrcName = String.join("$", Arrays.<CharSequence>copyOfRange((CharSequence[])parts, 0, pos + 1));
/*     */               String outerDstName = ((String[])this.dstNamesBySrcName.get(outerSrcName))[ns];
/*     */               if (outerDstName != null) {
/*     */                 dstName = outerDstName + "$" + String.join("$", Arrays.<CharSequence>copyOfRange((CharSequence[])parts, pos + 1, parts.length));
/*     */                 dstNames[ns] = dstName;
/*     */                 this.modifiedClasses.add(this.srcName);
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else if (!this.visitedDstName[ns] && dstName != null) {
/*     */           super.visitDstName(targetKind, ns, dstName);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     if (this.pass < 3)
/*     */       return false; 
/*     */     Arrays.fill(this.visitedDstName, false);
/*     */     return super.visitElementContent(targetKind);
/*     */   }
/*     */   
/*     */   public boolean visitEnd() throws IOException {
/*     */     if (this.pass++ < 3)
/*     */       return false; 
/*     */     return super.visitEnd();
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\OuterClassNamePropagator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */