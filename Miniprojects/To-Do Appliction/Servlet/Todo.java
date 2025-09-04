package todopro;

import java.sql.Timestamp;

public class Todo {
    private int id;
    private String description;
    private Timestamp dateandtime;
    private String status;

    // Constructor without ID (for inserts)
    public Todo(String description, Timestamp dateandtime, String status) {
        this.description = description;
        this.dateandtime = dateandtime;
        this.status = status;
    }

    // Constructor with ID (for selects/updates)
    public Todo(int id, String description, Timestamp dateandtime, String status) {
        this.id = id;
        this.description = description;
        this.dateandtime = dateandtime;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateandtime() {
        return dateandtime;
    }
    public void setDateandtime(Timestamp dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", description=" + description +
               ", dateandtime=" + dateandtime + ", status=" + status + "]";
    }
}
