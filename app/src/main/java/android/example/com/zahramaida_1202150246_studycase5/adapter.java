package android.example.com.zahramaida_1202150246_studycase5;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zahra Maida on 24-Mar-18.
 */

public class adapter extends RecyclerView.Adapter<adapter.holder> {
    //deklarasi variable yang akan digunakan
    private Context cntx;
    private List<AddData> list;
    int color;

    //konstruktor
    public adapter(Context cntx, List<AddData> list, int color){
        this.cntx=cntx;
        this.list=list;
        this.color=color;
    }

    //menentukan viewholder untuk recyclerview
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.cardview_list, parent, false);
        holder hldr = new holder(view);
        return hldr;
    }

    //menyetting nilai yang didapatkan pada viewholder
    @Override
    public void onBindViewHolder(holder holder, int position) {
        AddData data = list.get(position);
        holder.ToDo.setText(data.getTodo());
        holder.Description.setText(data.getDesc());
        holder.Priority.setText(data.getPrior());
        holder.cardv.setCardBackgroundColor(cntx.getResources().getColor(this.color));
    }

    //mendapatkan jumlah list
    @Override
    public int getItemCount() {
        return list.size();
    }

    //mendapatkan list dari adapter
    public AddData getData(int position){
        return list.get(position);
    }

    //untuk menghapus list pada todolist
    public void deleteData(int i){
        //remove item yang terpilih
        list.remove(i);
        //memberi notifikasi item yang di remove
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, list.size());
    }

    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable yang akan digunakan
        public TextView ToDo, Description, Priority;
        public CardView cardv;
        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            ToDo = itemView.findViewById(R.id.headline);
            Description = itemView.findViewById(R.id.explanation);
            Priority = itemView.findViewById(R.id.number);
            cardv = itemView.findViewById(R.id.cardlist);
        }
    }
}
