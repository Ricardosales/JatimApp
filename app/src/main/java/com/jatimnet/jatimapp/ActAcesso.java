package com.jatimnet.jatimapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.jatimnet.jatimapp.dataBase.DadosOpenHelper;
import com.jatimnet.jatimapp.dominio.entidade.Acesso;
import com.jatimnet.jatimapp.dominio.repositorio.AcessoRepositorio;

public class ActAcesso extends AppCompatActivity {

    private ConstraintLayout layoutContentMenuCadACesso;
    private SQLiteDatabase conexao;
    private SQLiteOpenHelper dadosOpenHelper;
    private AcessoRepositorio acessoRepositorio;
    private Acesso acesso;

    private EditText edtDescricao;
    private EditText edtIp;
    private EditText edtUsuario;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acesso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        edtIp = (EditText) findViewById(R.id.edtIp);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        layoutContentMenuCadACesso = (ConstraintLayout) findViewById(R.id.layoutContentMenuCadACesso);

        CriarConexao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_acesso, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void confirmar(){
        acesso = new Acesso();

        if(validaCampos()){
            try {

                acessoRepositorio.Salvar(acesso);
                finish();

            }catch (SQLException ex){

                MostrarMensagem(String.valueOf(R.string.lbl_erro), ex.getMessage());
            };

        }
    }

    private boolean validaCampos(){

        boolean res = false;

        String descricao = edtDescricao.getText().toString();
        String ip        = edtIp.getText().toString();
        String usuario   = edtUsuario.getText().toString();
        String senha     = edtSenha.getText().toString();

        acesso.descricao = descricao;
        acesso.ip = ip;
        acesso.usuario = usuario;
        acesso.senha = senha;

        if(res = isCampoVazio(descricao)){
            edtDescricao.requestFocus();
        }
        else
            if(res = isCampoVazio(ip)){
                edtIp.requestFocus();
            }
            else
                if(res = isCampoVazio(usuario)){
                    edtUsuario.requestFocus();
                }
                else
                    if(res = isCampoVazio(senha)){
                        edtSenha.requestFocus();
                    }

        if(res){
            MostrarMensagem(String.valueOf(R.string.lbl_aviso), String.valueOf(R.string.lbl_mensagem_campos_obrigatorios));
        }

        return !res;
    }

    private boolean isCampoVazio(String campo){
        boolean resultado = (TextUtils.isEmpty(campo)) || campo.trim().isEmpty();
        return resultado;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_ok:
                confirmar();
                break;
            case R.id.action_cancelar:
                Toast.makeText(this, R.string.lbl_cancelar, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CriarConexao(){
        try {

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();

            acessoRepositorio = new AcessoRepositorio(conexao);

        }catch (SQLException ex){
            MostrarMensagem(String.valueOf(R.string.lbl_erro), ex.getMessage());
        }
    }

    private void MostrarMensagem(String title, String msg){

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton(R.string.lbl_ok, null);
        dlg.show();
    }
}
