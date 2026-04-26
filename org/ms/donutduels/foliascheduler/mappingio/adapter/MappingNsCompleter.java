/*     */ package org.ms.donutduels.foliascheduler.mappingio.adapter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.ms.donutduels.foliascheduler.mappingio.MappedElementKind;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MappingNsCompleter
/*     */   extends ForwardingMappingVisitor
/*     */ {
/*     */   private final Map<String, String> alternatives;
/*     */   private final boolean addMissingNs;
/*     */   private int[] alternativesMapping;
/*     */   private String srcName;
/*     */   private String[] dstNames;
/*     */   private boolean relayHeaderOrMetadata;
/*     */   
/*     */   public MappingNsCompleter(MappingVisitor next, Map<String, String> alternatives) {
/*  42 */     this(next, alternatives, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingNsCompleter(MappingVisitor next, Map<String, String> alternatives, boolean addMissingNs) {
/*  51 */     super(next);
/*     */     
/*  53 */     this.alternatives = alternatives;
/*  54 */     this.addMissingNs = addMissingNs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitHeader() throws IOException {
/*  59 */     this.relayHeaderOrMetadata = this.next.visitHeader();
/*     */     
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {
/*  66 */     if (this.addMissingNs) {
/*  67 */       boolean copied = false;
/*     */       
/*  69 */       for (String ns : this.alternatives.keySet()) {
/*  70 */         if (ns.equals(srcNamespace) || dstNamespaces.contains(ns)) {
/*     */           continue;
/*     */         }
/*     */         
/*  74 */         if (!copied) {
/*  75 */           dstNamespaces = new ArrayList<>(dstNamespaces);
/*  76 */           copied = true;
/*     */         } 
/*     */         
/*  79 */         dstNamespaces.add(ns);
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     int count = dstNamespaces.size();
/*  84 */     this.alternativesMapping = new int[count];
/*  85 */     this.dstNames = new String[count];
/*     */     
/*  87 */     for (int i = 0; i < count; i++) {
/*  88 */       int srcIdx; String src = this.alternatives.get(dstNamespaces.get(i));
/*     */ 
/*     */       
/*  91 */       if (src == null) {
/*  92 */         srcIdx = i;
/*  93 */       } else if (src.equals(srcNamespace)) {
/*  94 */         srcIdx = -1;
/*     */       } else {
/*  96 */         srcIdx = dstNamespaces.indexOf(src);
/*  97 */         if (srcIdx < 0) throw new RuntimeException("invalid alternative mapping ns " + src + ": not in " + dstNamespaces + " or " + srcNamespace);
/*     */       
/*     */       } 
/* 100 */       this.alternativesMapping[i] = srcIdx;
/*     */     } 
/*     */     
/* 103 */     if (this.relayHeaderOrMetadata) this.next.visitNamespaces(srcNamespace, dstNamespaces);
/*     */   
/*     */   }
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/* 108 */     if (this.relayHeaderOrMetadata) this.next.visitMetadata(key, value);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean visitContent() throws IOException {
/* 113 */     this.relayHeaderOrMetadata = true;
/*     */     
/* 115 */     return this.next.visitContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/* 120 */     this.srcName = srcName;
/*     */     
/* 122 */     return this.next.visitClass(srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 127 */     this.srcName = srcName;
/*     */     
/* 129 */     return this.next.visitField(srcName, srcDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 134 */     this.srcName = srcName;
/*     */     
/* 136 */     return this.next.visitMethod(srcName, srcDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/* 141 */     this.srcName = srcName;
/*     */     
/* 143 */     return this.next.visitMethodArg(argPosition, lvIndex, srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/* 148 */     this.srcName = srcName;
/*     */     
/* 150 */     return this.next.visitMethodVar(lvtRowIndex, lvIndex, startOpIdx, endOpIdx, srcName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/* 155 */     this.dstNames[namespace] = name;
/*     */   }
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: iload_2
/*     */     //   3: aload_0
/*     */     //   4: getfield dstNames : [Ljava/lang/String;
/*     */     //   7: arraylength
/*     */     //   8: if_icmpge -> 139
/*     */     //   11: aload_0
/*     */     //   12: getfield dstNames : [Ljava/lang/String;
/*     */     //   15: iload_2
/*     */     //   16: aaload
/*     */     //   17: astore_3
/*     */     //   18: aload_3
/*     */     //   19: ifnonnull -> 121
/*     */     //   22: iload_2
/*     */     //   23: istore #4
/*     */     //   25: lconst_1
/*     */     //   26: iload #4
/*     */     //   28: lshl
/*     */     //   29: lstore #5
/*     */     //   31: aload_0
/*     */     //   32: getfield alternativesMapping : [I
/*     */     //   35: iload #4
/*     */     //   37: iaload
/*     */     //   38: istore #7
/*     */     //   40: iload #7
/*     */     //   42: ifge -> 53
/*     */     //   45: aload_0
/*     */     //   46: getfield srcName : Ljava/lang/String;
/*     */     //   49: astore_3
/*     */     //   50: goto -> 103
/*     */     //   53: iload #7
/*     */     //   55: iload #4
/*     */     //   57: if_icmpne -> 63
/*     */     //   60: goto -> 133
/*     */     //   63: lload #5
/*     */     //   65: lconst_1
/*     */     //   66: iload #7
/*     */     //   68: lshl
/*     */     //   69: land
/*     */     //   70: lconst_0
/*     */     //   71: lcmp
/*     */     //   72: ifeq -> 78
/*     */     //   75: goto -> 133
/*     */     //   78: iload #7
/*     */     //   80: istore #4
/*     */     //   82: aload_0
/*     */     //   83: getfield dstNames : [Ljava/lang/String;
/*     */     //   86: iload #4
/*     */     //   88: aaload
/*     */     //   89: astore_3
/*     */     //   90: lload #5
/*     */     //   92: lconst_1
/*     */     //   93: iload #4
/*     */     //   95: lshl
/*     */     //   96: lor
/*     */     //   97: lstore #5
/*     */     //   99: aload_3
/*     */     //   100: ifnull -> 31
/*     */     //   103: getstatic org/ms/donutduels/foliascheduler/mappingio/adapter/MappingNsCompleter.$assertionsDisabled : Z
/*     */     //   106: ifne -> 121
/*     */     //   109: aload_3
/*     */     //   110: ifnonnull -> 121
/*     */     //   113: new java/lang/AssertionError
/*     */     //   116: dup
/*     */     //   117: invokespecial <init> : ()V
/*     */     //   120: athrow
/*     */     //   121: aload_0
/*     */     //   122: getfield next : Lorg/ms/donutduels/foliascheduler/mappingio/MappingVisitor;
/*     */     //   125: aload_1
/*     */     //   126: iload_2
/*     */     //   127: aload_3
/*     */     //   128: invokeinterface visitDstName : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;ILjava/lang/String;)V
/*     */     //   133: iinc #2, 1
/*     */     //   136: goto -> 2
/*     */     //   139: aload_0
/*     */     //   140: getfield dstNames : [Ljava/lang/String;
/*     */     //   143: aconst_null
/*     */     //   144: invokestatic fill : ([Ljava/lang/Object;Ljava/lang/Object;)V
/*     */     //   147: aload_0
/*     */     //   148: getfield next : Lorg/ms/donutduels/foliascheduler/mappingio/MappingVisitor;
/*     */     //   151: aload_1
/*     */     //   152: invokeinterface visitElementContent : (Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;)Z
/*     */     //   157: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #160	-> 0
/*     */     //   #161	-> 11
/*     */     //   #163	-> 18
/*     */     //   #164	-> 22
/*     */     //   #165	-> 25
/*     */     //   #168	-> 31
/*     */     //   #170	-> 40
/*     */     //   #171	-> 45
/*     */     //   #172	-> 50
/*     */     //   #173	-> 53
/*     */     //   #174	-> 60
/*     */     //   #175	-> 63
/*     */     //   #176	-> 75
/*     */     //   #178	-> 78
/*     */     //   #179	-> 82
/*     */     //   #180	-> 90
/*     */     //   #182	-> 99
/*     */     //   #184	-> 103
/*     */     //   #187	-> 121
/*     */     //   #160	-> 133
/*     */     //   #190	-> 139
/*     */     //   #192	-> 147
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   40	59	7	newSrc	I
/*     */     //   25	96	4	src	I
/*     */     //   31	90	5	visited	J
/*     */     //   18	115	3	name	Ljava/lang/String;
/*     */     //   2	137	2	i	I
/*     */     //   0	158	0	this	Lorg/ms/donutduels/foliascheduler/mappingio/adapter/MappingNsCompleter;
/*     */     //   0	158	1	targetKind	Lorg/ms/donutduels/foliascheduler/mappingio/MappedElementKind;
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\adapter\MappingNsCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */