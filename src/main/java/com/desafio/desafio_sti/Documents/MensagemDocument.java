package com.desafio.desafio_sti.Documents;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.desafio.desafio_sti.Classe.Mensagem;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import net.minidev.json.JSONObject;

public class MensagemDocument {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;


    public MensagemDocument(){
        mongoClient = MongoClients.create();
        db = mongoClient.getDatabase("Desafio_sti");
        collection = db.getCollection("Conversas");
    }

    public Mensagem inserirUm(Mensagem mensagem){
        JSONObject json = new JSONObject();
        json = mensagem.tojson();
        json.put("_id",collection.countDocuments()+1);
        this.collection.insertOne(Document.parse(json.toString()));
        return mensagem;
    }

    public List<Mensagem> inserirVarios(List<Mensagem> mensagem){
        List <Document> docs = new ArrayList<Document>();
        long index = 0;
        index = collection.countDocuments();
        for (Mensagem m : mensagem) {
            m.setId(++index);
            docs.add(Document.parse(m.tojson().toJSONString()));
        }
        collection.insertMany(docs);
        return mensagem;
     }

    
    public JSONObject buscarUm(JSONObject id){
        FindIterable<Document> readDocument = null;
        readDocument = collection.find(Document.parse(id.toJSONString()));
        Document doc = readDocument.first();
        JSONObject json;
        json =  new JSONObject(doc);
        return json;
    }

    public JSONObject atualizar(JSONObject mensagemexiste ,JSONObject mensagem){
        JSONObject json;
        UpdateResult doc;
        json =  new JSONObject();
        doc = collection.replaceOne( Document.parse(mensagemexiste.toJSONString()), Document.parse(mensagem.toJSONString()));
        System.out.println(doc.toString());
        return json;
    }

    public List<JSONObject> buscarTodos(){
        FindIterable<Document> mensagens;
        JSONObject jsonaux;
        List<JSONObject> json= new ArrayList<JSONObject>();
        mensagens = collection.find();
        jsonaux= new JSONObject();
        for(Document doc :mensagens){
            jsonaux.put("id",doc.get("id"));
            jsonaux.put("Data",doc.get("Data"));
            jsonaux.put("Status",doc.get("Status"));
            jsonaux.put("Texto",doc.get("Texto"));
            json.add(jsonaux);
        }       
        return json;
     }

    public void excluirUm(JSONObject id){
        FindIterable<Document> readDocument = null;
        JSONObject json;
        readDocument = collection.find(Document.parse(id.toJSONString()));
        Document doc = readDocument.first();
        json = new JSONObject(doc);
        if(json != null)
            collection.deleteOne(Document.parse(id.toJSONString()));
        json = new JSONObject(doc);
    }
}
