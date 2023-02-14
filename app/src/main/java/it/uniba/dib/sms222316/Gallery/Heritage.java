package it.uniba.dib.sms222316.Gallery;

public class Heritage {

    private String Title, Description, Type;

    public Heritage() {
    }

    public Heritage(String title, String description, String type) {
        Title = title;
        Description = description;
        Type = type;
    }


    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getType() {
        return Type;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setType(String type) {
        Type = type;
    }

}
