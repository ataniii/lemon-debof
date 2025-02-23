/*     */ package club.minnced.discord.webhook.send;
/*     */ 
/*     */ import java.time.OffsetDateTime;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONPropertyIgnore;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebhookEmbed
/*     */   implements JSONString
/*     */ {
/*     */   public static final int MAX_FIELDS = 25;
/*     */   private final OffsetDateTime timestamp;
/*     */   private final Integer color;
/*     */   private final String description;
/*     */   private final String thumbnailUrl;
/*     */   private final String imageUrl;
/*     */   private final EmbedFooter footer;
/*     */   private final EmbedTitle title;
/*     */   private final EmbedAuthor author;
/*     */   private final List<EmbedField> fields;
/*     */   
/*     */   public WebhookEmbed(@Nullable OffsetDateTime timestamp, @Nullable Integer color, @Nullable String description, @Nullable String thumbnailUrl, @Nullable String imageUrl, @Nullable EmbedFooter footer, @Nullable EmbedTitle title, @Nullable EmbedAuthor author, @NotNull List<EmbedField> fields) {
/*  61 */     this.timestamp = timestamp;
/*  62 */     this.color = color;
/*  63 */     this.description = description;
/*  64 */     this.thumbnailUrl = thumbnailUrl;
/*  65 */     this.imageUrl = imageUrl;
/*  66 */     this.footer = footer;
/*  67 */     this.title = title;
/*  68 */     this.author = author;
/*  69 */     this.fields = Collections.unmodifiableList(fields);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyIgnore
/*     */   @Nullable
/*     */   public String getThumbnailUrl() {
/*  80 */     return this.thumbnailUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyIgnore
/*     */   @Nullable
/*     */   public String getImageUrl() {
/*  91 */     return this.imageUrl;
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
/*     */   public OffsetDateTime getTimestamp() {
/* 103 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @JSONPropertyIgnore
/*     */   @Nullable
/*     */   public EmbedTitle getTitle() {
/* 115 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getColor() {
/* 126 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/* 136 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EmbedFooter getFooter() {
/* 147 */     return this.footer;
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
/*     */   public EmbedAuthor getAuthor() {
/* 159 */     return this.author;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<EmbedField> getFields() {
/* 170 */     return this.fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookEmbed reduced() {
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return toJSONString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 195 */     JSONObject json = new JSONObject();
/* 196 */     if (this.description != null)
/* 197 */       json.put("description", this.description); 
/* 198 */     if (this.timestamp != null)
/* 199 */       json.put("timestamp", this.timestamp); 
/* 200 */     if (this.color != null)
/* 201 */       json.put("color", this.color.intValue() & 0xFFFFFF); 
/* 202 */     if (this.author != null)
/* 203 */       json.put("author", this.author); 
/* 204 */     if (this.footer != null)
/* 205 */       json.put("footer", this.footer); 
/* 206 */     if (this.thumbnailUrl != null)
/* 207 */       json.put("thumbnail", (new JSONObject())
/*     */           
/* 209 */           .put("url", this.thumbnailUrl)); 
/* 210 */     if (this.imageUrl != null)
/* 211 */       json.put("image", (new JSONObject())
/*     */           
/* 213 */           .put("url", this.imageUrl)); 
/* 214 */     if (!this.fields.isEmpty())
/* 215 */       json.put("fields", this.fields); 
/* 216 */     if (this.title != null) {
/* 217 */       if (this.title.getUrl() != null)
/* 218 */         json.put("url", this.title.url); 
/* 219 */       json.put("title", this.title.text);
/*     */     } 
/* 221 */     return json.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmbedField
/*     */     implements JSONString
/*     */   {
/*     */     private final boolean inline;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EmbedField(boolean inline, @NotNull String name, @NotNull String value) {
/* 247 */       this.inline = inline;
/* 248 */       this.name = Objects.<String>requireNonNull(name);
/* 249 */       this.value = Objects.<String>requireNonNull(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInline() {
/* 258 */       return this.inline;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getName() {
/* 269 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getValue() {
/* 280 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 290 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 295 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmbedAuthor
/*     */     implements JSONString
/*     */   {
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String iconUrl;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String url;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EmbedAuthor(@NotNull String name, @Nullable String iconUrl, @Nullable String url) {
/* 320 */       this.name = Objects.<String>requireNonNull(name);
/* 321 */       this.iconUrl = iconUrl;
/* 322 */       this.url = url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getName() {
/* 332 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @JSONPropertyName("icon_url")
/*     */     @Nullable
/*     */     public String getIconUrl() {
/* 344 */       return this.iconUrl;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getUrl() {
/* 356 */       return this.url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 366 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 371 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmbedFooter
/*     */     implements JSONString
/*     */   {
/*     */     private final String text;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EmbedFooter(@NotNull String text, @Nullable String icon) {
/* 394 */       this.text = Objects.<String>requireNonNull(text);
/* 395 */       this.icon = icon;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getText() {
/* 405 */       return this.text;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @JSONPropertyName("icon_url")
/*     */     @Nullable
/*     */     public String getIconUrl() {
/* 416 */       return this.icon;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 426 */       return toJSONString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toJSONString() {
/* 431 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmbedTitle
/*     */   {
/*     */     private final String text;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String url;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EmbedTitle(@NotNull String text, @Nullable String url) {
/* 453 */       this.text = Objects.<String>requireNonNull(text);
/* 454 */       this.url = url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public String getText() {
/* 464 */       return this.text;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public String getUrl() {
/* 474 */       return this.url;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 484 */       return (new JSONObject(this)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\WebhookEmbed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */