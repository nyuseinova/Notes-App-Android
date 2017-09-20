package com.example.nezife.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNote extends AppCompatActivity {
    String noteText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        Intent intent = getIntent();
        noteText = intent.getStringExtra(IntentConstants.NOTE_DATA);
        position = intent.getIntExtra(IntentConstants.ITEM_POSITION, -1);
        EditText noteData = (EditText) findViewById(R.id.note);
        noteData.setText(noteText);
    }

    public void onSaveButtonClick(View view) {
        String editedNoteText = ((EditText)findViewById(R.id.note)).getText().toString();
        Intent intent =  new Intent();
        intent.putExtra(IntentConstants.EDITED_NOTE, editedNoteText);
        intent.putExtra(IntentConstants.ITEM_POSITION, position);
        setResult(IntentConstants.SECOND_RESULT_CODE, intent);
        finish();
    }
}
