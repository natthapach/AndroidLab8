package th.ac.ku.androidlab8;

import android.content.DialogInterface;
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

import th.ac.ku.androidlab8.models.Person;
import th.ac.ku.androidlab8.models.PersonAdapter;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView personListView;
    private ArrayList<Person> personList;
    private PersonAdapter adapter;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
    }

    private void initInstance() {
        personList = new ArrayList<Person>();
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
                Person person = personList.get(index);

                final View personDialog = getLayoutInflater().inflate(R.layout.person_property_dialog, null);
                ((TextView) personDialog.findViewById(R.id.et_firstname)).setText(person.getFirstname());
                ((TextView) personDialog.findViewById(R.id.et_lastname)).setText(person.getLastname());
                ((TextView) personDialog.findViewById(R.id.et_nickname)).setText(person.getNickname());

                new AlertDialog.Builder(MainActivity.this)
                        .setView(personDialog)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nickname = ((EditText) personDialog.findViewById(R.id.et_nickname)).getText().toString();
                                String firstname = ((EditText) personDialog.findViewById(R.id.et_firstname)).getText().toString();
                                String lastname = ((EditText) personDialog.findViewById(R.id.et_lastname)).getText().toString();

                                Person person = new Person(nickname, firstname, lastname);
                                personList.set(index, person);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }

            @Override
            public boolean onFling(final MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.d("My App", "Fling");
                final int y = (int) motionEvent.getY();
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
        personList.remove(index);
        adapter.notifyDataSetChanged();
    }

    private void editByPosition(int x, int y){
//        int index =
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
                        personList.add(person);
                        adapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();
    }


}
