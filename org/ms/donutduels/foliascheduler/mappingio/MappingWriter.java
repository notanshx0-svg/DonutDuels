/*    */ package org.ms.donutduels.foliascheduler.mappingio;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.MappingFormat;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.enigma.EnigmaDirWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.enigma.EnigmaFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.intellij.MigrationMapFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.jobf.JobfFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.proguard.ProGuardFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.simple.RecafSimpleFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.CsrgFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.JamFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.SrgFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.srg.TsrgFileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.tiny.Tiny1FileWriter;
/*    */ import org.ms.donutduels.foliascheduler.mappingio.format.tiny.Tiny2FileWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface MappingWriter
/*    */   extends Closeable, MappingVisitor
/*    */ {
/*    */   @Nullable
/*    */   static MappingWriter create(Path file, MappingFormat format) throws IOException {
/* 44 */     if (format.hasSingleFile()) {
/* 45 */       return create(Files.newBufferedWriter(file, new java.nio.file.OpenOption[0]), format);
/*    */     }
/* 47 */     switch (format) { case ENIGMA_DIR:
/* 48 */         return (MappingWriter)new EnigmaDirWriter(file, true); }
/* 49 */      return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   static MappingWriter create(Writer writer, MappingFormat format) throws IOException {
/* 56 */     if (!format.hasSingleFile()) throw new IllegalArgumentException("format " + format + " is not applicable to a single writer");
/*    */     
/* 58 */     switch (format) { case TINY_FILE:
/* 59 */         return (MappingWriter)new Tiny1FileWriter(writer);
/* 60 */       case TINY_2_FILE: return (MappingWriter)new Tiny2FileWriter(writer, false);
/* 61 */       case ENIGMA_FILE: return (MappingWriter)new EnigmaFileWriter(writer);
/* 62 */       case SRG_FILE: return (MappingWriter)new SrgFileWriter(writer, false);
/* 63 */       case XSRG_FILE: return (MappingWriter)new SrgFileWriter(writer, true);
/* 64 */       case JAM_FILE: return (MappingWriter)new JamFileWriter(writer);
/* 65 */       case CSRG_FILE: return (MappingWriter)new CsrgFileWriter(writer);
/* 66 */       case TSRG_FILE: return (MappingWriter)new TsrgFileWriter(writer, false);
/* 67 */       case TSRG_2_FILE: return (MappingWriter)new TsrgFileWriter(writer, true);
/* 68 */       case PROGUARD_FILE: return (MappingWriter)new ProGuardFileWriter(writer);
/* 69 */       case INTELLIJ_MIGRATION_MAP_FILE: return (MappingWriter)new MigrationMapFileWriter(writer);
/* 70 */       case RECAF_SIMPLE_FILE: return (MappingWriter)new RecafSimpleFileWriter(writer);
/* 71 */       case JOBF_FILE: return (MappingWriter)new JobfFileWriter(writer); }
/* 72 */      return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean visitEnd() throws IOException {
/* 78 */     close();
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\PerfPlugins\DonutDuels-1.5.jar!\org\ms\donutduels\foliascheduler\mappingio\MappingWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */