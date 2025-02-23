/*    */ package club.minnced.discord.webhook.send;
/*    */ 
/*    */ import club.minnced.discord.webhook.IOUtil;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageAttachment
/*    */ {
/*    */   private final String name;
/*    */   private final byte[] data;
/*    */   
/*    */   MessageAttachment(@NotNull String name, @NotNull byte[] data) {
/* 35 */     this.name = name;
/* 36 */     this.data = data;
/*    */   }
/*    */   
/*    */   MessageAttachment(@NotNull String name, @NotNull InputStream stream) throws IOException {
/* 40 */     this.name = name;
/* 41 */     try (InputStream data = stream) {
/* 42 */       this.data = IOUtil.readAllBytes(data);
/*    */     } 
/*    */   }
/*    */   
/*    */   MessageAttachment(@NotNull String name, @NotNull File file) throws IOException {
/* 47 */     this(name, new FileInputStream(file));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getName() {
/* 52 */     return this.name;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public byte[] getData() {
/* 57 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\MessageAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */