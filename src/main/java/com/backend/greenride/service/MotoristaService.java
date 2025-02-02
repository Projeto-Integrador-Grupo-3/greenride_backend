package com.backend.greenride.service;

import com.backend.greenride.model.Motorista;
import com.backend.greenride.model.MotoristaLogin;
import com.backend.greenride.repository.MotoristaRepository;
import com.backend.greenride.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Motorista> cadastrarMotorista(Motorista motorista) {

        if (motoristaRepository.findByMotorista(motorista.getMotorista()).isPresent())
            return Optional.empty();

        motorista.setSenha(criptografarSenha(motorista.getSenha()));

        return Optional.of(motoristaRepository.save(motorista));

    }

    public Optional<Motorista> atualizarMotorista(Motorista motorista) {

        if (motoristaRepository.findById(motorista.getId()).isPresent()) {

            Optional<Motorista> buscaMotorista = motoristaRepository.findByMotorista(motorista.getMotorista());

            if ((buscaMotorista.isPresent()) && (buscaMotorista.get().getId() != motorista.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            motorista.setSenha(criptografarSenha(motorista.getSenha()));

            return Optional.ofNullable(motoristaRepository.save(motorista));

        }

        return Optional.empty();

    }

    public Optional<MotoristaLogin> autenticarMotorista(Optional<MotoristaLogin> motoristaLogin) {

        var credenciais = new UsernamePasswordAuthenticationToken(motoristaLogin.get().getMotorista(),
                motoristaLogin.get().getSenha());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        if (authentication.isAuthenticated()) {

            Optional<Motorista> motorista = motoristaRepository.findByMotorista(motoristaLogin.get().getMotorista());

            if (motorista.isPresent()) {

                motoristaLogin.get().setId(motorista.get().getId());
                motoristaLogin.get().setNome(motorista.get().getNome());
                motoristaLogin.get().setFoto(motorista.get().getFoto());
                motoristaLogin.get().setToken(gerarToken(motoristaLogin.get().getMotorista()));
                motoristaLogin.get().setSenha("");

                return motoristaLogin;

            }

        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private String gerarToken(String motorista) {
        return "Bearer " + jwtService.generateToken(motorista);
    }

}
