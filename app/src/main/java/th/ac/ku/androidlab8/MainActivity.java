package th.ac.ku.androidlab8;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.ac.ku.androidlab8.models.Person;
import th.ac.ku.androidlab8.models.PersonAdapter;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView personListView;
    private List<Person> personList;
    private PersonAdapter adapter;
    private GestureDetector gestureDetector;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db-person").build();
        personList = new ArrayList<>();
        new LoadDBExecution().execute();
        addButton = (Button) findViewById(R.id.btn_add);
        personListView = (ListView) findViewById(R.id.lv_person);

        adapter = new PersonAdapter(this, R.layout.person_item_view, personList);

        personListView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd(view);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                Log.d("My App", "Long press");
                final int y = (int) motionEvent.getY();
                final int index = personListView.pointToPosition(0, y);
                Log.d("My App", index + "");
                if (index >= personList.size() || index < 0)
                    return;
                final Person person = personList.get(index);

                final View personDialog = getLayoutInflater().inflate(R.layout.person_property_dialog, null);
                ((EditText) personDialog.findViewById(R.id.et_firstname)).setText(person.getFirstname());
                ((EditText) personDialog.findViewById(R.id.et_lastname)).setText(person.getLastname());
                ((EditText) personDialog.findViewById(R.id.et_nickname)).setText(person.getNickname());

                new AlertDialog.Builder(MainActivity.this)
                        .setView(personDialog)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nickname = ((EditText) personDialog.findViewById(R.id.et_nickname)).getText().toString();
                                String firstname = ((EditText) personDialog.findViewById(R.id.et_firstname)).getText().toString();
                                String lastname = ((EditText) personDialog.findViewById(R.id.et_lastname)).getText().toString();

//                                Person person = new Person(nickname, firstname, lastname);
//                                personList.set(index, person);
//                                adapter.notifyDataSetChanged();
                                person.setNickname(nickname);
                                person.setFirstname(firstname);
                                person.setLastname(lastname);
                                new UpdateDataExecution().execute(person);
                            }
                        })
                        .create()
                        .show();
            }

            @Override
            public boolean onFling(final MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.d("My App", "Fling");
                final int y = (int) motionEvent.getY();
                int index = (int) personListView.pointToPosition(0, y);
                if (index >= personList.size() || index < 0)
                    return false;
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure to delete?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteByPosition(0, y);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });

        personListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void deleteByPosition(int x, int y){
        int index = personListView.pointToPosition(x, y);
//        personList.remove(index);
//        adapter.notifyDataSetChanged();
        new DeleteDataExecution().execute(personList.get(index));
    }


    private void onClickAdd(View view){
        final View personDialog = getLayoutInflater().inflate(R.layout.person_property_dialog, null);
        new AlertDialog.Builder(MainActivity.this)
                .setView(personDialog)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nickname = ((EditText) personDialog.findViewById(R.id.et_nickname)).getText().toString();
                        String firstname = ((EditText) personDialog.findViewById(R.id.et_firstname)).getText().toString();
                        String lastname = ((EditText) personDialog.findViewById(R.id.et_lastname)).getText().toString();

                        Person person = new Person(nickname, firstname, lastname);
//                        db.personDao().insertPerson(person);
//                        adapter.notifyDataSetChanged();
                        new InsertDataExecution().execute(person);
                    }
                })
                .create()
                .show();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadDBExecution extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            personList.clear();
            personList.addAll(db.personDao().getPersons());
            Log.d("executing", "load data");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            Log.d("post execute", "load data " + personList);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertDataExecution extends AsyncTask<Person, Void, Void>{

        @Override
        protected Void doInBackground(Person... people) {
            db.personDao().insertPerson(people[0]);
            Log.d("executing", "insert " + people[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            Log.d("post execute", "insert");
            new LoadDBExecution().execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteDataExecution extends AsyncTask<Person, Void, Void>{

        @Override
        protected Void doInBackground(Person... people) {
            db.personDao().deletePerson(people[0]);
            Log.d("executing", "delete " + people[0] );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("post execute", "delete");
            new LoadDBExecution().execute();
        }
    }

    private class UpdateDataExecution extends AsyncTask<Person, Void, Void>{

        @Override
        protected Void doInBackground(Person... people) {
            db.personDao().updatePerson(people[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new LoadDBExecution().execute();
        }
    }

}
