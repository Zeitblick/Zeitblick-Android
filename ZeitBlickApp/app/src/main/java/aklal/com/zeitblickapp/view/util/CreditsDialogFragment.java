package aklal.com.zeitblickapp.view.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import aklal.com.zeitblickapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static aklal.com.zeitblickapp.R.layout.information_dialog;

/**
 * Created by Aklal on 27.10.16.
 */
public class CreditsDialogFragment extends DialogFragment {

    Unbinder unbinder;

    @BindView(R.id.et_credits)
    EditText mEtCredits;

    public CreditsDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CreditsDialogFragment newInstance(String title) {

        CreditsDialogFragment frag = new CreditsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(information_dialog, container);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        //mEtCredits = (EditText) view.findViewById(R.id.txt_your_name);
        mEtCredits.setKeyListener(null);
        mEtCredits.setText("# Credits\n" +
                "\n" +
                "## Eine App von:\n" +
                "Stefan Ruëtz / Carte Blanche Design Studio  \n" +
                "http://cb-ds.com/  \n" +
                "\n" +
                "Franz Krekeler  \n" +
                "http://filmfranz.de/\n" +
                "\n" +
                "Olivier Trébosc  \n" +
                "\n" +
                "Bastian Clausdorff  \n" +
                "http://digitalstuff.de/\n" +
                "\n" +
                "\n" +
                "## Special Thanks to\n" +
                "Dr. Antje Schmidt   \n" +
                "http://mkg-hamburg.de\n" +
                "\n" +
                "\n" +
                "## Rahmen\n" +
                "Entstanden im Rahmen des Kultur-Hackathons \"Coding Da Vinci Nord 2016\" " +
                "(https://codingdavinci.de) und unter Verwendung der offenen Kulturdaten " +
                "des \"Museums für Kunst und Gewerbe Hamburg\" " +
                "(http://sammlungonline.mkg-hamburg.de/).\n");

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Credits");
        getDialog().setTitle(title);
    }

}