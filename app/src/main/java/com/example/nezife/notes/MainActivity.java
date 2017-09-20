package com.example.nezife.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList <String> arrayList;
    ArrayAdapter <String> arrayAdapter;
    String noteText;
    int position;
    final int CONTEXT_MENU_DELETE = 1;
    final int CONTEXT_MENU_CANCEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditNote.class);
                intent.putExtra(IntentConstants.NOTE_DATA, arrayList.get(position).toString());
                intent.putExtra(IntentConstants.ITEM_POSITION, position);
                startActivityForResult(intent, IntentConstants.SECOND_REQUEST_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerForContextMenu(adapterView);
                openContextMenu(adapterView);

                return false;
            }
        });

        try {
            Scanner scanner = new Scanner(openFileInput("Note.txt"));
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                arrayAdapter.add(data);
            }
            scanner.close();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Delete or cancel?");
        menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "DELETE");
        menu.add(Menu.NONE, CONTEXT_MENU_CANCEL, Menu.NONE, "CANCEL");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE: {
                arrayList.remove(info.position);
                arrayAdapter.notifyDataSetChanged();
            }
            break;
            case CONTEXT_MENU_CANCEL: {}
            break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            PrintWriter printWriter = new PrintWriter(openFileOutput("Note.txt", Context.MODE_PRIVATE));
            for(String data: arrayList) {
                printWriter.println(data);
            }
            printWriter.close();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        finish();
    }

    public void onClickFAB(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, NoteActivity.class);
        startActivityForResult(intent, IntentConstants.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == IntentConstants.REQUEST_CODE) {
            noteText = data.getStringExtra(IntentConstants.NOTE_FIELD);
            arrayList.add(noteText);
            arrayAdapter.notifyDataSetChanged();
        } else if(resultCode == IntentConstants.SECOND_REQUEST_CODE) {
            noteText = data.getStringExtra(IntentConstants.EDITED_NOTE);
            position = data.getIntExtra(IntentConstants.ITEM_POSITION, -1);
            arrayList.remove(position);
            arrayList.add(position,noteText);
            arrayAdapter.notifyDataSetChanged();
        }

    }


}
