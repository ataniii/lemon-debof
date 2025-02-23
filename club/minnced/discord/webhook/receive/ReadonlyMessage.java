/*     */ package club.minnced.discord.webhook.receive;
/*     */ 
/*     */ import club.minnced.discord.webhook.send.WebhookMessage;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.json.JSONObject;
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
/*     */ public class ReadonlyMessage
/*     */   implements JSONString
/*     */ {
/*     */   private final long id;
/*     */   private final long channelId;
/*     */   private final boolean mentionsEveryone;
/*     */   private final boolean tts;
/*     */   private final ReadonlyUser author;
/*     */   private final String content;
/*     */   private final List<ReadonlyEmbed> embeds;
/*     */   private final List<ReadonlyAttachment> attachments;
/*     */   private final List<ReadonlyUser> mentionedUsers;
/*     */   private final List<Long> mentionedRoles;
/*     */   
/*     */   public ReadonlyMessage(long id, long channelId, boolean mentionsEveryone, boolean tts, @NotNull ReadonlyUser author, @NotNull String content, @NotNull List<ReadonlyEmbed> embeds, @NotNull List<ReadonlyAttachment> attachments, @NotNull List<ReadonlyUser> mentionedUsers, @NotNull List<Long> mentionedRoles) {
/*  52 */     this.id = id;
/*  53 */     this.channelId = channelId;
/*  54 */     this.mentionsEveryone = mentionsEveryone;
/*  55 */     this.tts = tts;
/*  56 */     this.author = author;
/*  57 */     this.content = content;
/*  58 */     this.embeds = embeds;
/*  59 */     this.attachments = attachments;
/*  60 */     this.mentionedUsers = mentionedUsers;
/*  61 */     this.mentionedRoles = mentionedRoles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getId() {
/*  70 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getChannelId() {
/*  79 */     return this.channelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMentionsEveryone() {
/*  88 */     return this.mentionsEveryone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTTS() {
/*  97 */     return this.tts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ReadonlyUser getAuthor() {
/* 107 */     return this.author;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getContent() {
/* 117 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<ReadonlyEmbed> getEmbeds() {
/* 128 */     return this.embeds;
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
/*     */   public List<ReadonlyAttachment> getAttachments() {
/* 140 */     return this.attachments;
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
/*     */   public List<ReadonlyUser> getMentionedUsers() {
/* 152 */     return this.mentionedUsers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Long> getMentionedRoles() {
/* 162 */     return this.mentionedRoles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebhookMessage toWebhookMessage() {
/* 173 */     return WebhookMessage.from(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 183 */     return toJSONString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 188 */     JSONObject json = new JSONObject();
/* 189 */     json.put("content", this.content)
/* 190 */       .put("embeds", this.embeds)
/* 191 */       .put("mentions", this.mentionedUsers)
/* 192 */       .put("mention_roles", this.mentionedRoles)
/* 193 */       .put("attachments", this.attachments)
/* 194 */       .put("author", this.author)
/* 195 */       .put("tts", this.tts)
/* 196 */       .put("id", Long.toUnsignedString(this.id))
/* 197 */       .put("channel_id", Long.toUnsignedString(this.channelId))
/* 198 */       .put("mention_everyone", this.mentionsEveryone);
/* 199 */     return json.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jaden\Desktop\marley8888.jar!\club\minnced\discord\webhook\receive\ReadonlyMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */