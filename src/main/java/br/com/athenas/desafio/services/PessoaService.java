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

    public Page<Pessoa> findAll(final Pageable pageable) {
        if (pageable == null) {
            throw new NullPointerException("m=PessoaService.findAll pageable is null");
        }
        return this.pessoaRepository.findAll(pageable);
    }
    
    public Page<Pessoa> findPersonsByName(Pageable pageable, String nome) {
        return this.pessoaRepository.findPersonsByName(pageable, nome);
    }

    public Pessoa getPessoa(final Long id) {
        if (id == null) {
            throw new NullPointerException("m=PessoaService.getPessoa id is null");
        }
        return this.pessoaRepository.findById(id).orElseThrow(null);
    }
    

    public Pessoa savePessoa(final Pessoa pessoa) {
        if (pessoa == null) {
            throw new NullPointerException("m=PessoaService.savePessoa pessoa is null");
        }
        return this.pessoaRepository.save(pessoa);
    }

    public Pessoa updatePessoa(final Long id, final  Pessoa pessoa) {
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

    public void deletePessoa(final Long id) {
        if (id == null) {
            throw new NullPointerException("m=PessoaService.deletePessoa id is null");
        }
        this.pessoaRepository.deleteById(id);
    }

    public String calcPesoIdeal(final Long id) {
        final Pessoa pessoa = this.getPessoa(id);
        Double peso;
        if (pessoa.getSexo().equals('M')) {
            peso = (72.7 * pessoa.getAltura()) - 58;
        } else {
            peso = (62.1 * pessoa.getAltura()) - 44.7;
        }
        return "Peso ideal " + peso;
    }

}
