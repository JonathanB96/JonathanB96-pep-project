package DAO;

import Model.Message;
import java.sql.*;
import java.sql.SQLException;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message insertMessage(Message message) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedMessageId = rs.getInt(1);
                return new Message(generatedMessageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");
                messages.add(new Message(messageId, postedBy, messageText, timePostedEpoch));
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return messages;
    }

    public Message getMessageById(int messageId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE message_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            rs = ps.executeQuery();
            if (rs.next()) {
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");
                return new Message(messageId, postedBy, messageText, timePostedEpoch);
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return null;
    }

    public boolean deleteMessage(int messageId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return false;
    }

    public boolean updateMessage(Message message) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getMessage_id());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return false;
    }

    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");
                messages.add(new Message(messageId, postedBy, messageText, timePostedEpoch));
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., logging the error
            System.out.println(e.getMessage());
        } 
        return messages;
    }
}
