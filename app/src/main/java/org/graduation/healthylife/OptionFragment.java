package org.graduation.healthylife;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class OptionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_main, container, false);
        view.findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = ((RadioGroup) view.findViewById(R.id.radiogroup_main))
                        .getCheckedRadioButtonId();
                if (checked == -1) {
                    return;
                }
                switch (checked) {
                    case R.id.radio_sad:
                        Log.d("onclick", "sad");
                        break;
                    case R.id.radio_happy:
                        Log.d("onclick", "happy");
                        break;
                    case R.id.radio_angry:
                        Log.d("onclick", "angry");
                        break;
                    case R.id.radio_content:
                        Log.d("onclick", "content");
                        break;
                    case R.id.radio_tense:
                        Log.d("onclick", "tense");
                        break;
                    case R.id.radio_energetic:
                        Log.d("onclick", "energetic");
                }
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.layout_mainpage, new ResultFragment())
                        .commit();
            }
        });
        return view;
    }
}
