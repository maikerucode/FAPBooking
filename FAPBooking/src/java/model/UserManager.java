package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author star
 */
public class UserManager {
    
    private static byte[] key;
    private final String algo = "AES";  // algorithm for encryption/decryption
    
    public UserManager() { }
    
    public User login(String email, String password,
                        String k, String cipher, Connection conn) {
        
        UserManager.key = k.getBytes();
        User user = null;
        
        try {
            String query = "SELECT * FROM hotelbookingdb.user_table WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, email);
            ResultSet result = ps.executeQuery();
            
            // check if ResultSet is not empty & if the passwords match
            if (result.next() && password.equals(decrypt(result.getString("password"), cipher, algo))) {
                user = new User();
                
                // retrieve then store the email & role
                user.setEmail(email);
                user.setRole(result.getString("role"));
            }
            
            result.close();
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return user;
    }
    
    public boolean register(String email, String password, String role,
                            String k, String cipher, Connection conn) {
        
        System.out.println("key: " + k);
        UserManager.key = k.getBytes();
        
        // encrypt password
        String newPass = encrypt(password, cipher, algo);
        
        try {
            String query = "INSERT INTO hotelbookingdb.user_table VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, email);
            ps.setString(2, newPass);
            ps.setString(3, role);
            ps.executeUpdate();
            
            System.out.println("user added!");
            return false;
        }
        
        catch (SQLException sqle) {
//            sqle.printStackTrace();
        }
        
        return true;
    }
    
    //Getting One User
    public ResultSet getSingleUser(String email, Connection conn){
        ResultSet records = null;
        
        try{
            if(conn != null) {
                String query = "SELECT * FROM user_table WHERE email = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, email);
                records = ps.executeQuery(); 
            } else {
                System.out.println("getSingleData is null: ");
            }
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return records;
    }
    
    //Get Data
    public ResultSet getUsers(Connection conn) {
        try {
            if (conn != null) {
                String query = "SELECT * FROM USER_TBL";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet records = ps.executeQuery();
                return records;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("getData is null: ");
        return null;
    }
    
    public static String encrypt(String str, String cipher, String algo) {
        
        String encryptedString = null;
        
        try {
            Cipher c = Cipher.getInstance(cipher);
            final SecretKeySpec secretKey = new SecretKeySpec(key, algo);
            c.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(c.doFinal(str.getBytes()));
        }
        
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return encryptedString;
    }

    public static String decrypt(String str, String cipher, String algo) {
        
        String decryptedString = null;
        
        try {
            Cipher c = Cipher.getInstance(cipher);
            final SecretKeySpec secretKey = new SecretKeySpec(key, algo);
            c.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(c.doFinal(Base64.decodeBase64(str)));
        }
        
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return decryptedString;
    }
    
    
}
