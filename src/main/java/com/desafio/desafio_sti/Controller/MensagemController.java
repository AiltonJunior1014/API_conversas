package com.desafio.desafio_sti.Controller;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.desafio.desafio_sti.Classe.Mensagem;
import com.desafio.desafio_sti.Documents.MensagemDocument;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import net.minidev.json.JSONObject;

@RequestMapping
@SpringBootApplication
@RestController
public class MensagemController {

        @GetMapping(path = "/buscartodasmensagens")
        public List<JSONObject> buscarTodos() {
                List<JSONObject> mensagem;
                MensagemDocument mensagemDocument = new MensagemDocument();
                mensagem = mensagemDocument.buscarTodos();
                return mensagem;
        }

        @GetMapping(path = "/buscarmensagem")
        public JSONObject buscarUm(@RequestBody JSONObject filtro) {
                JSONObject mensagem = new JSONObject();
                MensagemDocument mensagemDocument = new MensagemDocument();
                if (filtro != null) {
                        mensagem = mensagemDocument.buscarUm(filtro);
                }
                return mensagem;
        }

        @PostMapping(path = "/inserirmensagem")
        public void inserir(@RequestBody JSONObject mensagemJSON) {
                MensagemDocument mensagemDocument = new MensagemDocument();
                Mensagem mensagem = new Mensagem(mensagemJSON.getAsString("data"), mensagemJSON.getAsString("texto"),
                                mensagemJSON.getAsString("status"));
                mensagemDocument.inserirUm(mensagem);
        }

        @PostMapping(path = "/updatemensagem")
        public void atualizar(@RequestBody JSONObject mensagemJSON) {
                MensagemDocument mensagemDocument = new MensagemDocument();
                JSONObject json = new JSONObject(), mensagemexiste;
                Mensagem mensagem;
                if( mensagemJSON.getAsNumber("id") != null){
                        json.put("id", mensagemJSON.getAsNumber("id"));
                        mensagemexiste = mensagemDocument.buscarUm(json);
                        if(mensagemexiste != null){
                                mensagem = new Mensagem(mensagemexiste);
                                if(mensagemJSON.getAsString("Data") == null){
                                        mensagemJSON.put("Data",mensagem.getData().toString());
                                }
                                if(mensagemJSON.getAsString("Texto") == null){
                                        mensagemJSON.put("Texto",mensagem.getMensagem());
                                }
                                if(mensagemJSON.getAsString("Status") == null){
                                        mensagemJSON.put("Status",mensagem.getStatuts());
                                }
                                mensagemDocument.atualizar(mensagem.tojson(),mensagemJSON);
                        }
                }
                


        }

        @PostMapping(path = "/inserirviaarquivo")
        public void inserirviaarquivo(@RequestBody MultipartFile  arquivo) {
                List<String[]>  linhas;
                CSVReader csvReader;
                Reader reader;
                List<Mensagem> mensagem = new ArrayList<Mensagem>();
                MensagemDocument mensagemDocument = new MensagemDocument();
                try{
                        Files.copy(arquivo.getInputStream(), Paths.get("uploads"+arquivo.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
                        reader = Files.newBufferedReader(Paths.get("uploads"+arquivo.getOriginalFilename()));
                        csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
                        linhas = csvReader.readAll();

                        for(String[] line : linhas) {
                                mensagem.add(new Mensagem(line[0],line[1],line[2]));
                        }

                        mensagemDocument.inserirVarios(mensagem);
                                                
                        Files.delete(Paths.get("uploads"+arquivo.getOriginalFilename()));

                }catch(Exception e){
                        System.out.println(e);
                }
        }

        @PostMapping(path = "/deletemensagem")
        public void delete(@RequestBody JSONObject mensagemJSON) {
                MensagemDocument mensagemDocument = new MensagemDocument();
                if(mensagemJSON != null){
                        mensagemDocument.excluirUm(mensagemJSON);
                }
                
        }
}
