package it.uniba.dib.sms222316.Goals;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import it.uniba.dib.sms222316.Goals.achievements;
import it.uniba.dib.sms222316.Goals.missions;


public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private String[] titles;
    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String[] titles) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new achievements();
            case 1:
                return new missions();
        }
        return new achievements();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
