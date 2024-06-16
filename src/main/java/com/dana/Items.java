package com.dana;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Items {

    private int id;
    private String name;
    private  int price;
    private String type;
    private int isActive;

    public int getId(){return id;}

    public void setId(){this.id = id;}

    public String getName(){return name;}

    public void setName(){this.name = name;}

    public int getPrice(){return price;}

    public void setPrice(){this.price = price;}

    public String getType(){return type;}

    public void setType(){this.type = type;}

    public int getIsActive(){return isActive;}

    public void setIsActive(){this.isActive = isActive;}

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("product_name", name);
        jsonObject.put("price", price);
        jsonObject.put("type", type);
        jsonObject.put("is_active", isActive);
        return jsonObject;
    }

    public int itemsParse(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            name = obj.getString("name");
            price = obj.getInt("price");
            type = obj.getString("type");
            isActive = obj.getInt("is_active");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    public void insertItem() {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "INSERT INTO item (name, price, type, is_active) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.setString(3, type);
            pstmt.setInt(4, isActive);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateItem(String idItem) {
        try {
            Connection conn = connectionDatabase.getConnection();
            String sql = "UPDATE item SET name = \"" + name +
                    "\" , price = \"" + price +
                    "\" , type = \"" + type +
                    "\" , is_active = \"" + isActive +
                    "\" WHERE id = " + idItem;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
