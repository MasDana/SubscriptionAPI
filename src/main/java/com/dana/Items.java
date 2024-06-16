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

}
