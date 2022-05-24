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

    public UserManager() {
    }

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

                // retrieve then store user's account details
                user.setEmail(email);
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                user.setRole(result.getString("role"));
                user.setRefNumber(result.getString("refNumber"));
            }

            result.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return user;
    }

    public boolean register(String email, String firstName, String lastName,
            String password, String role, String k, String cipher,
            Connection conn) {

        System.out.println("key: " + k);
        UserManager.key = k.getBytes();

        // encrypt password
        String newPass = encrypt(password, cipher, algo);

        try {
            String query = "INSERT INTO hotelbookingdb.user_table VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, newPass);
            ps.setString(5, role);
            ps.setString(6, "");
            ps.executeUpdate();

            System.out.println("user added!");
            return false;
        } catch (SQLException sqle) {
//            sqle.printStackTrace();
        }

        return true;
    }

    public void updateRefNumber(String refNumber, String email, Connection conn) {
        System.out.println("new refNumber: " + refNumber);

        try {
            String query = "UPDATE hotelbookingdb.user_table"
                    + " SET refNumber=? WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, refNumber);
            ps.setString(2, email);
            ps.executeUpdate();

            System.out.println("reference number of user updated!");
        } catch (SQLException sqle) {
//            sqle.printStackTrace();
        }
    }

    public String getRefNumber(String email, Connection conn) {
        String refNumber = "";

        try {
            String query = "SELECT * FROM hotelbookingdb.user_table WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet result = ps.executeQuery();
            result.next();

            refNumber = result.getString("refNumber");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return refNumber;
    }

    public static String encrypt(String str, String cipher, String algo) {

        String encryptedString = null;

        try {
            Cipher c = Cipher.getInstance(cipher);
            final SecretKeySpec secretKey = new SecretKeySpec(key, algo);
            c.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(c.doFinal(str.getBytes()));
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return decryptedString;
    }

    public static ResultSet getAllUsers(Connection conn) {
        ResultSet result = null;
        
        try {
            if (conn != null) {
                String query = "SELECT * FROM hotelbookingdb.user_table";
                PreparedStatement ps = conn.prepareStatement(query);
                result = ps.executeQuery();
                System.out.println("reference number of user updated!");
                return result;
            } else {
                System.out.println("getAllUsers conn null");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }

        return result;
    }

    public static ResultSet getSingleUser(String email, Connection conn) {
        ResultSet result = null;
        
        try {
            if (conn != null) {
                String query = "SELECT * FROM hotelbookingdb.user_table WHERE email = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, email);
                result = ps.executeQuery();
                System.out.println("reference number of user updated!");
                return result;
            } else {
                System.out.println("getAllUsers conn null");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }

        return result;
    }
}
