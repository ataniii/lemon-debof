/*     */ package club.minnced.discord.webhook.send;
/*     */ 
/*     */ import club.minnced.discord.webhook.IOUtil;
/*     */ import club.minnced.discord.webhook.receive.ReadonlyMessage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import okhttp3.MultipartBody;
/*     */ import okhttp3.RequestBody;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebhookMessage
/*     */ {
/*     */   public static final int MAX_FILES = 10;
/*     */   public static final int MAX_EMBEDS = 10;
/*     */   protected final String username;
/*     */   protected final String avatarUrl;
/*     */   protected final String content;
/*     */   protected final List<WebhookEmbed> embeds;
/*     */   protected final boolean isTTS;
/*     */   protected final MessageAttachment[] attachments;
/*     */   protected final AllowedMentions allowedMentions;
/*     */   
/*     */   protected WebhookMessage(String username, String avatarUrl, String content, List<WebhookEmbed> embeds, boolean isTTS, MessageAttachment[] files, AllowedMentions allowedMentions) {
/*  55 */     this.username = username;
/*  56 */     this.avatarUrl = avatarUrl;
/*  57 */     this.content = content;
/*  58 */     this.embeds = embeds;
/*  59 */     this.isTTS = isTTS;
/*  60 */     this.attachments = files;
/*  61 */     this.allowedMentions = allowedMentions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getUsername() {
/*  71 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getAvatarUrl() {
/*  81 */     return this.avatarUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getContent() {
/*  91 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<WebhookEmbed> getEmbeds() {
/* 101 */     return (this.embeds == null) ? Collections.<WebhookEmbed>emptyList() : this.embeds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MessageAttachment[] getAttachments() {
/* 111 */     return this.attachments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTTS() {
/* 120 */     return this.isTTS;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WebhookMessage from(@NotNull ReadonlyMessage message) {
/* 138 */     Objects.requireNonNull(message, "Message");
/* 139 */     WebhookMessageBuilder builder = new WebhookMessageBuilder();
/* 140 */     builder.setAvatarUrl(message.getAuthor().getAvatarId());
/* 141 */     builder.setUsername(message.getAuthor().getName());
/* 142 */     builder.setContent(message.getContent());
/* 143 */     builder.setTTS(message.isTTS());
/* 144 */     builder.addEmbeds(message.getEmbeds());
/* 145 */     return builder.build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WebhookMessage embeds(@NotNull WebhookEmbed first, @NotNull WebhookEmbed... embeds) {
/* 166 */     Objects.requireNonNull(embeds, "Embeds");
/* 167 */     if (embeds.length >= 10)
/* 168 */       throw new IllegalArgumentException("Cannot add more than 10 embeds to a message"); 
/* 169 */     for (WebhookEmbed e : embeds) {
/* 170 */       Objects.requireNonNull(e);
/*     */     }
/* 172 */     List<WebhookEmbed> list = new ArrayList<>(1 + embeds.length);
/* 173 */     list.add(first);
/* 174 */     Collections.addAll(list, embeds);
/* 175 */     return new WebhookMessage(null, null, null, list, false, null, AllowedMentions.all());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WebhookMessage embeds(@NotNull Collection<WebhookEmbed> embeds) {
/* 194 */     Objects.requireNonNull(embeds, "Embeds");
/* 195 */     if (embeds.size() > 10)
/* 196 */       throw new IllegalArgumentException("Cannot add more than 10 embeds to a message"); 
/* 197 */     if (embeds.isEmpty())
/* 198 */       throw new IllegalArgumentException("Cannot build an empty message"); 
/* 199 */     embeds.forEach(Objects::requireNonNull);
/* 200 */     return new WebhookMessage(null, null, null, new ArrayList<>(embeds), false, null, AllowedMentions.all());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WebhookMessage files(@NotNull Map<String, ?> attachments) {
/* 221 */     Objects.requireNonNull(attachments, "Attachments");
/*     */     
/* 223 */     int fileAmount = attachments.size();
/* 224 */     if (fileAmount == 0)
/* 225 */       throw new IllegalArgumentException("Cannot build an empty message"); 
/* 226 */     if (fileAmount > 10)
/* 227 */       throw new IllegalArgumentException("Cannot add more than 10 files to a message"); 
/* 228 */     Set<? extends Map.Entry<String, ?>> entries = attachments.entrySet();
/* 229 */     MessageAttachment[] files = new MessageAttachment[fileAmount];
/* 230 */     int i = 0;
/* 231 */     for (Map.Entry<String, ?> attachment : entries) {
/* 232 */       String name = attachment.getKey();
/* 233 */       Objects.requireNonNull(name, "Name");
/* 234 */       Object data = attachment.getValue();
/* 235 */       files[i++] = convertAttachment(name, data);
/*     */     } 
/* 237 */     return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static WebhookMessage files(@NotNull String name1, @NotNull Object data1, @NotNull Object... attachments) {
/* 266 */     Objects.requireNonNull(name1, "Name");
/* 267 */     Objects.requireNonNull(data1, "Data");
/* 268 */     Objects.requireNonNull(attachments, "Attachments");
/* 269 */     if (attachments.length % 2 != 0)
/* 270 */       throw new IllegalArgumentException("Must provide even number of varargs arguments"); 
/* 271 */     int fileAmount = 1 + attachments.length / 2;
/* 272 */     if (fileAmount > 10)
/* 273 */       throw new IllegalArgumentException("Cannot add more than 10 files to a message"); 
/* 274 */     MessageAttachment[] files = new MessageAttachment[fileAmount];
/* 275 */     files[0] = convertAttachment(name1, data1);
/* 276 */     for (int i = 0, j = 1; i < attachments.length; j++, i += 2) {
/* 277 */       Object name = attachments[i];
/* 278 */       Object data = attachments[i + 1];
/* 279 */       if (!(name instanceof String))
/* 280 */         throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Expected String and found " + ((name == null) ? null : name.getClass().getName())); 
/* 281 */       files[j] = convertAttachment((String)name, data);
/*     */     } 
/* 283 */     return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFile() {
/* 292 */     return (this.attachments != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public RequestBody getBody() {
/* 303 */     JSONObject payload = new JSONObject();
/* 304 */     payload.put("content", this.content);
/* 305 */     if (this.embeds != null && !this.embeds.isEmpty()) {
/* 306 */       JSONArray array = new JSONArray();
/* 307 */       for (WebhookEmbed embed : this.embeds) {
/* 308 */         array.put(embed.reduced());
/*     */       }
/* 310 */       payload.put("embeds", array);
/*     */     } else {
/* 312 */       payload.put("embeds", new JSONArray());
/*     */     } 
/* 314 */     if (this.avatarUrl != null)
/* 315 */       payload.put("avatar_url", this.avatarUrl); 
/* 316 */     if (this.username != null)
/* 317 */       payload.put("username", this.username); 
/* 318 */     payload.put("tts", this.isTTS);
/* 319 */     payload.put("allowed_mentions", this.allowedMentions);
/* 320 */     String json = payload.toString();
/* 321 */     if (isFile()) {
/* 322 */       MultipartBody.Builder builder = (new MultipartBody.Builder()).setType(MultipartBody.FORM);
/*     */       
/* 324 */       for (int i = 0; i < this.attachments.length; i++) {
/* 325 */         MessageAttachment attachment = this.attachments[i];
/* 326 */         if (attachment == null)
/*     */           break; 
/* 328 */         builder.addFormDataPart("file" + i, attachment.getName(), (RequestBody)new IOUtil.OctetBody(attachment.getData()));
/*     */       } 
/* 330 */       return (RequestBody)builder.addFormDataPart("payload_json", json).build();
/*     */     } 
/* 332 */     return RequestBody.create(IOUtil.JSON, json);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static MessageAttachment convertAttachment(@NotNull String name, @NotNull Object data) {
/* 337 */     Objects.requireNonNull(name, "Name");
/* 338 */     Objects.requireNonNull(data, "Data");
/*     */     try {
/*     */       MessageAttachment a;
/* 341 */       if (data instanceof File) {
/* 342 */         a = new MessageAttachment(name, (File)data);
/* 343 */       } else if (data instanceof InputStream) {
/* 344 */         a = new MessageAttachment(name, (InputStream)data);
/* 345 */       } else if (data instanceof byte[]) {
/* 346 */         a = new MessageAttachment(name, (byte[])data);
/*     */       } else {
/* 348 */         throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Unexpected data type " + data.getClass().getName());
/* 349 */       }  return a;
/*     */     }
/* 351 */     catch (IOException ex) {
/* 352 */       MessageAttachment a; throw new IllegalArgumentException(a);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\WebhookMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */