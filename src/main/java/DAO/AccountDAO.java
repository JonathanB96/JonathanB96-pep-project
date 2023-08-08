package DAO;

import Model.Account;

import java.sql.*;
import java.sql.SQLException;
import Util.ConnectionUtil;


public class AccountDAO {

    
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account (username, password) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
           
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if (pkeyResultSet.next()) {
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    

    public Account findAccountByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                int accountId = rs.getInt("account_id");
                String password = rs.getString("password");
                return new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        } 
        return null;
    }

    public Account findAccountById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                
                String password = rs.getString("password");
                String username = rs.getString("username");
                return new Account(id, username, password);
            }
        } catch (SQLException e) {
           
            System.out.println(e.getMessage());
        } 
        return null;
    }


    public Account userLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
               
                
                return new Account(rs.getInt("account_id"),
                rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        } 
        return null;
    }
}
