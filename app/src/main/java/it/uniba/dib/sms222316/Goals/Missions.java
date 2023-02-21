package it.uniba.dib.sms222316.Goals;

public class Missions {

    private String Title;
    private int ProgressNumber, Rewards;

    public Missions(String title, int progressNumber, int rewards) {
        Title = title;
        ProgressNumber = progressNumber;
        Rewards = rewards;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getProgressNumber() {
        return ProgressNumber;
    }

    public void setProgressNumber(int progressNumber) {
        ProgressNumber = progressNumber;
    }

    public int getRewards() {
        return Rewards;
    }

    public void setRewards(int rewards) {
        Rewards = rewards;
    }
}
