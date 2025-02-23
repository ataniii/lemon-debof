/*     */ package club.minnced.discord.webhook.send;
/*     */ 
/*     */ import discord4j.core.spec.EmbedCreateSpec;
/*     */ import discord4j.discordjson.json.EmbedAuthorData;
/*     */ import discord4j.discordjson.json.EmbedData;
/*     */ import discord4j.discordjson.json.EmbedFieldData;
/*     */ import discord4j.discordjson.json.EmbedFooterData;
/*     */ import discord4j.discordjson.json.EmbedImageData;
/*     */ import discord4j.discordjson.json.EmbedThumbnailData;
/*     */ import discord4j.discordjson.possible.Possible;
/*     */ import java.awt.Color;
/*     */ import java.net.URL;
/*     */ import java.time.Instant;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import net.dv8tion.jda.api.entities.MessageEmbed;
/*     */ import org.javacord.api.entity.message.embed.Embed;
/*     */ import org.javacord.api.entity.message.embed.EmbedField;
/*     */ import org.javacord.api.entity.message.embed.EmbedFooter;
/*     */ import org.javacord.api.entity.message.embed.EmbedImage;
/*     */ import org.javacord.api.entity.message.embed.EmbedThumbnail;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ public class WebhookEmbedBuilder
/*     */ {
/*  60 */   private final List<WebhookEmbed.EmbedField> fields = new ArrayList<>(10);
/*     */   private OffsetDateTime timestamp;
/*     */   private Integer color;
/*     */   private String description;
/*     */   private String thumbnailUrl;
/*     */   private String imageUrl;
/*     */   private WebhookEmbed.EmbedFooter footer;
/*     */   private WebhookEmbed.EmbedTitle title;
/*     */   private WebhookEmbed.EmbedAuthor author;
/*     */   
/*     */   public WebhookEmbedBuilder(@Nullable WebhookEmbed embed) {
/*  71 */     this();
/*  72 */     if (embed != null) {
/*  73 */       this.timestamp = embed.getTimestamp();
/*  74 */       this.color = embed.getColor();
/*  75 */       this.description = embed.getDescription();
/*  76 */       this.thumbnailUrl = embed.getThumbnailUrl();
/*  77 */       this.imageUrl = embed.getImageUrl();
/*  78 */       this.footer = embed.getFooter();
/*  79 */       this.title = embed.getTitle();
/*  80 */       this.author = embed.getAuthor();
/*  81 */       this.fields.addAll(embed.getFields());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  89 */     this.fields.clear();
/*  90 */     this.timestamp = null;
/*  91 */     this.color = null;
/*  92 */     this.description = null;
/*  93 */     this.thumbnailUrl = null;
/*  94 */     this.imageUrl = null;
/*  95 */     this.footer = null;
/*  96 */     this.title = null;
/*  97 */     this.author = null;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setTimestamp(@Nullable TemporalAccessor timestamp) {
/* 114 */     if (timestamp instanceof Instant) {
/* 115 */       this.timestamp = OffsetDateTime.ofInstant((Instant)timestamp, ZoneId.of("UTC"));
/*     */     } else {
/*     */       
/* 118 */       this.timestamp = (timestamp == null) ? null : OffsetDateTime.from(timestamp);
/*     */     } 
/* 120 */     return this;
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
/*     */   public WebhookEmbedBuilder setColor(@Nullable Integer color) {
/* 133 */     this.color = color;
/* 134 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setDescription(@Nullable String description) {
/* 149 */     this.description = description;
/* 150 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setThumbnailUrl(@Nullable String thumbnailUrl) {
/* 164 */     this.thumbnailUrl = thumbnailUrl;
/* 165 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setImageUrl(@Nullable String imageUrl) {
/* 179 */     this.imageUrl = imageUrl;
/* 180 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setFooter(@Nullable WebhookEmbed.EmbedFooter footer) {
/* 194 */     this.footer = footer;
/* 195 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setTitle(@Nullable WebhookEmbed.EmbedTitle title) {
/* 209 */     this.title = title;
/* 210 */     return this;
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
/*     */   @NotNull
/*     */   public WebhookEmbedBuilder setAuthor(@Nullable WebhookEmbed.EmbedAuthor author) {
/* 224 */     this.author = author;
/* 225 */     return this;
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
/*     */   public WebhookEmbedBuilder addField(@NotNull WebhookEmbed.EmbedField field) {
/* 243 */     if (this.fields.size() == 25)
/* 244 */       throw new IllegalStateException("Cannot add more than 25 fields"); 
/* 245 */     this.fields.add(Objects.requireNonNull(field));
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 255 */     return (isEmpty(this.description) && 
/* 256 */       isEmpty(this.imageUrl) && 
/* 257 */       isEmpty(this.thumbnailUrl) && 
/* 258 */       isFieldsEmpty() && 
/* 259 */       isAuthorEmpty() && 
/* 260 */       isTitleEmpty() && 
/* 261 */       isFooterEmpty() && this.timestamp == null);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isEmpty(String s) {
/* 266 */     return (s == null || s.trim().isEmpty());
/*     */   }
/*     */   
/*     */   private boolean isTitleEmpty() {
/* 270 */     return (this.title == null || isEmpty(this.title.getText()));
/*     */   }
/*     */   
/*     */   private boolean isFooterEmpty() {
/* 274 */     return (this.footer == null || isEmpty(this.footer.getText()));
/*     */   }
/*     */   
/*     */   private boolean isAuthorEmpty() {
/* 278 */     return (this.author == null || isEmpty(this.author.getName()));
/*     */   }
/*     */   
/*     */   private boolean isFieldsEmpty() {
/* 282 */     if (this.fields.isEmpty())
/* 283 */       return true; 
/* 284 */     return this.fields.stream().allMatch(f -> (isEmpty(f.getName()) && isEmpty(f.getValue())));
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
/*     */   @NotNull
/*     */   public WebhookEmbed build() {
/* 298 */     if (isEmpty())
/* 299 */       throw new IllegalStateException("Cannot build an empty embed"); 
/* 300 */     return new WebhookEmbed(this.timestamp, this.color, this.description, this.thumbnailUrl, this.imageUrl, this.footer, this.title, this.author, new ArrayList<>(this.fields));
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
/*     */   @NotNull
/*     */   public static WebhookEmbedBuilder fromJDA(@NotNull MessageEmbed embed) {
/* 327 */     WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
/* 328 */     String url = embed.getUrl();
/* 329 */     String title = embed.getTitle();
/* 330 */     String description = embed.getDescription();
/* 331 */     MessageEmbed.Thumbnail thumbnail = embed.getThumbnail();
/* 332 */     MessageEmbed.AuthorInfo author = embed.getAuthor();
/* 333 */     MessageEmbed.Footer footer = embed.getFooter();
/* 334 */     MessageEmbed.ImageInfo image = embed.getImage();
/* 335 */     List<MessageEmbed.Field> fields = embed.getFields();
/* 336 */     int color = embed.getColorRaw();
/* 337 */     OffsetDateTime timestamp = embed.getTimestamp();
/*     */     
/* 339 */     if (title != null)
/* 340 */       builder.setTitle(new WebhookEmbed.EmbedTitle(title, url)); 
/* 341 */     if (description != null)
/* 342 */       builder.setDescription(description); 
/* 343 */     if (thumbnail != null)
/* 344 */       builder.setThumbnailUrl(thumbnail.getUrl()); 
/* 345 */     if (author != null)
/* 346 */       builder.setAuthor(new WebhookEmbed.EmbedAuthor(author.getName(), author.getIconUrl(), author.getUrl())); 
/* 347 */     if (footer != null)
/* 348 */       builder.setFooter(new WebhookEmbed.EmbedFooter(footer.getText(), footer.getIconUrl())); 
/* 349 */     if (image != null)
/* 350 */       builder.setImageUrl(image.getUrl()); 
/* 351 */     if (!fields.isEmpty())
/* 352 */       fields.forEach(field -> builder.addField(new WebhookEmbed.EmbedField(field.isInline(), field.getName(), field.getValue()))); 
/* 353 */     if (color != 536870911)
/* 354 */       builder.setColor(Integer.valueOf(color)); 
/* 355 */     if (timestamp != null) {
/* 356 */       builder.setTimestamp(timestamp);
/*     */     }
/* 358 */     return builder;
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
/*     */   @NotNull
/*     */   public static WebhookEmbedBuilder fromJavacord(@NotNull Embed embed) {
/* 374 */     WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
/*     */     
/* 376 */     embed.getTitle().ifPresent(title -> builder.setTitle(new WebhookEmbed.EmbedTitle(title, embed.getUrl().map(URL::toString).orElse(null))));
/*     */     
/* 378 */     embed.getDescription().ifPresent(builder::setDescription);
/* 379 */     embed.getTimestamp().ifPresent(builder::setTimestamp);
/* 380 */     embed.getColor().map(Color::getRGB).ifPresent(builder::setColor);
/* 381 */     embed.getFooter().map(footer -> new WebhookEmbed.EmbedFooter((String)footer.getText().orElseThrow(NullPointerException::new), footer.getIconUrl().map(URL::toString).orElse(null))).ifPresent(builder::setFooter);
/* 382 */     embed.getImage().map(EmbedImage::getUrl).map(URL::toString).ifPresent(builder::setImageUrl);
/* 383 */     embed.getThumbnail().map(EmbedThumbnail::getUrl).map(URL::toString).ifPresent(builder::setThumbnailUrl);
/* 384 */     embed.getFields().stream()
/* 385 */       .map(field -> new WebhookEmbed.EmbedField(field.isInline(), field.getName(), field.getValue()))
/* 386 */       .forEach(builder::addField);
/* 387 */     return builder;
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
/*     */   @NotNull
/*     */   public static WebhookEmbedBuilder fromD4J(@NotNull Consumer<? super EmbedCreateSpec> callback) {
/* 403 */     EmbedCreateSpec spec = new EmbedCreateSpec();
/* 404 */     callback.accept(spec);
/* 405 */     EmbedData data = spec.asRequest();
/* 406 */     return fromD4J(data);
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
/*     */   @NotNull
/*     */   public static WebhookEmbedBuilder fromD4J(@NotNull EmbedData data) {
/* 422 */     WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
/*     */ 
/*     */     
/* 425 */     Possible<String> title = data.title();
/* 426 */     Possible<String> description = data.description();
/* 427 */     Possible<String> url = data.url();
/* 428 */     Possible<String> timestamp = data.timestamp();
/* 429 */     Possible<Integer> color = data.color();
/* 430 */     Possible<EmbedFooterData> footer = data.footer();
/* 431 */     Possible<EmbedImageData> image = data.image();
/* 432 */     Possible<EmbedThumbnailData> thumbnail = data.thumbnail();
/* 433 */     Possible<EmbedAuthorData> author = data.author();
/* 434 */     Possible<List<EmbedFieldData>> fields = data.fields();
/*     */     
/* 436 */     if (!title.isAbsent())
/* 437 */       builder.setTitle(new WebhookEmbed.EmbedTitle((String)title.get(), url.toOptional().orElse(null))); 
/* 438 */     if (!description.isAbsent())
/* 439 */       builder.setDescription((String)description.get()); 
/* 440 */     if (!timestamp.isAbsent())
/* 441 */       builder.setTimestamp(OffsetDateTime.parse((CharSequence)timestamp.get())); 
/* 442 */     if (!color.isAbsent())
/* 443 */       builder.setColor((Integer)color.get()); 
/* 444 */     if (!footer.isAbsent())
/* 445 */       builder.setFooter(new WebhookEmbed.EmbedFooter(((EmbedFooterData)footer.get()).text(), ((EmbedFooterData)footer.get()).iconUrl().toOptional().orElse(null))); 
/* 446 */     if (!image.isAbsent())
/* 447 */       builder.setImageUrl((String)((EmbedImageData)image.get()).url().get()); 
/* 448 */     if (!thumbnail.isAbsent())
/* 449 */       builder.setThumbnailUrl((String)((EmbedThumbnailData)thumbnail.get()).url().get()); 
/* 450 */     if (!author.isAbsent()) {
/* 451 */       EmbedAuthorData authorData = (EmbedAuthorData)author.get();
/* 452 */       builder.setAuthor(new WebhookEmbed.EmbedAuthor((String)authorData
/* 453 */             .name().get(), authorData
/* 454 */             .iconUrl().toOptional().orElse(null), authorData
/* 455 */             .url().toOptional().orElse(null)));
/*     */     } 
/* 457 */     if (!fields.isAbsent()) {
/* 458 */       ((List)fields.get())
/* 459 */         .stream()
/* 460 */         .map(field -> new WebhookEmbed.EmbedField(((Boolean)field.inline().toOptional().orElse(Boolean.valueOf(false))).booleanValue(), field.name(), field.value()))
/* 461 */         .forEach(builder::addField);
/*     */     }
/*     */     
/* 464 */     return builder;
/*     */   }
/*     */   
/*     */   public WebhookEmbedBuilder() {}
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\WebhookEmbedBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */