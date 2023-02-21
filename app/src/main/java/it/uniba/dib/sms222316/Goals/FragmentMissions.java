package it.uniba.dib.sms222316.Goals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import it.uniba.dib.sms222316.R;

public class FragmentMissions extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_missions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Missions> fullHrg, data;
        fullHrg = new ArrayList<>();

        fullHrg.add(new Missions("Missione del giorno", 2,30));
        fullHrg.add(new Missions("Start", 3,80));
        fullHrg.add(new Missions("buh", 1,10));
        data = new ArrayList<>(fullHrg);
        RecyclerView myrv = view.findViewById(R.id.MissionsRecycler);

        RecyclerMissionsAdapter myAdapter = new RecyclerMissionsAdapter(data);
        myrv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        myrv.setAdapter(myAdapter);
        //canScrollHorizontally()

    }
}