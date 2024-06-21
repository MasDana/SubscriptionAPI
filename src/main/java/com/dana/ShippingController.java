package com.dana;
import java.sql.*;
public class ShippingController {
    public static boolean updateShippingAddressById(int id, ShippingAddresses address) {
        try (Connection conn = connectionDatabase.getConnection()) {
            String sql = "UPDATE shippingAddresses SET title = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address.getTitle());
            pstmt.setString(2, address.getLine1());
            pstmt.setString(3, address.getLine2());
            pstmt.setString(4, address.getCity());
            pstmt.setString(5, address.getProvince());
            pstmt.setString(6, address.getPostcode());
            pstmt.setInt(7, id);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
