package CIS3334.deckbuilder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultViewHolder extends RecyclerView.ViewHolder {

    TextView textViewName;
    TextView textViewId;
    TextView textViewTypes;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewId = itemView.findViewById(R.id.textViewId);
        textViewTypes = itemView.findViewById(R.id.textViewTypes);
    }
}
