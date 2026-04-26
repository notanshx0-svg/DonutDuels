/*     */ package org.ms.donutduels.foliascheduler.mappingio.format.intellij;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.stream.FactoryConfigurationError;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class MigrationMapFileWriter
/*     */   implements MappingWriter
/*     */ {
/*     */   public MigrationMapFileWriter(Writer writer) {
/*  42 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*     */     try {
/*  48 */       if (this.xmlWriter != null) {
/*  49 */         if (!this.wroteName) {
/*  50 */           this.xmlWriter.writeCharacters("\n\t");
/*  51 */           this.xmlWriter.writeEmptyElement("name");
/*  52 */           this.xmlWriter.writeAttribute("value", "Unnamed migration map");
/*     */         } 
/*     */         
/*  55 */         if (!this.wroteOrder) {
/*  56 */           this.xmlWriter.writeCharacters("\n\t");
/*  57 */           this.xmlWriter.writeEmptyElement("order");
/*  58 */           this.xmlWriter.writeAttribute("value", "0");
/*     */         } 
/*     */         
/*  61 */         this.xmlWriter.writeCharacters("\n");
/*  62 */         this.xmlWriter.writeEndDocument();
/*  63 */         this.xmlWriter.writeCharacters("\n");
/*  64 */         this.xmlWriter.close();
/*     */       } 
/*  66 */     } catch (XMLStreamException e) {
/*  67 */       throw new IOException(e);
/*     */     } finally {
/*  69 */       this.writer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MappingFlag> getFlags() {
/*  75 */     return flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitHeader() throws IOException {
/*  80 */     assert this.xmlWriter == null;
/*     */     
/*     */     try {
/*  83 */       this.xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(this.writer);
/*     */       
/*  85 */       this.xmlWriter.writeStartDocument("UTF-8", "1.0");
/*  86 */       this.xmlWriter.writeCharacters("\n");
/*  87 */       this.xmlWriter.writeStartElement("migrationMap");
/*  88 */     } catch (FactoryConfigurationError|XMLStreamException e) {
/*  89 */       throw new IOException(e);
/*     */     } 
/*     */     
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitNamespaces(String srcNamespace, List<String> dstNamespaces) throws IOException {}
/*     */ 
/*     */   
/*     */   public void visitMetadata(String key, @Nullable String value) throws IOException {
/*     */     try {
/* 102 */       switch (key) {
/*     */         case "name":
/* 104 */           this.wroteName = true;
/*     */           break;
/*     */         case "migrationmap:order":
/* 107 */           this.wroteOrder = true;
/* 108 */           key = "order";
/*     */           break;
/*     */       } 
/*     */       
/* 112 */       this.xmlWriter.writeCharacters("\n\t");
/* 113 */       this.xmlWriter.writeEmptyElement(key);
/* 114 */       this.xmlWriter.writeAttribute("value", value);
/* 115 */     } catch (XMLStreamException e) {
/* 116 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitClass(String srcName) throws IOException {
/* 122 */     this.srcName = srcName;
/* 123 */     this.dstName = null;
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitField(String srcName, @Nullable String srcDesc) throws IOException {
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethod(String srcName, @Nullable String srcDesc) throws IOException {
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodArg(int argPosition, int lvIndex, @Nullable String srcName) throws IOException {
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitMethodVar(int lvtRowIndex, int lvIndex, int startOpIdx, int endOpIdx, @Nullable String srcName) throws IOException {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitDstName(MappedElementKind targetKind, int namespace, String name) {
/* 150 */     if (namespace != 0)
/*     */       return; 
/* 152 */     this.dstName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean visitElementContent(MappedElementKind targetKind) throws IOException {
/* 157 */     if (this.dstName == null) return false;
/*     */     
/*     */     try {
/* 160 */       this.xmlWriter.writeCharacters("\n\t");
/* 161 */       this.xmlWriter.writeEmptyElement("entry");
/* 162 */       this.xmlWriter.writeAttribute("oldName", this.srcName.replace('/', '.'));
/* 163 */       this.xmlWriter.writeAttribute("newName", this.dstName.replace('/', '.'));
/* 164 */       this.xmlWriter.writeAttribute("type", "class");
/* 165 */     } catch (XMLStreamException e) {
/* 166 */       throw new IOException(e);
/*     */     } 
/*     */     
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitComment(MappedElementKind targetKind, String comment) throws IOException {}
/*     */ 
/*     */   
/* 177 */   private static final Set<MappingFlag> flags = EnumSet.of(MappingFlag.NEEDS_ELEMENT_UNIQUENESS);
/*     */   private final Writer writer;
/*     */   private XMLStreamWriter xmlWriter;
/*     */   private boolean wroteName;
/*     */   private boolean wroteOrder;
/*     */   private String srcName;
/*     */   private String dstName;
/*     */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\format\intellij\MigrationMapFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */