package Service;
import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import Model.Account;
import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        
        if (message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 254) {
            return null;
        }

        
        AccountDAO accountDAO = new AccountDAO();
        Account existingAccount = accountDAO.findAccountById(message.getPosted_by());
        if (existingAccount == null) {
            return null;
        }

        
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public boolean deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    public boolean updateMessage(Message message) {
        
        if (message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 254) {
            return false;
        }

        return messageDAO.updateMessage(message);
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }
}

