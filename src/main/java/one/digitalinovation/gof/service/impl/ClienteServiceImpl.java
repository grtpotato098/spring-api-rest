package one.digitalinovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import one.digitalinovation.gof.service.ClienteService;
import one.digitalinovation.gof.service.ViaCepService;
import one.model.Cliente;
import one.model.ClienteRepository;
import one.model.Endereco;
import one.model.EnderecoRepository;

public class ClienteServiceImpl implements ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;


    public Iterable<Cliente> buscarTodos(){

        return clienteRepository.findAll();
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Cliente buscarPorId(Long id) {
        // TODO Auto-generated method stub

        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }


    @Override
    public void deletar(Long id) {
        // TODO Auto-generated method stub
        clienteRepository.deleteById(id);
    }

    @Override
    public void inserir(Cliente cliente) {
        // TODO Auto-generated method stub
        salvarClienteComCep(cliente);
        
    }

    private void salvarClienteComCep(Cliente cliente) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(cliente);
	}

}
