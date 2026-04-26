/*     */ package org.ms.donutduels.foliascheduler.mappingio.format;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Experimental
/*     */ public class FeatureSetBuilder
/*     */ {
/*     */   private boolean hasNamespaces;
/*     */   private FeatureSet.MetadataSupport fileMetadata;
/*     */   private FeatureSet.MetadataSupport elementMetadata;
/*     */   private NameFeatureBuilder packages;
/*     */   private ClassSupportBuilder classes;
/*     */   private MemberSupportBuilder fields;
/*     */   private MemberSupportBuilder methods;
/*     */   private LocalSupportBuilder args;
/*     */   private LocalSupportBuilder vars;
/*     */   private FeatureSet.ElementCommentSupport elementComments;
/*     */   private boolean hasFileComments;
/*     */   
/*     */   public static FeatureSetBuilder create() {
/*  40 */     return new FeatureSetBuilder(false);
/*     */   }
/*     */   
/*     */   public static FeatureSetBuilder createFrom(FeatureSet featureSet) {
/*  44 */     return new FeatureSetBuilder(featureSet
/*  45 */         .hasNamespaces(), featureSet
/*  46 */         .fileMetadata(), featureSet
/*  47 */         .elementMetadata(), new NameFeatureBuilder(featureSet
/*  48 */           .packages()), new ClassSupportBuilder(featureSet
/*  49 */           .classes()), new MemberSupportBuilder(featureSet
/*  50 */           .fields()), new MemberSupportBuilder(featureSet
/*  51 */           .methods()), new LocalSupportBuilder(featureSet
/*  52 */           .args()), new LocalSupportBuilder(featureSet
/*  53 */           .vars()), featureSet
/*  54 */         .elementComments(), featureSet
/*  55 */         .hasFileComments());
/*     */   }
/*     */   
/*     */   FeatureSetBuilder(boolean initWithFullSupport) {
/*  59 */     this(initWithFullSupport, 
/*  60 */         initWithFullSupport ? FeatureSet.MetadataSupport.ARBITRARY : FeatureSet.MetadataSupport.NONE, 
/*  61 */         initWithFullSupport ? FeatureSet.MetadataSupport.ARBITRARY : FeatureSet.MetadataSupport.NONE, new NameFeatureBuilder(initWithFullSupport), new ClassSupportBuilder(initWithFullSupport), new MemberSupportBuilder(initWithFullSupport), new MemberSupportBuilder(initWithFullSupport), new LocalSupportBuilder(initWithFullSupport), new LocalSupportBuilder(initWithFullSupport), 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  68 */         initWithFullSupport ? FeatureSet.ElementCommentSupport.NAMESPACED : FeatureSet.ElementCommentSupport.NONE, initWithFullSupport);
/*     */   }
/*     */ 
/*     */   
/*     */   FeatureSetBuilder(boolean hasNamespaces, FeatureSet.MetadataSupport fileMetadata, FeatureSet.MetadataSupport elementMetadata, NameFeatureBuilder packages, ClassSupportBuilder classes, MemberSupportBuilder fields, MemberSupportBuilder methods, LocalSupportBuilder args, LocalSupportBuilder vars, FeatureSet.ElementCommentSupport elementComments, boolean hasFileComments) {
/*  73 */     this.hasNamespaces = hasNamespaces;
/*  74 */     this.fileMetadata = fileMetadata;
/*  75 */     this.elementMetadata = elementMetadata;
/*  76 */     this.packages = packages;
/*  77 */     this.classes = classes;
/*  78 */     this.fields = fields;
/*  79 */     this.methods = methods;
/*  80 */     this.args = args;
/*  81 */     this.vars = vars;
/*  82 */     this.elementComments = elementComments;
/*  83 */     this.hasFileComments = hasFileComments;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withNamespaces(boolean value) {
/*  87 */     this.hasNamespaces = value;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withFileMetadata(FeatureSet.MetadataSupport featurePresence) {
/*  92 */     this.fileMetadata = featurePresence;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withElementMetadata(FeatureSet.MetadataSupport featurePresence) {
/*  97 */     this.elementMetadata = featurePresence;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withPackages(Consumer<NameFeatureBuilder> featureApplier) {
/* 102 */     featureApplier.accept(this.packages);
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withClasses(Consumer<ClassSupportBuilder> featureApplier) {
/* 107 */     featureApplier.accept(this.classes);
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withFields(Consumer<MemberSupportBuilder> featureApplier) {
/* 112 */     featureApplier.accept(this.fields);
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withMethods(Consumer<MemberSupportBuilder> featureApplier) {
/* 117 */     featureApplier.accept(this.methods);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withArgs(Consumer<LocalSupportBuilder> featureApplier) {
/* 122 */     featureApplier.accept(this.args);
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withVars(Consumer<LocalSupportBuilder> featureApplier) {
/* 127 */     featureApplier.accept(this.vars);
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withElementComments(FeatureSet.ElementCommentSupport featurePresence) {
/* 132 */     this.elementComments = featurePresence;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSetBuilder withFileComments(boolean value) {
/* 137 */     this.hasFileComments = value;
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   public FeatureSet build() {
/* 142 */     return new FeatureSetImpl(this.hasNamespaces, this.fileMetadata, this.elementMetadata, this.packages
/*     */ 
/*     */ 
/*     */         
/* 146 */         .build(), this.classes
/* 147 */         .build(), this.fields
/* 148 */         .build(), this.methods
/* 149 */         .build(), this.args
/* 150 */         .build(), this.vars
/* 151 */         .build(), this.elementComments, this.hasFileComments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ClassSupportBuilder
/*     */   {
/*     */     private FeatureSetBuilder.NameFeatureBuilder names;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasRepackaging;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ClassSupportBuilder() {
/* 170 */       this(false);
/*     */     }
/*     */     
/*     */     ClassSupportBuilder(boolean initWithFullSupport) {
/* 174 */       this(new FeatureSetBuilder.NameFeatureBuilder(initWithFullSupport), initWithFullSupport);
/*     */     }
/*     */     
/*     */     ClassSupportBuilder(FeatureSet.ClassSupport classFeature) {
/* 178 */       this(new FeatureSetBuilder.NameFeatureBuilder(classFeature, null), classFeature.hasRepackaging());
/*     */     }
/*     */     
/*     */     private ClassSupportBuilder(FeatureSetBuilder.NameFeatureBuilder names, boolean hasRepackaging) {
/* 182 */       this.names = names;
/* 183 */       this.hasRepackaging = hasRepackaging;
/*     */     }
/*     */     
/*     */     public ClassSupportBuilder withSrcNames(FeatureSet.FeaturePresence featurePresence) {
/* 187 */       this.names.withSrcNames(featurePresence);
/* 188 */       return this;
/*     */     }
/*     */     
/*     */     public ClassSupportBuilder withDstNames(FeatureSet.FeaturePresence featurePresence) {
/* 192 */       this.names.withDstNames(featurePresence);
/* 193 */       return this;
/*     */     }
/*     */     
/*     */     public ClassSupportBuilder withRepackaging(boolean value) {
/* 197 */       this.hasRepackaging = value;
/* 198 */       return this;
/*     */     }
/*     */     
/*     */     public FeatureSet.ClassSupport build() {
/* 202 */       return new FeatureSetImpl.ClassSupportImpl(this.names.build(), this.hasRepackaging);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MemberSupportBuilder {
/*     */     private FeatureSetBuilder.NameFeatureBuilder names;
/*     */     private FeatureSetBuilder.DescFeatureBuilder descriptors;
/*     */     
/*     */     MemberSupportBuilder() {
/* 211 */       this(false);
/*     */     }
/*     */     
/*     */     MemberSupportBuilder(boolean initWithFullSupport) {
/* 215 */       this(new FeatureSetBuilder.NameFeatureBuilder(initWithFullSupport), new FeatureSetBuilder.DescFeatureBuilder(initWithFullSupport));
/*     */     }
/*     */     
/*     */     MemberSupportBuilder(FeatureSet.MemberSupport memberSupport) {
/* 219 */       this(new FeatureSetBuilder.NameFeatureBuilder(memberSupport, null), new FeatureSetBuilder.DescFeatureBuilder(memberSupport, null));
/*     */     }
/*     */     
/*     */     private MemberSupportBuilder(FeatureSetBuilder.NameFeatureBuilder names, FeatureSetBuilder.DescFeatureBuilder descriptors) {
/* 223 */       this.names = names;
/* 224 */       this.descriptors = descriptors;
/*     */     }
/*     */     
/*     */     public MemberSupportBuilder withSrcNames(FeatureSet.FeaturePresence featurePresence) {
/* 228 */       this.names.withSrcNames(featurePresence);
/* 229 */       return this;
/*     */     }
/*     */     
/*     */     public MemberSupportBuilder withDstNames(FeatureSet.FeaturePresence featurePresence) {
/* 233 */       this.names.withDstNames(featurePresence);
/* 234 */       return this;
/*     */     }
/*     */     
/*     */     public MemberSupportBuilder withSrcDescs(FeatureSet.FeaturePresence featurePresence) {
/* 238 */       this.descriptors.withSrcDescs(featurePresence);
/* 239 */       return this;
/*     */     }
/*     */     
/*     */     public MemberSupportBuilder withDstDescs(FeatureSet.FeaturePresence featurePresence) {
/* 243 */       this.descriptors.withDstDescs(featurePresence);
/* 244 */       return this;
/*     */     }
/*     */     
/*     */     public FeatureSet.MemberSupport build() {
/* 248 */       return new FeatureSetImpl.MemberSupportImpl(this.names.build(), this.descriptors.build());
/*     */     } }
/*     */   
/*     */   public static class LocalSupportBuilder {
/*     */     private FeatureSet.FeaturePresence positions;
/*     */     private FeatureSet.FeaturePresence lvIndices;
/*     */     private FeatureSet.FeaturePresence lvtRowIndices;
/*     */     
/*     */     LocalSupportBuilder() {
/* 257 */       this(false);
/*     */     }
/*     */     private FeatureSet.FeaturePresence startOpIndices; private FeatureSet.FeaturePresence endOpIndices; private FeatureSetBuilder.NameFeatureBuilder names; private FeatureSetBuilder.DescFeatureBuilder descriptors;
/*     */     LocalSupportBuilder(boolean initWithFullSupport) {
/* 261 */       this(initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 262 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 263 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 264 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 265 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, new FeatureSetBuilder.NameFeatureBuilder(), new FeatureSetBuilder.DescFeatureBuilder());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     LocalSupportBuilder(FeatureSet.LocalSupport localSupport) {
/* 271 */       this(localSupport.positions(), localSupport
/* 272 */           .lvIndices(), localSupport
/* 273 */           .lvtRowIndices(), localSupport
/* 274 */           .startOpIndices(), localSupport
/* 275 */           .endOpIndices(), new FeatureSetBuilder.NameFeatureBuilder(localSupport, null), new FeatureSetBuilder.DescFeatureBuilder(localSupport, null));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private LocalSupportBuilder(FeatureSet.FeaturePresence positions, FeatureSet.FeaturePresence lvIndices, FeatureSet.FeaturePresence lvtRowIndices, FeatureSet.FeaturePresence startOpIndices, FeatureSet.FeaturePresence endOpIndices, FeatureSetBuilder.NameFeatureBuilder names, FeatureSetBuilder.DescFeatureBuilder descriptors) {
/* 281 */       this.positions = positions;
/* 282 */       this.lvIndices = lvIndices;
/* 283 */       this.lvtRowIndices = lvtRowIndices;
/* 284 */       this.startOpIndices = startOpIndices;
/* 285 */       this.endOpIndices = endOpIndices;
/* 286 */       this.names = names;
/* 287 */       this.descriptors = descriptors;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withPositions(FeatureSet.FeaturePresence featurePresence) {
/* 291 */       this.positions = featurePresence;
/* 292 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withLvIndices(FeatureSet.FeaturePresence featurePresence) {
/* 296 */       this.lvIndices = featurePresence;
/* 297 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withLvtRowIndices(FeatureSet.FeaturePresence featurePresence) {
/* 301 */       this.lvtRowIndices = featurePresence;
/* 302 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withStartOpIndices(FeatureSet.FeaturePresence featurePresence) {
/* 306 */       this.startOpIndices = featurePresence;
/* 307 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withEndOpIndices(FeatureSet.FeaturePresence featurePresence) {
/* 311 */       this.endOpIndices = featurePresence;
/* 312 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withSrcNames(FeatureSet.FeaturePresence featurePresence) {
/* 316 */       this.names.withSrcNames(featurePresence);
/* 317 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withDstNames(FeatureSet.FeaturePresence featurePresence) {
/* 321 */       this.names.withDstNames(featurePresence);
/* 322 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withSrcDescs(FeatureSet.FeaturePresence featurePresence) {
/* 326 */       this.descriptors.withSrcDescs(featurePresence);
/* 327 */       return this;
/*     */     }
/*     */     
/*     */     public LocalSupportBuilder withDstDescs(FeatureSet.FeaturePresence featurePresence) {
/* 331 */       this.descriptors.withDstDescs(featurePresence);
/* 332 */       return this;
/*     */     }
/*     */     
/*     */     public FeatureSet.LocalSupport build() {
/* 336 */       return new FeatureSetImpl.LocalSupportImpl(this.positions, this.lvIndices, this.lvtRowIndices, this.startOpIndices, this.endOpIndices, this.names
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 342 */           .build(), this.descriptors
/* 343 */           .build());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NameFeatureBuilder
/*     */   {
/*     */     private FeatureSet.FeaturePresence srcNames;
/*     */     
/*     */     private FeatureSet.FeaturePresence dstNames;
/*     */ 
/*     */     
/*     */     NameFeatureBuilder() {
/* 357 */       this(false);
/*     */     }
/*     */     
/*     */     NameFeatureBuilder(boolean initWithFullSupport) {
/* 361 */       this(initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 362 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT);
/*     */     }
/*     */     
/*     */     private NameFeatureBuilder(FeatureSet.NameSupport nameFeature) {
/* 366 */       this(nameFeature.srcNames(), nameFeature.dstNames());
/*     */     }
/*     */     
/*     */     private NameFeatureBuilder(FeatureSet.FeaturePresence srcNames, FeatureSet.FeaturePresence dstNames) {
/* 370 */       this.srcNames = srcNames;
/* 371 */       this.dstNames = dstNames;
/*     */     }
/*     */     
/*     */     public NameFeatureBuilder withSrcNames(FeatureSet.FeaturePresence featurePresence) {
/* 375 */       this.srcNames = featurePresence;
/* 376 */       return this;
/*     */     }
/*     */     
/*     */     public NameFeatureBuilder withDstNames(FeatureSet.FeaturePresence featurePresence) {
/* 380 */       this.dstNames = featurePresence;
/* 381 */       return this;
/*     */     }
/*     */     
/*     */     public FeatureSet.NameSupport build() {
/* 385 */       return new FeatureSetImpl.NameSupportImpl(this.srcNames, this.dstNames);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DescFeatureBuilder {
/*     */     private FeatureSet.FeaturePresence srcDescriptors;
/*     */     private FeatureSet.FeaturePresence dstDescriptors;
/*     */     
/*     */     DescFeatureBuilder() {
/* 394 */       this(false);
/*     */     }
/*     */     
/*     */     DescFeatureBuilder(boolean initWithFullSupport) {
/* 398 */       this(initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT, 
/* 399 */           initWithFullSupport ? FeatureSet.FeaturePresence.OPTIONAL : FeatureSet.FeaturePresence.ABSENT);
/*     */     }
/*     */     
/*     */     private DescFeatureBuilder(FeatureSet.DescSupport descFeature) {
/* 403 */       this(descFeature.srcDescs(), descFeature.dstDescs());
/*     */     }
/*     */     
/*     */     private DescFeatureBuilder(FeatureSet.FeaturePresence srcDescriptors, FeatureSet.FeaturePresence dstDescriptors) {
/* 407 */       this.srcDescriptors = srcDescriptors;
/* 408 */       this.dstDescriptors = dstDescriptors;
/*     */     }
/*     */     
/*     */     public DescFeatureBuilder withSrcDescs(FeatureSet.FeaturePresence featurePresence) {
/* 412 */       this.srcDescriptors = featurePresence;
/* 413 */       return this;
/*     */     }
/*     */     
/*     */     public DescFeatureBuilder withDstDescs(FeatureSet.FeaturePresence featurePresence) {
/* 417 */       this.dstDescriptors = featurePresence;
/* 418 */       return this;
/*     */     }
/*     */     
/*     */     public FeatureSet.DescSupport build() {
/* 422 */       return new FeatureSetImpl.DescSupportImpl(this.srcDescriptors, this.dstDescriptors);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\FeatureSetBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */