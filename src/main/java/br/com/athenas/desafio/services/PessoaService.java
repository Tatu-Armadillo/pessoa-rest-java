package br.com.athenas.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.repositories.PessoaRepository;
import jakarta.transaction.Transactional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(final PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Page<Pessoa> findAll(Pageable pageable) {
        return this.pessoaRepository.findAll(pageable);
    }
    
    public Page<Pessoa> findPersonsByName(Pageable pageable, String nome) {
        return this.pessoaRepository.findPersonsByName(pageable, nome);
    }

    public Pessoa getPessoa(Long id) {
        return this.pessoaRepository.findById(id).orElseThrow(null);
    }
    

    public Pessoa savePessoa(Pessoa pessoa) {
        return this.pessoaRepository.save(pessoa);
    }

    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
        Pessoa update = this.getPessoa(id);
        update.setNome(update.getNome().equals(pessoa.getNome()) ? update.getNome() : pessoa.getNome());
        update.setDataNasc(update.getDataNasc().equals(pessoa.getDataNasc()) ? update.getDataNasc() : pessoa.getDataNasc());
        update.setAltura(update.getAltura().equals(pessoa.getAltura()) ? update.getAltura() : pessoa.getAltura());
        update.setPeso(update.getPeso().equals(pessoa.getPeso()) ? update.getPeso() : pessoa.getPeso());
        update.setSexo(update.getSexo().equals(pessoa.getSexo()) ? update.getSexo() : pessoa.getSexo());
        return update;
    }

    @Transactional
    public Pessoa updatedPeso(Long id, Double peso) {
        this.pessoaRepository.updatedPeso(id, peso);
        return this.getPessoa(id);
    }

    public void deletePessoa(Long id) {
        this.pessoaRepository.deleteById(id);
    }

    public String calcPesoIdeal(Long id) {
        Pessoa pessoa = this.getPessoa(id);
        Double peso = 0.0;
        if (pessoa.getSexo().equals('M')) {
            peso = (72.7 * pessoa.getAltura()) - 58;
        } else {
            peso = (62.1 * pessoa.getAltura()) - 44.7;
        }
        return "Peso ideal " + peso;
    }

}
