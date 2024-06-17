package com.dana;

import org.json.JSONObject;

public class shippingAddresses {
    private int id;
    private int customer;
    private String title;
    private String line1;
    private String line2;
    private String city;
    private String province;
    private String postcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer() {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1() {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2() {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity() {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince() {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode() {
        this.postcode = postcode;
    }

    public JSONObject objectJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", id);
        jsonObject.put("customer", customer);
        jsonObject.put("title", title);
        jsonObject.put("line1", line1);
        jsonObject.put("line2", line2);
        jsonObject.put("city", city);
        jsonObject.put("province", province);
        jsonObject.put("postcode", postcode)
        return jsonObject;
    }

    public int shippingAddressParsing(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            customer= obj.getInt("customer");
            title = obj.getString("title");
            line1 = obj.getString("line1");
            line2 = obj.getString("line2");
            city = obj.getString("city");
            province = obj.getString("province");
            postcode = obj.getString("postcode");
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }
}

