package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/", ctx -> ctx.result("Welcome to my Project"));
        app.post("/register", this::insertAccountHandler);
        app.post("/login", this::userLoginHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        // app.get("/messages", ctx -> {
        //     List<Message> messages = messageService.getAllMessages();
        //     ctx.json(messages);
        // });
        // app.get("/accounts/{account_id}/messages", ctx -> {
        //     int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        //     List<Message> messages = messageService.getMessagesByUserId(accountId);
        //     ctx.json(messages);
        // });


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void insertAccountHandler(Context ctx) throws JsonProcessingException {
        
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
            Account addedAccount = accountService.registerAccount(account);
            if(addedAccount!=null){
                ctx.json(mapper.writeValueAsString(addedAccount));
            } else{
                ctx.status(400);
            }
            
    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggeInUser = accountService.loginAccount(account);
        if(loggeInUser!=null){
            ctx.json(loggeInUser);
        } else{
            ctx.status(401);
        }

    }

    private void getAllMessagesHandler(Context ctx){
        
            List<Message> messages = messageService.getAllMessages();
            ctx.json(messages);

    }
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        
            if (createdMessage != null) {
                ctx.json(createdMessage);
            } else {
                ctx.status(400);
            }
    }
    private void getMessageById(Context ctx) throws JsonProcessingException{
        // ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
            Message foundMessage = messageService.getMessageById(messageId);
           
            if (foundMessage != null) {
                ctx.json(foundMessage);
            } else {
                ctx.status(200);
            }
    }

    private void deleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        boolean deleted = messageService.deleteMessage(messageId);
            if (deleted) {
                ctx.json(message);
            } else {
                ctx.status(200);
            }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException{
        // ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);
        // int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        // boolean updatedMessage = messageService.updateMessage(message);
             
            
        //     if (updatedMessage) {
        //         ctx.json(message);
        //     } else {
        //         ctx.status(200);
        //     }
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        message.setMessage_id(messageId);
        message.setPosted_by(message.getPosted_by());
        message.setTime_posted_epoch(message.getTime_posted_epoch());
        boolean updated = messageService.updateMessage(message);
        if (updated) {
            ctx.json(message);
        } else {
            ctx.status(400);
        }
    }



}