package com.jatimnet.jatimapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jatimnet.jatimapp.dominio.entidade.Acesso;

import java.util.List;

public class AcessoAdapter extends RecyclerView.Adapter<AcessoAdapter.ViewHolderAcesso> {

    private List<Acesso> dados;

    public AcessoAdapter(List<Acesso> dados){
        this.dados = dados;
    }

    @Override
    public AcessoAdapter.ViewHolderAcesso onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.lista_acesso, parent, false);

        ViewHolderAcesso holderAcesso = new ViewHolderAcesso(view);

        return holderAcesso;
    }

    @Override
    public void onBindViewHolder(@NonNull AcessoAdapter.ViewHolderAcesso holder, int position) {

        if((dados != null) && (dados.size() > 0)) {
            Acesso acesso = dados.get(position);

            holder.txtDescricao.setText(acesso.descricao);
            holder.txtIp.setText(acesso.ip);
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderAcesso extends RecyclerView.ViewHolder{

        private TextView txtDescricao;
        private TextView txtIp;

        public ViewHolderAcesso(View itemView) {
            super(itemView);

            txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricao);
            txtIp        = (TextView) itemView.findViewById(R.id.txtIp);
        }
    }
}
