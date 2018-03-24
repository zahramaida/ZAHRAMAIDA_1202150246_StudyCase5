package android.example.com.zahramaida_1202150246_studycase5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ListToDo extends AppCompatActivity {
    //deklarasi variable yang akan digunakan
    database db;
    RecyclerView rv;
    adapter adapter;
    ArrayList<AddData> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_to_do);
        //set title menjadi To Do List
        setTitle("To Do List");

        //mengakses recyclerview yang ada pada layout
        rv = findViewById(R.id.recview);
        //membuat araylist baru
        datalist = new ArrayList<>();
        //membuat database baru
        db = new database(this);
        //memanggil method readdata
        db.readdata(datalist);

        //menginisialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);

        //membuat adapter baru
        adapter = new adapter(this,datalist, color);
        //menghindari perubahan ukuran yang tidak perlu ketika menambahkan / hapus item pada recycler view
        rv.setHasFixedSize(true);
        //menampilkan layoutnya linier
        rv.setLayoutManager(new LinearLayoutManager(this));
        //inisiasi adapter untuk recycler view
        rv.setAdapter(adapter);

        //menjalankan method hapus data pada list to do
        swipeToDelete();
    }

    //membuat method untuk menghapus item pada to do list
    public void swipeToDelete(){
        //membuat touch helper baru untuk recycler view
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);
                //apabila item di swipe ke arah kiri
                if(direction==ItemTouchHelper.LEFT){
                    //remove item yang dipilih dengan mengenali todonya sebagai primary key
                    if(db.removedata(current.getTodo())){
                        //menghapus data
                        adapter.deleteData(position);
                        //membuat snack bar dan pemberitahuan bahwa item sudah terhapus dengan durasi 1 sekon
                        Snackbar.make(findViewById(R.id.coor), "Data Deleted", 1000).show();
                    }
                }
            }
        };
        //menentukan itemtouchhelper untuk recycler view
        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rv);
    }
    //ketika menu pada activity di buat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    //method yang dijalankan ketika item di pilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //mendapatkan id dari item yang
        int id = item.getItemId();
        //apabila item yang dipilih adalah setting
        if (id==R.id.action_settings){
            //membuat intent baru dari list to do ke pengaturan
            Intent intent = new Intent(ListToDo.this, Setting.class);
           //memulai intent
            startActivity(intent);
            //menutup aktivitas setelah intent dijalankan
            finish();
        }
        return true;
    }

    //method yang akan dijalankan ketika tombol add di klik
    public void add(View view) {
        //intent dari list to do ke add to do
        Intent intent = new Intent(ListToDo.this, AddToDo.class);
        //memulai intent
        startActivity(intent);
    }
}
