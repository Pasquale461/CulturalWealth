package it.uniba.dib.sms222316.Gallery;

public class Heritage {

    private String Title, Description, Type, Pic;

    public Heritage() {
    }

    public Heritage(String title, String description, String type, String pic) {
        Title = title;
        Description = description;
        Type = type;
        Pic = pic;
    }

    public String getPic() {
        return Pic;
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
