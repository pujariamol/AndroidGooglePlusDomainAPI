package com.amol.plusmini;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.api.services.plusDomains.model.Person;

import java.util.List;

public class PersonsListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "PersonsListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();

        List<Person> personsList  = ShareObject.getCirclePeople();

//        for(Person person : personsList) {
//            personsList.add(person);
//        }
        listView.setAdapter(new PersonsListAdapter(this, personsList));
        listView.setMinimumHeight(30);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "List item clicked --> " + adapterView.getItemAtPosition(i));
        Person item = (Person)adapterView.getItemAtPosition(i);
        Log.d(TAG," ==== "+item.getDisplayName());
        ShareObject.setSelfProfile(item);
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private class PersonsListAdapter extends BaseAdapter {
        private Context context;
        private List<Person> personList;
        public PersonsListAdapter(Context context, List<Person> personList) {
            this.context = context;
            this.personList = personList;
        }

        @Override
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return personList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView profilePhoto;
            TextView name;


            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.colleagues_list_item, null);
            }
            profilePhoto = (ImageView)convertView.findViewById(R.id.profile_photo);
            name = (TextView)convertView.findViewById(R.id.colleague_name);

            // Set person details over here
            name.setText(personList.get(position).getDisplayName());

            profilePhoto.setBackgroundResource(R.drawable.ic_launcher);

            return convertView;
        }

    }
}
