package com.jatimnet.jatimapp.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jatimnet.jatimapp.dominio.entidade.Acesso;

import java.util.ArrayList;
import java.util.List;

public class AcessoRepositorio extends AbstractRepositorio<AcessoRepositorio> {

    private SQLiteDatabase conexao;
    private static final String tabelaNome = "ACESSO";

    public AcessoRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void Salvar(Acesso entidade){

        ContentValues contentValues = new ContentValues();
        contentValues.put("DESCRICAO", entidade.descricao);
        contentValues.put("IP", entidade.ip);
        contentValues.put("USUARIO", entidade.usuario);
        contentValues.put("SENHA", entidade.senha);

        if(entidade.codigo == 0) {
            conexao.insertOrThrow(tabelaNome, null, contentValues);
        }
        else {
            String[] parametros = new String[1];
            parametros[0] = String.valueOf(entidade.codigo);
            conexao.update(tabelaNome, contentValues, "CODIGO = ?", parametros);
        }
    }

    public void Deletar(int codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);
        conexao.delete(tabelaNome, "CODIGO = ?", parametros);
    }

    public List<Acesso> BuscarTodos(){

        List<Acesso> acessos = new ArrayList<Acesso>();

        StringBuilder query = new StringBuilder();
        query.append(" SELECT CODIGO, DESCRICAO, IP, USUARIO, SENHA ");
        query.append("      FROM ACESSO ");

        Cursor resultado = conexao.rawQuery(query.toString(), null);
        if( resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                Acesso acesso    = new Acesso();
                acesso.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                acesso.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
                acesso.ip        = resultado.getString(resultado.getColumnIndexOrThrow("IP"));
                acesso.usuario   = resultado.getString(resultado.getColumnIndexOrThrow("USUARIO"));
                acesso.senha     = resultado.getString(resultado.getColumnIndexOrThrow("SENHA"));

                acessos.add(acesso);

            }while (resultado.moveToNext());
        }

        return acessos;
    }

    public Acesso BuscarPorCodigo(int codigo){

        StringBuilder query = new StringBuilder();
        query.append(" SELECT CODIGO, DESCRICAO, IP, USUARIO, SENHA ");
        query.append("      FROM ACESSO ");
        query.append(" WHERE CODIGO = ? ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(query.toString(), parametros);
        if( resultado.getCount() > 0){
            resultado.moveToFirst();

            Acesso acesso    = new Acesso();
            acesso.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            acesso.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
            acesso.ip        = resultado.getString(resultado.getColumnIndexOrThrow("IP"));
            acesso.usuario   = resultado.getString(resultado.getColumnIndexOrThrow("USUARIO"));
            acesso.senha     = resultado.getString(resultado.getColumnIndexOrThrow("SENHA"));

            return acesso;
        }
        return null;
    }
}
