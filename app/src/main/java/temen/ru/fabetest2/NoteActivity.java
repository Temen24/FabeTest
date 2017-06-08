package temen.ru.fabetest2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;
    private String mNoteFileName;
    private Note mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mEtTitle = (EditText) findViewById(R.id.note_et_title);
        mEtContent = (EditText) findViewById(R.id.note_et_content);
        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        if (mNoteFileName != null && !mNoteFileName.isEmpty()) {
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);
            if (mLoadedNote != null) {
                mEtTitle.setText(mLoadedNote.getmTitle());
                mEtContent.setText(mLoadedNote.getmContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mEtTitle.getText().toString().trim().isEmpty() && mEtContent.getText().toString().isEmpty()){
            deleteNote();
        } else {
            saveNote();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_note_delete:
                deleteNote();
                break;
            case R.id.action_share:
                Toast.makeText(getApplicationContext(), R.string.share_unav, Toast.LENGTH_SHORT).show();

        }
        return true;
    }


    private void saveNote() {
        Note note;

        if(mEtTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, R.string.emptyTitle, Toast.LENGTH_SHORT).show();
            return;
        }
        if(mLoadedNote == null) {
            note = new Note(System.currentTimeMillis(), mEtTitle.getText().toString(),
                    mEtContent.getText().toString());
        } else {
            note = new Note(mLoadedNote.getmDateTime(), mEtTitle.getText().toString(),
                    mEtContent.getText().toString());
        }
        if (Utilities.saveNote(this, note)) {
            Toast.makeText(this, R.string.noteSaved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.noteNotSaved, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteNote() {
        if(mLoadedNote == null){
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Deleting...")
                    .setMessage("You are about to delete the note, are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Utilities.deleteNote(getApplicationContext(), mLoadedNote.getmDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);
            dialog.show();
        }
    }

}
