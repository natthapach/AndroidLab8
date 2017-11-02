package th.ac.ku.androidlab8.models;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import th.ac.ku.androidlab8.R;

/**
 * Created by MegapiesPT on 27/10/2560.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    public PersonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_item_view, null);
        TextView nicknameTextView = (TextView) convertView.findViewById(R.id.tv_nickname);
        TextView firstnameTextView = (TextView) convertView.findViewById(R.id.tv_firstname);
        TextView lastnameTextView = (TextView) convertView.findViewById(R.id.tv_lastname);

        Person person = getItem(position);
        nicknameTextView.setText(person.getNickname());
        firstnameTextView.setText(person.getFirstname());
        lastnameTextView.setText(person.getLastname());

        return convertView;
    }
}
