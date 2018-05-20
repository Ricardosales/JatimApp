package com.jatimnet.jatimapp;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jatimnet.jatimapp.dataBase.DadosOpenHelper;
import com.jatimnet.jatimapp.dominio.repositorio.AcessoRepositorio;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView lstAcesso;
    private ConstraintLayout layoutContentMenu;
    private SQLiteDatabase conexao;
    private SQLiteOpenHelper dadosOpenHelper;
    private AcessoAdapter acessoAdapter;
    private AcessoRepositorio acessoRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        lstAcesso = (RecyclerView)findViewById(R.id.lstAcesso);
        layoutContentMenu = (ConstraintLayout) findViewById(R.id.layoutContentMain);

        CriarConexao();

        lstAcesso.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lstAcesso.setLayoutManager(layoutManager);

        acessoRepositorio = new AcessoRepositorio(conexao);
        acessoAdapter = new AcessoAdapter(acessoRepositorio.BuscarTodos());

        lstAcesso.setAdapter(acessoAdapter);
    }

    public void AbrirCadastroAcesso(View view){
        Intent it = new Intent(MainActivity.this, ActAcesso.class);
        startActivity(it);
    }

    private void CriarConexao(){
        try {

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentMenu, R.string.lbl_criado_com_sucesso, Snackbar.LENGTH_SHORT)
                .setAction(R.string.lbl_ok, null)
                .show();

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.lbl_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.lbl_ok, null);
            dlg.show();
        }
    }

}
