public class Task {
    private String name;
    private String description;
    private int id = 0; // модификаторы доступа?

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id++;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
