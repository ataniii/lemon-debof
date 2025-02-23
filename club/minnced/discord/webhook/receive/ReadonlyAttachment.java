/*     */ package club.minnced.discord.webhook.receive;
/*     */ 
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONPropertyName;
/*     */ import org.json.JSONString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadonlyAttachment
/*     */   implements JSONString
/*     */ {
/*     */   private final String url;
/*     */   private final String proxyUrl;
/*     */   private final String fileName;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int size;
/*     */   private final long id;
/*     */   
/*     */   public ReadonlyAttachment(@NotNull String url, @NotNull String proxyUrl, @NotNull String fileName, int width, int height, int size, long id) {
/*  40 */     this.url = url;
/*  41 */     this.proxyUrl = proxyUrl;
/*  42 */     this.fileName = fileName;
/*  43 */     this.width = width;
/*  44 */     this.height = height;
/*  45 */     this.size = size;
/*  46 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getUrl() {
/*  56 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyName("proxy_url")
/*     */   @NotNull
/*     */   public String getProxyUrl() {
/*  68 */     return this.proxyUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyName("filename")
/*     */   @NotNull
/*     */   public String getFileName() {
/*  79 */     return this.fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  88 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  97 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 106 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/* 115 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     return toJSONString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 130 */     return (new JSONObject(this)).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\receive\ReadonlyAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */