package com.desafio.desafio_sti.Classe;

import java.time.LocalDate;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;
import net.minidev.json.JSONObject;

@Data
@Document(collation = "Conversas")
public class Mensagem {
    
    @Id
    @MongoId
    private long id;

    private LocalDate data;
    private String texto;
    private String statuts;

    public Mensagem() {
        this.data = null; 
        this.texto = "";
        this.statuts = "";
    }

    public Mensagem(LocalDate data, String texto, String statuts) {
        this.data = data;
        this.texto = texto;
        this.statuts = statuts;
    }

    public Mensagem(String newdata, String statuts, String texto) {
        
        this.data = LocalDate.parse(newdata);
        this.texto = texto;
        this.statuts = statuts;
    }

    public Mensagem(JSONObject json) {
        LocalDate datanova;
        String textonovo;
        String statutsnovo;
        long idnovo;

        datanova = LocalDate.parse(json.getAsString("Data"));
        textonovo = json.getAsString("Texto");
        statutsnovo = json.getAsString("Status");
        idnovo = Long.parseLong(json.getAsString("id"));

        this.data = datanova;
        this.texto = textonovo;
        this.statuts = statutsnovo;
        this.id = idnovo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMensagem() {
        return texto;
    }

    public void setMensagem(String texto) {
        this.texto = texto;
    }

    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }   

    public void setId(long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Status: "+this.statuts+"| Data: "+this.data+"| Texto: "+this.texto;
    }

    public JSONObject tojson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("Data", this.data.toString());
        json.put("Status", this.statuts);
        json.put("Texto", this.texto);
        return json;
    }

}
