package com.backend.greenride.controller;

import java.util.List;
import java.util.Optional;

import com.backend.greenride.model.MotoristaLogin;
import com.backend.greenride.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.backend.greenride.model.Motorista;
import com.backend.greenride.repository.MotoristaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/motorista")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MotoristaController {

	@Autowired
	public MotoristaRepository motoristaRepository;

	@Autowired
	public MotoristaService motoristaService;

	@GetMapping("/all")
	public ResponseEntity<List<Motorista>> getAll() {
		return ResponseEntity.ok(motoristaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Motorista> getById(@PathVariable Long id) {
		return motoristaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Motorista>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(motoristaRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Motorista> post(@Valid @RequestBody Motorista motorista) {
		return ResponseEntity.status(HttpStatus.CREATED).body(motoristaRepository.save(motorista));
	}

	@PostMapping("/logar")
	public ResponseEntity<MotoristaLogin> autenticarMotorista(@RequestBody Optional<MotoristaLogin> motoristaLogin){

		return motoristaService.autenticarMotorista(motoristaLogin)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Motorista> postMostorista(@RequestBody @Valid Motorista motorista) {

		return motoristaService.cadastrarMotorista(motorista)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@PutMapping("/atualizar")
	public ResponseEntity<Motorista> putMotorista(@Valid @RequestBody Motorista motorista) {

		return motoristaService.atualizarMotorista(motorista)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@PutMapping
	public ResponseEntity<Motorista> put(@Valid @RequestBody Motorista motorista) {
		return motoristaRepository.findById(motorista.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(motoristaRepository.save(motorista)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Motorista> motorista = motoristaRepository.findById(id);
		if (motorista.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		motoristaRepository.deleteById(id);
	}
}
