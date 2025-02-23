/*     */ package club.minnced.discord.webhook.send;
/*     */ 
/*     */ import discord4j.core.spec.MessageCreateSpec;
/*     */ import discord4j.discordjson.json.AllowedMentionsData;
/*     */ import discord4j.discordjson.json.EmbedData;
/*     */ import discord4j.discordjson.json.MessageCreateRequest;
/*     */ import discord4j.discordjson.possible.Possible;
/*     */ import discord4j.rest.util.MultipartRequest;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import net.dv8tion.jda.api.entities.ISnowflake;
/*     */ import net.dv8tion.jda.api.entities.Message;
/*     */ import net.dv8tion.jda.api.entities.MessageEmbed;
/*     */ import net.dv8tion.jda.internal.entities.DataMessage;
/*     */ import org.javacord.api.entity.DiscordEntity;
/*     */ import org.javacord.api.entity.message.Message;
/*     */ import org.javacord.api.entity.message.embed.Embed;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import reactor.util.function.Tuple2;
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
/*     */ public class WebhookMessageBuilder
/*     */ {
/*  45 */   protected final StringBuilder content = new StringBuilder();
/*  46 */   protected final List<WebhookEmbed> embeds = new LinkedList<>();
/*  47 */   protected final MessageAttachment[] files = new MessageAttachment[10];
/*  48 */   protected AllowedMentions allowedMentions = AllowedMentions.all();
/*     */   
/*     */   protected String username;
/*  51 */   private int fileIndex = 0;
/*     */   
/*     */   protected String avatarUrl;
/*     */   
/*     */   protected boolean isTTS;
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  59 */     return (this.content.length() == 0 && this.embeds.isEmpty() && getFileAmount() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFileAmount() {
/*  68 */     return this.fileIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookMessageBuilder reset() {
/*  78 */     this.content.setLength(0);
/*  79 */     resetEmbeds();
/*  80 */     resetFiles();
/*  81 */     this.username = null;
/*  82 */     this.avatarUrl = null;
/*  83 */     this.isTTS = false;
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookMessageBuilder resetFiles() {
/*  94 */     for (int i = 0; i < 10; i++) {
/*  95 */       this.files[i] = null;
/*     */     }
/*  97 */     this.fileIndex = 0;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookMessageBuilder resetEmbeds() {
/* 108 */     this.embeds.clear();
/* 109 */     return this;
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
/*     */   public WebhookMessageBuilder setAllowedMentions(@NotNull AllowedMentions mentions) {
/* 127 */     this.allowedMentions = Objects.<AllowedMentions>requireNonNull(mentions);
/* 128 */     return this;
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
/*     */   public WebhookMessageBuilder addEmbeds(@NotNull WebhookEmbed... embeds) {
/* 146 */     Objects.requireNonNull(embeds, "Embeds");
/* 147 */     if (this.embeds.size() + embeds.length > 10)
/* 148 */       throw new IllegalStateException("Cannot add more than 10 embeds to a message"); 
/* 149 */     for (WebhookEmbed embed : embeds) {
/* 150 */       Objects.requireNonNull(embed, "Embed");
/* 151 */       this.embeds.add(embed);
/*     */     } 
/* 153 */     return this;
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
/*     */   public WebhookMessageBuilder addEmbeds(@NotNull Collection<? extends WebhookEmbed> embeds) {
/* 171 */     Objects.requireNonNull(embeds, "Embeds");
/* 172 */     if (this.embeds.size() + embeds.size() > 10)
/* 173 */       throw new IllegalStateException("Cannot add more than 10 embeds to a message"); 
/* 174 */     for (WebhookEmbed embed : embeds) {
/* 175 */       Objects.requireNonNull(embed, "Embed");
/* 176 */       this.embeds.add(embed);
/*     */     } 
/* 178 */     return this;
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
/*     */   public WebhookMessageBuilder setContent(@Nullable String content) {
/* 194 */     if (content != null && content.length() > 2000)
/* 195 */       throw new IllegalArgumentException("Content may not exceed 2000 characters!"); 
/* 196 */     this.content.setLength(0);
/* 197 */     if (content != null && !content.isEmpty())
/* 198 */       this.content.append(content); 
/* 199 */     return this;
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
/*     */   public WebhookMessageBuilder append(@NotNull String content) {
/* 218 */     Objects.requireNonNull(content, "Content");
/* 219 */     if (this.content.length() + content.length() > 2000)
/* 220 */       throw new IllegalArgumentException("Content may not exceed 2000 characters!"); 
/* 221 */     this.content.append(content);
/* 222 */     return this;
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
/*     */   public WebhookMessageBuilder setUsername(@Nullable String username) {
/* 238 */     this.username = (username == null || username.trim().isEmpty()) ? null : username.trim();
/* 239 */     return this;
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
/*     */   public WebhookMessageBuilder setAvatarUrl(@Nullable String avatarUrl) {
/* 255 */     this.avatarUrl = (avatarUrl == null || avatarUrl.trim().isEmpty()) ? null : avatarUrl.trim();
/* 256 */     return this;
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
/*     */   public WebhookMessageBuilder setTTS(boolean tts) {
/* 269 */     this.isTTS = tts;
/* 270 */     return this;
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
/*     */   public WebhookMessageBuilder addFile(@NotNull File file) {
/* 287 */     Objects.requireNonNull(file, "File");
/* 288 */     return addFile(file.getName(), file);
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
/*     */   public WebhookMessageBuilder addFile(@NotNull String name, @NotNull File file) {
/* 307 */     Objects.requireNonNull(file, "File");
/* 308 */     Objects.requireNonNull(name, "Name");
/* 309 */     if (!file.exists() || !file.canRead()) throw new IllegalArgumentException("File must exist and be readable"); 
/* 310 */     if (this.fileIndex >= 10) {
/* 311 */       throw new IllegalStateException("Cannot add more than 10 attachments to a message");
/*     */     }
/*     */     try {
/* 314 */       MessageAttachment attachment = new MessageAttachment(name, file);
/* 315 */       this.files[this.fileIndex++] = attachment;
/* 316 */       return this;
/*     */     }
/* 318 */     catch (IOException ex) {
/* 319 */       throw new IllegalArgumentException(ex);
/*     */     } 
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
/*     */   public WebhookMessageBuilder addFile(@NotNull String name, @NotNull byte[] data) {
/* 339 */     Objects.requireNonNull(data, "Data");
/* 340 */     Objects.requireNonNull(name, "Name");
/* 341 */     if (this.fileIndex >= 10) {
/* 342 */       throw new IllegalStateException("Cannot add more than 10 attachments to a message");
/*     */     }
/* 344 */     MessageAttachment attachment = new MessageAttachment(name, data);
/* 345 */     this.files[this.fileIndex++] = attachment;
/* 346 */     return this;
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
/*     */   public WebhookMessageBuilder addFile(@NotNull String name, @NotNull InputStream data) {
/* 365 */     Objects.requireNonNull(data, "InputStream");
/* 366 */     Objects.requireNonNull(name, "Name");
/* 367 */     if (this.fileIndex >= 10) {
/* 368 */       throw new IllegalStateException("Cannot add more than 10 attachments to a message");
/*     */     }
/*     */     try {
/* 371 */       MessageAttachment attachment = new MessageAttachment(name, data);
/* 372 */       this.files[this.fileIndex++] = attachment;
/* 373 */       return this;
/*     */     }
/* 375 */     catch (IOException ex) {
/* 376 */       throw new IllegalArgumentException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookMessage build() {
/* 388 */     if (isEmpty())
/* 389 */       throw new IllegalStateException("Cannot build an empty message!"); 
/* 390 */     return new WebhookMessage(this.username, this.avatarUrl, this.content.toString(), this.embeds, this.isTTS, (this.fileIndex == 0) ? null : 
/* 391 */         Arrays.<MessageAttachment>copyOf(this.files, this.fileIndex), this.allowedMentions);
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
/*     */   public static WebhookMessageBuilder fromJDA(@NotNull Message message) {
/* 412 */     WebhookMessageBuilder builder = new WebhookMessageBuilder();
/* 413 */     builder.setTTS(message.isTTS());
/* 414 */     builder.setContent(message.getContentRaw());
/* 415 */     message.getEmbeds().forEach(embed -> builder.addEmbeds(new WebhookEmbed[] { WebhookEmbedBuilder.fromJDA(embed).build() }));
/*     */     
/* 417 */     if (message instanceof DataMessage) {
/* 418 */       DataMessage data = (DataMessage)message;
/* 419 */       AllowedMentions allowedMentions = AllowedMentions.none();
/* 420 */       EnumSet<Message.MentionType> parse = data.getAllowedMentions();
/* 421 */       allowedMentions.withUsers(data.getMentionedUsersWhitelist());
/* 422 */       allowedMentions.withRoles(data.getMentionedRolesWhitelist());
/* 423 */       if (parse != null) {
/* 424 */         allowedMentions.withParseUsers(parse.contains(Message.MentionType.USER));
/* 425 */         allowedMentions.withParseRoles(parse.contains(Message.MentionType.ROLE));
/* 426 */         allowedMentions.withParseEveryone((parse.contains(Message.MentionType.EVERYONE) || parse.contains(Message.MentionType.HERE)));
/*     */       } 
/* 428 */       builder.setAllowedMentions(allowedMentions);
/* 429 */     } else if (message instanceof net.dv8tion.jda.internal.entities.ReceivedMessage) {
/* 430 */       AllowedMentions allowedMentions = AllowedMentions.none();
/* 431 */       allowedMentions.withRoles((Collection<String>)message
/* 432 */           .getMentionedRoles().stream()
/* 433 */           .map(ISnowflake::getId)
/* 434 */           .collect(Collectors.toList()));
/* 435 */       allowedMentions.withUsers((Collection<String>)message
/* 436 */           .getMentionedUsers().stream()
/* 437 */           .map(ISnowflake::getId)
/* 438 */           .collect(Collectors.toList()));
/* 439 */       allowedMentions.withParseEveryone(message.mentionsEveryone());
/* 440 */       builder.setAllowedMentions(allowedMentions);
/*     */     } 
/* 442 */     return builder;
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
/*     */   public static WebhookMessageBuilder fromJavacord(@NotNull Message message) {
/* 458 */     WebhookMessageBuilder builder = new WebhookMessageBuilder();
/* 459 */     builder.setTTS(message.isTts());
/* 460 */     builder.setContent(message.getContent());
/* 461 */     message.getEmbeds().forEach(embed -> builder.addEmbeds(new WebhookEmbed[] { WebhookEmbedBuilder.fromJavacord(embed).build() }));
/*     */     
/* 463 */     AllowedMentions allowedMentions = AllowedMentions.none();
/* 464 */     allowedMentions.withUsers((Collection<String>)message
/* 465 */         .getMentionedUsers().stream()
/* 466 */         .map(DiscordEntity::getIdAsString)
/* 467 */         .collect(Collectors.toList()));
/* 468 */     allowedMentions.withRoles((Collection<String>)message
/* 469 */         .getMentionedRoles().stream()
/* 470 */         .map(DiscordEntity::getIdAsString)
/* 471 */         .collect(Collectors.toList()));
/* 472 */     allowedMentions.withParseEveryone(message.mentionsEveryone());
/* 473 */     builder.setAllowedMentions(allowedMentions);
/* 474 */     return builder;
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
/*     */   public static WebhookMessageBuilder fromD4J(@NotNull Consumer<? super MessageCreateSpec> callback) {
/* 490 */     WebhookMessageBuilder builder = new WebhookMessageBuilder();
/* 491 */     MessageCreateSpec spec = new MessageCreateSpec();
/* 492 */     callback.accept(spec);
/* 493 */     MultipartRequest data = spec.asRequest();
/* 494 */     data.getFiles().forEach(tuple -> builder.addFile((String)tuple.getT1(), (InputStream)tuple.getT2()));
/* 495 */     MessageCreateRequest parts = data.getCreateRequest();
/* 496 */     if (parts == null) {
/* 497 */       return builder;
/*     */     }
/* 499 */     Possible<String> content = parts.content();
/* 500 */     Possible<EmbedData> embed = parts.embed();
/* 501 */     Possible<Boolean> tts = parts.tts();
/* 502 */     Possible<AllowedMentionsData> allowedMentions = parts.allowedMentions();
/*     */     
/* 504 */     if (!content.isAbsent())
/* 505 */       builder.setContent((String)content.get()); 
/* 506 */     if (!embed.isAbsent())
/* 507 */       builder.addEmbeds(new WebhookEmbed[] { WebhookEmbedBuilder.fromD4J((EmbedData)embed.get()).build() }); 
/* 508 */     if (!tts.isAbsent()) {
/* 509 */       builder.setTTS(((Boolean)tts.get()).booleanValue());
/*     */     }
/* 511 */     if (!allowedMentions.isAbsent()) {
/* 512 */       AllowedMentionsData mentions = (AllowedMentionsData)allowedMentions.get();
/* 513 */       AllowedMentions whitelist = AllowedMentions.none();
/* 514 */       if (!mentions.users().isAbsent())
/* 515 */         whitelist.withUsers((Collection<String>)mentions.users().get()); 
/* 516 */       if (!mentions.roles().isAbsent())
/* 517 */         whitelist.withRoles((Collection<String>)mentions.roles().get()); 
/* 518 */       if (!mentions.parse().isAbsent()) {
/* 519 */         List<String> parse = (List<String>)mentions.parse().get();
/* 520 */         whitelist.withParseRoles(parse.contains("roles"));
/* 521 */         whitelist.withParseEveryone(parse.contains("everyone"));
/* 522 */         whitelist.withParseUsers(parse.contains("users"));
/*     */       } 
/* 524 */       builder.setAllowedMentions(whitelist);
/*     */     } 
/*     */     
/* 527 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\send\WebhookMessageBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */