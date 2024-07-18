package br.com.giulianabezerra.demospringboot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;




@SpringBootApplication
public class DemoSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringbootApplication.class, args);
	}

}

@Entity
class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    String nome;
    String email;
    String senha;
    @Enumerated(EnumType.STRING)
    Papel papel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    enum Papel {
        CLIENTE, ATENDENTE, ADMINISTRADOR
    }
}

@RestController
@RequestMapping("/usuario")
class UsuarioController {
    UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable("id") Long id) {
        return usuarioService.getUsuario(id).orElse(null);
    }
}

@Service
class UsuarioService {
    UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> getUsuario(Long id) {
        return usuarioRepository.findById(id);
    }
}

interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
@Entity
class Categoria implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    String nome;
    String descricao;

    // Getters and setters

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

@RestController
@RequestMapping("/categoria")
class CategoriaController {

    @Autowired
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/{id}")
    public Categoria getCategoria(@PathVariable("id") Long id) {
        return categoriaService.getCategoria(id).orElse(null);
    }
}

@Service
class CategoriaService {
    CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<Categoria> getCategoria(Long id) {
        return categoriaRepository.findById(id);
    }
}

interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}


@Entity
@Table(name = "modeloresposta")
 class ModeloResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String conteudo;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
 interface ModeloRespostaRepository extends JpaRepository<ModeloResposta, Long> {
}


@Service
 class ModeloRespostaService implements Serializable {
    private final ModeloRespostaRepository modeloRespostaRepository;

    public ModeloRespostaService(ModeloRespostaRepository modeloRespostaRepository) {
        this.modeloRespostaRepository = modeloRespostaRepository;
    }

    public Optional<ModeloResposta> getModeloResposta(Long id) {
        return modeloRespostaRepository.findById(id);
    }

    public List<ModeloResposta> getAllModelosResposta() {
        return modeloRespostaRepository.findAll();
    }

    public ModeloResposta createModeloResposta(ModeloResposta modeloResposta) {
        return modeloRespostaRepository.save(modeloResposta);
    }

    public ModeloResposta updateModeloResposta(Long id, ModeloResposta modeloResposta) {
        return modeloRespostaRepository.findById(id)
                .map(existingModeloResposta -> {
                    existingModeloResposta.setNome(modeloResposta.getNome());
                    existingModeloResposta.setConteudo(modeloResposta.getConteudo());
                    return modeloRespostaRepository.save(existingModeloResposta);
                })
                .orElseGet(() -> {
                    modeloResposta.setId(id);
                    return modeloRespostaRepository.save(modeloResposta);
                });
    }

    public void deleteModeloResposta(Long id) {
        modeloRespostaRepository.deleteById(id);
    }
}


@RestController
@RequestMapping("/modeloResposta")
 class ModeloRespostaController {
    private final ModeloRespostaService modeloRespostaService;

    public ModeloRespostaController(ModeloRespostaService modeloRespostaService) {
        this.modeloRespostaService = modeloRespostaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResposta> getModeloResposta(@PathVariable("id") Long id) {
        Optional<ModeloResposta> modeloResposta = modeloRespostaService.getModeloResposta(id);
        if (modeloResposta.isPresent()) {
            System.out.println("ModeloResposta encontrado: " + modeloResposta.get());
            return ResponseEntity.ok(modeloResposta.get());
        } else {
            System.out.println("ModeloResposta com ID " + id + " não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}


@Entity
@Table(name = "ticket")
 class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataResolucao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private Usuario atendente;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getAtendente() {
        return atendente;
    }

    public void setAtendente(Usuario atendente) {
        this.atendente = atendente;
    }

    public enum Status {
        ABERTO, EM_ANDAMENTO, RESOLVIDO, FECHADO
    }

    public enum Prioridade {
        BAIXA, MEDIA, ALTA
    }
}


 interface TicketRepository extends JpaRepository<Ticket, Long> {
}


@Service
 class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> getTicket(Long id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, Ticket ticket) {
        return ticketRepository.findById(id)
                .map(existingTicket -> {
                    existingTicket.setTitulo(ticket.getTitulo());
                    existingTicket.setDescricao(ticket.getDescricao());
                    existingTicket.setStatus(ticket.getStatus());
                    existingTicket.setPrioridade(ticket.getPrioridade());
                    existingTicket.setDataResolucao(ticket.getDataResolucao());
                    existingTicket.setCliente(ticket.getCliente());
                    existingTicket.setAtendente(ticket.getAtendente());
                    return ticketRepository.save(existingTicket);
                })
                .orElseGet(() -> {
                    ticket.setId(id);
                    return ticketRepository.save(ticket);
                });
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}



@RestController
@RequestMapping("/tickets")
 class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable("id") Long id) {
        Optional<Ticket> ticket = ticketService.getTicket(id);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("id") Long id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}



@Component
 class DataInitializer implements CommandLineRunner {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ticketRepository.count() == 0) {
            Usuario cliente = new Usuario();
            cliente.setNome("Cliente Exemplo");
            usuarioRepository.save(cliente);

            Usuario atendente = new Usuario();
            atendente.setNome("Atendente Exemplo");
            usuarioRepository.save(atendente);

            Ticket ticket = new Ticket();
            ticket.setTitulo("Problema de Exemplo");
            ticket.setDescricao("Descrição do problema de exemplo.");
            ticket.setStatus(Ticket.Status.ABERTO);
            ticket.setPrioridade(Ticket.Prioridade.MEDIA);
            ticket.setDataCriacao(LocalDateTime.now());
            ticket.setCliente(cliente);
            ticket.setAtendente(atendente);
            ticketRepository.save(ticket);
            System.out.println("Ticket inicializado com sucesso!");
        }
    }
}
