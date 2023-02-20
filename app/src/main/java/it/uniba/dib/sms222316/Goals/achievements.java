package it.uniba.dib.sms222316.Goals;

public class Achievements {
    private String TargetPoint, CategoryName, Relic;

    public Achievements(String targetPoint, String categoryName, String relic) {
        TargetPoint = targetPoint;
        CategoryName = categoryName;
        Relic = relic;
    }

    public String getTargetPoint() {
        return TargetPoint;
    }

    public void setTargetPoint(String targetPoint) {
        TargetPoint = targetPoint;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getRelic() {
        return Relic;
    }

    public void setRelic(String relic) {
        Relic = relic;
    }
}
