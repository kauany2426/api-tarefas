package com.kauany.apitarefas.controller;

import com.kauany.apitarefas.model.Tarefa;
import com.kauany.apitarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    //Metodo post
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        // salva a tarefa no banco de dados
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        // Retorna com o status da tarefa criada
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);

    }

    // Listar todas as tarefas
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        return ResponseEntity.ok(tarefas);
    }

    // METODO GET - BUSCAR UMA TAREFA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {

        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        return tarefa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        // Verifca se a tarefa já existe
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);

        if (tarefaExistente.isPresent()) {
            // Atualiza os dados da tarefa
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setNome(tarefaAtualizada.getNome());
            tarefa.setDataEntrega(tarefaAtualizada.getDataEntrega());
            tarefa.setResponsavel(tarefaAtualizada.getResponsavel());

            // salva
            Tarefa tarefaSalva = tarefaRepository.save(tarefa);
            // Retorna 200
            return ResponseEntity.ok(tarefaSalva);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
            // Verifica se a tarefa existe
            if (tarefaRepository.existsById(id)) {
                // Deleta a tarefa
                tarefaRepository.deleteById(id);

                // Retorna 204 (No Content) - sucesso sem conteúdo
                return ResponseEntity.noContent().build();
            } else {
                // Retorna 404 (Not Found) se não encontrou
                return ResponseEntity.notFound().build();
            }

        }
    }



