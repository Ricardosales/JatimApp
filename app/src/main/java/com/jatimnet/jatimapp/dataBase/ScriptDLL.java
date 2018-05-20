package com.jatimnet.jatimapp.dataBase;

public class ScriptDLL {

    public static String getCreateTableAcesso(){

        StringBuilder query = new StringBuilder();

        query.append(" CREATE TABLE IF NOT EXISTS ACESSO ( ");
        query.append("      CODIGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        query.append("      DESCRICAO VARCHAR (255) NOT NULL DEFAULT (''), ");
        query.append("      IP VARCHAR (30) NOT NULL DEFAULT (''), ");
        query.append("      USUARIO VARCHAR (255) NOT NULL DEFAULT (''), ");
        query.append("      SENHA VARCHAR (255) NOT NULL DEFAULT ('') ");
        query.append("  ) ");

        return query.toString();
    }

}
