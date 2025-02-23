/*     */ package club.minnced.discord.webhook.receive;
/*     */ 
/*     */ import club.minnced.discord.webhook.send.WebhookEmbed;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ 
/*     */ 
/*     */ public class ReadonlyEmbed
/*     */   extends WebhookEmbed
/*     */ {
/*     */   private final EmbedProvider provider;
/*     */   private final EmbedImage thumbnail;
/*     */   private final EmbedImage image;
/*     */   private final EmbedVideo video;
/*     */   
/*     */   public ReadonlyEmbed(@Nullable OffsetDateTime timestamp, @Nullable Integer color, @Nullable String description, @Nullable EmbedImage thumbnail, @Nullable EmbedImage image, @Nullable WebhookEmbed.EmbedFooter footer, @Nullable WebhookEmbed.EmbedTitle title, @Nullable WebhookEmbed.EmbedAuthor author, @NotNull List<WebhookEmbed.EmbedField> fields, @Nullable EmbedProvider provider, @Nullable EmbedVideo video) {
/*  43 */     super(timestamp, color, description, (thumbnail == null) ? null : thumbnail
/*  44 */         .getUrl(), (image == null) ? null : image
/*  45 */         .getUrl(), footer, title, author, fields);
/*     */     
/*  47 */     this.thumbnail = thumbnail;
/*  48 */     this.image = image;
/*  49 */     this.provider = provider;
/*  50 */     this.video = video;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EmbedProvider getProvider() {
/*  62 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EmbedImage getThumbnail() {
/*  72 */     return this.thumbnail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EmbedImage getImage() {
/*  82 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EmbedVideo getVideo() {
/*  94 */     return this.video;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookEmbed reduced() {
/* 107 */     return new WebhookEmbed(
/* 108 */         getTimestamp(), getColor(), getDescription(), (this.thumbnail == null) ? null : this.thumbnail
/* 109 */         .getUrl(), (this.image == null) ? null : this.image
/* 110 */         .getUrl(), 
/* 111 */         getFooter(), getTitle(), getAuthor(), getFields());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     return toJSONString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 127 */     JSONObject base = new JSONObject(super.toJSONString());
/* 128 */     base.put("provider", this.provider)
/* 129 */       .put("thumbnail", this.thumbnail)
/* 130 */       .put("video", this.video)
/* 131 */       .put("image", this.image);
/* 132 */     if (getTitle() != null) {
/* 133 */       base.put("title", getTitle().getText());
/* 134 */       base.put("url", getTitle().getUrl());
/*     */     } 
/* 136 */     return base.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EmbedProvider
/*     */     implements JSONString
/*     */   {
/*     */     private final String name;
/*     */     
/*     */     private final String url;
/*     */     
/*     */     public EmbedProvider(@NotNull String name, @NotNull String url) {
/* 148 */       this.name = name;
/* 149 */       this.url = url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getName() {
/* 159 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getUrl() {
/* 169 */       return this.url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 179 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 184 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EmbedVideo
/*     */     implements JSONString
/*     */   {
/*     */     private final String url;
/*     */     
/*     */     private final int width;
/*     */     private final int height;
/*     */     
/*     */     public EmbedVideo(@NotNull String url, int width, int height) {
/* 198 */       this.url = url;
/* 199 */       this.width = width;
/* 200 */       this.height = height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getUrl() {
/* 210 */       return this.url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 219 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 228 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 238 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 243 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EmbedImage
/*     */     implements JSONString
/*     */   {
/*     */     private final String url;
/*     */     
/*     */     private final String proxyUrl;
/*     */     
/*     */     private final int width;
/*     */     
/*     */     private final int height;
/*     */     
/*     */     public EmbedImage(@NotNull String url, @NotNull String proxyUrl, int width, int height) {
/* 260 */       this.url = url;
/* 261 */       this.proxyUrl = proxyUrl;
/* 262 */       this.width = width;
/* 263 */       this.height = height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getUrl() {
/* 273 */       return this.url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @JSONPropertyName("proxy_url")
/*     */     @NotNull
/*     */     public String getProxyUrl() {
/* 285 */       return this.proxyUrl;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 294 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 303 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 313 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 318 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\receive\ReadonlyEmbed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */