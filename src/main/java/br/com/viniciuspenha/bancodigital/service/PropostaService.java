package br.com.viniciuspenha.bancodigital.service;

import br.com.viniciuspenha.bancodigital.exception.NotFoundException;
import br.com.viniciuspenha.bancodigital.exception.UnprocessableEntity;
import br.com.viniciuspenha.bancodigital.model.db.Cliente;
import br.com.viniciuspenha.bancodigital.model.db.Proposta;
import br.com.viniciuspenha.bancodigital.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PropostaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropostaService.class);

    private final PropostaRepository propostaRepository;
    private final ClienteService clienteService;

    public PropostaService(PropostaRepository propostaRepository, ClienteService clienteService) {
        this.propostaRepository = propostaRepository;
        this.clienteService = clienteService;
    }

    public void criaProposta(Long clienteId, boolean aceite) throws NotFoundException, UnprocessableEntity {
        LOGGER.info("PropostaService.criaProposta - Verificando...");
        Cliente cliente = clienteService.getClienteValidoComFotoDoCPF(clienteId);
        Proposta proposta = this.validaSeClienteJaTemProposta(clienteId);
        if (proposta != null) {
            this.atualizaProposta(proposta, aceite);
            return;
        }
        LOGGER.info("PropostaService.criaProposta - Criando proposta...");
        propostaRepository.save(new Proposta(cliente.getId(), aceite));
        LOGGER.info("PropostaService.criaProposta - Proposta criada");
    }

    private void atualizaProposta(Proposta proposta, boolean aceite) {
        LOGGER.info("PropostaService.criaProposta - Atualizando proposta...");
        proposta.setAceite(aceite);
        proposta.setDataAtualizacao(LocalDateTime.now());
        propostaRepository.save(proposta);
        LOGGER.info("PropostaService.criaProposta - Proposta atualizada");
    }

    private Proposta validaSeClienteJaTemProposta(Long clienteId) {
        Optional<Proposta> proposta = propostaRepository.findByClienteId(clienteId);
        return proposta.orElse(null);
    }
}