package one.digitalinovation.gof.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import one.model.Endereco;

public interface ViaCepService {

    @GetMapping("/{cep}/json/")
	Endereco consultarCep(@PathVariable("cep") String cep);

}
