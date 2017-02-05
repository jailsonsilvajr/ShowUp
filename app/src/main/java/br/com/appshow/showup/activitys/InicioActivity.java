package br.com.appshow.showup.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.appshow.showup.R;
/**
 * Created by jailson on 04/02/17.
 */

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_content);

        TextView inicio_content_textView_cadastro = (TextView) findViewById(R.id.inicio_content_textView_cadastro);
        inicio_content_textView_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open_cadastro_activity();
            }
        });

        TextView inicio_content_textView_login = (TextView) findViewById(R.id.inicio_content_textView_login);
        inicio_content_textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open_login_activity();
            }
        });
    }

    public void open_cadastro_activity(){

        Intent activity_cadastro = new Intent(this, CadastroActivity.class);
        startActivity(activity_cadastro);
    }

    public void open_login_activity(){

        Intent activity_login = new Intent(this, LoginActivity.class);
        startActivity(activity_login);
    }
}
