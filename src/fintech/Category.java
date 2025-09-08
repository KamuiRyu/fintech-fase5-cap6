package fintech;

import java.util.Date;

public class Category {
    private int id;
    private String name;
    private String description;
    private short type;
    private Date createdAt;
    private Date updatedAt;

    // Construtor
    public Category(int id, String name, String description, short type, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters (Encapsulamento)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void showInfo() {
        System.out.println("Category: " + name + " (ID: " + id + ")");
        System.out.println("Description: " + description);
        System.out.println("Type: " + type);
        System.out.println("Created At: " + createdAt);
        System.out.println("Updated At: " + updatedAt);
    }
}

