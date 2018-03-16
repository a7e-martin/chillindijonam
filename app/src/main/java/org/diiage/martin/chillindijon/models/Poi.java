package org.diiage.martin.chillindijon.models;

import org.json.JSONObject;

/**
 * Created by alexandre on 16/03/18.
 */

public class Poi {

    /*public static class PoiBuilder{
        public static Poi build(JSONObject jsonObject){

        }
    }*/

    private String id;
    private String type;
    private String name;
    private Location location;

    public Poi() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
