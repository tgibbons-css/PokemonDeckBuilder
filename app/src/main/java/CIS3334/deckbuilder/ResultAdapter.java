package CIS3334.deckbuilder;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private List<Card> cards;

    public ResultAdapter() {
        cards = new ArrayList<>();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.textViewName.setText(cards.get(position).getName());
        holder.textViewId.setText(cards.get(position).getId());
        //TODO: add this types back into the view
//        holder.textViewTypes.setText(cards.get(position).getTypes());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void updateCardList(List<Card> newCardList) { cards = newCardList; }
}
