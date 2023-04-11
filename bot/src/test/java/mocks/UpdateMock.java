package mocks;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.ChatJoinRequest;
import com.pengrad.telegrambot.model.ChatMemberUpdated;
import com.pengrad.telegrambot.model.ChosenInlineResult;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Poll;
import com.pengrad.telegrambot.model.PollAnswer;
import com.pengrad.telegrambot.model.PreCheckoutQuery;
import com.pengrad.telegrambot.model.ShippingQuery;
import com.pengrad.telegrambot.model.Update;

public class UpdateMock extends Update {

    private Integer update_id;
    private Message message;
    private Message edited_message;
    private Message channel_post;
    private Message edited_channel_post;
    private InlineQuery inline_query;
    private ChosenInlineResult chosen_inline_result;
    private CallbackQuery callback_query;
    private ShippingQuery shipping_query;
    private PreCheckoutQuery pre_checkout_query;
    private Poll poll;
    private PollAnswer poll_answer;
    private ChatMemberUpdated my_chat_member;
    private ChatMemberUpdated chat_member;
    private ChatJoinRequest chat_join_request;

    public UpdateMock setUpdate_id(Integer update_id) {
        this.update_id = update_id;
        return this;
    }

    public UpdateMock setMessage(Message message) {
        this.message = message;
        return this;
    }

    public UpdateMock setEdited_message(Message edited_message) {
        this.edited_message = edited_message;
        return this;
    }

    public UpdateMock setChannel_post(Message channel_post) {
        this.channel_post = channel_post;
        return this;
    }

    public UpdateMock setEdited_channel_post(Message edited_channel_post) {
        this.edited_channel_post = edited_channel_post;
        return this;
    }

    public UpdateMock setInline_query(InlineQuery inline_query) {
        this.inline_query = inline_query;
        return this;
    }

    public UpdateMock setChosen_inline_result(ChosenInlineResult chosen_inline_result) {
        this.chosen_inline_result = chosen_inline_result;
        return this;
    }

    public UpdateMock setCallback_query(CallbackQuery callback_query) {
        this.callback_query = callback_query;
        return this;
    }

    public UpdateMock setShipping_query(ShippingQuery shipping_query) {
        this.shipping_query = shipping_query;
        return this;
    }

    public UpdateMock setPre_checkout_query(PreCheckoutQuery pre_checkout_query) {
        this.pre_checkout_query = pre_checkout_query;
        return this;
    }

    public UpdateMock setPoll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public UpdateMock setPoll_answer(PollAnswer poll_answer) {
        this.poll_answer = poll_answer;
        return this;
    }

    public UpdateMock setMy_chat_member(ChatMemberUpdated my_chat_member) {
        this.my_chat_member = my_chat_member;
        return this;
    }

    public UpdateMock setChat_member(ChatMemberUpdated chat_member) {
        this.chat_member = chat_member;
        return this;
    }

    public UpdateMock setChat_join_request(ChatJoinRequest chat_join_request) {
        this.chat_join_request = chat_join_request;
        return this;
    }

    @Override
    public Integer updateId() {
        return update_id;
    }

    @Override
    public Message message() {
        return message;
    }

    @Override
    public Message editedMessage() {
        return edited_message;
    }

    @Override
    public Message channelPost() {
        return channel_post;
    }

    @Override
    public Message editedChannelPost() {
        return edited_channel_post;
    }

    @Override
    public InlineQuery inlineQuery() {
        return inline_query;
    }

    @Override
    public ChosenInlineResult chosenInlineResult() {
        return chosen_inline_result;
    }

    @Override
    public CallbackQuery callbackQuery() {
        return callback_query;
    }

    @Override
    public ShippingQuery shippingQuery() {
        return shipping_query;
    }

    @Override
    public PreCheckoutQuery preCheckoutQuery() {
        return pre_checkout_query;
    }

    @Override
    public Poll poll() {
        return poll;
    }

    @Override
    public PollAnswer pollAnswer() {
        return poll_answer;
    }

    @Override
    public ChatMemberUpdated myChatMember() {
        return my_chat_member;
    }

    @Override
    public ChatMemberUpdated chatMember() {
        return chat_member;
    }

    @Override
    public ChatJoinRequest chatJoinRequest() {
        return chat_join_request;
    }
}
