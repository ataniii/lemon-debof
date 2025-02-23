/*     */ package club.minnced.discord.webhook.receive;
/*     */ 
/*     */ import club.minnced.discord.webhook.send.WebhookEmbed;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityFactory
/*     */ {
/*     */   @NotNull
/*     */   public static ReadonlyUser makeUser(@NotNull JSONObject json) {
/*  46 */     long id = Long.parseUnsignedLong(json.getString("id"));
/*  47 */     String name = json.getString("username");
/*  48 */     String avatar = json.optString("avatar", null);
/*  49 */     short discriminator = Short.parseShort(json.getString("discriminator"));
/*  50 */     boolean bot = (!json.isNull("bot") && json.getBoolean("bot"));
/*     */     
/*  52 */     return new ReadonlyUser(id, discriminator, bot, name, avatar);
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
/*     */   public static ReadonlyAttachment makeAttachment(@NotNull JSONObject json) {
/*  65 */     String url = json.getString("url");
/*  66 */     String proxy = json.getString("proxy_url");
/*  67 */     String name = json.getString("filename");
/*  68 */     int size = json.getInt("size");
/*  69 */     int width = json.optInt("width", -1);
/*  70 */     int height = json.optInt("height", -1);
/*  71 */     long id = Long.parseUnsignedLong(json.getString("id"));
/*  72 */     return new ReadonlyAttachment(url, proxy, name, width, height, size, id);
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
/*     */   @Nullable
/*     */   public static WebhookEmbed.EmbedField makeEmbedField(@Nullable JSONObject json) {
/*  85 */     if (json == null)
/*  86 */       return null; 
/*  87 */     String name = json.getString("name");
/*  88 */     String value = json.getString("value");
/*  89 */     boolean inline = (!json.isNull("inline") && json.getBoolean("inline"));
/*  90 */     return new WebhookEmbed.EmbedField(inline, name, value);
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
/*     */   @Nullable
/*     */   public static WebhookEmbed.EmbedAuthor makeEmbedAuthor(@Nullable JSONObject json) {
/* 103 */     if (json == null)
/* 104 */       return null; 
/* 105 */     String name = json.getString("name");
/* 106 */     String url = json.optString("url", null);
/* 107 */     String icon = json.optString("icon_url", null);
/* 108 */     return new WebhookEmbed.EmbedAuthor(name, icon, url);
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
/*     */   @Nullable
/*     */   public static WebhookEmbed.EmbedFooter makeEmbedFooter(@Nullable JSONObject json) {
/* 121 */     if (json == null)
/* 122 */       return null; 
/* 123 */     String text = json.getString("text");
/* 124 */     String icon = json.optString("icon_url", null);
/* 125 */     return new WebhookEmbed.EmbedFooter(text, icon);
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
/*     */   @Nullable
/*     */   public static WebhookEmbed.EmbedTitle makeEmbedTitle(@NotNull JSONObject json) {
/* 138 */     String text = json.optString("title", null);
/* 139 */     if (text == null)
/* 140 */       return null; 
/* 141 */     String url = json.optString("url", null);
/* 142 */     return new WebhookEmbed.EmbedTitle(text, url);
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
/*     */   @Nullable
/*     */   public static ReadonlyEmbed.EmbedImage makeEmbedImage(@Nullable JSONObject json) {
/* 155 */     if (json == null)
/* 156 */       return null; 
/* 157 */     String url = json.getString("url");
/* 158 */     String proxyUrl = json.getString("proxy_url");
/* 159 */     int width = json.getInt("width");
/* 160 */     int height = json.getInt("height");
/* 161 */     return new ReadonlyEmbed.EmbedImage(url, proxyUrl, width, height);
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
/*     */   @Nullable
/*     */   public static ReadonlyEmbed.EmbedProvider makeEmbedProvider(@Nullable JSONObject json) {
/* 174 */     if (json == null)
/* 175 */       return null; 
/* 176 */     String url = json.getString("url");
/* 177 */     String name = json.getString("name");
/* 178 */     return new ReadonlyEmbed.EmbedProvider(name, url);
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
/*     */   @Nullable
/*     */   public static ReadonlyEmbed.EmbedVideo makeEmbedVideo(@Nullable JSONObject json) {
/* 191 */     if (json == null)
/* 192 */       return null; 
/* 193 */     String url = json.getString("url");
/* 194 */     int height = json.getInt("height");
/* 195 */     int width = json.getInt("width");
/* 196 */     return new ReadonlyEmbed.EmbedVideo(url, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static ReadonlyEmbed makeEmbed(@NotNull JSONObject json) {
/*     */     OffsetDateTime timestamp;
/* 209 */     String description = json.optString("description", null);
/* 210 */     Integer color = json.isNull("color") ? null : Integer.valueOf(json.getInt("color"));
/* 211 */     ReadonlyEmbed.EmbedImage image = makeEmbedImage(json.optJSONObject("image"));
/* 212 */     ReadonlyEmbed.EmbedImage thumbnail = makeEmbedImage(json.optJSONObject("thumbnail"));
/* 213 */     ReadonlyEmbed.EmbedProvider provider = makeEmbedProvider(json.optJSONObject("provider"));
/* 214 */     ReadonlyEmbed.EmbedVideo video = makeEmbedVideo(json.optJSONObject("video"));
/* 215 */     WebhookEmbed.EmbedFooter footer = makeEmbedFooter(json.optJSONObject("footer"));
/* 216 */     WebhookEmbed.EmbedAuthor author = makeEmbedAuthor(json.optJSONObject("author"));
/* 217 */     WebhookEmbed.EmbedTitle title = makeEmbedTitle(json);
/*     */     
/* 219 */     if (json.isNull("timestamp")) {
/* 220 */       timestamp = null;
/*     */     } else {
/* 222 */       timestamp = OffsetDateTime.parse(json.getString("timestamp"));
/* 223 */     }  JSONArray fieldArray = json.optJSONArray("fields");
/* 224 */     List<WebhookEmbed.EmbedField> fields = new ArrayList<>();
/* 225 */     if (fieldArray != null)
/* 226 */       for (int i = 0; i < fieldArray.length(); i++) {
/* 227 */         JSONObject obj = fieldArray.getJSONObject(i);
/* 228 */         WebhookEmbed.EmbedField field = makeEmbedField(obj);
/* 229 */         if (field != null) {
/* 230 */           fields.add(field);
/*     */         }
/*     */       }  
/* 233 */     return new ReadonlyEmbed(timestamp, color, description, thumbnail, image, footer, title, author, fields, provider, video);
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
/*     */   public static ReadonlyMessage makeMessage(@NotNull JSONObject json) {
/* 246 */     long id = Long.parseUnsignedLong(json.getString("id"));
/* 247 */     long channelId = Long.parseUnsignedLong(json.getString("channel_id"));
/* 248 */     ReadonlyUser author = makeUser(json.getJSONObject("author"));
/* 249 */     String content = json.getString("content");
/* 250 */     boolean tts = json.getBoolean("tts");
/* 251 */     boolean mentionEveryone = json.getBoolean("mention_everyone");
/* 252 */     JSONArray usersArray = json.getJSONArray("mentions");
/* 253 */     JSONArray rolesArray = json.getJSONArray("mention_roles");
/* 254 */     JSONArray embedArray = json.getJSONArray("embeds");
/* 255 */     JSONArray attachmentArray = json.getJSONArray("attachments");
/* 256 */     List<ReadonlyUser> mentionedUsers = convertToList(usersArray, EntityFactory::makeUser);
/* 257 */     List<ReadonlyEmbed> embeds = convertToList(embedArray, EntityFactory::makeEmbed);
/* 258 */     List<ReadonlyAttachment> attachments = convertToList(attachmentArray, EntityFactory::makeAttachment);
/* 259 */     List<Long> mentionedRoles = new ArrayList<>();
/* 260 */     for (int i = 0; i < rolesArray.length(); i++) {
/* 261 */       mentionedRoles.add(Long.valueOf(Long.parseUnsignedLong(rolesArray.getString(i))));
/*     */     }
/* 263 */     return new ReadonlyMessage(id, channelId, mentionEveryone, tts, author, content, embeds, attachments, mentionedUsers, mentionedRoles);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> List<T> convertToList(JSONArray arr, Function<JSONObject, T> converter) {
/* 271 */     if (arr == null)
/* 272 */       return Collections.emptyList(); 
/* 273 */     List<T> list = new ArrayList<>();
/* 274 */     for (int i = 0; i < arr.length(); i++) {
/* 275 */       JSONObject json = arr.getJSONObject(i);
/* 276 */       T out = converter.apply(json);
/* 277 */       if (out != null)
/* 278 */         list.add(out); 
/*     */     } 
/* 280 */     return Collections.unmodifiableList(list);
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\receive\EntityFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */