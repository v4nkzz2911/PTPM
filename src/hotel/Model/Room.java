/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.Model;

/**
 *
 * @author er1nzz
 */
public class Room {
    private String id;
    private String name;
    private String type;
    private String displayPrice;
    private String description;

    public Room() {
    }

    public Room(String id, String name, String type, String displayPrice, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.displayPrice = displayPrice;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name=" + name + ", type=" + type + ", displayPrice=" + displayPrice + ", description=" + description + '}';
    }
    
    
    
}
