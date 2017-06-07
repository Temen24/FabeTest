package temen.ru.fabetest2;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    public static final int WRAP_CONTENT_LENGTH = 50;
    public NoteAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, null);
        }

        Note note = getItem(position);

        if (note != null){
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);

            title.setText(note.getmTitle());
            date.setText(note.getDateTimeFormatted(getContext()));

            int toWrap = WRAP_CONTENT_LENGTH;
            int lineBreakIndex = note.getmContent().indexOf('\n');
            if(note.getmContent().length() > WRAP_CONTENT_LENGTH || lineBreakIndex < WRAP_CONTENT_LENGTH) {
                if(lineBreakIndex < WRAP_CONTENT_LENGTH) {
                    toWrap = lineBreakIndex;
                }
                if(toWrap > 0) {
                    content.setText(note.getmContent().substring(0, toWrap) + "...");
                } else {
                    content.setText(note.getmContent());
                }
            } else {
                content.setText(note.getmContent());
            }
        }
        return convertView;
    }
}
