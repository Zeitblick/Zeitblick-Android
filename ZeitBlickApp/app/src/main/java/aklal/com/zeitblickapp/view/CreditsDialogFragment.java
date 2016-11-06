package aklal.com.zeitblickapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import aklal.com.zeitblickapp.R;

/**
 * Created by Aklal on 27.10.16.
 *
 * Display the credits when button on intro_fragment is clicked
 */
public class CreditsDialogFragment extends DialogFragment {

    HtmlTextView mHTView;

    final String CREDIT = "<h1>Credits:</h1>" +
            "\n" +
            "<h2>Eine App von:</h2>" +
            "<ul><li>Stefan Ruëtz / Carte Blanche Design Studio  \n" +
            "<a href=\"http://cb-ds.com/\">http://cb-ds.com/</a></li>" +
            "\n" +
            "<li>Franz Krekeler  \n" +
            "<a href=\"http://filmfranz.de/\">http://filmfranz.de/</a></li>" +
            "\n" +
            "<li>Bastian Clausdorff  \n" +
            "<a href=\"http://digitalstuff.de/\">http://digitalstuff.de/</a>" +
            "\n" +
            "<li>Olivier Trébosc </li></ul>" +
            "\n" +
            "\n" +
            "<h2>Special Thanks to:</h2>" +
            "Dr. Antje Schmidt   \n" +
            "<a href=\"http://mkg-hamburg.de\">http://mkg-hamburg.de</a></li>\n" +
            "\n" +
            "\n" +
            "<h2>Rahmen:</h2>" +
            "Entstanden im Rahmen des Kultur-Hackathons \"Coding Da Vinci Nord 2016\" (<a href=\"https://codingdavinci.de\">https://codingdavinci.de</a>) und unter Verwendung der offenen Kulturdaten des \"Museums für Kunst und Gewerbe Hamburg\" (<a href=\"http://sammlungonline.mkg-hamburg.de/\">http://sammlungonline.mkg-hamburg.de/</a>).";


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
        View view = inflater.inflate(R.layout.credits_dialog, container);
        mHTView = (HtmlTextView) view.findViewById(R.id.html_text);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHTView.setHtml(CREDIT);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Credits");
        getDialog().setTitle(title);
    }
}