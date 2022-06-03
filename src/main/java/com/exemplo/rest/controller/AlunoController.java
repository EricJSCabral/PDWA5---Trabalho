package com.exemplo.rest.controller;

import com.exemplo.rest.model.Aluno;
import com.exemplo.rest.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @PostMapping(path = "/aluno")
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody Aluno aluno){
        Aluno aluno1 = alunoRepository.save(aluno);
        aluno1.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class)
                .consultarAluno(aluno1.getId())).withSelfRel());
        return new ResponseEntity<Aluno>(aluno1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/aluno/{id}")
    public ResponseEntity<Aluno> consultarAluno(@PathVariable("id") Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        aluno.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class)
                .consultarTodosAlunos()).withRel("Lista de Alunos"));
        return new ResponseEntity<Aluno>(aluno.get(), HttpStatus.OK);
    }

    @GetMapping(path = "/aluno")
    public ResponseEntity<List<Aluno>> consultarTodosAlunos(){
        List<Aluno> aluno = alunoRepository.findAll();
        for (Aluno value : aluno) {
            Long id = value.getId();
            value.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class)
                    .consultarAluno(id)).withSelfRel());
        }
        return new ResponseEntity<List<Aluno>>(aluno, HttpStatus.OK);
    }

    @DeleteMapping(path = "/aluno/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluno(@PathVariable("id") Long id){
        alunoRepository.deleteById(id);
    }

    @PutMapping(path = "/aluno")
    public ResponseEntity<Aluno> atualizarAluno(@RequestBody Aluno aluno){
        Aluno aluno1 =  alunoRepository.save(aluno);
        aluno1.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AlunoController.class)
                .consultarAluno(aluno1.getId())).withSelfRel());
        return new ResponseEntity<Aluno>(aluno1, HttpStatus.OK);
    }
}
