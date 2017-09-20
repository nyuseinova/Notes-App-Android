package com.example.nezife.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
    }

    public void onSaveButtonClick(View view){
        String noteText = ((EditText)findViewById(R.id.note)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(IntentConstants.NOTE_FIELD, noteText);
        setResult(IntentConstants.RESULT_CODE, intent);
        finish();
    }
}
