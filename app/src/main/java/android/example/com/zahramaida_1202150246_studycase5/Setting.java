package android.example.com.zahramaida_1202150246_studycase5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Setting extends AppCompatActivity {
    //deklarasi variable yang akan digunakan
    TextView shapecolor;
    int colorid;
    AlertDialog.Builder alert;
    SharedPreferences.Editor sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        //membuat alert dialog baru bernama alert
        alert = new AlertDialog.Builder(this);

        //menginisialisasi shared preference
        SharedPreferences sharedP = getApplicationContext().getSharedPreferences("Preferences", 0);
        sp = sharedP.edit();
        colorid = sharedP.getInt("Colourground", R.color.white);
        //mengakses text view pada layout
        shapecolor = findViewById(R.id.shapecolor);
        //set shape color dengan color id yang terpilih
        shapecolor.setText(getShapeColor(colorid));
    }

    //apabila tombol back di tekan
    @Override
    public void onBackPressed() {
        //intent baru dari pengaturan menuju list to do
        Intent intent = new Intent(Setting.this, ListToDo.class);
        //memulai intent
        startActivity(intent);
        //menutup aktivity setelah intent di jalanlan
        finish();
    }

    //method yang dijalankan ketika pilihan pada menu dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            //menjalankan method onbackpressed
            this.onBackPressed();
        }
        return true;
    }

    //mendapatkan string warna yang akan digunakan untuk mengubah shape color
    public String getShapeColor(int i){
        if (i==R.color.red){
            return "Red";
        }else if (i==R.color.green){
            return "Green";
        }else if (i==R.color.blue){
            return "Blue";
        }else if (i==R.color.purple){
            return "Purple";
        }else if (i==R.color.deep_orange){
            return "Orange";
        }else{
            return "Default";
        }
    }

    //mendapatkan id dari warna yang akan digunakan
    public int getColorid(int i){
        if (i==R.color.red){
            return R.id.red;
        }else if (i==R.color.green){
            return R.id.green;
        }else if (i==R.color.blue){
            return R.id.blue;
        }else if (i==R.color.purple){
            return R.id.purple;
        }else if (i==R.color.deep_orange){
            return R.id.orange;
        }else{
            return R.id.white;
        }
    }

    public void choosecolor(View view) {
        //set title dari alert dialog menjadi Shape Color
        alert.setTitle("Shape Color");
        //membuat view baru
        View view1 = getLayoutInflater().inflate(R.layout.colorsettings, null);
        //menampilkan view yang telah dibuat
        alert.setView(view1);

        //mengakses radio group pada layout
        final RadioGroup radG = view1.findViewById(R.id.radiocolor);
        radG.check(getColorid(colorid));

        //ketika menekan Ok pada alert dialog
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //mendapatkan id radio button yang di pilih
                int a = radG.getCheckedRadioButtonId();
                switch (a){
                    case R.id.red:
                        colorid = R.color.red;
                        break;
                    case R.id.green:
                        colorid = R.color.green;
                        break;
                    case R.id.blue:
                        colorid = R.color.blue;
                        break;
                    case R.id.purple:
                        colorid = R.color.purple;
                        break;
                    case R.id.orange:
                        colorid = R.color.deep_orange;
                        break;
                    case R.id.white:
                        colorid = R.color.white;
                        break;
                }
                //set shape color menjadi color id yang dipilih
                shapecolor.setText(getShapeColor(colorid));
                //menaruh shared preference
                sp.putInt("Colourground", colorid);
                //commit shared preference
                sp.commit();
            }
        });

        //ketika menekan Cancel pada alert dialog
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //membuat dan menampilkan alert dialog
        alert.create().show();
    }
}
